 //package blackjack;
//
 //class Std_bet implements Strategy
 //{
 //   private int start_bet;
//
 //   public Std_bet() 
 //   {
 //       startBet = minBet;        
 //   }   
//
 //   @Override
 //   public int Advice(Hand playerHand, Card dealerCard) //mandar vir para aqui o minBet, maxBet e Bet = lastBet
 //   {
 //       int foo = 0;
 //       //TODO
 //       if(betCount == 0) { //para saber se ja fez alguma aposta ou nao
 //           return startBet;
 //       } else {
 //           return Increase_Decrease(roundOutcome, lastBet, minBet, maxBet);
 //       }        
 //   }
//
 //   public int Increase_Decrease(int roundOutcome, int lastBet, int minBet, int maxBet)
 //   {
 //       int newBet = 0;
//
 //       if (roundOutcome == 1) // win
 //       { 
 //           newBet = lastBet + minBet;
 //           if(newBet > maxBet) newBet = maxBet;
 //       } 
 //       else if (roundOutcome == 0) // push
 //       { 
 //           newBet = lastBet;
 //       }
 //       else // lost
 //       { 
 //           newBet = lastBet - minBet;
 //           if(newBet < minBet) newBet = minBet;
 //       }
//
 //       return newBet;
 //   }
 //}
