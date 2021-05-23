package blackjack;

public class Basic //implements Strategy
{   
    private char suggest;

    private char[][] hard = {{ 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H'}, //linha extra (quando temos 22 e nao pode fazer split e um hard 4)
                             { 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H'}, 
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

    private char[][] soft = {{ 'H', 'H', 'H', 'D', 'D', 'H', 'H', 'H', 'H' ,'H'}, //linha extra (quando temos AA e nao pode fazer mais split e um soft 12)
                             { 'H', 'H', 'H', 'D', 'D', 'H', 'H', 'H', 'H' ,'H'},
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

    
    public void advice(Game game, boolean print, boolean split)
    {             
        Hand playerHand = game.player.hands.get(game.player.handNumber);
        Card dealerCard = game.dealer.hand.getCard(0);
        int handSum = playerHand.handSum(); //soma da mao do jogador
        int tableSelector = 0;
        
        int aceCount = playerHand.hasAce();
        int aceOf1 = playerHand.getAceOf1();

        if ((playerHand.getNumCards() == 2) && (playerHand.get(0) == playerHand.get(1))) { //opening hand
                tableSelector = 0; // pair        
        } else { //after hitting, more than 2 cards or the 2 initial cards are not the same
            //* When you have more than two cards, hands with aces where at least one Ace
            //* values 11 refer to "Soft table". Hands without aces or where all aces value 1
            //* refer to "Hard table".

            if (aceCount == 0 || aceOf1 == aceCount) { //ou nao tem ases ou todos os que tem valem 1
                tableSelector = 2;  //hard  
            } else if (aceCount != 0 && (aceCount - aceOf1) == 1) { //tem 1 Ace que vale 11 (antes tava >0)
                tableSelector = 1; //soft
            }                
        }

        switch (tableSelector) {
            case 1: //*select from the soft table
                suggest = soft[handSum - 12][dealerCard.getValue() - 2];
                break;

            case 2: //*select from the hard table
                suggest = hard[handSum - 4][dealerCard.getValue() - 2];
                break;
        
            default: //*select from the pair table
                if (split) {
                    suggest = pair[playerHand.getCard(0).getValue() - 2][dealerCard.getValue() - 2];                    
                } else {
                    if (handSum == 12) {
                        suggest = soft[handSum - 12][dealerCard.getValue() - 2];
                    } else {
                        suggest = hard[handSum - 4][dealerCard.getValue() - 2];
                    }
                }
                break;                                                                           
        }                                                                                        
        if(print)
            System.out.println(this);
    } 

    
    /**
     * Estou a fazer isto para que seja so preciso fazer print(hilo/basic)
     */
    @Override
    public String toString() {
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

