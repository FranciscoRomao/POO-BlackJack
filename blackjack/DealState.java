package blackjack;
import java.util.Scanner;

public class DealState implements State{    
    private boolean firstInput = true;
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
                if(context.game.mode == 'd' && firstInput){                    
                    try {
                        context.game.player.bet = Double.parseDouble(action);  
                        System.out.println("b "+context.game.player.bet);    
                        break;                  
                    } catch (Exception e) {
                    }
                }
                System.out.println(action+": illegal command");
                break;
        }
        firstInput = false;
        s.close();
        return !action.equals("q");
    }
}
