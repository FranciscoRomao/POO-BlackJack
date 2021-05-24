package blackjack;

import java.util.Iterator;

import blackjack.deck.*;
import blackjack.state_pattern.*;;
/**
 * Class that implements the game's dealer
 */
public class Dealer
{   
    protected Game game;
    private Card holeCard;
    protected Shoe shoe;
    protected Hand hand;
    public int nHands;
    /**
     * Dealer constructor for simulation or interactive modes
     * @param game The game that's going to be played
     */
    public Dealer(Game game)
    {      
        this.game = game;
        this.hand = new Hand(0);
        if(game.mode == 'i')
            System.out.println("shuffling the shoe...");
        this.shoe = new Shoe(this.game.shoe);
    }
    /**
     * Dealer construcotr for debug mode
     * @param game Game that's going to be played
     * @param shoe String with the name of the file with the shoe
     */
    public Dealer(Game game, String shoe)
    {
        this.game = game;
        this.hand = new Hand(0);
        this.shoe = new Shoe(shoe);
    }

    /**
     * Implements the hit command. Takes card out of the shoe and adds it to te dealer's hand. It also counts the cards for the hilo and ace5 strategies
     */
    public void hit()
    {
        Card aux;
        aux = shoe.getCard();
        hand.addCard(aux);
        if(game.mode != 's')
            System.out.println("dealer hits");
        game.getPlayer().hilo.Count(aux); 
        game.getPlayer().ace5.ace5Count(aux);
    }

    /**
     * Deals cards to the player and to themselves. It also counts the cards for the hilo and ace5 strategies
     */
    public void dealCards()
    {
        Card aux;
        for(int i=0; i<2; i++)
        {
            aux = shoe.getCard();
            game.player.hands.get(game.player.handNumber).addCard(aux);
            game.player.hilo.Count(aux);
            game.player.ace5.ace5Count(aux);
        }
        aux = shoe.getCard();
        
        holeCard = aux;
        
        aux = shoe.getCard();
        game.player.hilo.Count(aux);
        game.player.ace5.ace5Count(aux);
        hand.addCard(aux);
        if(game.mode != 's'){
            System.out.println(showHand()+"X");
            System.out.println("player's hand "+game.player.hands.get(game.player.handNumber)+"("+game.player.hands.get(game.player.handNumber).handSum()+")");
        }
    }

    /**
     * Checks if the received hand is a bust
     * @param handToCheck Hand to check if busted or not
     * @return int -1 if busted, 1 otherwise
     */
    public int bustCheck(Hand handToCheck)
    {
        if(handToCheck.handSum() > 21)
        {
            if(game.mode != 's')
                System.out.print("player busts ");
            game.player.hands.get(game.player.handNumber).busted = true;

            if(game.player.splitted && game.mode != 's')
                System.out.print("["+(game.player.handNumber + 1)+"]");
            if(game.mode != 's')
                System.out.println();

            if(game.player.handsLeft > 1)
            {
                playOtherHand();
                return -1;   
            }
            bust();
            return -1;
        }
        return 1;
    }

    /**
     * Method that starts playing the next hand, in case of a split
     */
    public void playOtherHand()
    {
        String th = "nd";

        game.player.handsLeft--;
        game.player.handNumber++;
        if((game.player.handNumber+1) == 3)
        {
            th = "rd";
        } else if((game.player.handNumber+1) > 3)
        {
            th = "th";
        }
        if(game.mode != 's')
            System.out.println("playing "+(game.player.handNumber + 1)+th+" hand...");
        game.player.hit(false);
        game.changeState(new SideRulesState());
    }

    /**
     * Method that starts a new round, resetting all necessary variables and checking if shuffle is needed
     */
    public void newRound(){
        game.round++;
        game.player.nHands++;
        game.player.insuranceBet = -1;
        game.player.splitNumber = 0;
        game.player.splitted = false;
        int oldBet = game.player.hands.get(game.player.handNumber).bet;    
        game.player.hands.clear();
        hand = new Hand(0);
        nHands++; //number of dealer hands
        game.player.hands.add(new Hand(oldBet));
        game.player.handNumber = 0;
        game.changeState(new GameStart());
        if(shoe.getPlayedCards() > game.shuffle){
            if(game.mode == 'i')
                System.out.println("shuffling the shoe...");
            shoe = new Shoe(game.shoe);
            game.player.hilo.resetCount();
            game.player.ace5.resetCount();
            game.shuffleNum--;
        }
    }

    /**
     * Shows the dealer's hole card
     */
    public void showHole(){
        hand.addCard(holeCard);
        game.player.hilo.Count(holeCard);
        game.player.ace5.ace5Count(holeCard);
   if(game.mode != 's')
            System.out.println(showHand()+"("+hand.handSum()+")");
    }

