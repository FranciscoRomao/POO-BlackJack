package blackjack;
import java.util.Scanner;

public class EndGameState implements State {
    @Override
    public boolean play(StateContext context){
        Player player = context.game.player;
        Dealer dealer = context.game.dealer;
        String action;
        int handStatus = 1;
        action = player.readPlay();
        try(Scanner s = new Scanner(action)) {
            //todo implement all siderules here
            switch (s.next()) {
                case "$":
                    System.out.println(player.balance+"$");
                    break;
                case "h":
                    player.hit();
                    handStatus = dealer.bustCheck(player.hands.getFirst());
                    break;
                case "s":
                    dealer.stand();
                    handStatus = -1;
                    break;
                case "ad":
                    System.out.println("player asks for advice");
                    break;
                case "st":
                    System.out.println("player asks for stats");
                    break;
                default:
                    System.out.println(action+": illegal command");
                    break;
            }
        } catch (Exception e) {
            return true;
        } 
        if(handStatus != 1 && player.balance < context.game.min_bet){
            System.out.println("player doesn't have enough money to start a new game");
            return false;
        }
        return !action.equals("q");
    }
}
