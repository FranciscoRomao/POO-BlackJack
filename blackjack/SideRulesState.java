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
        if(context.game.mode == 'd')
            System.out.println("-cmd "+action);
        try(Scanner s = new Scanner(action)) {
            //todo implement all siderules here
            switch (s.next()) {
                case "$":
                    System.out.println(player.balance+"$");
                    break;
                case "i":
                    if(player.splitted){
                        System.out.println("i: illegal command");
                        break;
                    }
                    player.insure();
                    break;
                case "u":
                    player.surrender();
                    context.setState(new GameStart());
                    break;
                case "p":
                    player.split();
                    break;
                case "2":
                    player.doubleDown();
                    break;
                case "h":
                    player.hit(true);
                    handStatus = dealer.bustCheck(player.hands.get(player.handNumber));
                    if(handStatus == 1) //nao foi blackjack nem bust
                        context.setState(new EndGameState());
                    break;
                case "s":
                    player.stand();
                    break;
                case "ad":
                    //*fazer a logicas antes de ler e decidir o que imprime dependendo das tabelas e tal
                    //System.out.println("basic\t\t"+basicAdvice);
                    //System.out.println("hi-lo\t\t"+hiloAdvice);
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
