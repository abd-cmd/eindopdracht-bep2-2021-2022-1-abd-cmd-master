package nl.hu.bep2.casino.blackjack.application;

import nl.hu.bep2.casino.blackjack.domain.Deck;

public class DeckFactory {
    private int totaal;

    public DeckFactory(int totaal) {
        this.totaal = totaal;
    }

    public Deck create(){
        return Deck.AddFullDeck(totaal);
    }
}
