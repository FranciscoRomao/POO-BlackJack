package blackjack;
import java.util.Scanner;

public class DealState implements State
{    
    private boolean firstInput = true;
    
    @Override
    public boolean play(StateContext context)
    {
        Player player = context.game.player;
        String action;

        action = player.readPlay(1);
        
        if(context.game.mode != 'i' )
            System.out.println("-cmd "+action);
        Scanner s = new Scanner(action);
       // try(Scanner s = new Scanner(action))
       // {
            switch (s.next())
            {
                case "$":
                    if(context.game.mode == 'd' && firstInput)
                        player.placeBet(-1);

                    System.out.println(player.balance+"$");
                    break;

                case "d":
                    if(context.game.mode == 'd' && firstInput)
                        player.placeBet(-1);

                    context.game.dealer.dealCards();
                    context.setState(new SideRulesState());
                    break;

                case "st":
                    player.stats(); //#aqui
                    break;

                default:
                    if(context.game.mode == 'd' && firstInput)
                    {         
                        try
                        {
                            if(!player.placeBet(Integer.parseInt(action)))
                                context.setState(new GameStart());

                            break;                  
                        } catch (Exception e){}
                    }
                    System.out.println(action+": illegal command");
                    break;
            }
        // } catch (Exception e)
        // {
        //     System.out.println("shitaaaaa");
        //     return true;
        // } 
        firstInput = false;
        return !action.equals("q");
    }
}
