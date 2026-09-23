package com.connectfour.game;

import com.connectfour.model.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComputerPlayerTest {
    private ComputerPlayer computer;
    private Board board;

    @BeforeEach
    void setUp() {
        computer = new ComputerPlayer();
        board = new Board();
    }

    @Test
    void testChooseMoveReturnsValidColumn() {
        int col = computer.chooseMove(board);
        assertTrue(col >= 0 && col < Board.COLS);
        assertTrue(board.canDrop(col));
    }

    @Test
    void testChooseMoveOnlyReturnsColumnsWithSpace() {
        // Fill columns 0, 1, 2
        for (int c = 0; c < 3; c++) {
            for (int r = 0; r < Board.ROWS; r++) {
                board.dropDisc(c, c % 2 == 0 ? com.connectfour.model.Player.HUMAN : com.connectfour.model.Player.COMPUTER);
            }
        }

        // Computer should only choose from columns 3-6
        for (int i = 0; i < 100; i++) {
            int col = computer.chooseMove(board);
            assertTrue(col >= 3 && col <= 6);
            assertTrue(board.canDrop(col));
        }
    }

    @Test
    void testChooseMoveReturnsMinusOneWhenBoardFull() {
        for (int c = 0; c < Board.COLS; c++) {
            for (int r = 0; r < Board.ROWS; r++) {
                board.dropDisc(c, com.connectfour.model.Player.HUMAN);
            }
        }
        assertEquals(-1, computer.chooseMove(board));
    }

    @Test
    void testChooseMoveDistribution() {
        // Run many times and verify all valid columns can be chosen
        boolean[] seen = new boolean[Board.COLS];
        for (int i = 0; i < 1000; i++) {
            int col = computer.chooseMove(board);
            seen[col] = true;
        }
        for (int c = 0; c < Board.COLS; c++) {
            assertTrue(seen[c], "Column " + c + " was never chosen");
        }
    }
}
