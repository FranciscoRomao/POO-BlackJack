package blackjack;

import java.util.*; 
import java.io.*;

public class Player
{
    public Game game;
    public LinkedList<Chip> my_chips;
    public LinkedList<Hand> hands;
    public int hilo_count;
    public int ace5_count;
    //public HiLo hilo;
    //public Std_bet standard;
    //public Basic basic;
    //public Ace5 ace5;
    public double balance;
    public String action;
    public File cmdFile;
    public boolean stand;
    public double bet;
    public  LinkedList<Chip> bet_chips;

    private Scanner s;
    private InputStream input;
    private String delim;
    public int handNumber;
    private boolean splitted;
    public int nHands;

    //public Player(Game game, LinkedList<Chip> init_chips, int strat)
    public Player(Game game, int balance, String string)
    {
        this.game = game;
        this.balance = balance;
        this.bet = game.min_bet;
        this.bet_chips = new LinkedList<Chip>();
        this.hands = new LinkedList<Hand>();
        this.hands.add(new Hand());
        this.stand = false;

        this.hilo_count = 0;
        this.ace5_count = 0;

        handNumber = 0;
        splitted = false;
        nHands = 1;

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

    public void hit()
    {
        hands.getFirst().addCard(game.dealer.shoe.getCard());
        System.out.println("player hits");
        if(splitted){
            System.out.println("player's hand ["+handNumber+"]"+hands.getFirst()+hands.getFirst().handSum());
        }
        else {
            System.out.println("player's hand "+hands.getFirst()+"("+hands.getFirst().handSum()+")");
        }
    }

    public void Split()
    {
        hands.add(new Hand());
        splitted = true;
        nHands++;
    }

    public void Insurance()
    {
        this.bet = this.bet * 1.5;
    }

    public void Stand()
    {
        this.stand = true;
    }

    public void Surrender()
    {
        this.bet = 0;
        this.stand = false;
        this.game.dealer.dealCards();
        this.game.round = 0;
    }

    public String showAllHands(int num){
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

    public boolean placeBet(double value){
        if(value > balance || (value == -1 && balance < bet)){
            System.out.println("Player doesn't have enough money to bet. Available balance: "+balance);
            return false;
        }
        if(value != -1 && (value < game.min_bet || value > game.max_bet)){
            System.out.println("Invalid bet ammount. Has to be greater than "+game.min_bet+" and smaller than "+game.max_bet);
            return false;
        }
        if(value != -1){
            bet = value;
        }
        System.out.println("player is betting "+bet);
        balance -= bet;
        return true;
    }
}
