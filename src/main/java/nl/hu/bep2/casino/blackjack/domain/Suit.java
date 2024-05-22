package nl.hu.bep2.casino.blackjack.domain;

public enum Suit {

    HEARTS(" Hearts"),
    SPADES(" Spades"),
    DIAMONDS(" Diamonds"),
    CLUBS(" Clubs");

    private final String suitText;

    Suit(String suitText) {
        this.suitText = suitText;
    }

    public String PrintSuitText() {
        return suitText;
    }
}
