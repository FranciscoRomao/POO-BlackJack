package blackjack;
import java.util.*;

public interface PlayStrategy{
    //*acho que aqui precisamos de dois metodos diferentes pq os metodos de aposta retornam um valor mas os de jogada um char
    public char Advice(Player player, Hand playerHand, Card dealerCard);    
}






