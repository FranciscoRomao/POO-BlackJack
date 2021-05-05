package BlackJack;

import java.util.LinkedList;

class Dealer
{
    public Game game;
    private int hole_card;
    private Shoe shoe;
    public LinkedList<Card> hand;
    
    public Dealer(Game game)
    {
        this.game = game;
        this.hand = new LinkedList<Card>();
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