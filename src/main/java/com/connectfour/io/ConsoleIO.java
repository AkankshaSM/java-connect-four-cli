package com.connectfour.io;

import com.connectfour.game.GameEngine;
import com.connectfour.model.Board;
import com.connectfour.model.Player;

import java.util.Scanner;

/**
 * Handles all terminal input and output.
 */
public class ConsoleIO {
    private final Scanner scanner;

    public ConsoleIO() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Prompts the human player for a column choice.
     *
     * @return column index (0-6), or -1 if user wants to quit
     */
    public int promptHumanMove() {
        while (true) {
            System.out.print("Enter column (1-7) or 'q' to quit: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit")) {
                return -1;
            }

            try {
                int col = Integer.parseInt(input) - 1; // Convert to 0-indexed
                if (col >= 0 && col < Board.COLS) {
                    return col;
                } else {
                    System.out.println("Invalid column: " + input + ". Please enter 1-7.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: " + input + ". Please enter a number 1-7 or 'q' to quit.");
            }
        }
    }

    public void printBoard(Board board) {
        System.out.println();
        System.out.println(board);
        System.out.println();
    }

    public void printWelcome() {
        System.out.println("==================================");
        System.out.println("     CONNECT FOUR - Java CLI      ");
        System.out.println("==================================");
        System.out.println("You are RED (R). Computer is YELLOW (Y).");
        System.out.println("You go first. Enter column 1-7.");
        System.out.println("Type 'q' or 'quit' to exit.");
        System.out.println();
    }

    public void printTurn(Player player) {
        if (player == Player.HUMAN) {
            System.out.println("--- Your turn (RED) ---");
        } else {
            System.out.println("--- Computer's turn (YELLOW) ---");
        }
    }

    public void printMoveResult(GameEngine.MoveResult result) {
        if (!result.isSuccess()) {
            System.out.println("Error: " + result.getMessage());
        } else {
            System.out.println(result.getMessage());
        }
    }

    public void printGameOver(GameEngine.GameState state) {
        System.out.println();
        System.out.println("========== GAME OVER ==========");
        switch (state) {
            case HUMAN_WON -> System.out.println("🎉 Congratulations! You win!");
            case COMPUTER_WON -> System.out.println("🤖 Computer wins. Better luck next time!");
            case DRAW -> System.out.println("🤝 It's a draw!");
            default -> System.out.println("Game ended.");
        }
        System.out.println("===============================");
    }

    public void printComputerMove(int col) {
        System.out.println("Computer chooses column " + (col + 1) + ".");
    }

    public void close() {
        scanner.close();
    }
}