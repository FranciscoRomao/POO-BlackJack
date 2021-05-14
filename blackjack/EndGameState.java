package blackjack;
import java.util.Scanner;

public class EndGameState implements State {
    @Override
    public boolean play(StateContext context){
        String action;
        action = context.game.player.readPlay();
        Scanner s = new Scanner(action);
        //todo implement all siderules here
        switch (s.next()) {
            case "$":
                System.out.println(context.game.player.balance+"$");
                break;
            case "h":
                System.out.println("player hits");
                break;
            case "s":
                System.out.println("player stands");
                //todo finish hand method
                context.setState(new GameStart());
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
