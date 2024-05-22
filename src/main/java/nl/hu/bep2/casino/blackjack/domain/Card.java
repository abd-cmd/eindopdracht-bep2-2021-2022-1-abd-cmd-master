package nl.hu.bep2.casino.blackjack.domain;

import java.io.Serializable;
import java.util.Objects;

public class Card implements Serializable {

    private final Suit suit;
    private final Rank rank;

    public Card(Rank rank, Suit suit) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Card card = (Card) other;
        return suit == card.suit && rank == card.rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, rank);
    }

    public int calculateValue() {
        return this.rank.getValue();
    }

    public String toString(){
        return this.suit.toString() + "-" + this.rank.toString();
    }
}
