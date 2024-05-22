package nl.hu.bep2.casino.blackjack.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {
    @Test
    void calculatesScoreForMultipleAces() {
        Hand hand = new Hand();
        hand.add(new Card(Rank.ACE, Suit.DIAMONDS));
        hand.add(new Card(Rank.FOUR, Suit.DIAMONDS));
        hand.add(new Card(Rank.ACE, Suit.DIAMONDS));

        int score = hand.getScore();

        assertEquals(16, score);
    }
}