package blackjack;
import java.util.*;

public interface Strategy
{
    //*acho que aqui precisamos de dois metodos diferentes pq os metodos de aposta retornam um valor mas os de jogada um char
    public char playAdvice(Player player, Hand playerHand, Card dealerCard);    
    public int betAdvice(Player player, Hand playerHand, Card dealerCard);

}






