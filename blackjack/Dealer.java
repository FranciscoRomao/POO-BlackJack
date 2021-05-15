package blackjack;
import java.util.*;

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

    public void hit()
    {
        Card aux;
        aux = shoe.getCard();
        hand.addCard(aux);
    }

    public void dealCards()
    {
        Card aux;

        for(int i=0; i<2; i++)
        {
            aux = shoe.getCard();
            game.player.hands.getFirst().addCard(aux);
        }

        aux = shoe.getCard();
        
        hole_card = aux;
        
        aux = shoe.getCard();

        hand.addCard(aux);
        System.out.println(showHand()+"X");
        System.out.println("player's hand "+game.player.hands.getFirst()+"("+game.player.hands.getFirst().handSum()+")");
    }

    public int checkBJ()
    {
        if(this.game.player.hands.getFirst().handSum() == 21)
            return 1;
        
        if(this.hand.handSum() == 21)
            return 2;
            
        return 0;
    }

    public void endRound(int handSum){
        if(handSum > 21){
            System.out.println("player busts");
            hand.addCard(hole_card);
            System.out.println(showHand());
            System.out.println("player loses and his current balance is "+game.player.balance);
        }
        game.player.hands.remove(0);
        game.player.nHands--;
        if(game.player.nHands == 0){
            game.player.hands.add(new Hand());
        }
    }

    public String showHand(){
        return "dealer's hand "+hand;
    }
}