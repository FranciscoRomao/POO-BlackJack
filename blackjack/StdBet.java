package blackjack;

public class StdBet {
    private int startBet;
    private int bet;

    public void Advice(Game game, boolean print) // mandar vir para aqui o minBet, maxBet e Bet = lastBet
    {    
        startBet = game.min_bet;
        if (game.round == 0) { // para saber se ja fez alguma aposta ou nao
            bet = startBet;
            // return startBet;
        } else {
            bet = Increase_Decrease(game.player.roundOutcome, game.player.lastBet, game.min_bet, game.max_bet);

            // return Increase_Decrease(player.roundOutcome, player.lastBet,
            // player.game.min_bet, player.game.max_bet);
        }

        if (print)
            System.out.println(this);
    }

    public int Increase_Decrease(int roundOutcome, int lastBet, int minBet, int maxBet) {
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

    public String simAction() {
        return "b " + bet;
    }
}
