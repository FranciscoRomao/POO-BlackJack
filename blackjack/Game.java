package blackjack;

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


    // public int init_bal; //?ja nao precisas

    // Number of decks on the shoe
    protected int shoe;

    // Percentage of shoe used to shuffle again
    protected int shuffle;

    protected int shuffleNum;

    // Game round counter
    protected int round;

    private StateContext context;

    /**
     * 
     * @param args Input Parameters
     */
    public Game(String[] args) {        
        if (args.length == 0) { // args vazio
            System.out.println("Specify input parameters");
            System.exit(-1);
        }
        initMode(args);       
        //try {
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
        // } catch (Exception e) {
        //     System.out.println("Please insert numbers only besides the mode and the strategy");
        //     System.exit(-1);
        // }
    }

    /**
     * 
     */
    @Override
    public String toString() {
        return mode + " " + min_bet + " " + max_bet + " " + this.player.balance + " " + shoe + " " + shuffle;
    }

    /**
     * 
     */
    public void startGame(){
        boolean playing = true;
        while(playing)
            playing = context.play(); //Primeiro entra no GameStart -> DealState
        if(mode == 's'){
            player.stats();
        }
        System.out.println("bye");
    }

    
    public void changeState(State newState){
        context.setState(newState);
    }

    private void initMode(String args[]){
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

    private void initSim(String args[]){
        if (this.mode == 's') {
            this.strat = args[7];
            this.shuffleNum = Integer.parseInt(args[6]);
        }
    }

    private void errorChecking(String args[]){
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
    private void initCounts(String args[]){        
        playerBJcount = 0;
        dealerBJcount = 0;
        winCount = 0;
        loseCount = 0;
        pushCount = 0;
    }

    private void initNotDbug(String args[]){
        float percentage = Integer.parseInt(args[5]);
        shoe = Integer.parseInt(args[4]);
        dealer = new Dealer(this);
        player = new Player(this, Integer.parseInt(args[3]), this.strat);
        context = new StateContext(this);
        percentage = (percentage / 100) * dealer.shoe.getNumCards();
        shuffle = (int) percentage;
    }

    private void initDbug(String args[]){
        this.dealer = new Dealer(this, args[4]);
        this.player = new Player(this, Integer.parseInt(args[3]), args[5]);
        shuffle = dealer.shoe.getNumCards() + 1;
        context = new StateContext(this);
    }
}