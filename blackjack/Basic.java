package blackjack;
//import java.util.*;
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

public class Basic implements PlayStrategy
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
    public char Advice(Player player, Hand playerHand, Card dealerCard)
    {     
        ////playerHand.getFirst();
        ////playerHand.size();
        ////playerHand.get(1);
        /*Ace - 1
        two...Ten - 2...10
        Jacks - 11
        Queen - 12
        King - 13*/

        //1. Confirmar se há Ases
        //2. Se sim verificar se o Ás pode ser 11 sem a soma passar 21, se sim é um SOFT, se não e HARD
        //3. Se não há Ases é um HARD
        //3. Se os Ases forem todos 1 é HARD
        //4. Se houver um par é PAIR
        
        int HandSum = playerHand.handSum(); //soma da mao do jogador
        int nonAceSum = 0;
        int tableSelector = 0;
        char suggest = '\0';
        int aceCount = 0;
        int aceOf_1 = 0;

        if ((playerHand.getNumCards() == 2) && (playerHand.get(0) == playerHand.get(1))) { //opening hand
                tableSelector = 0; // pair        
        } else { //after hitting, more than 2 cards or the 2 initial cards are not the same
            //* When you have more than two cards, hands with aces where at least one Ace
            //* values 11 refer to "Soft table". Hands without aces or where all aces value 1
            //* refer to "Hard table".

            for (int i = 0; i < playerHand.getNumCards(); i++) { //verificar quantos ases tem para saber se eles valem 1 ou 11
                if (playerHand.get(i) == 1) aceCount++;
                else nonAceSum += playerHand.getCard(i).getValue();

                if (nonAceSum+aceCount+(aceCount*10) > 21) {
                    aceOf_1++;
                    nonAceSum -= 10; //tirar pq agora vale 1         
                }
            }

            if (!(playerHand.hasAce()) || (aceCount-aceOf_1) == 0) { //ou nao tem ases ou todos os que tem valem 1 (aceCount-aceOf_1 = nmr ases 11/todos sao 1)
                tableSelector = 2;  //hard  
            } else if (aceCount != 0 && aceCount != aceOf_1) { //tem 1 Ace que vale 11
                tableSelector = 1; //soft
            }                
        }

        switch (tableSelector) {
            case 1: //*select from the soft table
                suggest = soft[HandSum - 13][dealerCard.getValue() - 2];
                break;

            case 2: //*select from the hard table
                suggest = hard[HandSum - 5][dealerCard.getValue() - 2];
                break;
        
            default: //*select from the pair table
                suggest = pair[playerHand.get(0) - 2][dealerCard.getValue() - 2];
                break;
        }

        return suggest;
    }
}

