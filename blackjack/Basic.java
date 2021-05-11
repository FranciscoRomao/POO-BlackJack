package blackjack;
import java.util.*;
/**
 * hard – hands without aces, or where all aces value 1, and without an opening deal of a
pair of aces.
• soft – hands with aces where at least one ace values 11 and without an opening deal of a
pair of aces.
• pairs – both cards in the opening deal are of the same value.
The

Dh - D
Ds - d
 */

public class Basic implements Strategy
{
    private char[][] hard = {{ 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H'}, 
                             { 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H'},
                             { 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H'},
                             { 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H'},
                             { 'H', 'D', 'D', 'D', 'D', 'H', 'H', 'H', 'H', 'H'},
                             { 'D', 'D', 'D', 'D', 'D', 'D', 'D', 'D', 'H', 'H'},
                             { 'D', 'D', 'D', 'D', 'D', 'D', 'D', 'D', 'D', 'H'},
                             { 'H', 'H', 'S', 'S', 'S', 'H', 'H', 'H', 'H', 'H'},
                             { 'S', 'S', 'S', 'S', 'S', 'H', 'H', 'H', 'H', 'H'},
                             { 'S', 'S', 'S', 'S', 'S', 'H', 'H', 'H', 'H', 'H'},
                             { 'S', 'S', 'S', 'S', 'S', 'H', 'H', 'H', 'R', 'H'},
                             { 'S', 'S', 'S', 'S', 'S', 'H', 'H', 'R', 'R', 'R'},
                             { 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
                             { 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
                             { 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
                             { 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
                             { 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'}};

    private char[][] soft = {{ 'H', 'H', 'H', 'D', 'D', 'H', 'H', 'H', 'H' ,'H'},
                             { 'H', 'H', 'H', 'D', 'D', 'H', 'H', 'H', 'H' ,'H'},
                             { 'H', 'H', 'D', 'D', 'D', 'H', 'H', 'H', 'H' ,'H'},
                             { 'H', 'H', 'D', 'D', 'D', 'H', 'H', 'H', 'H' ,'H'},
                             { 'H', 'D', 'D', 'D', 'D', 'H', 'H', 'H', 'H' ,'H'},
                             { 'S', 'd', 'd', 'd', 'd', 'S', 'S', 'H', 'H' ,'H'},
                             { 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S' ,'S'},
                             { 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S' ,'S'},
                             { 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S' ,'S'}};
    
    private char[][] pair = {{ 'H', 'H', 'P', 'P', 'P', 'P', 'H', 'H', 'H', 'H'},
                             { 'H', 'H', 'P', 'P', 'P', 'P', 'H', 'H', 'H', 'H'},
                             { 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H'},
                             { 'D', 'D', 'D', 'D', 'D', 'D', 'D', 'D', 'H', 'H'},
                             { 'H', 'P', 'P', 'P', 'P', 'H', 'H', 'H', 'H', 'H'},
                             { 'P', 'P', 'P', 'P', 'P', 'P', 'H', 'H', 'H', 'H'},
                             { 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
                             { 'P', 'P', 'P', 'P', 'P', 'P', 'S', 'P', 'S', 'S'},
                             { 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
                             { 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'}};
    
    @Override
    public int Advice(Hand playerHand, Card dealerCard)
    {
        
        playerHand.get(0).rank;
        playerHand.size();
        playerHand.get(1).rank;
        /*Ace - 0
        One...Ten - 1...10
        Jacks - 11
        Queen - 12
        King - 13*/

        //1. Confirmar se há Ases
        //2. Se sim verificar se o Ás pode ser 11 sem a soma passar 21, se sim é um SOFT, se não e HARD
        //3. Se não há Ases é um HARD
        //3. Se os Ases forem todos 1 é HARD
        //4. Se houver um par é PAIR
        
        int HandSum = playerHand.HandSum(); //soma da mao do jogador

        if (playerHand.size() > 2 && (playerHand.get(0).rank == playerHand.get(1).rank))
        {
            mat = 0; //pair
        } 
        else 
        {
            /*for (int i = 0; i < playerHand.size(); i++) { //verificar se tem às
                sum += playerHand.get(i);
                if (playerHand.get(i).rank == 0)
                {
                    ace=0;
                    
                    if (sum > 21) {mat = 1} //hard
                    else {mat = 2} //soft
                }    
            }*/
            
            if(playerHand.HandhasAce() && playerHand.HandSum()>21)
                mat=1;
            else
                mat=2;
        }
        
        if (mat == 0) {
            char suggest = pair[playerHand.get(0)-2][dealerCard.getValue()-2];
        } else if (mat == 2) {
            char suggest = soft[sum-13][dealerCard.getValue()-2];
        } else { //hard
            char suggest = hard[sum-5][dealerCard.getValue()-2];
        }

        //return suggest
        return 0;
    }
}

