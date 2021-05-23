package blackjack;

public class StateContext {
    private State state;
    public Game game;
    
    public StateContext(Game game_) {
        game = game_;
        game.round = 0;
        state = new GameStart();
    }
    /**
     * Set the current state.
     * Normally only called by classes implementing the State interface.
     * @param newState the new state of this context
     */
    void setState(State newState) {
        state = newState;
    }

    public boolean play() {        
        return state.play(this);
    }
}
