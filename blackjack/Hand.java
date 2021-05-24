package blackjack;

import java.util.LinkedList;
import blackjack.deck.*;
import java.util.Iterator;
/**
 * Class that implements one hand.
 * <p>It contains a list of cards, the bet associated with the hand, and the number of aces that value 1
 */
public class Hand
{
    private LinkedList<Card> cards;
    public int bet;
    public boolean busted;
    private int aceOf_1;

    /**
     * Hand constructor
     * @param minBet The minimum bet allowed in this game 
     */
    public Hand(int minBet)
    {
        cards = new LinkedList<Card>();
        bet = minBet;
        busted = false;
        aceOf_1 = 0;
    }

    
    /** 
     * Returns the sum of the hand
     * @return int Sum of the hand
     */
    public int handSum() //Assuming all Aces value 11
    {
        int sum = 0;
        int aceCount = hasAce();
        aceOf_1 = 0;

        for(int i=0; i<cards.size(); i++)
        {
            sum += cards.get(i).getValue();
        }

        while(sum > 21 && aceCount>0)
        {
            sum -= 10;
            aceCount--;
            aceOf_1++;
        }

        return sum;
    }

    
    /** 
     * Adds a card to the hand
     * @param to_add Card to add to the hand
     */
    public void addCard(Card to_add)
    {
        this.cards.add(to_add);
    }

    
    /** 
     * @param toRemove
     */
    public void removeCard(Card toRemove){
        this.cards.remove(toRemove);
    }

    
    /** 
     * Checks the hand for aces
     * @return int Number of aces in hand
     */
    public int hasAce() //Assuming all Aces value 11
    {
        int rank = 0;
        int aceCount = 0;

        for(int i=0; i<cards.size(); i++)
        {
            rank = cards.get(i).getRank();
            
            if(rank == 1)
                aceCount++;
        }
        
        return aceCount;
    }

    
    /** 
     * Returns the rank of the specified card
     * @param index Index of the card
     * @return int Rank of the specified card
     */
    public int get(int index) 
    {
        return cards.get(index).getRank();
    }

    
    /** 
     * Returns the specified card
     * @param index Index of the card
     * @return Card Card wanted
     */
    public Card getCard(int index){
        return cards.get(index);
    }

    
    /**  
     * Returns the number of cards in the hand
     * @return int Number of cards
     */
    public int getNumCards()
    {
        return cards.size();
    }

    
    /** 
     * Checks if the hand has a blackjack or not
     * @return boolean True if there is a blackjack, false otherwise
     */
    public boolean hasBlackjack()
    {
            return (this.handSum() == 21 && this.getNumCards() == 2);
    }

    
    /**
     * Checks if the hand is splittable
     * 
     * @return Boolean that indicates if the hand is splittable
     */
    public boolean isSplittable()
    {
        return (cards.getFirst().getValue() == cards.get(1).getValue() || cards.getFirst().showRank().equals(cards.get(1).showRank()));
    }

    
    /** 
     * Get the number of aces that value 1 in the hand
     * 
     * @return Integer aceOf_1
     */
    public int getAceOf1() {
        return aceOf_1;
    }

    
    /**
     * Method Override to make the print of the asked hand
     * 
     * @return String to be printed by the print and println methods
     */
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("");
        Iterator<Card> it = cards.iterator();
        while(it.hasNext()){
            Card c = it.next();
            str.append(c.showRank()+""+c.getSuit()+" ");
        }
        return str.toString();
    }
}
