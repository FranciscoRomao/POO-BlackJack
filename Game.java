package BlackJack;
import java.io.*;
class Game
{
    public char mode;
    public Player player;
    public Dealer dealer;
    public String strat;
    
    public int min_bet;
    public int max_bet;
    //public int init_bal;
    
    public int shoe;
    //Number of decks on the shoe
    
    public int shuffle;
    
    //Percentage of shoe used to shuffle again

    public int shuffleNum;
    
    public int round;

    /**
     * 
     * @param gameMode Game mode
     * @param min Minimum bet 
     * @param max Maximum bet
     * @param balance Initial ammount of money
     * @param ndecks Number of 52 card decks in the shoe
     * @param shuffle_rate Percentage of shoe played before shuffling
     * @param cmd Name of the file with the commands
     * @param shoeFile Name of the file with the shoe
     * @param sNum Number of shuffles to perform until ending the simulation
     * @param strat Counting cards strategy to use
     */
    //char gameMode, int min, int max, int balance, int ndecks, int shuffle_rate, File cmd, File shoeFile, int sNum, String strat
    public Game(String[] args)
    {
        this.mode = args[0].charAt(1);
        if(this.mode != 'd' || this.mode != 'i' || this.mode != 's')
            //TODO invalid mode exit -1
        this.min_bet = min;
        this.max_bet = max;
        this.round = 0;
        if(gameMode != 'd'){
            this.player = new Player(this, balance, strat);
            this.shoe = ndecks;
            this.shuffle = shuffle_rate;
            if(gameMode == 'i')
                return;
            this.shuffleNum = sNum;
            this.strat = strat;
            return;
        }
        if(gameMode == 'd'){
            this.dealer = new Dealer(this, shoeFile);
            this.player = new Player(this, balance, strat, cmd);
        }

    }

    public static void main(String[] args)
    {
        Game newGame = new Game(args[0], min, max, balance, ndecks, shuffle_rate, cmd, shoeFile, sNum, strat)
        
    }
}