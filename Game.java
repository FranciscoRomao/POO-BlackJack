package BlackJack;

class Game
{
    public int type;
    public Player player;
    public Dealer dealer;
    /*interactive - 0
    simulation - 1
    debug - 2*/
    
    public int strat;
    /*BS - 0
    HL - 1
    BS_AF - 2
    HL_AF - 3*/
    
    public int min_bet;
    public int max_bet;
    //public int init_bal;
    
    public int shoe;
    //Number of decks on the shoe
    
    public int shuffle;

    //Percentage of shoe used to shuffle again
    
    public int round;
    
    public Game(int gameType, int min, int max, int strategy, int balance, int ndecks, int shuffle_rate)
    {
        this.type = gameType;
        this.strat = strategy;
        this.max_bet = max;
        this.min_bet = min;
        this.round = 0;
        this.shoe = ndecks;
        this.shuffle = shuffle_rate;
    }

    public static void main(String[] args)
    {
        //TODO     
    }
}