package BlackJack;

class Card
    {
        public int rank;
        /*Ace - 0
        One...Ten - 1...10
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
    }
