package com.connectfour.game;

import com.connectfour.model.Board;
import com.connectfour.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Computer player that selects random legal moves.
 */
public class ComputerPlayer {
    private final Random random = new Random();

    /**
     * Chooses a random valid column for the computer's move.
     *
     * @param board the current game board
     * @return a valid column index (0-6), or -1 if no moves available
     */
    public int chooseMove(Board board) {
        List<Integer> validColumns = new ArrayList<>();
        for (int c = 0; c < Board.COLS; c++) {
            if (board.canDrop(c)) {
                validColumns.add(c);
            }
        }
        if (validColumns.isEmpty()) {
            return -1;
        }
        return validColumns.get(random.nextInt(validColumns.size()));
    }
}