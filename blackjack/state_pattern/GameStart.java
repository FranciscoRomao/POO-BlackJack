package blackjack.state_pattern;
import java.util.Scanner;

import blackjack.*;
/**
 * Class representing the initial state of the game
 * <p>This is where the player chooses the ammount of money to bet
 */
public class GameStart implements State
{
    @Override
    public boolean play(StateContext context)
    {
        Player player = context.getGame().getPlayer();
        boolean nextState = false;
        String action;
        if(context.getGame().getShuffleNum() == 0 && context.getGame().getMode() == 's')
            return false;
        action = player.readPlay(0);        
        try(Scanner s = new Scanner(action))
        {
            switch (s.next())
            {
                case "$":
                    if(context.getGame().getMode() == 'd')
                        System.out.println("-cmd $");
                    System.out.println(player.balance+"$");
                    break;
                case "b":
                    try
                    {
                        nextState = player.placeBet(Integer.parseInt(s.next()));
                    } 
                    catch (Exception e)
                    {
                        if(context.getGame().getMode() != 'd')
                            nextState = player.placeBet(-1);
                    }

                    if(context.getGame().getMode() == 'd' || nextState)
                        context.setState(new DealState());

                    break;

                case "ad":
                    player.getAce5().advice(context.getGame(), true);
                    player.getStdBet().advice(context.getGame(), true);
                    break;

                case "st":
                    context.getGame().stats(); //#aqui
                    break;
                case "q":
                    return false;
                default:
                    System.out.println(action+": illegal command");
                    break;
            }
        }
        catch (Exception e)
        {
            System.out.println("deu shita");
            return true;
        }
        return !action.equals("q");
    }
}