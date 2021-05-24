package main;
import blackjack.*;

public class Main {
    
    /** 
     * @param args
     */
    public static void main(String[] args)
    {
        Game newGame = new Game(args);
        newGame.startGame();
    }
}
