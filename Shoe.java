package BlackJack;

import java.util.*;
import java.io.*;
import java.lang.Math;

class Shoe
{
    private LinkedList<Card> cards;

    /**
     * Shoe constructor
     * @param   numDecks    Number of decks to add to the shoe
     */
    public Shoe(int numDecks)
    {
        LinkedList<Card> cards = new LinkedList<Card>();
        LinkedList<Card> aux;

        for(int i=0; i<numDecks; i++)
        {
            aux = singleDeck();
            cards.addAll(aux);
        }

        Shuffle();
    }

    public Shoe(String fileName)
    {
        File shoeFile = new File("./shoe-file.txt");

        Scanner scan = new Scanner(shoeFile);

        

        LinkedList<Card> cards = new LinkedList<Card>();
        LinkedList<Card> aux;

        for(int i=0; i<numDecks; i++)
        {
            aux = singleDeck();
            cards.addAll(aux);
        }

        Shuffle();
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
