package com.connectfour;

import com.connectfour.game.GameEngine;
import com.connectfour.io.ConsoleIO;
import com.connectfour.model.Player;

/**
 * Main entry point for the Connect Four CLI game.
 */
public class Main {
    public static void main(String[] args) {
        ConsoleIO io = new ConsoleIO();
        GameEngine engine = new GameEngine();

        io.printWelcome();
        io.printBoard(engine.getBoard());

        while (engine.getState() == GameEngine.GameState.IN_PROGRESS) {
            io.printTurn(engine.getCurrentPlayer());

            if (engine.getCurrentPlayer() == Player.HUMAN) {
                int col = io.promptHumanMove();
                if (col == -1) {
                    System.out.println("Thanks for playing! Goodbye.");
                    break;
                }

                GameEngine.MoveResult result = engine.makeMove(col);
                io.printMoveResult(result);

                if (result.isSuccess()) {
                    io.printBoard(engine.getBoard());
                }
            } else {
                // Computer's turn
                GameEngine.MoveResult result = engine.makeComputerMove();
                if (result.isSuccess()) {
                    io.printComputerMove(engine.getLastMoveCol());
                    io.printMoveResult(result);
                    io.printBoard(engine.getBoard());
                } else {
                    io.printMoveResult(result);
                }
            }
        }

        if (engine.getState() != GameEngine.GameState.IN_PROGRESS) {
            io.printGameOver(engine.getState());
        }

        io.close();
    }
}