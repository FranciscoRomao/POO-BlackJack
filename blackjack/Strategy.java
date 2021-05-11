package blackjack;
import java.util.*;

public interface Strategy
{
    public int Advice(LinkedList<Card> playerHand, Card dealerCard);
}






