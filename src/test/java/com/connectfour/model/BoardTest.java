package com.connectfour.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    void testInitialBoardIsEmpty() {
        for (int r = 0; r < Board.ROWS; r++) {
            for (int c = 0; c < Board.COLS; c++) {
                assertEquals(Cell.EMPTY, board.getCell(r, c));
            }
        }
        assertFalse(board.isFull());
    }

    @Test
    void testDropDiscInEmptyColumn() {
        int row = board.dropDisc(3, Player.HUMAN);
        assertEquals(0, row);
        assertEquals(Cell.HUMAN, board.getCell(0, 3));
        assertEquals(1, board.columnHeights[3]);
    }

    @Test
    void testDropDiscStacksVertically() {
        board.dropDisc(2, Player.HUMAN);
        int row = board.dropDisc(2, Player.COMPUTER);
        assertEquals(1, row);
        assertEquals(Cell.HUMAN, board.getCell(0, 2));
        assertEquals(Cell.COMPUTER, board.getCell(1, 2));
    }

    @Test
    void testDropDiscInFullColumnReturnsMinusOne() {
        // Fill column 0 completely
        for (int i = 0; i < Board.ROWS; i++) {
            board.dropDisc(0, Player.HUMAN);
        }
        int row = board.dropDisc(0, Player.COMPUTER);
        assertEquals(-1, row);
    }

    @Test
    void testCanDropReturnsTrueForEmptyColumn() {
        assertTrue(board.canDrop(0));
        assertTrue(board.canDrop(6));
    }

    @Test
    void testCanDropReturnsFalseForFullColumn() {
        for (int i = 0; i < Board.ROWS; i++) {
            board.dropDisc(4, Player.HUMAN);
        }
        assertFalse(board.canDrop(4));
    }

    @Test
    void testCanDropReturnsFalseForOutOfBounds() {
        assertFalse(board.canDrop(-1));
        assertFalse(board.canDrop(7));
    }

    @Test
    void testHorizontalWin() {
        // Human wins horizontally at bottom row
        board.dropDisc(0, Player.HUMAN);
        board.dropDisc(1, Player.HUMAN);
        board.dropDisc(2, Player.HUMAN);
        int row = board.dropDisc(3, Player.HUMAN);
        assertTrue(board.checkWin(row, 3));
    }

    @Test
    void testVerticalWin() {
        // Human wins vertically in column 3
        board.dropDisc(3, Player.HUMAN);
        board.dropDisc(3, Player.HUMAN);
        board.dropDisc(3, Player.HUMAN);
        int row = board.dropDisc(3, Player.HUMAN);
        assertTrue(board.checkWin(row, 3));
    }

    @Test
    void testDiagonalBackslashWin() {
        // Human wins on diagonal \ (bottom-left to top-right)
        // Build: (0,0), (1,1), (2,2), (3,3)
        // Need to stack appropriately
        board.dropDisc(0, Player.HUMAN);        // (0,0)
        board.dropDisc(1, Player.COMPUTER);
        board.dropDisc(1, Player.HUMAN);        // (1,1)
        board.dropDisc(2, Player.COMPUTER);
        board.dropDisc(2, Player.COMPUTER);
        board.dropDisc(2, Player.HUMAN);        // (2,2)
        board.dropDisc(3, Player.COMPUTER);
        board.dropDisc(3, Player.COMPUTER);
        board.dropDisc(3, Player.COMPUTER);
        int row = board.dropDisc(3, Player.HUMAN); // (3,3)
        assertTrue(board.checkWin(row, 3));
    }

    @Test
    void testDiagonalSlashWin() {
        // Human wins on diagonal / (bottom-right to top-left)
        // Build: (0,6), (1,5), (2,4), (3,3)
        board.dropDisc(6, Player.HUMAN);        // (0,6)
        board.dropDisc(5, Player.COMPUTER);
        board.dropDisc(5, Player.HUMAN);        // (1,5)
        board.dropDisc(4, Player.COMPUTER);
        board.dropDisc(4, Player.COMPUTER);
        board.dropDisc(4, Player.HUMAN);        // (2,4)
        board.dropDisc(3, Player.COMPUTER);
        board.dropDisc(3, Player.COMPUTER);
        board.dropDisc(3, Player.COMPUTER);
        int row = board.dropDisc(3, Player.HUMAN); // (3,3)
        assertTrue(board.checkWin(row, 3));
    }

    @Test
    void testNoWinWithThreeInARow() {
        board.dropDisc(0, Player.HUMAN);
        board.dropDisc(1, Player.HUMAN);
        int row = board.dropDisc(2, Player.HUMAN);
        assertFalse(board.checkWin(row, 2));
    }

    @Test
    void testIsFullWhenBoardFilled() {
        // Fill entire board
        for (int c = 0; c < Board.COLS; c++) {
            for (int r = 0; r < Board.ROWS; r++) {
                Player p = (r % 2 == 0) ? Player.HUMAN : Player.COMPUTER;
                board.dropDisc(c, p);
            }
        }
        assertTrue(board.isFull());
    }

    @Test
    void testCopyConstructor() {
        board.dropDisc(3, Player.HUMAN);
        board.dropDisc(3, Player.COMPUTER);
        board.dropDisc(4, Player.HUMAN);

        Board copy = new Board(board);
        assertEquals(board, copy);
        assertNotSame(board, copy);

        // Modify original, copy should be unchanged
        board.dropDisc(4, Player.COMPUTER);
        assertNotEquals(board, copy);
    }

    @Test
    void testToStringContainsColumnHeaders() {
        String str = board.toString();
        assertTrue(str.contains("1"));
        assertTrue(str.contains("7"));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 7})
    void testDropDiscOutOfBoundsThrows(int col) {
        assertThrows(IllegalArgumentException.class, () -> board.dropDisc(col, Player.HUMAN));
    }
}
