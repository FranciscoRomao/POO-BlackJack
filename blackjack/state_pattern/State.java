package blackjack.state_pattern;

import blackjack.StateContext;
/**
 * State interface, to implement the game's state pattern
 */
public interface State {
    /**
     * Handles the player/simulation/debug file inputs, and processes what to do with it.
     * At every state the player can choose to leave the program('q'), check their balance('$') and check the statistics('st')
     * @param context The state context controlling the state
     * @return True if the game should continue, false otherwise
     */
    public boolean play(StateContext context);
}
