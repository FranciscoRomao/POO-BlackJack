package blackjack;

import java.util.*;
import blackjack.strategies.*;
import java.io.*;

/**
 * Class that implements the player of the game
 *<p> It contains the methods corresponding to player moves and necessary atributes
 */
public class Player
{
    public Game game;
    protected LinkedList<Hand> hands;

    protected HiLo hilo;
    protected Basic basic;
    protected Ace5 ace5;
    protected StdBet stdbet;
    protected String strat;
    
    public float balance;
    public String action;
    public File cmdFile;
    public float insuranceBet;
    public float initBalance;
    public int splitNumber;

    private Scanner s;
    private InputStream input;
    private String delim;
    public int handNumber;
    public boolean splitted;
    public int handsLeft;
    public int nHands;
    public boolean allBlackjack;
    public int lastBet; 
    public int roundOutcome; 

    /**
     * Player constructor
     * @param game Game this player is playing
     * @param balance Player's balance
     * @param string Command file name(in debug mode) or strategy(in simulation mode)
     */
    public Player(Game game, int balance, String string)
    {
        this.game = game;
        this.balance = balance;
        initBalance = balance;
        insuranceBet = -1;
        hands = new LinkedList<Hand>();
        hands.add(new Hand(game.min_bet));
        lastBet = game.min_bet;

        splitNumber = 0;
        nHands = 0;
        handNumber = 0;
        splitted = false;
        handsLeft = 1;
        allBlackjack = true;

        basic = new Basic();
        hilo = new HiLo();
        ace5 = new Ace5(game.min_bet, game.max_bet);
        stdbet = new StdBet(); 

        switch (this.game.mode)
        {
            case 'd':
                cmdFile = new File(string);      
                if(!cmdFile.exists())
                {
                    System.out.println("File doesn't exist");
                    System.exit(-1);
                }
                try
                {
                    input = new FileInputStream(cmdFile);                
                }
                catch (Exception e)
                {
                    System.out.println("Error with the file");
                    System.exit(-1);
                }
                delim = " |\\n";
                break;
            case 'i':
                input = System.in;
                delim = "\n";               
                break;
            case 's':
                strat = string;
                break;
            default:
                break;
        }
        if(this.game.mode != 's')
            s = new Scanner(input).useDelimiter(delim); 
    }

    /**
     * Method that reads the next player action, whether it's in debub, interactice or simulation mode
     * @param state The current state of the game
     * @return String The command to be played
     */
    public String readPlay(int state)
    {
        switch (game.mode) {
            case 'i':
            case 'd':
                try
                {           
                    if(game.mode == 'i')
                        System.out.print("Command >> ");
                    if(s.hasNext())
                    {
                        action = s.next();
                    }
                    else action = "q";

                } catch (Exception e) {
                    System.out.println("Error reading from input");
                }
                break;
            case 's': 
                //System.out.println("player's hand "+hands.get(handNumber)+"("+hands.get(handNumber).handSum()+")");
                action = simulation(strat, state);
                break;
            default:
                break;
        }
        return action;
    }
    
    /** 
     * Method that calls strategies methods in order to obtain the next move according to those strategies
     * @param strat Strategy to be used in simulation
     * @param state Current state of the game
     * @return String
     */
    public String simulation(String strat, int state) {
        String simString;

        if(state == 1)
            return "d";

        switch (strat) {
            case "BS-AF":
                if(state == 0){
                    ace5.advice(game, false);
                    return ace5.simAction();                
                }
                basic.advice(game, false);
                return basic.simAction(this, state);
            case "HL":
                if(state == 0){
                    stdbet.advice(game, false);
                    return stdbet.simAction();
                }
                hilo.advice(game, false, state);
                simString = hilo.simAction(this, state);
                if(simString.equals("BASIC")){
                    basic.advice(game, false);
                    simString = basic.simAction(this, state);
                }                
                return simString;
            case "HL-AF":
                if(state == 0) {
                    ace5.advice(game, false);
                    return ace5.simAction();
                }
                hilo.advice(game, false, state);
                simString = hilo.simAction(this, state);
                if(simString.equals("BASIC")){
                    basic.advice(game, false);
                    simString = basic.simAction(this, state);
                } 
                return simString; 
            case "BS":
                if(state == 0) {
                    stdbet.advice(game, false);
                    return stdbet.simAction();
                }
                basic.advice(game, false);
                return basic.simAction(this, state);
            default:
                System.out.println("Illegal strategy, exiting...");
                System.exit(-1);
        }
        return null;
    }


