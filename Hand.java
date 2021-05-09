package BlackJack;

import java.util.LinkedList;

public class Hand
{
    private LinkedList<Card> cards;

    public Hand()
    {
        cards = new LinkedList<Card>();
    }

    public int HandSum() //Assuming all Aces value 11
    {
        int sum = 0;
        int rank = 0;

        for(int i=0; i<cards.size(); i++)
        {
            rank = cards.get(i).rank;
            
            if(rank == 0)
            {
                sum += 11;   
            }
            else if(rank>=2 && rank <=10)
            {
                sum+=rank;
            }
            else
            {
                sum+=10;
            }
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
            
            if(rank == 0)
                return true;
        }

        return false;
    }

    public int get(int index)
    {
        return cards.get(index).rank;
    }
}
