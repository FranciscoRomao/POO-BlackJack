package blackjack;

public interface State {
    public boolean play(StateContext context);
}