     /**
      * Method that processes the hit command. It takes one card from the deck and adds it to the player's hand
      * @param print indicates if "player hits" is printed or not
      */
    public void hit(boolean print)
    {
        hands.get(handNumber).addCard(game.dealer.shoe.getCard());
        hilo.Count(game.dealer.shoe.getCard()); //ok problema grave.. estes todos sao do tipo strategy e ta se a queixar
        ace5.ace5Count(game.dealer.shoe.getCard());

        if(game.mode != 's'){
            if(print)
                System.out.println("player hits");
            if(splitted){
                System.out.println("player's hand ["+(handNumber+1)+"] "+hands.get(handNumber)+"("+hands.get(handNumber).handSum()+")");
            }
            else {
                System.out.println("player's hand "+hands.get(handNumber)+"("+hands.get(handNumber).handSum()+")");
            }
        }
    }
    
    /**
     * Method that processes the stand command
     */
    public void stand()
    {
        if(game.mode != 's')
            System.out.print("player stands ");

        if((handsLeft > 1 || handNumber > 0) && game.mode != 's'){
            System.out.print("["+(game.player.handNumber + 1)+"]");        
            System.out.println();
        }

        if(handsLeft > 1)
        {
            game.dealer.playOtherHand();
            return;
        }
        game.dealer.stand(false); 
    }

    
    /** 
     * Checks if the player can split or not
     * @return boolean Returns true if player can split, false otherwise
     */
    public boolean splitCheck(){
        if(!hands.get(handNumber).isSplittable() || splitNumber > 3){
            if(game.mode != 's')
                System.out.println("p: illegal command");
            return false;
        }
        return true;
    }

    /**
     * Implements the split siderule
     */
    public void split()
    {
        String th = "st";
        if(splitCheck()){
        if(game.mode != 's')
                System.out.println("player is splitting");
            nHands++;
            hands.add(new Hand(hands.get(handNumber).bet));
            hands.get(handNumber+handsLeft).addCard(hands.get(handNumber).getCard(1));
            hands.get(handNumber).removeCard(hands.get(handNumber).getCard(1));
            handsLeft++;
            splitted = true;
            balance -= hands.get(handNumber).bet;
            if((handNumber+1) == 2){
                th = "nd";
            } else if((handNumber+1) == 3){
                th = "rd";
            } else if((handNumber+1) > 3){
                th = "th";
            }
       if(game.mode != 's')
                System.out.println("playing "+(handNumber+1)+th+" hand...");
            hit(false);
        }
        splitNumber++;
    }

    
    /** 
     * Check if the player can insure or not
     * @param print indicates if "illegal command" is printed or not
     * @return boolean True if player can insure, false otherwise
     */
    public boolean insuranceCheck(boolean print){
        if(game.dealer.hand.get(0) != 1 || insuranceBet != -1 || splitted){    //1==ACE
            if(game.mode != 's'&& print)
                System.out.println("i: illegal command");
            return false;
        }
        return true;
    }
    /**
     * Implements the insure siderule
     */
    public void insure()
    {
        if(insuranceCheck(true)){
        if(game.mode != 's')
            System.out.println("player is insuring");
        insuranceBet = hands.get(handNumber).bet;
        balance -= insuranceBet;
        }
    }

