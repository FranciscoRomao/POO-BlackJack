package BlackJack;

import java.util.*;
import java.io.*;

class Shoe
{
    private LinkedList<Card> cards;

    /**
     * Shoe constructor
     * @param   numDecks    Number of decks to add to the shoe
     */
    public Shoe(int numDecks)
    {
        cards = new LinkedList<Card>();
        LinkedList<Card> aux;

        for(int i=0; i<numDecks; i++)
        {
            aux = singleDeck();
            cards.addAll(aux);
        }
        cards = Shuffle();
    }

    public Shoe(String fileName)
    {
        File shoeFile = new File("./shoe-file.txt");
        LinkedList<Card> cards = new LinkedList<Card>();
        String str;
        Card aux;
        
        Scanner scan = new Scanner(shoeFile).useDelimiter(" ");

        while (s.hasNext())
        {
            str = s.next();
            aux = new Card(str);

            cards.add(aux);
        }
    }

    public LinkedList<Card> singleDeck()
    {
        LinkedList<Card> deck = new LinkedList<Card>();
        Card aux;

        for(int i=0; i<13; i++)
        {
            for(int j=0; j<4; j++)
            {
                aux = new Card(i,j);
                deck.add(aux);
            }
        }

        return deck;
    }

    public LinkedList<Card> Shuffle()
    {
        int ncards = cards.size();
        int getCard_idx;
        LinkedList<Card> shuffled = new LinkedList<Card>();
        Random r = new Random();

        while(ncards > 0)
        {
            getCard_idx = r.nextInt(ncards);

            shuffled.add(cards.get(getCard_idx));

            cards.remove(getCard_idx);

            ncards--;
        }

        return shuffled;
    }

    public Card getCard()
    {
        return this.cards.pop();
    }
}
