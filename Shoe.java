package BlackJack;

import java.util.*;
import java.lang.Math;

class Shoe
{
    private LinkedList<Card> cards;
    
    public Shoe(int numDecks)
    {
        //TODO
    }

    public LinkedList<Card> Shuffle()
    {
        int ncards = cards.size();
        int getCard_idx;
        LinkedList<Card> shuffled = new LinkedList<Card>();

        while(ncards > 0)
        {
            getCard_idx = (int)Math.random()*ncards;

            shuffled.add(cards.get(getCard_idx));

            cards.remove(getCard_idx);

            ncards--;
        }

        return shuffled;
    }

    public int GetCard()
    {
        //TODO
        return 0;
    }
}
