package nl.hu.bep2.casino.blackjack.application;

import nl.hu.bep2.casino.blackjack.data.GameRepository;
import nl.hu.bep2.casino.blackjack.domain.Game;
import nl.hu.bep2.casino.blackjack.domain.exception.InvalidMoveException;
import nl.hu.bep2.casino.chips.application.ChipsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class BlackJackService {
    private ChipsService chipsService;
    private GameRepository gameRepository;

    public BlackJackService(ChipsService chipsService, GameRepository gameRepository) {
        this.chipsService = chipsService;
        this.gameRepository = gameRepository;
    }

    public GameData startGame(String username, Long bet , Integer totaal) throws InvalidMoveException {
        Game game = new Game();

        this.chipsService.withdrawChips(username, bet);

        game.start(username, bet ,totaal );

        // chips storten???
        if (game.hasEnded()){
            this.chipsService.depositChips(username, game.getPayout());
        }

        this.gameRepository.save(game);

        //return a result
        return createGameData(game);
    }

    public GameData hit(String username, Long id) throws InvalidMoveException {
        Game game = this.gameRepository.findGameByUsernameAndId(username, id);
        if (game == null) {
            throw new GameNotFoundException();
        }

        this.chipsService.withdrawChips(username,game.getBet());

        game.hit();

        if (game.hasEnded()) {
            chipsService.depositChips(username, game.getPayout());
        }

        this.gameRepository.save(game);

        return createGameData(game);
    }


    public GameData doubleDown(String username, Long id) throws InvalidMoveException {
        Game game = this.gameRepository.findGameByUsernameAndId(username, id);
        if (game == null) {
            throw new GameNotFoundException();
        }

        // chips service -> withdraw bet
        this.chipsService.withdrawChips(username,game.getBet());

        game.doubleDown();

        if (game.hasEnded()) {
            chipsService.depositChips(username, game.getPayout());
        }

        this.gameRepository.save(game);

        return createGameData(game);
    }

    public GameData stand(String username, Long id) throws InvalidMoveException {
        Game game = this.gameRepository.findGameByUsernameAndId(username, id);
        if (game == null) {
            throw new GameNotFoundException();
        }

        this.chipsService.withdrawChips(username,game.getBet());

        game.stand();

        if (game.hasEnded()) {
            chipsService.depositChips(username, game.getPayout());
        }

        this.gameRepository.save(game);

        return createGameData(game);
    }

    public GameData surrender(String username, Long id) throws InvalidMoveException {
        Game game = this.gameRepository.findGameByUsernameAndId(username, id);
        if (game == null) {
            throw new GameNotFoundException();
        }

        this.chipsService.withdrawChips(username,game.getBet());

        game.surrender();

        if (game.hasEnded()) {
            chipsService.depositChips(username, game.getPayout());
        }

        this.gameRepository.save(game);

        return createGameData(game);
    }

    private GameData createGameData(Game game) {
        return new GameData(
                game.getUsername(), game.getBet(), game.getPlayerHand(),
                game.getDealerHand(), game.getId(), game.getGameStatus(), game.getPayout());
    }
}
