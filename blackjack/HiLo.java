package blackjack;

//import java.util.LinkedList;

public class HiLo
{
	private int count;
	private char suggest;

	public HiLo() { //?acho que este construtor nao e preciso....
		count = 0;
	}

    public void Advice(Game game, boolean print)
    {
		Hand playerHand = game.player.hands.get(game.player.handNumber);
        //Count(dealerCard); 	//?nao sei onde vao parar as cartas que sao mostradas
							//*isto é uma coisa que é para se chamar toda a vez que o dealer mostra uma carta tens que me explicar onde se faz isso Zé
							//*ah ok ja percebi e so chamar o metodo onde for e o count atualiza
		
		float trueCount = getTrueCount(game.player.game.dealer.shoe); 		
		
		suggest = bestAction(playerHand, game.dealer.hand.getCard(0), trueCount);
		
		if(print)
			System.out.println(this);
    }

	/**
	 * Chamar cada vez que uma carta é mostrada no jogo, nao sei onde isso e feito
	 */
	public void Count(Card card) {
		int cardValue = card.getValue();
		if(cardValue >= 2 && cardValue <=6) count++;
		else if (cardValue >= 10) count--; //?verificar isto aqui. o As vale 11 portanto nao fazia sentido fazer (cardValue==11 || cardValue==10) certo?
	}	

	public float getTrueCount(Shoe shoe) {
		float remainDecks = shoe.getNumCards()/52;

		return count/remainDecks;

		//return (float)(Math.round(_trueCount/0.5) * 0.5); //*no slack a prof disse para mandarmos o float mesmo, e acho que aqui era (int) n float xD tavamos a dormir
	}

	public char bestAction (Hand playerHand, Card dealerCard, float trueCount) { //podia calcular o trueValue aqui dentro mesmo e tinha que mandar o player aqui para 
																				 //dentro tambem, é o que preferirem
		//float trueCount = getTrueCount(player.game.dealer.shoe);

		int HandSum = playerHand.handSum(); 
		String dealerRank = dealerCard.showRank();
		boolean pair10 = false;
		char _suggest = '\u0000';

		if ((playerHand.getNumCards() == 2) && playerHand.getCard(0).showRank() == "10" && playerHand.getCard(1).showRank() == "10") //check if opening hand is a pair of 10s
                pair10 = true;      
        
		//Illustrious18 //!falta o insurance
		_suggest = (HandSum==16 && dealerRank.equals("10")) ? ((trueCount >= 0) ? 'S' : 'H' ):
				   (pair10 && dealerRank.equals("5")) ? ((trueCount >= 5) ? 'P' : 'S' ):
				   (pair10 && dealerRank.equals("6")) ? ((trueCount >= 4) ? 'P' : 'S' ):
				   (HandSum==10 && dealerRank.equals("10")) ? ((trueCount >= 4) ? 'D' : 'H' ):
				   (HandSum==12 && dealerRank.equals("3")) ? ((trueCount >= 2) ? 'S' : 'H' ):
				   (HandSum==12 && dealerRank.equals("2")) ? ((trueCount >= 3) ? 'S' : 'H' ):
				   (HandSum==11 && dealerRank.equals("A")) ? ((trueCount >= 1) ? 'D' : 'H' ):
				   (HandSum==9 && dealerRank.equals("2")) ? ((trueCount >= 1) ? 'D' : 'H' ):
				   (HandSum==10 && dealerRank.equals("A")) ? ((trueCount >= 4) ? 'D' : 'H' ):
				   (HandSum==9 && dealerRank.equals("7")) ? ((trueCount >= 3) ? 'D' : 'H' ):
				   (HandSum==16 && dealerRank.equals("9")) ? ((trueCount >= 5) ? 'S' : 'H' ):
				   (HandSum==13 && dealerRank.equals("2")) ? ((trueCount >= -1) ? 'S' : 'H' ):
				   (HandSum==12 && dealerRank.equals("4")) ? ((trueCount >= 0) ? 'S' : 'H' ):
				   (HandSum==12 && dealerRank.equals("5")) ? ((trueCount >= -2) ? 'S' : 'H' ):
				   (HandSum==12 && dealerRank.equals("6")) ? ((trueCount >= -1) ? 'S' : 'H' ):
   				   (HandSum==13 && dealerRank.equals("3")) ? ((trueCount >= -2) ? 'S' : 'H' ): 'B';

	  	//Fab4
		_suggest = (HandSum==14 && dealerRank.equals("10")) ? ((trueCount >= 3) ? 'R' : 'B' ):
				   (HandSum==15 && dealerRank.equals("9")) ? ((trueCount >= 2) ? 'R' : 'B' ):  
				   (HandSum==15 && dealerRank.equals("a")) ? ((trueCount >= 1) ? 'R' : 'B' ): _suggest;
		//overlap
		if (HandSum == 15 && dealerRank.equals("10")) {
			if (trueCount >= 0 && trueCount <= 3) {
				_suggest = 'R';
			} else if (trueCount >= 4) {
				_suggest = 'S';
			} else if (trueCount < 0) {
				_suggest = 'H';
			}		
		}

		return _suggest;
	}

	/**
	 * To reset the running count at the beggining of each deck/shoe
	 */
	public void resetCount() {
		count = 0;
	}

	/**
	 * Nao sei para que isto vai servir mas fica aqui seclahar da jeito no futuro
	 */
	public int getRunningCount() {
		return count;
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
				advice = "double";
				break;
			case 'I': //insurance ainda nao esta feito
				advice = "insure";
				break;
			case 'R':
				advice = "surrender";
				break;
			default: //follow basic, case not found in the HiLo strat
				break;
		}

		return "hi-lo\t\t" + advice;
	}

	public String simAction(Player player, int state) {
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
			case 'I': //insurance ainda nao esta feito
				action = "i";
				break;
			case 'R':
				action = "u";
				break;
			default: //follow basic, case not found in the HiLo strat
				action = "BASIC";
				break;
		}

		return action;
	}
	
}
