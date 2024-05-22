package nl.hu.bep2.casino.blackjack.data;

import nl.hu.bep2.casino.blackjack.domain.Card;
import nl.hu.bep2.casino.blackjack.domain.Rank;
import nl.hu.bep2.casino.blackjack.domain.Suit;
import org.apache.logging.log4j.util.Strings;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import java.util.ArrayList;
import java.util.List;

@Convert
public class CardsListConverter implements AttributeConverter<List<Card>, String> {
    @Override
    public String convertToDatabaseColumn(List<Card> cards) {
        List<String> cardString = new ArrayList<>();

        for (Card card : cards){
            String encodedCard = card.getSuit() + "," + card.getRank();
            cardString.add(encodedCard);
        }

        return Strings.join(cardString, ';');
    }

    @Override
    public List<Card> convertToEntityAttribute(String data) {
        // CLUBS, FIVE;HEARTS, TEN
        String[] cardStrings = data.split(";");
        List<Card> cards = new ArrayList<>();
        for (String encodedCard : cardStrings){
            String[] parts = encodedCard.split(",");

            Suit suit = Suit.valueOf(parts[0]);
            Rank rank = Rank.valueOf(parts[1]);

            cards.add(new Card(rank, suit));
        }
        return cards;
    }
}
