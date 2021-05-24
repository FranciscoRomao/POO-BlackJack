package blackjack.strategies;

import blackjack.*;
import blackjack.deck.*;

/**
 * Class that implements the Hi-Lo counting strategy, adviving the player what is the next 
 * best move according to what has already been played. Its associated to the player object.
 * A player as a reference to this type of object
 */
public class HiLo {
	private int count;
	private char suggest;

	public HiLo() {
		count = 0;
	}

    
	/**
	 * Method that advises the player on what should be is best move. The advice
	 * is dictated by 2 sets of rules: Illustrious 18 and Fab 4. These rules check
	 * the player's hand, the dealer hole card and the true count.
	 * 
	 * @param game  Reference to the game Object
	 * @param print Variable that dictactes if the play suggestion should be printed
	 *              or not
	 * @param state Indicates which state of the game it's currently on
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
	 * Method called everytime a new card is shown from the shoe. Checks the card
	 * value. The strategy count is updated according to the face value of the card
	 * 
	 * @param card Reference of the card to be checked
	 */
	public void Count(Card card) {
		int cardValue = card.getValue();
		if(cardValue >= 2 && cardValue <=6) count++;
		else if (cardValue >= 10) count--;
	}	

	/**
	 * Calculates the true count. This is done dividing the count by the number of
	 * remaining decks.
	 * 
	 * @param shoe
	 * @return
	 */
	public float getTrueCount(Shoe shoe) {
		float remainDecks = shoe.getNumCards()/52;

		return count/remainDecks;
	}

	/**
	 * Computes the best action to execute next by checking all the possible cases
	 * that the Illustrious 18 and Fab 4 rules have. It checks the player's hand value,
	 * the dealer hole card and the true count to make this decision. 
	 * 
	 * @param playerHand Reference to the player hand when he asks for advice
	 * @param dealerCard Reference to dealer hole card
	 * @param trueCount  True Coun variable
	 * @param insure     Variable that checks if it is possible to insure
	 * @param state      Indicates which state of the game it's currently on
	 * @return 			 Character that indicates the next best action
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

		//Illustrious18
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
	 * Method to get the current Hi-Lo running count
	 * 
	 * @return Integer of the running count
	 */
	public int getRunningCount() {
		return count;
	}

	/**
	 * Method Override to make the print of the advice of this strategy
	 * 
	 * @return String to be printed by the print and println methods
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
	 * Method that returns the next play move. Used in the simulation mode to get
	 * the next play move automatically from this strategy.
	 * 
	 * @param player Reference to the player Object
	 * @param state  Indicates which state of the game it's currently on
	 * @return Action to be executed
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
