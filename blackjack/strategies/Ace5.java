package blackjack.strategies;

import blackjack.*;
import blackjack.deck.*;

public class Ace5 //implements Strategy
{
    private int count;
    private int minBet;
    private int maxBet;
    private int lastBet;
    private int suggest;

    public Ace5(int minBeT, int maxBeT) {
        double aux = (double) maxBeT + 1;
        minBet = minBeT;
        int i = 4;
        while(aux > maxBeT){
            aux = minBeT*Math.pow(2, i); 
            i--;
        }
        maxBet = (int) aux;
        count = 0;
    }

    
    /** 
     * @param game
     * @param print
     */
    public void advice(Game game, boolean print) {
        if(game.getRound() != 0)
            lastBet = game.getPlayer().lastBet;    
        else
            lastBet = minBet;         
        
            suggest = lastBet;

        if (count >= 2) {
            suggest = 2*lastBet;
            if (suggest > maxBet)
                suggest = maxBet;
        } else if (count < 2) 
            suggest = minBet;       
        
        if(print)
            System.out.println(this);
    }

    /**
     * Make Ace5 strategy count and update it depending if the card shown is a 5 or an Ace
     * @param card The card that was just revealed in the game to check its value
     */
    public void ace5Count(Card card) {
        int cardValue = card.getValue();

        if (cardValue == 5) count++;
        else if (cardValue == 11) count--;
    }

    /**
     * To reset the count and the starting bet 
     * Call at the beggining of each deck/shoe
     */
    public void resetCount() {
        lastBet = minBet;
        count = 0;
    }

    /**
     * Estou a fazer isto para que seja so preciso fazer print(hilo/basic)
     */
    @Override
    public String toString() {
        return "ace-five\t\tbet " + suggest;
    }

    
    /** 
     * @return String
     */
    public String simAction() {
        return "b " + suggest;
    }
}