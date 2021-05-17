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
        System.out.println("dealer hits");
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

    public int handCheck(Hand handToCheck){
        if(handToCheck.handSum() > 21){
            bust();
            return -1;
        }
        return 1;
    }

    public void newRound(){
        game.player.hands.removeFirst();
        game.player.nHands--;
        hand = new Hand();
        if(game.player.nHands == 0){
            game.player.hands.add(new Hand());
            game.player.nHands++;
            game.changeState(new GameStart());
        } else {
            dealCards();
            game.changeState(new SideRulesState());
        }
    }

    public void bust(){
        System.out.println("player busts");
        hand.addCard(hole_card);
        System.out.println(showHand()+"("+hand.handSum()+")");
        System.out.println("player loses and his current balance is "+game.player.balance);
        newRound();
    }

    public void stand(){
        boolean busts = false;
        System.out.println("player stands");
        hand.addCard(hole_card);
        System.out.println(showHand()+"("+hand.handSum()+")");
        while(hand.handSum() < 17){
            hit();
            System.out.println(showHand()+"("+hand.handSum()+")");
        }        
        if(hand.handSum() > 21){
            busts = true;
            System.out.println("dealer busts");
        } else {
            System.out.println("dealer stands");
        }
        if(game.player.hands.getFirst().handSum() > hand.handSum() || busts){
            game.player.balance += game.player.bet * 2;
            System.out.println("player wins and his current balance is "+game.player.balance);
        } else if(game.player.hands.getFirst().handSum() < hand.handSum()){
            System.out.println("player loses and his current balance is "+game.player.balance);
        } else if(game.player.hands.getFirst().handSum() == hand.handSum()){
            game.player.balance += game.player.bet;
            System.out.println("player pushes and his current balance is  "+game.player.balance);
        }
        newRound();
    }


    public String showHand(){
        return "dealer's hand "+hand;
    }
}