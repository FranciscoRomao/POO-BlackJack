package blackjack.strategies;

import blackjack.*;
import blackjack.deck.*;

/**
 * Class that implements the Ace-five counting strategy to help the player bet
 * Its associated to the player object
 * A player as a reference to this type of object
 */
public class Ace5 {
    private int count;
    private int minBet;
    private int maxBet;
    private int lastBet;
    private int suggest;

    /**
     * Ace-five strategy constructor
     * @param minBeT Amount that will be the minimum recommended by the strategy
     * @param maxBeT Max amount of bet in the game. Used to calculate the max amount of bet recommended
     */
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
     * Method that computes the next bet amount. When called prints to the player
     * this amount
     * 
     * @param game  Reference to the game Object
     * @param print Variable that dictactes if the bet suggestion should be printed
     *              or not
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
     * @param card The card that was just revealed in the game, check its value
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
     * Method Override to make the print of the advice of this strategy
     * @return String to be printed by the print and println methods
     */
    @Override
    public String toString() {
        return "ace-five\t\tbet " + suggest;
    }

    
    /** 
     * Method that returns the next bet move with an amount
     * Used in the simulation mode to get the next bet move automatically from this strategy
     * @return String: command "b " + bet amount
     */
    public String simAction() {
        return "b " + suggest;
    }
}