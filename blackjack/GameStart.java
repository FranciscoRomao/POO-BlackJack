package blackjack;
import java.util.Scanner;

public class GameStart implements State{
    @Override
    public boolean play(StateContext context){
        Player player = context.game.player;
        boolean nextState = false;
        String action;
        if(context.game.shuffleNum == 0 && context.game.mode == 's'){
            return true;
        }
        action = player.readPlay(0);
        if(context.game.mode != 'i')
            System.out.println("-cmd "+action);
        
        try(Scanner s = new Scanner(action)) {
            switch (s.next()) {
                case "$":                
                System.out.println(player.balance+"$");
                break;
                case "b":                
                    try {                       
                        nextState = player.placeBet(Float.parseFloat(s.next()));                         
                    } catch (Exception e) {
                        if(context.game.mode != 'd')
                            nextState = player.placeBet(-1);  
                    }
                    if(context.game.mode == 'd' || nextState)             
                        context.setState(new DealState());
                    break;
                case "ad":                    
                    player.ace5.Advice(context.game, true);
                    player.stdbet.Advice(context.game, true);
                    break;
                default:
                    System.out.println(action+": illegal command");
                    break;
            }
        } catch (Exception e) {
            System.out.println("deu shita");
            return true;
        } 
        return !action.equals("q");
    }
}