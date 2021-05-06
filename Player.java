package BlackJack;

import java.util.*; 
import java.io.*;

public class Player
{
    public Game game;
    public LinkedList<Chip> my_chips;
    public LinkedList<Chip> bet_chips;
    public Hand hand;
    public int hilo_count;
    public int ace5_count;
    public HiLo hilo;
    public Std_bet standard;
    public Basic basic;
    public Ace5 ace5;
    public int balance;
    public String action;

    //public Player(Game game, LinkedList<Chip> init_chips, int strat)
    public Player(Game game, int balance, String string)
    {
        this.game = game;
        this.balance = balance;
        this.bet_chips = new LinkedList<Chip>();
        this.hand = new Hand();

        this.hilo_count = 0;
        this.ace5_count = 0;

        if(game.mode == 'd'){
            File cmdFile = new File(string);
            try {
                Scanner s = new Scanner(cmdFile).useDelimiter("/Z");
                action = s.next();    
            } catch (Exception e) {
                System.out.println("Error reading file");
                System.exit(-1);
            }            
        }
    }

    public int Play()
    {
        //TODO
        /**
         * if debug scaner do action
         * if interactive scaner da consola
         */
        return 0;
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

    public Hit()
    {
        //TODO
        return 0;
    }

    public GetBalance()
    {
        //TODO
        return 0;
    }

    public Bet()
    {
        //TODO
        return 0;
    }

    public Insurance()
    {
        //TODO
        return 0;
    }
*/
}
