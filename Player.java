package BlackJack;

import java.util.*; 

public class Player
{
    public Game game;
    public LinkedList<Chip> my_chips;
    public LinkedList<Chip> bet_chips;
    public LinkedList<Card> hand;
    public int hilo_count;
    public int ace5_count;

    public Player(Game game, LinkedList<Chip> init_chips)
    {
        this.game = game;
        this.my_chips = init_chips;
        this.bet_chips = new LinkedList<Chip>();
        this.hand = new LinkedList<Card>();

        this.hilo_count = 0;
        this.ace5_count = 0;
    }

    public int Play()
    {
        //TODO

        return 0;
    }

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

}
