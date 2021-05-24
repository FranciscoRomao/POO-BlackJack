package blackjack.deck;

import java.util.*;
import java.io.*;

public class Shoe
{
    private LinkedList<Card> cards;
    private int totalCards;

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
        totalCards = cards.size(); 
    }

    public Shoe(String fileName)
    {
        File shoeFile = new File(fileName);
        cards = new LinkedList<Card>();
        String str;
        Card aux;
        try(Scanner scan = new Scanner(shoeFile).useDelimiter(" ")) {
            while (scan.hasNext())
            {
                str = scan.next();
                aux = new Card(str);
                cards.add(aux);
            }
        } catch (Exception e)
        {
            System.out.println("Error reading shoe file");
            System.exit(1);
        }
        totalCards = cards.size();
    }

    
    /** 
     * @return LinkedList<Card>
     */
    public LinkedList<Card> singleDeck()
    {
        LinkedList<Card> deck = new LinkedList<Card>();
        Card aux;

        for(int i=1; i<=13; i++)
        {
            for(int j=0; j<4; j++)
            {
                aux = new Card(i,j);
                deck.add(aux);
            }
        }

        return deck;
    }

    
    /** 
     * @return LinkedList<Card>
     */
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

    
    /** 
     * @return Card
     */
    public Card getCard()
    {
        return cards.pop();
    }

    
    /** 
     * @return int
     */
    public int getPlayedCards()
    {
        return totalCards - cards.size();
    }

    
    /** 
     * @return int
     */
    public int getNumCards()
    {
        return cards.size();
    }

    
    /** 
     * @return String
     */
    @Override
    public String toString()
    {
        StringBuilder str = new StringBuilder();
        str.append("");
        Iterator<Card> it = cards.iterator();
        while(it.hasNext())
        {
            Card c = it.next();
            str.append(c.showRank()+""+c.getSuit()+" ");
            str.append("\n");
        }
        return str.toString();
    }
}
