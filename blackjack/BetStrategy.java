package blackjack;

public interface BetStrategy {
    public int Advice(Player player, Hand playerHand, Card dealerCard);
}
