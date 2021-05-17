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
    public int Advice(Hand playerHand, Card dealerCard)
    {
        int foo = 0;

		if(playerHand.handSum() == 16)











        return foo;
    }

	public void Count(Card card)
	{
		int rank = card.rank;

		if(rank >= 2 && rank <=6)
		{
			count++;
		}
		else
		{
			if(rank==0 || (rank >= 10 && rank <=13))
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
