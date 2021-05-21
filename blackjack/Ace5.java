package blackjack;

//import org.graalvm.compiler.nodes.IfNode; //?o que e isto quem meteu isto aqui

class Ace5 implements BetStrategy
{
    private int count;
    private int minBet;
    private int maxBet;
    private int startBet;
    private int lastBet;

    Ace5(int _minBet, int maxMulti) { //já não me lembro se se pode apostar só inteiros ou não
        minBet = _minBet;
        maxBet = _minBet*maxMulti; //*aqui recomendava fazer o multiplicador a 16x. Nao faz sentido ser 32x pq a maxBet nao pode ser mais que 20xminBet nas regras do jogo
    }

    @Override
    public int Advice(Player player, Hand playerHand, Card dealerCard) {
        int suggest = 0;

        if(player.game.round == 0) //para saber se ja fez alguma aposta ou nao
            lastBet = startBet;
        else
            lastBet = player.game.last_bet; //!preciso mesmo de saber qual foi a ultima aposta do jogador

        if (count >= 2) {
            suggest = 2*lastBet;
            if (suggest > maxBet)
                suggest = maxBet;
        } else if (count < 2) 
            suggest = minBet;       
        
        return suggest;
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
        startBet = minBet;
        count = 0;
    }
}