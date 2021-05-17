package blackjack;
import java.util.Scanner;

public class EndGameState implements State {
    @Override
    public boolean play(StateContext context){
        Player player = context.game.player;
        Dealer dealer = context.game.dealer;
        String action;
        action = player.readPlay();
        Scanner s = new Scanner(action);
        //todo implement all siderules here
        switch (s.next()) {
            case "$":
                System.out.println(player.balance+"$");
                break;
            case "h":
                player.hit();
                dealer.handCheck(player.hands.getFirst());
                break;
            case "s":
                dealer.stand();
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
        s.close();
        return !action.equals("q");
    }
    
}
