package nl.hu.bep2.casino.blackjack.presention.controller;


import nl.hu.bep2.casino.blackjack.application.BlackJackService;
import nl.hu.bep2.casino.blackjack.application.GameData;
import nl.hu.bep2.casino.blackjack.domain.exception.InvalidMoveException;
import nl.hu.bep2.casino.blackjack.presention.Dto.Bet;
import nl.hu.bep2.casino.chips.domain.exception.NotEnoughChipsException;
import nl.hu.bep2.casino.security.domain.UserProfile;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/blackjack")
public class BlackjackController {
    private BlackJackService service;

    public BlackjackController(BlackJackService service) {
        this.service = service;
    }

    @PostMapping("/game")
    public GameData startGame(Authentication authentication, @Validated @RequestBody Bet bet) {
        UserProfile profile = (UserProfile) authentication.getPrincipal();
        String username = profile.getUsername();

        try {
            return this.service.startGame(username, bet.amount , bet.totaal);
        } catch (InvalidMoveException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (NotEnoughChipsException e) {
            throw new NotEnoughChipsException(String.format("Cannot withdraw %d chips: %d chips remaining", bet.amount));
        }
    }

    @PostMapping("/game/{id}/hit")
    public GameData hit(Authentication authentication, @PathVariable Long id) {

        try {
            String username = parseUsername(authentication);
            return this.service.hit(username, id);
        } catch (InvalidMoveException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PostMapping("/game/{id}/double_down")
    public GameData double_down(Authentication authentication, @PathVariable Long id) {

        try {
            String username = parseUsername(authentication);
            return this.service.doubleDown(username, id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/game/{id}/stand")
    public GameData stand(Authentication authentication, @PathVariable Long id) {

        try {
            String username = parseUsername(authentication);
            return this.service.stand(username, id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/game/{id}/surrender")
    public GameData surrender(Authentication authentication, @PathVariable Long id){

        try {
            String username = parseUsername(authentication);
            return this.service.surrender(username, id);
        }catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            }
    }

    private String parseUsername(Authentication authentication) {
        UserProfile profile = (UserProfile) authentication.getPrincipal();
        return profile.getUsername();
    }
}
