package blackjack.strategies;

import blackjack.*;
import blackjack.deck.*;

public class HiLo
{
	private int count;
	private char suggest;

	public HiLo() { //?acho que este construtor nao e preciso....
		count = 0;
	}

    
	/** 
	 * @param game
	 * @param print
	 * @param state
	 */
	public void advice(Game game, boolean print, int state)
    {
		Hand playerHand = game.getPlayer().getHands().get(game.getPlayer().handNumber);
		
		float trueCount = getTrueCount(game.getPlayer().game.getDealer().getShoe()); 		
		suggest = bestAction(playerHand, game.getDealer().getHand().getCard(0), trueCount, game.getPlayer().insuranceCheck(false), state);
		
		if(print)
			System.out.println(this);
    }

	/**
	 * Chamar cada vez que uma carta é mostrada no jogo, nao sei onde isso e feito
	 */
	public void Count(Card card) {
		int cardValue = card.getValue();
		if(cardValue >= 2 && cardValue <=6) count++;
		else if (cardValue >= 10) count--;
	}	

	
	/** 
	 * @param shoe
	 * @return float
	 */
	public float getTrueCount(Shoe shoe) {
		float remainDecks = shoe.getNumCards()/52;

		return count/remainDecks;

		//return (float)(Math.round(_trueCount/0.5) * 0.5); //*no slack a prof disse para mandarmos o float mesmo, e acho que aqui era (int) n float xD tavamos a dormir
	}

	
	/** 
	 * @param playerHand
	 * @param dealerCard
	 * @param trueCount
	 * @param insure
	 * @param state
	 * @return char
	 */
	public char bestAction (Hand playerHand, Card dealerCard, float trueCount, boolean insure, int state) { 
		int handSum = playerHand.handSum(); 
		String dealerRank = dealerCard.showRank();
		boolean pair10 = false;
		char suggesT;

		if ((playerHand.getNumCards() == 2) && playerHand.getCard(0).showRank() == "10" && playerHand.getCard(1).showRank() == "10") //check if opening hand is a pair of 10s
                pair10 = true;      
        
		if (insure && state == 2 && trueCount >= 3) {
			suggesT = 'I';
			return suggesT;
		}

		//Illustrious18 //!falta o insurance
		suggesT = (handSum==16 && dealerRank.equals("10")) ? ((trueCount >= 0) ? 'S' : 'H' ):
				  (pair10 && dealerRank.equals("5")) ? ((trueCount >= 5) ? 'P' : 'S' ):
				  (pair10 && dealerRank.equals("6")) ? ((trueCount >= 4) ? 'P' : 'S' ):
				  (handSum==10 && dealerRank.equals("10")) ? ((trueCount >= 4) ? 'D' : 'H' ):
				  (handSum==12 && dealerRank.equals("3")) ? ((trueCount >= 2) ? 'S' : 'H' ):
				  (handSum==12 && dealerRank.equals("2")) ? ((trueCount >= 3) ? 'S' : 'H' ):
				  (handSum==11 && dealerRank.equals("A")) ? ((trueCount >= 1) ? 'D' : 'H' ):
				  (handSum==9 && dealerRank.equals("2")) ? ((trueCount >= 1) ? 'D' : 'H' ):
				  (handSum==10 && dealerRank.equals("A")) ? ((trueCount >= 4) ? 'D' : 'H' ):
				  (handSum==9 && dealerRank.equals("7")) ? ((trueCount >= 3) ? 'D' : 'H' ):
				  (handSum==16 && dealerRank.equals("9")) ? ((trueCount >= 5) ? 'S' : 'H' ):
				  (handSum==13 && dealerRank.equals("2")) ? ((trueCount >= -1) ? 'S' : 'H' ):
				  (handSum==12 && dealerRank.equals("4")) ? ((trueCount >= 0) ? 'S' : 'H' ):
				  (handSum==12 && dealerRank.equals("5")) ? ((trueCount >= -2) ? 'S' : 'H' ):
				  (handSum==12 && dealerRank.equals("6")) ? ((trueCount >= -1) ? 'S' : 'H' ):
				  (handSum==13 && dealerRank.equals("3")) ? ((trueCount >= -2) ? 'S' : 'H' ): 'B';

	  	//Fab4
		suggesT = (handSum==14 && dealerRank.equals("10")) ? ((trueCount >= 3) ? 'R' : 'B' ):
				  (handSum==15 && dealerRank.equals("9")) ? ((trueCount >= 2) ? 'R' : 'B' ):  
				  (handSum==15 && dealerRank.equals("a")) ? ((trueCount >= 1) ? 'R' : 'B' ): suggesT;
		//overlap
		if (handSum == 15 && dealerRank.equals("10")) {
			if (trueCount >= 0 && trueCount <= 3) {
				suggesT = 'R';
			} else if (trueCount >= 4) {
				suggesT = 'S';
			} else if (trueCount < 0) {
				suggesT = 'H';
			}		
		}
		return suggesT;
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

	
	/** 
	 * @param player
	 * @param state
	 * @return String
	 */
	public String simAction(Player player, int state) {
		String action;

		switch (suggest) {
			case 'H':
				action = "h";
				break;
			case 'S':
				action = "s";
				break;
			case 'P':
				if(player.splitCheck() && state == 2){
					action = "p";
					break;
				}
				action = "BASIC";
				break;
			case 'D':
				if(player.doubleCheck(false) && state == 2){
					action = "2";
					break;
				}
				action = "BASIC";
				break;
			case 'I': 
				action = "i";
				break;
			case 'R':
				if(state == 2){
					action = "u";
					break;
				}
				action = "BASIC";
				break;
			default: //follow basic, case not found in the HiLo strat
				action = "BASIC";
				break;
		}

		return action;
	}
	
}