    /**
     * Method that indicates if the player insured or not in the current round
     * @return boolean True if player insured in this round, false otherwise
     */
    public boolean insured(){
        return (insuranceBet != -1);
    }

    /**
     * Implements the surrender siderule
     */
    public void surrender() {
        if(game.mode != 's')        
            System.out.println("payer is surrendering");
        balance += hands.get(handNumber).bet*0.5;
        hands.get(handNumber).busted = true;
        if(game.dealer.hand.hasBlackjack()){
            if(game.mode != 's')
                System.out.println("blackjack!!");  
            game.dealer.insuranceCheck();
        }
        if(handsLeft > 1){
            game.dealer.playOtherHand();
            return;
        }
        game.dealer.stand(false);
    }
    
    
    /** 
     * Checks if the player can double or not
     * @param print indicates if "illegal command" is printed or not
     * @return boolean True if player can double, false otherwise
     */
    public boolean doubleCheck(boolean print){
        if(hands.get(handNumber).handSum() < 9 || hands.get(handNumber).handSum() > 11){
            if(game.mode != 's' && print)
                System.out.println("2: illegal command");
            return false;
        }
        return true;
    }
    /**
     * Implements the double siderule
     */
    public void doubleDown(){
        if(doubleCheck(true)){
            balance -= hands.get(handNumber).bet;
            hands.get(handNumber).bet += hands.get(handNumber).bet;
            hit(false);
            if(handsLeft > 1){
                game.dealer.playOtherHand();
                return;
            }
            game.dealer.stand(false);
        }
    }

    /**
     * Shows all of the player's hands
     * @return String Player's hands
     */
    public String showAllHands(){
        StringBuilder str = new StringBuilder();
        str.append("player's hand ");
        Iterator<Hand> it = hands.iterator();
        boolean hasSplit = false;
        int i = 1;
        while(it.hasNext()){
            Hand h = it.next();
            if(it.hasNext())
                hasSplit = true;
            if(hasSplit)
                str.append("["+i+"]"+h+"("+h.handSum()+")"); 
            str.append(h+"("+h.handSum()+")");
            if(it.hasNext())
                str.append("\n");
        }
        return str.toString();
    }

    /**
     * Implements the bet command
     * @param value Ammount of money to bet
     * @return boolean True if betting the specified ammount is possible, false otherwise
     */
    public boolean placeBet(int value){
        /*if(value > balance || (value == -1 && balance < hands.get(handNumber).bet)){
       if(game.mode != 's')
            System.out.println("Player doesn't have enough money to bet. Available balance: "+balance);
            return false;
        }*/
        if(value != -1 && (value < game.min_bet || value > game.max_bet)){
       if(game.mode != 's')
                System.out.println("Invalid bet ammount. Has to be greater than "+game.min_bet+" and smaller than "+game.max_bet);
            return false;
        }
        if(value != -1){
            hands.get(handNumber).bet = value;
            lastBet = value; 
        }
        hands.get(handNumber).bet = lastBet;
   if(game.mode != 's')
            System.out.println("player is betting "+hands.get(handNumber).bet);
        balance -= hands.get(handNumber).bet;
        return true;
    }

    
    /** 
     * Returns player hands
     * @return LinkedList<Hand> Player hands
     */
    public LinkedList<Hand> getHands(){
        return hands;
    }

    
    /** 
     * Returns hi-lo strategy
     * @return HiLo Strategy
     */
    public HiLo getHilo(){
        return hilo;
    }
    
    /** 
     * Returns basic strategy
     * @return Basic Strategy
     */
    public Basic getBasic(){
        return basic;
    }
    
    /** 
     * Returns ace-five strategy
     * @return Ace5
     */
    public Ace5 getAce5(){
        return ace5;
    }
    
    /** 
     * Returns standard bet strategy 
     * @return StdBet Strategy
     */
    public StdBet getStdBet(){
        return stdbet;
    }
}


