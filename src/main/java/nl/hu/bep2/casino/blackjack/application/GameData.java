package nl.hu.bep2.casino.blackjack.application;

import nl.hu.bep2.casino.blackjack.domain.GameStatus;
import nl.hu.bep2.casino.blackjack.domain.Hand;

public class GameData {

    private Long id;
    private String username;
    private Long bet;
    private Hand playerhand;
    private Hand dealerHand;
    private GameStatus gameStatus;
    private Long payout;

    public GameData(String username, Long bet, Hand playerhand, Hand dealerHand, Long id, GameStatus gameStatus, Long payout) {
        this.username = username;
        this.bet = bet;
        this.playerhand = playerhand;
        this.dealerHand = dealerHand;
        this.id = id;
        this.gameStatus = gameStatus;
        this.payout = payout;
    }

    public Long getPayout() {
        return payout;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Hand getPlayerhand() {
        return playerhand;
    }

    public Hand getDealerHand() {
        return dealerHand;
    }

    public Long getBet() {
        return bet;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