    /**
     * Processes what happens when the player busts
     */
    public void bust()
    {
        showHole();
        
        if(hand.hasBlackjack())
        {
            game.dealerBJcount++;
            if(game.mode != 's')
                System.out.println("blackjack!!");
            insuranceCheck();
        }

        printEndScreen(false);
        newRound();
    }

    /**
     * Processes what happens when the player stands
     * @param print True if printing commands is required, false otherwise
     */
    public void stand(boolean print)
    {
        boolean busts = false;
        if(print)
            System.out.println("player stands");

        showHole();

        if(hand.hasBlackjack())
        {
            game.dealerBJcount++;
       if(game.mode != 's'){
                System.out.println("dealer stands");
                System.out.println("blackjack!!");
            }
            insuranceCheck();
        } 
        while(hand.handSum() < 17)
        {
            hit();
       if(game.mode != 's')
                System.out.println(showHand()+"("+hand.handSum()+")");
        }        
        
        if(hand.handSum() > 21)
        {
            busts = true;
       if(game.mode != 's')
                System.out.println("dealer busts");
        }
        else
        {
       if(game.mode != 's')
                System.out.println("dealer stands");
        }                                
        printEndScreen(busts);
        newRound();
    }   

    /**
     * If player insured, proceeds accordingly
     */
    public void insuranceCheck()
    {
        if(hand.hasBlackjack() && game.player.insured())
        {
            game.player.balance += game.player.insuranceBet*2;
            game.player.insuranceBet = -1;
       if(game.mode != 's')
                System.out.println("player wins insurance");
            return;
        }
        if(!hand.hasBlackjack() && game.player.insured() && game.mode != 's')
            System.out.println("players loses insurance");
    }

    /**
     * Prints the end of round screen, indicating whether the player won, lost or pushed
     * @param dealerBust True if dealer busted, false otherwise
     */
    private void printEndScreen(boolean dealerBust) {
        String lastString = " and his current balance is ";
        Iterator<Hand> it = game.player.hands.iterator();
        Hand playerHand;
        int counter = 0;         
        while (it.hasNext()) {
            playerHand = it.next();
            counter++;
            if ((playerHand.handSum() > hand.handSum() && !playerHand.busted) || dealerBust) {
                if (playerHand.hasBlackjack()) {
                    game.playerBJcount++;
                    game.winCount++;
                    if (game.mode != 's')
                        System.out.println("blackjack!!");
                    game.player.balance += playerHand.bet * 2.5;
                    if (game.mode != 's')
                        System.out.print("player wins ");
                    game.player.roundOutcome = 1; // win //*verificar com cuidado

                    if (game.player.splitted && game.mode != 's')
                        System.out.print("[" + counter + "]");
                    if (game.mode != 's')
                        System.out.println(lastString + game.player.balance);

                    continue;
                }
                game.winCount++;
                game.player.balance += playerHand.bet * 2;
                if (game.mode != 's')
                    System.out.print("player wins ");
                game.player.roundOutcome = 1; // win //*verificar com cuidado

                if (game.mode != 's') {
                    if (game.player.splitted)
                        System.out.print("[" + counter + "]");
                    System.out.println(lastString + game.player.balance);
                }

            } else if (playerHand.handSum() < hand.handSum() || playerHand.busted) {
                game.loseCount++;

                if (game.mode != 's')
                    System.out.print("player loses");
                game.player.roundOutcome = -1; // lose //*verificar com cuidado
                if (game.mode != 's') {
                    if (game.player.splitted) {
                        System.out.print("[" + counter + "]");
                    }
                    System.out.println(lastString + game.player.balance);
                }
            } else if (playerHand.handSum() == hand.handSum()) {
                game.pushCount++;
                game.player.balance += playerHand.bet;
                if (game.mode != 's')
                    System.out.print("player pushes ");
                game.player.roundOutcome = 0; // push //*verificar com cuidado
                if (game.mode != 's') {
                    if (game.player.splitted) {
                        System.out.print("[" + counter + "]");
                    }
                    System.out.println(lastString + game.player.balance);
                }
            }
        }
    }

     /**
      * Shows dealer hand
      */
    public String showHand()
    {
        return "dealer's hand "+hand;
    }

    
    /** 
     * Returns dealer hand
     * @return Hand Dealer hand
     */
    public Hand getHand(){
        return hand;
    }
    
    /** 
     * Returns the shoe
     * @return Shoe Game shoe
     */
    public Shoe getShoe(){
        return shoe;
    }

}