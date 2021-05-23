package blackjack;

import java.util.Iterator;

public class Dealer
{   
    protected Game game;
    private Card hole_card;
    protected Shoe shoe;
    protected Hand hand;
    public int nHands;
    
    public Dealer(Game game)
    {      
        this.game = game;
        this.hand = new Hand(0);
        this.shoe = new Shoe(this.game.shoe);
    }
    
    public Dealer(Game game, String shoe)
    {
        this.game = game;
        this.hand = new Hand(0);
        this.shoe = new Shoe(shoe);
    }

    /**
     * 
     */
    public void hit()
    {
        Card aux;
        aux = shoe.getCard();
        hand.addCard(aux);
        System.out.println("dealer hits");
        game.player.hilo.Count(aux); 
        game.player.ace5.Ace5Count(aux);
    }

    /**
     * 
     */
    public void dealCards()
    {
        Card aux;
        for(int i=0; i<2; i++)
        {
            System.out.printf("aqui---------------\n");
            aux = shoe.getCard();
            System.out.printf("aqui----------11-----\n");
            game.player.hands.get(game.player.handNumber).addCard(aux);
            game.player.hilo.Count(aux);
            game.player.ace5.Ace5Count(aux);
        }
        aux = shoe.getCard();
        
        hole_card = aux;
        
        aux = shoe.getCard();
        game.player.hilo.Count(aux);
        game.player.ace5.Ace5Count(aux);
        hand.addCard(aux);
        System.out.println(showHand()+"X");
        System.out.println("player's hand "+game.player.hands.get(game.player.handNumber)+"("+game.player.hands.get(game.player.handNumber).handSum()+")");
    }

    /**
     * 
     */
    public int checkBJ()
    {
        if(this.game.player.hands.get(game.player.handNumber).handSum() == 21)
            return 1;
        
        if(this.hand.handSum() == 21)
            return 2;
            
        return 0;
    }

    /**
     * 
     */
    public int bustCheck(Hand handToCheck)
    {
        if(handToCheck.handSum() > 21)
        {
            System.out.print("player busts ");
            game.player.hands.get(game.player.handNumber).busted = true;

            if(game.player.splitted)
                System.out.print("["+(game.player.handNumber + 1)+"]");
            
            System.out.println();

            if(game.player.handsLeft > 1)
            {
                playOtherHand();
                return -1;   
            }
            bust();
            return -1;
        }
        return 1;
    }

    /**
     * 
     */
    public void playOtherHand()
    {
        String th = "nd";

        game.player.handsLeft--;
        game.player.handNumber++;

        if((game.player.handNumber+1) == 3)
        {
            th = "rd";
        } else if((game.player.handNumber+1) > 3)
        {
            th = "th";
        }

        System.out.println("playing "+(game.player.handNumber + 1)+th+" hand...");
        game.player.hit(false);
        game.changeState(new SideRulesState());
    }

    /**
     * 
     */
    public void newRound(){
        game.round++;
        game.player.nHands++;
        float oldBet = game.player.hands.get(game.player.handNumber).bet;    
        game.player.hands.clear();
        hand = new Hand(0);
        nHands++; //number of dealer hands
        game.player.hands.add(new Hand(oldBet));
        game.player.handNumber = 0;
        game.changeState(new GameStart());
        if(shoe.getPlayedCards() > game.shuffle){
            shoe = new Shoe(game.shoe);
            game.player.hilo.resetCount();
            game.player.ace5.resetCount();
            game.shuffleNum--;
        }
    }

    /**
     * 
     */
    public void showHole(){
        hand.addCard(hole_card);
        game.player.hilo.Count(hole_card);
        game.player.ace5.Ace5Count(hole_card);
        System.out.println(showHand()+"("+hand.handSum()+")");
    }

    /**
     * 
     */
    public void bust()
    {
        showHole();
        
        if(hand.hasBlackjack())
        {
            game.dealerBJcount++;
            System.out.println("blackjack!!");
            insuranceCheck();
        }

        printEndScreen(false);
        newRound();
    }

    /**
     * 
     */
    public void stand(boolean print)
    {
        boolean busts = false;
        if(print)
            System.out.println("player stands");

        showHole();

        if(hand.hasBlackjack())
        {
            game.dealerBJcount++;
            System.out.println("dealer stands");
            System.out.println("blackjack!!");
            insuranceCheck();
        } 
        while(hand.handSum() < 17)
        {
            hit();
            System.out.println(showHand()+"("+hand.handSum()+")");
        }        
        
        if(hand.handSum() > 21)
        {
            busts = true;
            System.out.println("dealer busts");
        }
        else
        {
            System.out.println("dealer stands");
        }                                
        printEndScreen(busts);
        newRound();
    }   

    /**
     * 
     */
    public void insuranceCheck()
    {
        if(hand.hasBlackjack() && game.player.insured())
        {
            game.player.balance += game.player.insuranceBet*2;
            game.player.insuranceBet = -1;
            System.out.println("player wins insurance");
            return;
        }

        if(!hand.hasBlackjack() && game.player.insured())
            System.out.println("players loses insurance");

    }

    /**
     * 
     * @param dealerBust
     */
    private void printEndScreen(boolean dealerBust) 
    {
        String lastString = " and his current balance is ";

        Iterator<Hand> it = game.player.hands.iterator();
        Hand playerHand;
        int counter = 0;
         
        while (it.hasNext()) {
            playerHand = it.next();
            counter++;
            if ((playerHand.handSum() > hand.handSum() && !playerHand.busted) || dealerBust) {
                if (playerHand.hasBlackjack()) {
                    game.playerBJcount++;
                    game.winCount++;                   
                    System.out.println("blackjack!!");
                    game.player.balance += playerHand.bet * 2.5;
                    System.out.print("player wins ");
                    game.player.roundOutcome = 1; // win //*verificar com cuidado

                    if (game.player.splitted)
                        System.out.print("[" + counter + "]");

                    System.out.println(lastString + game.player.balance);
                    continue;
                }

                game.winCount++;
                game.player.balance += playerHand.bet * 2;
                System.out.print("player wins ");
                game.player.roundOutcome = 1; // win //*verificar com cuidado

                if (game.player.splitted)
                    System.out.print("[" + counter + "]");
                System.out.println(lastString + game.player.balance);

            } else if (playerHand.handSum() < hand.handSum() || playerHand.busted) {
                game.loseCount++;

                System.out.print("player loses");
                game.player.roundOutcome = -1; // lose //*verificar com cuidado

                if (game.player.splitted) {
                    System.out.print("[" + counter + "]");
                }
                System.out.println(lastString + game.player.balance);
            } else if (playerHand.handSum() == hand.handSum()) {
                game.pushCount++;

                game.player.balance += playerHand.bet;
                System.out.print("player pushes ");
                game.player.roundOutcome = 0; // push //*verificar com cuidado

                if (game.player.splitted) {
                    System.out.print("[" + counter + "]");
                }
                System.out.println(lastString + game.player.balance);
            }
        }
        System.out.println("GAMEEEE COUNTTTSSSSS------\n-----------BJcount "+game.playerBJcount+"--------winCount"+game.winCount+"-------losecount "+game.loseCount+"----------Pushcount"+game.pushCount);
    }

     /**
      * 
      */
    public String showHand()
    {
        return "dealer's hand "+hand;
    }

}