package blackjack.strategies;

import blackjack.Game;

public class StdBet {
    private int bet;

    
    /** 
     * @param game
     * @param print
     */
    public void advice(Game game, boolean print) // mandar vir para aqui o minBet, maxBet e Bet = lastBet
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
     * @param roundOutcome
     * @param lastBet
     * @param minBet
     * @param maxBet
     * @return int
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
     * Estou a fazer isto para que seja so preciso fazer print(hilo/basic)
     */
    @Override
    public String toString() {
        return "std-bet\t\tbet " + bet;
    }

    
    /** 
     * @return String
     */
    public String simAction() {
        return "b " + bet;
    }
}
