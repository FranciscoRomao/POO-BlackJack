package blackjack;
import java.util.Scanner;

public class EndGameState implements State
{
    @Override
    public boolean play(StateContext context)
    {
        Player player = context.game.player;
        Dealer dealer = context.game.dealer;
        String action;
        int handStatus = 1;
        System.out.println("endgame");
        action = player.readPlay(3);

        if(context.game.mode != 'i')
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
                    handStatus = dealer.bustCheck(player.hands.get(player.handNumber));
                    break;

                case "s":
                    player.stand();
                    handStatus = -1;
                    break;

                case "ad":
                    System.out.println("player asks for advice");
                    player.basic.advice(context.game, true);
                    player.hilo.advice(context.game, true, 3);
                    break;

                case "st":
                    player.stats(); //#aqui
                    break;

                default:
                    System.out.println(action+": illegal command");
                    break;
            }
        } catch (Exception e) {return true;}

        // if(handStatus != 1 && player.balance < context.game.min_bet)
        // {
        //     System.out.println("Player doesn't have enough money to start a new game");
        //     return false;
        // }

        return !action.equals("q");
    }
}
