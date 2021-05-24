package blackjack;

import java.util.LinkedList;
import blackjack.deck.*;
import java.util.Iterator;

public class Hand
{
    private LinkedList<Card> cards;
    public int bet;
    public boolean busted;
    private int aceOf_1;

    public Hand(int minBet)
    {
        cards = new LinkedList<Card>();
        bet = minBet;
        busted = false;
        aceOf_1 = 0;
    }

    
    /** 
     * @return int
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
     * @param to_add
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
     * @return int
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
     * @param index
     * @return int
     */
    public int get(int index) 
    {
        return cards.get(index).getRank();
    }

    
    /** 
     * @param index
     * @return Card
     */
    public Card getCard(int index){
        return cards.get(index);
    }

    
    /** 
     * @return int
     */
    public int getNumCards()
    {
        return cards.size();
    }

    
    /** 
     * @return boolean
     */
    public boolean hasBlackjack()
    {
            return (this.handSum() == 21 && this.getNumCards() == 2);
    }

    
    /** 
     * @return boolean
     */
    public boolean isSplittable()
    {
        return (cards.getFirst().getValue() == cards.get(1).getValue() || cards.getFirst().showRank().equals(cards.get(1).showRank()));
    }

    
    /** 
     * @return int
     */
    public int getAceOf1() {
        return aceOf_1;
    }

    
    /** 
     * @return String
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
