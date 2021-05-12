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

    //public Player(Game game, LinkedList<Chip> init_chips, int strat)
    public Player(Game game, int balance, String string)
    {
        this.game = game;
        this.balance = balance;
        this.bet = 0.0;
        this.bet_chips = new LinkedList<Chip>();
        this.hands = new LinkedList<Hand>();
        this.hands.add(new Hand());
        this.stand = false;

        this.hilo_count = 0;
        this.ace5_count = 0;

        if(this.game.mode == 'd'){
            this.cmdFile = new File(string);      
            if(!this.cmdFile.exists()){
                System.out.println("File doesn't exist");
                System.exit(-1);
            }    
        } else if(this.game.mode == 's'){
            //todo strat = string
        }
    }

    public int readPlay()
    {
        if(game.mode != 's'){
            InputStream input = null;
            Scanner s = null;
            if(game.mode == 'd'){
                try {
                    input = new FileInputStream(cmdFile);                
                } catch (Exception e) {
                    System.out.println("Error with the file");
                    System.exit(-1);
                }
            } 
            else if(game.mode == 'i'){
                input = System.in;
            }
            try {
                s = new Scanner(input).useDelimiter(" |\\n");            
                if(game.mode == 'i')
                    System.out.print("Command >> ");
                while (s.hasNext()) {
                    action = s.next();
                    System.out.println(action);
                    //todo this.play(string action)
                    //este if a baixo é para tirar mas dá jeito agora
                    if(action.equals("q")){
                        s.close();
                        return 1;
                    }
                    if(game.mode == 'i')
                        System.out.print("Command >> ");
                }
            } catch (Exception e) {
                System.out.println("Error reading from input");
            }
            if(s != null)
                s.close();
        }
        else {
            //todo action = get advice
            //play(string action)
        }

        return 0;
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
}
