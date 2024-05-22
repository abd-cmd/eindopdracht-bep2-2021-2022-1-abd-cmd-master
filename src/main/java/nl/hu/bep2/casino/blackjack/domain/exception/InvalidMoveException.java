package nl.hu.bep2.casino.blackjack.domain.exception;

public class InvalidMoveException extends Exception {
    public InvalidMoveException(String message) {
        super(message);
    }
}

