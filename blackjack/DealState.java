package blackjack;
import java.util.Scanner;

public class DealState implements State{    
    private boolean firstInput = true;
    @Override
    public boolean play(StateContext context){
        Player player = context.game.player;
        String action;
        action = player.readPlay();
        try(Scanner s = new Scanner(action)) {
            switch (s.next()) {
                case "$":
                    if(context.game.mode == 'd' && firstInput)
                        player.placeBet(-1);
                    System.out.println(player.balance+"$");
                    break;
                case "d":
                    if(context.game.mode == 'd' && firstInput)
                        player.placeBet(-1);
                    context.game.dealer.dealCards();
                    context.setState(new SideRulesState());
                    break;
                default:
                    if(context.game.mode == 'd' && firstInput){                    
                        try {
                            if(!player.placeBet(Float.parseFloat(action))){
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
        } catch (Exception e) {
            return true;
        } 
        return !action.equals("q");
    }
}
