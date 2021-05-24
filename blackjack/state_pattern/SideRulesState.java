package blackjack.state_pattern;
import java.util.Scanner;

import blackjack.*;
/**
 * Class representing the state of the game after the deal command
 * <p>This is where the player can decide to pick any of the available siderules
 */
public class SideRulesState implements State
{
    @Override
    public boolean play(StateContext context)
    {
        Player player = context.getGame().getPlayer();
        Dealer dealer = context.getGame().getDealer();
        String action;
        int handStatus = 1;

        action = player.readPlay(2);
        if(context.getGame().getMode() == 'd')
            System.out.println("-cmd "+action);

        try(Scanner s = new Scanner(action))
        {
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
                    handStatus = dealer.bustCheck(player.getHands().get(player.handNumber));
                    if(handStatus == 1) //nao foi blackjack nem bust
                        context.setState(new EndGameState());
                    break;
                case "s":
                    player.stand();
                    break;
                case "ad":
                    System.out.println("player asks for advice");
                    player.getBasic().advice(context.getGame(), true);
                    player.getHilo().advice(context.getGame(), true, 2);                  
                    break;                
                case "st":
                    context.getGame().stats(); 
                    break;
                case "q":
                    return false;
                default:
                    System.out.println(action+": illegal command");
                    break;
            }
        } catch (Exception e)
        {
            System.out.println("deu shita");
            return true;
        }

        // if(handStatus != 1 && player.balance < context.getGame().min_bet)
        // {
        //     System.out.println("Player doesn't have enough money to start a new game");
        //     return false;
        // }

        return !action.equals("q");
    }
}
