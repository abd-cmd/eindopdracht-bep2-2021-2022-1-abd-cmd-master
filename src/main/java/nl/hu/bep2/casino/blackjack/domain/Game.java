package nl.hu.bep2.casino.blackjack.domain;


import nl.hu.bep2.casino.blackjack.application.DeckFactory;
import nl.hu.bep2.casino.blackjack.domain.exception.InvalidMoveException;

import javax.persistence.*;

@Entity
public class Game {
    @Id
    @GeneratedValue
    private long id;
    private String username;
    private GameStatus gameStatus = GameStatus.WAITING;
    private Long payout;
    private Long bet;
    @OneToOne(cascade = CascadeType.ALL)
    private Hand playerHand;
    @OneToOne(cascade = CascadeType.ALL)
    private Hand dealerHand;
    @OneToOne(cascade = CascadeType.ALL)
    private Deck deck;


    public void start(String username, Long bet , int totaal) throws InvalidMoveException{
        if (gameStatus != GameStatus.WAITING) {
            throw new InvalidMoveException("Move is not allowed, game has already been started.");
        }

        this.gameStatus = GameStatus.PLAYING;

        this.username = username;
        this.bet = bet;

        this.deck = new DeckFactory(totaal).create();

        this.deck.shuffle();

        this.playerHand = new Hand();
        this.dealerHand = new Hand();

        this.playerHand.add(this.deck.draw());
        this.playerHand.add(this.deck.draw());
        this.dealerHand.add(this.deck.draw());

        if (playerHand.getScore() == 21) {
            this.gameStatus = GameStatus.BLACKJACK;
            this.payout = bet * 5;

            this.dealerHand.add(this.deck.draw());

            if (dealerHand.getScore() == 21) {
                this.gameStatus = GameStatus.PUSH;
                this.payout = bet;
            }
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getBet() {
        return bet;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Hand getDealerHand() {
        return dealerHand;
    }

    public Hand getPlayerHand() {
        return playerHand;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public Long getPayout() {
        return payout;
    }

    public void hit() throws InvalidMoveException {
        if (this.hasEnded()) {
            throw new InvalidMoveException("Move is not allowed, game is not playing.");
        }

        playerHand.add(deck.draw());

        if (playerHand.getScore() > 21) {
            gameStatus = GameStatus.BUST;
            payout = 0L;
        }
    }

    public void doubleDown() throws InvalidMoveException {
        if (this.hasEnded()) {
            throw new InvalidMoveException("Move is not allowed, game is not playing.");
        }

        if (playerHand.size() > 2) {
            throw new InvalidMoveException("Double is only possible with two cards");
        }

        bet = bet * 2;
        hit();

        if (!this.hasEnded()) {
            stand();
        }
    }

    public void stand() throws InvalidMoveException {
        if (this.hasEnded()) {
            throw new InvalidMoveException("Move is not allowed, game is not playing.");
        }

        dealerHand.add(deck.draw());
        while (dealerHand.getScore() < 17) {
            dealerHand.add(deck.draw());
        }

        int playerScore = playerHand.getScore();
        int dealerScore = dealerHand.getScore();

        if (dealerScore > 21) {
            gameStatus = GameStatus.WIN;
            payout = bet * 2;
        } else if (playerScore > dealerScore) {
            gameStatus = GameStatus.WIN;
            payout = bet * 2;
        } else if (playerScore == dealerScore) {
            gameStatus = GameStatus.PUSH;
            payout = bet;
        } else {
            gameStatus = GameStatus.LOSE;
            payout = 0L;
        }
    }

    public void surrender() throws InvalidMoveException {
        if (this.hasEnded()) {
            throw new InvalidMoveException("Move is not allowed, game is not playing.");
        }

        gameStatus = GameStatus.SURRENDER;
        payout = bet / 2;
    }

    public boolean hasEnded() {
        return this.gameStatus != GameStatus.PLAYING
            && this.gameStatus != GameStatus.WAITING;
    }
}