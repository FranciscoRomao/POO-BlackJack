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
    public int balance;
    public String action;
    public File cmdFile;
    public boolean stand;
    public double bet;
    public  LinkedList<Chip> bet_chips;

    private Scanner s;
    private InputStream input;
    private String delim;

    //public Player(Game game, LinkedList<Chip> init_chips, int strat)
    public Player(Game game, int balance, String string)
    {
        this.game = game;
        this.balance = balance;
        this.bet = 11.0;
        this.bet_chips = new LinkedList<Chip>();
        this.hands = new LinkedList<Hand>();
        this.hands.add(new Hand());
        this.stand = false;

        this.hilo_count = 0;
        this.ace5_count = 0;

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

    public void play(String action){
        //todo verificar em que fase do jogo se está e possíveis comandos
        //chamar as funções especificas de cada comando
    }
    /*
    public int Debug()
    {
        //TODO
        return 0;
    }

    public Sim()
    {
        //TODO
        return 0;
    }
    */
    public void Hit()
    {
        Card aux;
        
        aux = game.dealer.shoe.getCard();
        //! é preciso especifacar a hand
        //hands.addCard(aux);
    }
    /*
    public Bet()
    {
        
        return 0;
    }*/

    public void Split()
    {
        hands.add(new Hand());

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
        this.game.dealer.DealCards();
        this.game.round = 0;
    }
    
    public String showHand(){
        StringBuilder str = new StringBuilder();
        str.append("player's hand ");
        Iterator<Hand> it = hands.iterator();
        while(it.hasNext()){
            Hand h = it.next();
            str.append(h);
            if(it.hasNext())
                str.append("\n");
        }
        return str.toString();
    }
}