package blackjack;
import java.util.Scanner;
//!dá erro se no terminal escrever \n qq
public class GameStart implements State{
    @Override
    public boolean play(StateContext context){
        Player player = context.game.player;
        boolean nextState = false;
        String action;
        action = player.readPlay();
        if(context.game.mode == 'd')
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
                    //System.out.println("ace-five\t\tbet"+player.betStrat.Advice()) //!preciso de ajuda nao tou a entender ja tou com sono
                    break;
                default:
                    System.out.println(action+": illegal command");
                    break;
            }
        } catch (Exception e) {
            return true;
        } 
        return !action.equals("q");
    }
}