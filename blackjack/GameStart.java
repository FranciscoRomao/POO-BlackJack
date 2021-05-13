package blackjack;
import java.util.Scanner;

public class GameStart implements State{
    @Override
    public boolean play(StateContext context){
        String action;
        action = context.game.player.readPlay();
        Scanner s = new Scanner(action);
        switch (s.next()) {
            case "$":                
                System.out.println(context.game.player.balance+"$");
                break;
            case "b":
                //todo player.bet
                if(context.game.player.game.mode == 'd'){
                    try {
                        context.game.player.bet = Double.parseDouble(context.game.player.readPlay());
                    } catch (Exception e) {
                    } finally{
                        System.out.println(action+" "+context.game.player.bet);
                    }
                } else {
                    try {                                                
                        context.game.player.bet = Double.parseDouble(s.next());
                    } catch (Exception e) {
                    } finally{
                        System.out.println("b "+context.game.player.bet);
                    }                    
                }
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