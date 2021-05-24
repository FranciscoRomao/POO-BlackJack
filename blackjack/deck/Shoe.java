package blackjack.deck;

import java.util.*;
import java.io.*;
/**
 * Shoe class containing the cards that make the whole shoe
 */
public class Shoe
{
    private LinkedList<Card> cards;
    private int totalCards;

    /**
     * Shoe constructor when creating shoe automatically
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
    /**
     * Shoe constructor when creating shoe from a file
     * @param fileName Name of the file that contains the shoe
     */
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
     * Creates a single deck of ordered cards
     * @return LinkedList<Card> List of cards that make the deck
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
     * Shuffles the shoe
     * @return LinkedList<Card> List of shuffled cards
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
     * Returns the first card of the shoe
     * @return Card First card of the shoe
     */
    public Card getCard()
    {
        return cards.pop();
    }

    
    /** 
     * Indicates the number of cards that have been played
     * @return int Number of played cards
     */
    public int getPlayedCards()
    {
        return totalCards - cards.size();
    }

    
    /** 
     * Returns the ammount of cards left in the shoe
     * @return int Number of cards in shoe
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
