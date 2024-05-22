package nl.hu.bep2.casino.blackjack.domain;

import nl.hu.bep2.casino.blackjack.data.CardsListConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Deck implements Serializable {

    @Convert(converter  = CardsListConverter.class)
    @Column(length = 2000)
    private List<Card> cards = new ArrayList<Card>();
    @Id
    @GeneratedValue
    private Long id;

    public List<Card> getCards() {
        return cards;
    }

    public Card draw(){
        return this.cards.remove(0);
    }

    public static Deck full(){
        Deck deck = new Deck();

        for (Rank rank : Rank.values()){
            for (Suit suit : Suit.values()){
                deck.cards.add(new Card(rank,suit));
            }
        }

        return deck;
    }

    public void shuffle(){
        Collections.shuffle(cards);
    }

    public String toString(){
       String cardlistOutput = "";
       int i = 0;
       for (Card card : this.cards){
          cardlistOutput += "\n" + i + "-" + card.toString();
          i ++;
       }

       return cardlistOutput;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static Deck AddFullDeck(int totaal){
        Deck deck = new Deck();

        for (int i = 0 ; i < totaal ; i++ ) {
            for (Rank rank : Rank.values()) {
                for (Suit suit : Suit.values()) {
                    deck.cards.add(new Card(rank, suit));
                }
            }
        }

        return deck;
    }
}
