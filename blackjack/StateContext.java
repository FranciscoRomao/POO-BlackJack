package blackjack;

import blackjack.state_pattern.GameStart;
import blackjack.state_pattern.State;

public class StateContext {
    private State state;
    private Game game;
    
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
     * @return boolean
     */
    public boolean play() {        
        return state.play(this);
    }
    
    /** 
     * @return Game
     */
    public Game getGame(){
        return game;
    }
}
