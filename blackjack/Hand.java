package blackjack;

import java.util.LinkedList;
import java.util.Iterator;

public class Hand
{
    private LinkedList<Card> cards;
    public float bet;
    public boolean busted;
    private int aceOf_1;

    // public Hand()
    // {
    //     cards = new LinkedList<Card>();
    //     bet = 0;
    //     busted = false;
    // }

    public Hand(float minBet)
    {
        cards = new LinkedList<Card>();
        bet = minBet;
        busted = false;
        aceOf_1 = 0;
    }

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

    public void addCard(Card to_add)
    {
        this.cards.add(to_add);
    }

    public void removeCard(Card toRemove){
        this.cards.remove(toRemove);
    }

    public int hasAce() //Assuming all Aces value 11
    {
        int rank = 0;
        int aceCount = 0;

        for(int i=0; i<cards.size(); i++)
        {
            rank = cards.get(i).rank;
            
            if(rank == 1)
                aceCount++;
        }
        return aceCount;
    }

    public int get(int index) 
    {
        return cards.get(index).rank;
    }

    public Card getCard(int index){
        return cards.get(index);
    }

    public int getNumCards()
    {
        return cards.size();
    }

    public boolean hasBlackjack()
    {
            return (this.handSum() == 21 && this.getNumCards() == 2);
    }

    public boolean isSplittable()
    {
        return (cards.getFirst().getValue() == cards.get(1).getValue() || cards.getFirst().showRank().equals(cards.get(1).showRank()));
    }

    public int getAceOf1() {
        return aceOf_1;
    }

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
