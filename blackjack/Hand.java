package blackjack;

import java.util.LinkedList;
import java.util.Iterator;

public class Hand
{
    private LinkedList<Card> cards;
    public int bet;

    public Hand()
    {
        cards = new LinkedList<Card>();
        bet = 0;
    }

    public int handSum() //Assuming all Aces value 11
    {
        int sum = 0;
        int rank = 0;

        for(int i=0; i<cards.size(); i++)
        {
            sum += cards.get(i).getValue();
        }

        if(sum > 21 && HandhasAce())
        {
            sum = sum - 10;
        }

        return sum;
    }

    public void addCard(Card to_add)
    {
        this.cards.add(to_add);
    }

    public boolean HandhasAce() //Assuming all Aces value 11
    {
        int rank = 0;

        for(int i=0; i<cards.size(); i++)
        {
            rank = cards.get(i).rank;
            
            if(rank == 1)
                return true;
        }
        return false;
    }

    public int get(int index)
    {
        return cards.get(index).rank;
    }

    public int getNumCards(){
        return cards.size();
    }

    public boolean hasBlackjack(){
            return (this.handSum() == 21 && this.getNumCards() == 2);
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
