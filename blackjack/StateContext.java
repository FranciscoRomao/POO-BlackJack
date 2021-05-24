package blackjack;

import blackjack.state_pattern.GameStart;
import blackjack.state_pattern.State;
/**
 * Class that manages the states. Calls the play method of state interface, which changes depending on the game state
 */
public class StateContext {
    private State state;
    private Game game;
    /**
     * StateContext constructor
     * @param game_ The game being played
     */
    public StateContext(Game game_) {
        game = game_;
        game.setRound(0);
        state = new GameStart();
    }
    /**
     * Set the current state.
     * Normally only called by classes implementing the State interface.
     * @param newState the new state of this context
     */
    public void setState(State newState) {
        state = newState;
    }

    
    /** 
     * Method that will play the game. Depending on the state, different action will be available
     * @return boolean True if the game should continue, false otherwise
     */
    public boolean play() {        
        return state.play(this);
    }
    
    /** 
     * Returns the game that's being played 
     * @return Game Game that's being played
     */
    public Game getGame(){
        return game;
    }
}
