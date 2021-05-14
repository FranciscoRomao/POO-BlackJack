package blackjack;
import java.util.Scanner;

public class DealState implements State{    
    private boolean firstInput = true;
    @Override
    public boolean play(StateContext context){
        boolean goBack = false;
        String action;
        action = context.game.player.readPlay();
        Scanner s = new Scanner(action);
        switch (s.next()) {
            case "$":
                if(context.game.mode == 'd' && firstInput)
                    context.game.player.placeBet(-1);
                System.out.println(context.game.player.balance+"$");
                break;
            case "d":
                if(context.game.mode == 'd' && firstInput)
                    context.game.player.placeBet(-1);
                context.game.dealer.DealCards();
                System.out.println(context.game.dealer.showHand());
                System.out.println(context.game.player.showHand());
                context.setState(new SideRulesState());
                break;
            default:
                if(context.game.mode == 'd' && firstInput){                    
                    try {
                        if(!context.game.player.placeBet(Double.parseDouble(action))){
                            context.setState(new GameStart());
                        }
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
