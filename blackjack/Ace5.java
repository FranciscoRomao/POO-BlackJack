package blackjack;

public class Ace5 //implements Strategy
{
    private int count;
    private int minBet;
    private int maxBet;
    private int lastBet;
    private int suggest;

    Ace5(int _minBet, int _maxBet) { //já não me lembro se se pode apostar só inteiros ou não
        double aux = _maxBet + 1;
        minBet = _minBet;
        int i = 4;
        while(aux > _maxBet){
            aux = _minBet*Math.pow(2, i); //*aqui recomendava fazer o multiplicador a 16x. Nao faz sentido ser 32x pq a maxBet nao pode ser mais que 20xminBet nas regras do jogo
            i--;
        }
        maxBet = (int) aux;
        count = 0;
    }

    public void Advice(Game game, boolean print) {
        if(game.round != 0) //para saber se ja fez alguma aposta ou nao
            lastBet = game.player.lastBet;    
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
    public void Ace5Count(Card card) {
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

    public String simAction() {
        return "b " + suggest;
    }
}