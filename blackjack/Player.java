package blackjack;

import java.util.*; 
import java.io.*;

public class Player
{
    public Game game;
    public LinkedList<Hand> hands;
    public int hilo_count;
    public int ace5_count;
    //public HiLo hilo;
    //public Std_bet standard;
    //public Basic basic;
    //public Ace5 ace5;
    public float balance;
    public String action;
    public File cmdFile;
    public float insuranceBet;

    private Scanner s;
    private InputStream input;
    private String delim;
    public int handNumber;
    public boolean splitted;
    public int nHands;
    public boolean allBlackjack;
    public int lastBet; //?nao sei se deve de ser protected ou public 
    public int roundOutcome; //?nao sei se deve de ser protected ou public

    //public Player(Game game, LinkedList<Chip> init_chips, int strat)
    public Player(Game game, int balance, String string)
    {
        this.game = game;
        this.balance = balance;
        insuranceBet = -1;
        hands = new LinkedList<Hand>();
        hands.add(new Hand(game.min_bet));

        hilo_count = 0;
        ace5_count = 0;

        handNumber = 0;
        splitted = false;
        nHands = 1;
        allBlackjack = true;

        switch (this.game.mode) {
            case 'd':
                this.cmdFile = new File(string);      
                if(!this.cmdFile.exists()){
                    System.out.println("File doesn't exist");
                    System.exit(-1);
                } 
                try {
                    input = new FileInputStream(cmdFile);                
                } catch (Exception e) {
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
                //todo strat = string            
                break;
            default:
                break;
        }
        s = new Scanner(input).useDelimiter(delim); 
    }

    public String readPlay()
    {
        switch (game.mode) {
            case 'i', 'd':
                try {           
                    if(game.mode == 'i')
                        System.out.print("Command >> ");
                    if(s.hasNext())     {
                        action = s.next();

                    } else action = "q";
                } catch (Exception e) {
                    System.out.println("Error reading from input");
                }
                break;
            case 's':
                //todo action = get advice
                break;
            default:
                break;
        }
        return action;
    }
     /**
      * 
      * @param print indicates if "player hits" is printed or not
      */
    public void hit(boolean print)
    {
        hands.get(handNumber).addCard(game.dealer.shoe.getCard());
        if(print)
            System.out.println("player hits");
        if(splitted){
            System.out.println("player's hand ["+(handNumber+1)+"] "+hands.get(handNumber)+"("+hands.get(handNumber).handSum()+")");
        }
        else {
            System.out.println("player's hand "+hands.get(handNumber)+"("+hands.get(handNumber).handSum()+")");
        }
    }

    public void stand(){
        if(!hands.get(handNumber).hasBlackjack())
            allBlackjack = false;
        System.out.print("player stands ");
        if(nHands > 1 || handNumber > 0){
            System.out.print("["+(game.player.handNumber + 1)+"]");
        }
        System.out.println();
        if(nHands > 1){
            game.dealer.playOtherHand();
            return;
        }
        game.dealer.stand(false); 
    }

    public void split()
    {
        String th = "st";
        if(!hands.get(handNumber).isSplittable() || handNumber == 2){
            System.out.println("p: illegal command");
            return;
        }
        System.out.println("player is splitting");
        hands.add(new Hand(hands.get(handNumber).bet));
        nHands++;
        hands.get(handNumber+1).addCard(hands.get(handNumber).getCard(1));
        hands.get(handNumber).removeCard(hands.get(handNumber).getCard(1));
        splitted = true;
        balance -= hands.get(handNumber).bet;
        if((handNumber+1) == 2){
            th = "nd";
        } else if((handNumber+1) == 3){
            th = "rd";
        } else if((handNumber+1) > 3){
            th = "th";
        }
        System.out.println("playing "+(handNumber+1)+th+" hand...");
        hit(false);
    }

    public void insure()
    {
        if(game.dealer.hand.get(0) != 1){    //1==ACE
            System.out.println("i: illegal command");
            return;
        }
        System.out.println("player is insuring");
        insuranceBet = hands.get(handNumber).bet;
        balance -= insuranceBet;
    }

    public boolean insured(){
        return (insuranceBet != -1);
    }

    public void surrender()
    {
        System.out.println("payer is surrendering");
        balance += hands.get(handNumber).bet*0.5;
        game.dealer.showHole();
        if(game.dealer.hand.hasBlackjack()){
            System.out.println("blackjack!!");   //todo meter esta condição numa função dependendo do que a prof responder
            game.dealer.insuranceCheck();
        }
        System.out.println("player loses and his current balance is "+game.player.balance);
        if(nHands > 1){
            game.dealer.playOtherHand();
            return;
        }
        game.dealer.stand(false);
    }

    public void doubleDown(){
        if(hands.get(handNumber).handSum() < 9 || hands.get(handNumber).handSum() > 11){
            System.out.println("2: illegal command");
            return;
        }
        balance -= hands.get(handNumber).bet;
        hands.get(handNumber).bet += hands.get(handNumber).bet;
        hit(false);
        if(nHands > 1){
            game.dealer.playOtherHand();
            return;
        }
        game.dealer.stand(false);
    }

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

    public boolean placeBet(float value){
        if(value > balance || (value == -1 && balance < hands.get(handNumber).bet)){
            System.out.println("Player doesn't have enough money to bet. Available balance: "+balance);
            return false;
        }
        if(value != -1 && (value < game.min_bet || value > game.max_bet)){
            System.out.println("Invalid bet ammount. Has to be greater than "+game.min_bet+" and smaller than "+game.max_bet);
            return false;
        }
        if(value != -1){
            hands.get(handNumber).bet = value;
            lastBet= (int) value; //!nao sei pq uma bet esta a float, acho que a prof diz explicitamente que quer as bets com inteiros
        }
        System.out.println("player is betting "+hands.get(handNumber).bet);
        balance -= hands.get(handNumber).bet;
        return true;
    }
}
