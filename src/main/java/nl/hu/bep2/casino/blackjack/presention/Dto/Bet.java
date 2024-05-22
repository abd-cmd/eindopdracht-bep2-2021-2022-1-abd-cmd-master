package nl.hu.bep2.casino.blackjack.presention.Dto;

import nl.hu.bep2.casino.blackjack.domain.Card;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class Bet {
    @Min(1)
    public  Long amount;
    @Min(1)
    @Max(3)
    public int totaal;
}
