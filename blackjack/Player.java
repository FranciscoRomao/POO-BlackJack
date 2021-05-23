package blackjack;

import java.util.*;
import java.io.*;

public class Player
{
    public Game game;
    public LinkedList<Hand> hands;
    public int hilo_count;
    public int ace5_count;

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

    //public Player(Game game, LinkedList<Chip> init_chips, int strat)
    public Player(Game game, int balance, String string)
    {
        this.game = game;
        this.balance = balance;
        initBalance = balance;
        insuranceBet = -1;
        hands = new LinkedList<Hand>();
        hands.add(new Hand(game.min_bet));
        lastBet = game.min_bet;
        hilo_count = 0;
        ace5_count = 0;

        splitNumber = 0;
        nHands = 0;
        handNumber = 0;
        splitted = false;
        handsLeft = 1;
        allBlackjack = true;

        basic = new Basic();
        hilo = new HiLo();
        ace5 = new Ace5(game.min_bet, 16);
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
     * 
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
                action = simulation(strat, state);
                break;
            default:
                break;
        }
        return action;
    }

    public void stats()
    {
        float gains = ((balance-initBalance)/initBalance) * 100;
        System.out.printf("BJ P/D\t%.3f/%.3f%n", (float)game.playerBJcount/nHands, (float)game.dealerBJcount/game.dealer.nHands);
        System.out.printf("Win \t%.2f%n", (float)game.winCount/nHands);
        System.out.printf("Lose\t%.2f%n", (float)game.loseCount/nHands);
        System.out.printf("Push\t%.2f%n", (float)game.pushCount/nHands);
        System.out.printf("Balance\t%.2f(%.2f%%)%n", balance, gains);
    }

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
                basic.advice(game, false, this.splitCheck());
                return basic.simAction(this, state);
            case "HL":
                if(state == 0){
                    stdbet.advice(game, false);
                    return stdbet.simAction();
                }
                hilo.advice(game, false);
                simString = hilo.simAction(this, state);
                if(simString.equals("BASIC")){
                    basic.advice(game, false, this.splitCheck());
                    simString = basic.simAction(this, state);
                }                
                return simString;
            case "HL-AF":
                if(state == 0) {
                    ace5.advice(game, false);
                    return ace5.simAction();
                }
                hilo.advice(game, false);
                simString = hilo.simAction(this, state);
                if(simString.equals("BASIC")){
                    basic.advice(game, false, this.splitCheck());
                    simString = basic.simAction(this, state);
                } 
                return simString; 
            case "BS":
                if(state == 0) {
                    stdbet.advice(game, false);
                    return stdbet.simAction();
                }
                basic.advice(game, false, this.splitCheck());
                return basic.simAction(this, state);
            default:
                System.out.println("Illegal strategy, exiting...");
                System.exit(-1);
        }
        return null;
    }


     /**
      * 
      * @param print indicates if "player hits" is printed or not
      */
    public void hit(boolean print)
    {
        hands.get(handNumber).addCard(game.dealer.shoe.getCard());
        hilo.Count(game.dealer.shoe.getCard()); //ok problema grave.. estes todos sao do tipo strategy e ta se a queixar
        ace5.ace5Count(game.dealer.shoe.getCard());

        if(print && game.mode != 's')
            System.out.println("player hits");
        if(splitted){
            System.out.println("player's hand ["+(handNumber+1)+"] "+hands.get(handNumber)+"("+hands.get(handNumber).handSum()+")");
        }
        else {
            System.out.println("player's hand "+hands.get(handNumber)+"("+hands.get(handNumber).handSum()+")");
        }
    }
    
    /**
     * 
     */
    public void stand()
    {
        System.out.print("player stands ");

        if(handsLeft > 1 || handNumber > 0)
            System.out.print("["+(game.player.handNumber + 1)+"]");
        
            System.out.println();

        if(handsLeft > 1)
        {
            game.dealer.playOtherHand();
            return;
        }
        game.dealer.stand(false); 
    }

    public boolean splitCheck(){
        if(!hands.get(handNumber).isSplittable() || splitNumber > 3){
            System.out.println("p: illegal command");
            return false;
        }
        return true;
    }

    /**
     * 
     */
    public void split()
    {
        String th = "st";
        if(splitCheck()){
    //print   if(game.mode != 's')
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
    //print   if(game.mode != 's')
                System.out.println("playing "+(handNumber+1)+th+" hand...");
            hit(false);
        }
        splitNumber++;
    }

    public boolean insuranceCheck(){
        if(game.dealer.hand.get(0) != 1){    //1==ACE
    //print   if(game.mode != 's')
                System.out.println("i: illegal command");
            return false;
        }
        return true;
    }
    /**
     * 
     */
    public void insure()
    {
        if(insuranceCheck()){
    //print   if(game.mode != 's')
                System.out.println("player is insuring");
            insuranceBet = hands.get(handNumber).bet;
            balance -= insuranceBet;
        }
    }

    /**
     * 
     */
    public boolean insured(){
        return (insuranceBet != -1);
    }

    /**
     * 
     */
    public void surrender() {
//print   if(game.mode != 's')        
            System.out.println("payer is surrendering");
        balance += hands.get(handNumber).bet*0.5;
        hands.get(handNumber).busted = true;
        if(game.dealer.hand.hasBlackjack()){
    //print   if(game.mode != 's')
                System.out.println("blackjack!!");  
            game.dealer.insuranceCheck();
        }
        if(handsLeft > 1){
            game.dealer.playOtherHand();
            return;
        }
        game.dealer.stand(false);
    }
    
    public boolean doubleCheck(){
        if(hands.get(handNumber).handSum() < 9 || hands.get(handNumber).handSum() > 11){
    //print   if(game.mode != 's')
                System.out.println("2: illegal command");
            return false;
        }
        return true;
    }
    /**
     * 
     */
    public void doubleDown(){
        if(doubleCheck()){
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
     * 
     * @return
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
     * 
     */
    public boolean placeBet(int value){
        //if(value > balance || (value == -1 && balance < hands.get(handNumber).bet)){
    //print   if(game.mode != 's')
             //   System.out.println("Player doesn't have enough money to bet. Available balance: "+balance);
         //   return false;
      //  }
        if(value != -1 && (value < game.min_bet || value > game.max_bet)){
    //print   if(game.mode != 's')
                System.out.println("Invalid bet ammount. Has to be greater than "+game.min_bet+" and smaller than "+game.max_bet);
            return false;
        }
        if(value != -1){
            hands.get(handNumber).bet = value;
            lastBet = value; 
        }
        hands.get(handNumber).bet = lastBet;
//print   if(game.mode != 's')
            System.out.println("player is betting "+hands.get(handNumber).bet);
        balance -= hands.get(handNumber).bet;
        return true;
    }
}


