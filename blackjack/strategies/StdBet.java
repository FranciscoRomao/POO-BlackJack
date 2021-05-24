package blackjack.strategies;

import blackjack.Game;

/**
 * Class that implements the Standard Bet strategy to help on bet amounts
 * Its associated to the player object
 * A player as a reference to this type of object
 */
public class StdBet {
    private int bet;

    /**
     * Method that computes the next bet amount. When called prints to the player
     * this amount
     * 
     * @param game  Reference to the game Object
     * @param print Variable that dictactes if the bet suggestion should be printed
     *              or not
     */
    public void advice(Game game, boolean print)
    {       
        if (game.getRound() == 0) { // para saber se ja fez alguma aposta ou nao
            bet = game.getMinBet();
        } else {
            bet = increaseDecrease(game.getPlayer().roundOutcome, game.getPlayer().lastBet, game.getMinBet(), game.getMaxBet());
        }

        if (print)
            System.out.println(this);
    }

    
    /**
     * Method that decides wheter the player's next bet amount should be the last
     * bet increased, decreased or maintained. This decision is done according to
     * the last round outcome (win, lose or push, respectively). Increases/decreases
     * by amounts equal to minimum bet up to maximum bet.
     * 
     * @param roundOutcome Variable that says if the last round was won, lost or pushed
     * @param lastBet      Player last bet
     * @param minBet       Game minimum bet
     * @param maxBet       Game maximum bet
     * @return Integer that indicates the recommended bet amount from this strategy
     */
    public int increaseDecrease(int roundOutcome, int lastBet, int minBet, int maxBet) {
        int newBet = 0;

        if (roundOutcome == 1) { // win
            newBet = lastBet + minBet;
            if (newBet > maxBet)
                newBet = maxBet;
        } else if (roundOutcome == 0) { // push
            newBet = lastBet;
        } else { // lost
            newBet = lastBet - minBet;
            if (newBet < minBet)
                newBet = minBet;
        }

        return newBet;
    }

    /**
     * Method Override to make the print of the advice of this strategy
     * 
     * @return String to be printed by the print and println methods
     */
    @Override
    public String toString() {
        return "std-bet\t\tbet " + bet;
    }

    
    /**
     * Method that returns the next bet move with an amount 
     * Used in the simulation mode to get the next bet move automatically from this strategy
     * 
     * @return String: command "b " + bet amount
     */
    public String simAction() {
        return "b " + bet;
    }
}
