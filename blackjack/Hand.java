package blackjack;

import java.util.LinkedList;
import java.util.Iterator;

public class Hand
{
    private LinkedList<Card> cards;
    public float bet;
    public boolean busted;

    public Hand()
    {
        cards = new LinkedList<Card>();
        bet = 0;
        busted = false;
    }
    public Hand(float minBet)
    {
        cards = new LinkedList<Card>();
        bet = minBet;
        busted = false;
    }

    public int handSum() //Assuming all Aces value 11 //!totalmente mal feito (explicar desenho) p.ex. AA10Q continua a tirar 10 pq tem A e passou de 21 e nunca mais acaba 
    {
        int sum = 0;
        int rank = 0;

        for(int i=0; i<cards.size(); i++)
        {
            sum += cards.get(i).getValue();
        }

        if(sum > 21 && hasAce())
        {
            sum = sum - 10;
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

    public boolean hasAce() //Assuming all Aces value 11
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

    public int get(int index) //!isto aqui acho que esta obsoleto. nao seria melhor dar o valor da carta em vez do rank? e igual ok
    {
        return cards.get(index).rank;
    }

    public Card getCard(int index){
        return cards.get(index);
    }

    public int getNumCards(){
        return cards.size();
    }

    public boolean hasBlackjack(){
            return (this.handSum() == 21 && this.getNumCards() == 2);
    }

    public boolean isSplittable(){
        return (cards.getFirst().getValue() == cards.get(1).getValue() || cards.getFirst().showRank().equals(cards.get(1).showRank()));
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
