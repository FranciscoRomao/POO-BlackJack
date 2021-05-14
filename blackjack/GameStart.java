package blackjack;
import java.util.Scanner;

public class GameStart implements State{
    @Override
    public boolean play(StateContext context){
        boolean nextState = false;
        String action;
        action = context.game.player.readPlay();
        Scanner s = new Scanner(action);
        switch (s.next()) {
            case "$":                
            System.out.println(context.game.player.balance+"$");
            break;
            case "b":                
                try {                       
                    nextState = context.game.player.placeBet(Double.parseDouble(s.next()));                         
                } catch (Exception e) {
                    if(context.game.mode != 'd')
                        nextState = context.game.player.placeBet(-1);  
                }
                if(context.game.mode == 'd' || nextState)             
                    context.setState(new DealState());
                break;
            default:
                System.out.println(action+": illegal command");
                break;
        }
        s.close();
        return !action.equals("q");
    }
}