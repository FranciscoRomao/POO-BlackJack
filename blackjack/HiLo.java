/*package blackjack;

import java.util.LinkedList;

public class HiLo implements Strategy
{
	private int count;

	public HiLo()
	{
		count = 0;
	}

    @Override
    public char playAdvice(Player player, Hand playerHand, Card dealerCard)
    {
        char foo = '\0';

		if(playerHand.handSum() == 16)











        return foo;
    }

	public void Count(Card card)
	{
		int cardValue = card.getValue();

		if(cardValue >= 2 && cardValue <=6)
		{
			count++;
		}
		else
		{
			if(cardValue==1 || cardValue == 10)
			{
				count--;
			}
		}
	}

	public int getRunningCount()
	{
		return count;
	}

	public float getTrueCount(Shoe shoe)
	{
		float num_decks = shoe.getNumCards()/52;

		float true_count = count/num_decks;

		return (float)(Math.round(true_count/0.5) * 0.5);
	}
}*/
