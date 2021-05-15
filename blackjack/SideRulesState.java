package blackjack;
import java.util.Scanner;

public class SideRulesState implements State {
    @Override
    public boolean play(StateContext context){
        Player player = context.game.player;
        String action;
        action = player.readPlay();
        Scanner s = new Scanner(action);
        //todo implement all siderules here
        switch (s.next()) {
            case "$":
                System.out.println(player.balance+"$");
                break;
            case "i":
                System.out.println("player insures");
                break;
            case "u":
                System.out.println("player surrenders");
                //todo finish hand method
                context.setState(new GameStart());
                break;
            case "p":
                System.out.println("player splits");
                break;
            case "2":
                System.out.println("player doubles");
                break;
            case "h":
                if(player.hit()){
                    context.game.dealer.endRound(player.hands.get(player.handNumber).handSum());
                    if(player.nHands == 0){
                        player.nHands++;
                        context.setState(new GameStart());
                    }
                    else {
                        context.game.dealer.dealCards();
                        context.setState(new SideRulesState());
                    }
                    break;
                }
                context.setState(new EndGameState());
                break;
            case "s":
                System.out.println("player stands");
                //todo finish hand method
                context.setState(new GameStart());
                break;
            default:
                System.out.println(action+": illegal command");
                break;
        }
        s.close();
        return !action.equals("q");
    }
}
