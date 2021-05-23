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

public class Basic //implements Strategy
{   
    private char suggest;

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

    
    public void Advice(Game game, boolean print)
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
        
        Hand playerHand = game.player.hands.get(game.player.handNumber);
        Card dealerCard = game.dealer.hand.getCard(0);
        int HandSum = playerHand.handSum(); //soma da mao do jogador
        int nonAceSum = 0;
        int tableSelector = 0;
        
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

            if (playerHand.hasAce() == 0 || (aceCount-aceOf_1) == 0) { //ou nao tem ases ou todos os que tem valem 1 (aceCount-aceOf_1 = nmr ases 11/todos sao 1)
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
                suggest = pair[playerHand.getCard(0).getValue() - 2][dealerCard.getValue() - 2]; //*antes estava playerHand.get(0) - 2 mas assim so tinha em conta o rank 
                break;                                                                           //*e os K, J e Q nao estavam incluidos na tabela dos pares
        }                                                                                        //*tambem estava mal feito pq se fosse A,A ia buscar a primeira linha da tabela e nao a ultima
        if(print)
            System.out.println(this);
    } //TODO adicionar duas linjas nas tabelas har e soft pq se o pair for 2,2 ou A,A e nao der para fazer split nao tem para onde ir

    
    /**
     * Estou a fazer isto para que seja so preciso fazer print(hilo/basic)
     */
    @Override
    public String toString() { //?faco isto ou faco no player returnAdvice e assim fica igual para todos e nao repete codigo
        String advice = "--";

        switch (suggest) {
            case 'H':
                advice = "hit";
                break;
            case 'S':
                advice = "stand";
                break;
            case 'P':
                advice = "split";
                break;
            case 'D':
                advice = "double, if impossible hit";
                break;
            case 'd':
                advice = "double, if impossible stand";
                break;
            case 'R':
                advice = "surrender, hit if impossible";
                break;    
            default:
                break;
        }

        return "basic\t\t" + advice;
    }

    /**
     * 
     * @param player 
     * @param state indicates which state of the game it's currently on
     * @return action simulated by this strategy
     */
    public String simAction (Player player, int state){
        String action = "--";

        switch (suggest) {
            case 'H':
                action = "h";
                break;
            case 'S':
                action = "s";
                break;
            case 'P':
                action = "p";
                break;
            case 'D':
                if(player.doubleCheck() && state == 2){
                    action = "2";
                    break;
                }
                action = "h";
                break;
            case 'd':
                if(player.doubleCheck() && state == 2){
                    action = "2";
                    break;
                }
                action = "s";
                break;
            case 'R':
                if(state == 2){
                    action = "u";
                    break;
                }
                action = "h";
                break;
            default:
            break;
        }

        return action;
    }
}

