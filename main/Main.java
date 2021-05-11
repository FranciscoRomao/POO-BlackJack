package main;
import blackjack.*;

public class Main {
    public static void main(String[] args)
    {
        Game newGame = new Game(args);
        System.out.println(newGame);
        //newGame.player.readPlay();
        newGame.dealer.DealCards();
        System.out.println(newGame.player.hand);
    }
}
