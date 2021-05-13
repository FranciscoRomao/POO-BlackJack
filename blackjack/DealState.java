package blackjack;
import java.util.Scanner;

public class DealState implements State{
    @Override
    public boolean play(StateContext context){
        String action;
        action = context.game.player.readPlay();
        Scanner s = new Scanner(action);
        switch (s.next()) {
            case "$":
                System.out.println(context.game.player.balance+"$");
                break;
            case "d":
                context.game.dealer.DealCards();
                System.out.println(context.game.dealer.showHand());
                System.out.println(context.game.player.showHand());
                context.setState(new SideRulesState());
                break;
            default:
                System.out.println(action+": illegal command");
                break;
        }
        return !action.equals("q");
    }
}
