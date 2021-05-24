package blackjack.state_pattern;
import java.util.Scanner;

import blackjack.*;
/**
 * Class representing the final state of the game, where no more siderules are available to pick
 */
public class EndGameState implements State
{
    @Override
    public boolean play(StateContext context)
    {
        Player player = context.getGame().getPlayer();
        Dealer dealer = context.getGame().getDealer();
        String action;
        int handStatus = 1;
        action = player.readPlay(3);

        if(context.getGame().getMode() == 'd')
            System.out.println("-cmd "+action);

        try(Scanner s = new Scanner(action))
        {
            switch (s.next())
            {
                case "$":
                    System.out.println(player.balance+"$");
                    break;

                case "h":
                    player.hit(true);
                    handStatus = dealer.bustCheck(player.getHands().get(player.handNumber));
                    break;

                case "s":
                    player.stand();
                    handStatus = -1;
                    break;

                case "ad":
                    if(context.getGame().getMode() != 's')
                        System.out.println("player asks for advice");
                    player.getBasic().advice(context.getGame(), true);
                    player.getHilo().advice(context.getGame(), true, 3);
                    break;

                case "st":
                    player.stats(); //#aqui
                    break;
                case "q":
                    return false;
                default:
                    if(context.getGame().getMode() != 's')
                        System.out.println(action+": illegal command");
                    break;
            }
        } catch (Exception e) {return true;}

        // if(handStatus != 1 && player.balance < context.getGame().min_bet)
        // {
        //     System.out.println("Player doesn't have enough money to start a new game");
        //     return false;
        // }

        return !action.equals("q");
    }
}
