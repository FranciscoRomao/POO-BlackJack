package blackjack;
import java.util.Scanner;

public class SideRulesState implements State {
    @Override
    public boolean play(StateContext context){
        Player player = context.game.player;
        Dealer dealer = context.game.dealer;
        String action;
        int handStatus = 1;
        action = player.readPlay();
        try(Scanner s = new Scanner(action)) {
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
                    player.hit();
                    handStatus = dealer.handCheck(player.hands.getFirst());
                    if(handStatus == 1) //nao foi blackjack nem bust
                        context.setState(new EndGameState());
                    break;
                case "s":
                    dealer.stand();
                    break;
                default:
                    System.out.println(action+": illegal command");
                    break;
            }
        } catch (Exception e) {
            return true;
        } 
        if(handStatus != 1 && player.balance < context.game.min_bet){
            System.out.println("player doesn't have enough money to start a new game");
            return false;
        }
        return !action.equals("q");
    }
}
