package blackjack.state_pattern;

import blackjack.StateContext;

public interface State {
    public boolean play(StateContext context);
}
