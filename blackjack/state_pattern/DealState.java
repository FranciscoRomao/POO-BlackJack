package blackjack.state_pattern;

import java.util.Scanner;
import blackjack.*;

/**
 * Class representing the state of the game right after the player bets, and is waiting for the deal command
 * <p>The player can also check their balance
 */
public class DealState implements State
{    
    private boolean firstInput = true;
    
    @Override
    public boolean play(StateContext context)
    {
        Player player = context.getGame().getPlayer();
        String action;
        action = player.readPlay(1);
        if(context.getGame().getMode() == 'd' && !firstInput)
            System.out.println("-cmd "+action);
        Scanner s = new Scanner(action);
       // try(Scanner s = new Scanner(action))
       // {
            switch (s.next())
            {
                case "$":
                    if(context.getGame().getMode() == 'd' && firstInput){
                        System.out.println("-cmd b");
                        player.placeBet(-1);
                        System.out.println("-cmd $");
                    }

                    System.out.println(player.balance+"$");
                    break;

                case "d":
                    if(context.getGame().getMode() == 'd' && firstInput){
                        System.out.println("-cmd b");
                        player.placeBet(-1);
                        System.out.println("-cmd d");
                    }

                    context.getGame().getDealer().dealCards();
                    context.setState(new SideRulesState());
                    break;

                case "st":
                    player.stats(); //#aqui
                    break;
                case "q":
                    return false;
                default:
                    if(context.getGame().getMode() == 'd' && firstInput)
                    {         
                        try
                        {
                            System.out.println("-cmd b "+Integer.parseInt(action));
                            if(!player.placeBet(Integer.parseInt(action)))
                                context.setState(new GameStart());

                            break;                  
                        } catch (Exception e){}
                    }
                    if(context.getGame().getMode() != 's')
                        System.out.println(action+": illegal command");
                    break;
            }
        // } catch (Exception e)
        // {
        //     System.out.println("shitaaaaa");
        //     return true;
        // } 
        firstInput = false;
        return true;
    }
}
