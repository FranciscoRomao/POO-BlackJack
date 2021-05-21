package blackjack;

public class Game { // ?a classe game nao devia ser uma coisa abrasta? o jogo nao existe sozinho so com o dealer e player e que e alguma coisa
    //?os atributos das classes não deveriam de ser private, package ou protected? nunca devem ser publicos segundo a prof
    //TODO: mudar entao os que fizerem sentido ou para package ou para protected
    public char mode;
    public Player player;
    public Dealer dealer;
    public String strat;

    public int min_bet;
    public int max_bet;

    // public int init_bal;

    // Number of decks on the shoe
    public int shoe;

    // Percentage of shoe used to shuffle again
    public int shuffle;

    public int shuffleNum;

    // Game round counter
    public int round;

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
        try {
            this.min_bet = Integer.parseInt(args[1]);
            if (this.min_bet < 1) {
                System.out.println("Minimum bet has to be more than 1$");
                System.exit(-1);
            }
            this.max_bet = Integer.parseInt(args[2]);
            if (this.max_bet < 10 * this.min_bet || this.max_bet > 20 * this.min_bet) {
                System.out.println("Maximum bet has to be a value between " + 10 * this.min_bet + " and "
                        + 20 * this.min_bet + " if the minimum bet is " + this.min_bet);
                System.exit(-1);
            }
            this.round = 0;
            if (this.mode != 'd') {
                if (Integer.parseInt(args[3]) < 50 * this.min_bet) {
                    System.out.println("Balance has to be a value greater than " + 50 * this.min_bet
                            + " if the minimum bet is " + this.min_bet);
                    System.exit(-1);
                }
                if (this.mode == 's') {
                    // TODO verificação da strategy
                    this.strat = args[7];
                    this.shuffleNum = Integer.parseInt(args[6]);
                }
                if (Integer.parseInt(args[4]) < 4 || Integer.parseInt(args[4]) > 8) {
                    System.out.println("The number of decks has to be between 4 and 8");
                    System.exit(-1);
                }
                this.shoe = Integer.parseInt(args[4]);
                if (Integer.parseInt(args[5]) < 10 || Integer.parseInt(args[5]) > 100) {
                    System.out.println("Percentage of shoe played has to be between 10 and 100");
                    System.exit(-1);
                }
                shuffle = Integer.parseInt(args[5]); //?nao devia de ser this.shuffle como this.shoe?
                dealer = new Dealer(this);
                player = new Player(this, Integer.parseInt(args[3]), this.strat);
                context = new StateContext(this);
                return;
            }
            if (this.mode == 'd') {
                this.dealer = new Dealer(this, args[4]);
                this.player = new Player(this, Integer.parseInt(args[3]), args[5]);
                context = new StateContext(this);
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

    public void startGame(){
        boolean playing = true;
        while(playing){
            playing = context.play();
        }
        System.out.println("bye");
    }

    public void changeState(State newState){
        context.setState(newState);
    }
}