package blackjack;

public class GameStart implements State{
    @Override
    public boolean play(StateContext context){
        //Available commands: bet, $
        String action;
        action = context.player.readPlay();
        switch (action.charAt(0)) {
            case '$':
                System.out.println(context.player.balance);
                break;
            case 'b':
                System.out.println(action);
                //todo observo o action todo para definir o tamanho da aposta
                //todo set proximo estado
            default:
                //return true;
                break;
        }
        return !action.equals("q");
    }
}
