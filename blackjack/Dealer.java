package blackjack;

import java.util.LinkedList;
import java.io.*;

public class Dealer
{
    public Game game;
    private Card hole_card;
    public Shoe shoe;
    public Hand hand;
    
    public Dealer(Game game)
    {      
        this.game = game;
        this.hand = new Hand();
        this.shoe = new Shoe(this.game.shoe);
    }
    
    public Dealer(Game game, String shoe)
    {
        this.game = game;
        this.hand = new Hand();
        this.shoe = new Shoe(shoe);
    }

    public void Hit()
    {
        Card aux;
        aux = shoe.getCard();
        this.hand.addCard(aux);
    }

    public void DealCards()
    {
        Card aux;

        for(int i=0; i<2; i++)
        {
            aux = shoe.getCard();
            //!é preciso especificar qual a hand - meti o get first para deixar de dar erro
            this.game.player.hands.getFirst().addCard(aux);
        }

        aux = shoe.getCard();
        
        this.hole_card = aux;
        
        aux = shoe.getCard();

        this.hand.addCard(aux);
    }

    public int CheckBJ()
    {
        if(this.game.player.hands.getFirst().HandSum() == 21)
            return 1;
        
        if(this.hand.HandSum() == 21)
            return 2;
            
        return 0;
    }
}