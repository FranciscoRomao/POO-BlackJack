package blackjack;

public class StateContext {
    private State state;
    public Player player;
    
    public StateContext(Player player_) {
        player = player_;
        System.out.println(player);
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
