package blackjack;

class Card
{
    public int rank;
    /*Ace - 0
    Two...Ten - 2...10
    Jacks - 11
    Queen - 12
    King - 13*/

    public int suit;
    /*Clubs - 0
    Spades - 1
    Diamonds - 2
    Hearts - 3*/

    public Card(int rank, int suit)
    {
        this.rank = rank;
        this.suit = suit;
    }

    public Card(String cardCode)//tipo 2S
    {
        switch (cardCode.charAt(0))
        {
            case 'A':
                this.rank = 1; break;

            case 'J':
                this.rank = 11; break;

            case 'Q':
                this.rank = 12; break;

            case 'K':
                this.rank = 13; break;

            default:
                this.rank = Character.getNumericValue(cardCode.charAt(0));
                break;
        }

        switch (cardCode.charAt(1))
        {
            case 'C':
                this.suit = 0; break;

            case 'S':
                this.suit = 1; break;

            case 'D':
                this.suit = 2; break;

            case 'H':
                this.suit = 3; break;
        
            default:
                break;
        }
    }

    public char getSuit(){
        char retSuit = '\0';
        switch (suit)
        {
            case 0:
                retSuit = 'C'; break;

            case 1:
                retSuit = 'S'; break;

            case 2:
                retSuit = 'D'; break;

            case 3:
                retSuit = 'H'; break;
        
            default:
                break;
        }
        return retSuit;
    }
    public String showRank(){
        char retSuit = '\0';
        switch (rank)
        {
            case 1:
                retSuit = 'A'; break;

            case 11:
                retSuit = 'J'; break;

            case 12:
                retSuit = 'Q'; break;

            case 13:
                retSuit = 'K'; break;
        
            default:
                return String.valueOf(rank);                
        }
        return Character.toString(retSuit);
    }

    public int getValue()
    {
        if(rank == 1)
        {
            return 11;
        }
        else if(rank > 1 && rank <= 10)
        {
            return rank;
        }
        else
        {
            return 10;
        }
    }
}
