package BlackJack;

import java.util.LinkedList;
import java.io.*;

class Dealer
{
    public Game game;
    private int hole_card;
    private Shoe shoe;
    public Hand hand;
    
    public Dealer(Game game)
    {
        this.game = game;
        this.hand = new Hand();
        this.shoe = new Shoe(this.game.shoe);
    }
    
    public Dealer(Game game, File shoe)
    {
        this.game = game;
        this.hand = new Hand();
        this.shoe = new Shoe(this.game.shoe);
        //TODO create shoe from file
    }

    public int Hit()
    {
        //getCard()
        //TODO
        return 0;
    }

    public int DealCards()
    {
        //TODO
        return 0;
    }
    
    public int CheckBJ()
    {
        //TODO
        return 0;
    }
}