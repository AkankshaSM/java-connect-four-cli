package com.connectfour.game;

import com.connectfour.model.Board;
import com.connectfour.model.Player;

/**
 * Core game logic - separates rules from I/O.
 */
public class GameEngine {
    private final Board board;
    private final ComputerPlayer computerPlayer;
    private Player currentPlayer;
    private GameState state;
    private int lastMoveRow;
    private int lastMoveCol;

    public GameEngine() {
        this.board = new Board();
        this.computerPlayer = new ComputerPlayer();
        this.currentPlayer = Player.HUMAN; // Human always starts
        this.state = GameState.IN_PROGRESS;
        this.lastMoveRow = -1;
        this.lastMoveCol = -1;
    }

    /**
     * Attempts to make a move for the current player in the given column.
     *
     * @param col column index (0-6)
     * @return MoveResult indicating the outcome
     */
    public MoveResult makeMove(int col) {
        if (state != GameState.IN_PROGRESS) {
            return new MoveResult(false, "Game is already over");
        }
        if (col < 0 || col >= Board.COLS) {
            return new MoveResult(false, "Invalid column: " + (col + 1) + ". Choose 1-7.");
        }
        if (!board.canDrop(col)) {
            return new MoveResult(false, "Column " + (col + 1) + " is full. Choose another.");
        }

        int row = board.dropDisc(col, currentPlayer);
        lastMoveRow = row;
        lastMoveCol = col;

        if (board.checkWin(row, col)) {
            state = currentPlayer == Player.HUMAN ? GameState.HUMAN_WON : GameState.COMPUTER_WON;
            return new MoveResult(true, currentPlayer + " wins!");
        }

        if (board.isFull()) {
            state = GameState.DRAW;
            return new MoveResult(true, "It's a draw!");
        }

        currentPlayer = currentPlayer.opponent();
        return new MoveResult(true, "Move accepted");
    }

    /**
     * Makes a move for the computer player.
     *
     * @return MoveResult indicating the outcome
     */
    public MoveResult makeComputerMove() {
        if (state != GameState.IN_PROGRESS) {
            return new MoveResult(false, "Game is already over");
        }
        if (currentPlayer != Player.COMPUTER) {
            return new MoveResult(false, "Not computer's turn");
        }

        int col = computerPlayer.chooseMove(board);
        if (col == -1) {
            return new MoveResult(false, "No valid moves for computer");
        }

        return makeMove(col);
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public GameState getState() {
        return state;
    }

    public int getLastMoveRow() {
        return lastMoveRow;
    }

    public int getLastMoveCol() {
        return lastMoveCol;
    }

    /**
     * Result of a move attempt.
     */
    public static class MoveResult {
        private final boolean success;
        private final String message;

        public MoveResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }

    /**
     * Game state enumeration.
     */
    public enum GameState {
        IN_PROGRESS,
        HUMAN_WON,
        COMPUTER_WON,
        DRAW
    }
}