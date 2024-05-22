package nl.hu.bep2.casino.blackjack.domain;

import nl.hu.bep2.casino.blackjack.data.CardsListConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Hand implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Convert(converter = CardsListConverter.class)
    @Column(length = 2000)
    private List<Card> cardList = new ArrayList<Card>();

    public Hand() {}

    public void add(Card card) {
        cardList.add(card);
    }

    public List<Card> getCardList() {
        return cardList;
    }

    public int getScore() {
        int handSum = 0;
        int aces = 0;

        for (Card card : cardList) {
            if (card.getRank() == Rank.ACE) {
                aces++;
            }
            handSum += card.calculateValue();
        }

        while (handSum > 21) {
            if (aces == 1) {
                handSum -= 10;
            } else if (aces > 1) {
                handSum -= 10 * (aces - 1);
            }
        }

        return handSum;
    }

    public int size() {
        return this.cardList.size();
    }
}
