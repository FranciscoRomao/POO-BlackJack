package blackjack;
import java.util.Scanner;

public class SideRulesState implements State
{
    @Override
    public boolean play(StateContext context)
    {
        Player player = context.game.player;
        Dealer dealer = context.game.dealer;
        String action;
        int handStatus = 1;

        action = player.readPlay(2);
        if(context.game.mode == 'd')
            System.out.println("-cmd "+action);
            Scanner s = new Scanner(action);
        // try(Scanner s = new Scanner(action))
        // {
            switch (s.next())
            {
                case "$":
                    System.out.println(player.balance+"$");
                    break;
                case "i":
                    player.insure();
                    break;
                case "u":
                    player.surrender();
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
                    System.out.println("player asks for advice");
                    player.basic.advice(context.game, true);
                    player.hilo.advice(context.game, true, 2);                  
                    break;                
                case "st":
                    player.stats(); //#aqui
                    break;
                
                default:
                    System.out.println(action+": illegal command");
                    break;
            }
        // } catch (Exception e)
        // {
        //     System.out.println("deu shita");
        //     return true;
        // }

        // if(handStatus != 1 && player.balance < context.game.min_bet)
        // {
        //     System.out.println("Player doesn't have enough money to start a new game");
        //     return false;
        // }

        return !action.equals("q");
    }
}
