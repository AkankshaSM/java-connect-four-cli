package com.connectfour.model;

import java.util.Arrays;
import java.util.Objects;

/**
 * Connect Four game board - 6 rows x 7 columns.
 * Row 0 is the bottom row, row 5 is the top row.
 * Column 0 is the leftmost column, column 6 is the rightmost.
 */
public class Board {
    public static final int ROWS = 6;
    public static final int COLS = 7;

    private final Cell[][] grid;
    private final int[] columnHeights;

    public Board() {
        this.grid = new Cell[ROWS][COLS];
        this.columnHeights = new int[COLS];
        for (int r = 0; r < ROWS; r++) {
            Arrays.fill(grid[r], Cell.EMPTY);
        }
    }

    /** Copy constructor for creating a deep copy. */
    public Board(Board other) {
        this.grid = new Cell[ROWS][COLS];
        this.columnHeights = Arrays.copyOf(other.columnHeights, COLS);
        for (int r = 0; r < ROWS; r++) {
            System.arraycopy(other.grid[r], 0, this.grid[r], 0, COLS);
        }
    }

    /**
     * Attempts to drop a disc in the given column (0-indexed).
     *
     * @param col column index (0-6)
     * @param player the player making the move
     * @return the row where the disc landed, or -1 if column is full
     */
    public int dropDisc(int col, Player player) {
        if (col < 0 || col >= COLS) {
            throw new IllegalArgumentException("Column index out of bounds: " + col);
        }
        if (columnHeights[col] >= ROWS) {
            return -1; // Column is full
        }
        int row = columnHeights[col];
        grid[row][col] = Cell.fromPlayer(player);
        columnHeights[col]++;
        return row;
    }

    /**
     * Checks if a column has space for a disc.
     */
    public boolean canDrop(int col) {
        return col >= 0 && col < COLS && columnHeights[col] < ROWS;
    }

    /**
     * Gets the cell at the given position.
     */
    public Cell getCell(int row, int col) {
        if (row < 0 || row >= ROWS || col < 0 || col >= COLS) {
            return Cell.EMPTY;
        }
        return grid[row][col];
    }

    /**
     * Checks if the board is completely full (draw condition).
     */
    public boolean isFull() {
        for (int h : columnHeights) {
            if (h < ROWS) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the last move at (row, col) creates a winning line.
     */
    public boolean checkWin(int row, int col) {
        Cell playerCell = grid[row][col];
        if (playerCell == Cell.EMPTY) {
            return false;
        }

        // Check four directions: horizontal, vertical, diagonal \, diagonal /
        return checkDirection(row, col, 0, 1, playerCell)   // horizontal
            || checkDirection(row, col, 1, 0, playerCell)   // vertical
            || checkDirection(row, col, 1, 1, playerCell)   // diagonal \
            || checkDirection(row, col, 1, -1, playerCell); // diagonal /
    }

    private boolean checkDirection(int row, int col, int dRow, int dCol, Cell target) {
        int count = 1; // Count the starting cell

        // Check in positive direction
        for (int i = 1; i < 4; i++) {
            int r = row + i * dRow;
            int c = col + i * dCol;
            if (r >= 0 && r < ROWS && c >= 0 && c < COLS && grid[r][c] == target) {
                count++;
            } else {
                break;
            }
        }

        // Check in negative direction
        for (int i = 1; i < 4; i++) {
            int r = row - i * dRow;
            int c = col - i * dCol;
            if (r >= 0 && r < ROWS && c >= 0 && c < COLS && grid[r][c] == target) {
                count++;
            } else {
                break;
            }
        }

        return count >= 4;
    }

    /**
     * Returns a string representation of the board for display.
     * Row 5 (top) is printed first, row 0 (bottom) last.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // Column headers
        sb.append("  ");
        for (int c = 0; c < COLS; c++) {
            sb.append(c + 1).append(" ");
        }
        sb.append("\n");

        // Board rows (top to bottom)
        for (int r = ROWS - 1; r >= 0; r--) {
            sb.append("| ");
            for (int c = 0; c < COLS; c++) {
                sb.append(grid[r][c].getSymbol()).append(" ");
            }
            sb.append("|\n");
        }

        // Bottom border
        sb.append("+-");
        for (int c = 0; c < COLS; c++) {
            sb.append("--");
        }
        sb.append("+");

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board board)) return false;
        return Arrays.deepEquals(grid, board.grid);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(grid);
    }
}