package blackjack;

import blackjack.state_pattern.*;
/**
 * Class that implements the game.
 * <p> It contains a player, a dealer and the initial arguments
 */
public class Game {
    protected char mode;
    protected Player player;
    protected Dealer dealer;
    protected String strat;

    protected int min_bet;
    protected int max_bet;

    //Stats
    public int playerBJcount;
    public int dealerBJcount;
    public int winCount;
    public int loseCount;
    public int pushCount;


    // Number of decks on the shoe
    protected int shoe;

    // Percentage of shoe used to shuffle again
    protected int shuffle;

    protected int shuffleNum;

    // Game round counter
    protected int round;

    private StateContext context;

    /**
     * Game constructor. Initializes all the atributes according to the program parameters
     * @param args Program parameters
     */
    public Game(String[] args) {        
        if (args.length == 0) { // args vazio
            System.out.println("Specify input parameters");
            System.exit(-1);
        }
        initMode(args);       
        try {
            initBet(args[1], args[2]);
            this.round = 0;
            if (this.mode != 'd') {                
                initSim(args);
                errorChecking(args);
                initCounts(args);                
                initNotDbug(args);
                return;
            }
            if (this.mode == 'd') {
                initCounts(args);
                initDbug(args);
            }
        } catch (Exception e) {
            System.out.println("Please insert numbers only besides the mode and the strategy");
            System.exit(-1);
        }
    }

    @Override
    public String toString() {
        return mode + " " + min_bet + " " + max_bet + " " + this.player.balance + " " + shoe + " " + shuffle;
    }

    /**
     * Initiates a game, calling the play method which gets the command from the player, file or simulation
     */
    public void startGame(){
        boolean playing = true;
        while(playing)
            playing = context.play(); //Primeiro entra no GameStart -> DealState
        if(mode == 's'){
            stats();
            return;
        }
        System.out.println("bye");
    }

    
    
    /** 
     * Changes the current game state
     * @param newState
     */
    public void changeState(State newState){
        context.setState(newState);
    }

    
    /** 
     * Initializes the game mode
     * @param args Program parameters
     */
    private void initMode(String[] args){
        if (args[0].charAt(0) != '-') {
            System.out.println("Specify the mode like this -<mode>");
            System.exit(-1);
        }
        this.mode = args[0].charAt(1);
        if (this.mode != 'd' && this.mode != 'i' && this.mode != 's') {
            System.out.println("Invalid mode");
            System.exit(-1);
        }
        if ((this.mode != 's' && args.length != 6) || (this.mode == 's' && args.length != 8)) {
            System.out.println("Wrong number of parameters");
            System.exit(-1);
        } 
    }

    
    /** 
     * Initializes the bets
     * @param min Minimum bet
     * @param max Maximum bet
     */
    private void initBet(String min, String max){
        min_bet = Integer.parseInt(min);
        if (min_bet < 1) {
            System.out.println("Minimum bet has to be more than 1$");
            System.exit(-1);
        }
        max_bet = Integer.parseInt(max);
        if (max_bet < 10 * min_bet || max_bet > 20 * min_bet) {
            System.out.println("Maximum bet has to be a value between " + 10 * min_bet + " and "
                    + 20 * min_bet + " if the minimum bet is " + min_bet);
            System.exit(-1);
        }
    }

    
    /** 
     * Initializes the simulation
     * @param args Program parameters
     */
    private void initSim(String[] args){
        if (this.mode == 's') {
            this.strat = args[7];
            this.shuffleNum = Integer.parseInt(args[6]);
        }
    }

    
    /** 
     * Checks for bad parameters
     * @param args Program parameters
     */
    private void errorChecking(String[] args){
        if (Integer.parseInt(args[3]) < 50 * this.min_bet) {
            System.out.println("Balance has to be a value greater than " + 50 * this.min_bet+ " if the minimum bet is " + this.min_bet);
            System.exit(-1);
        }
        if (Integer.parseInt(args[4]) < 4 || Integer.parseInt(args[4]) > 8) {
            System.out.println("The number of decks has to be between 4 and 8");
            System.exit(-1);
        }
        if (Integer.parseInt(args[5]) < 10 || Integer.parseInt(args[5]) > 100) {
            System.out.println("Percentage of shoe played has to be between 10 and 100");
            System.exit(-1);
        }
    }
    
    /** 
     * Initiliazes counters for statistics
     * @param args
     */
    private void initCounts(String[] args){        
        playerBJcount = 0;
        dealerBJcount = 0;
        winCount = 0;
        loseCount = 0;
        pushCount = 0;
    }

    
    /** 
     * Initialization of atributes for the interactive and simulation modes
     * @param args
     */
    private void initNotDbug(String[] args){
        float percentage = Integer.parseInt(args[5]);
        shoe = Integer.parseInt(args[4]);
        dealer = new Dealer(this);
        player = new Player(this, Integer.parseInt(args[3]), this.strat);
        context = new StateContext(this);
        percentage = (percentage / 100) * dealer.shoe.getNumCards();
        shuffle = (int) percentage;
    }

    
    /** 
     * Initialization of atributes for the debug mode
     * @param args
     */
    private void initDbug(String[] args){
        this.dealer = new Dealer(this, args[4]);
        this.player = new Player(this, Integer.parseInt(args[3]), args[5]);
        shuffle = dealer.shoe.getNumCards() + 1;
        context = new StateContext(this);
    }

    /**
     * Method that prints the statistics of the game 
     */
    public void stats()
    {
        float gains = ((player.balance-player.initBalance)/player.initBalance) * 100f;
        System.out.printf("BJ P/D\t%.3f/%.3f%n", (float)playerBJcount/player.nHands, (float)dealerBJcount/dealer.nHands);
        System.out.printf("Win \t%.2f%n", (float)winCount/player.nHands);
        System.out.printf("Lose\t%.2f%n", (float)loseCount/player.nHands);
        System.out.printf("Push\t%.2f%n", (float)pushCount/player.nHands);
        System.out.printf("Balance\t%.2f(%.2f%%)%n", player.balance, gains);
    }
    
    /** 
     * Returns the player
     * @return Player player
     */
    public Player getPlayer(){
        return player;
    }

    
    /** 
     * Returns the dealer
     * @return Dealer dealer
     */
    public Dealer getDealer(){
        return dealer;
    }

    
    /** 
     * Returns the round
     * @return int round
     */
    public int getRound(){
        return round;
    }
    
    /** 
     * Sets the round
     * @param value Value to set the round to
     */
    public void setRound(int value){
        round = value;
    }

    
    /** 
     * Returns the minimum bet
     * @return int minimum bet
     */
    public int getMinBet(){
        return min_bet;
    }
    
    /** 
     * Returns the maximum bet
     * @return int maximum bet
     */
    public int getMaxBet(){
        return max_bet;
    }
    
    /** 
     * Returns the game mode
     * @return char game mode
     */
    public char getMode(){
        return mode;
    }
    
    /** 
     * Returns the number of shuffles
     * @return int number of shuffles
     */
    public int getShuffleNum(){
        return shuffleNum;
    }
}