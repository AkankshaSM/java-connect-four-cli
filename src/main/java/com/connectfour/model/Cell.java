package com.connectfour.model;

/**
 * Represents a cell on the Connect Four board.
 */
public enum Cell {
    EMPTY(' '),
    HUMAN('R'),
    COMPUTER('Y');

    private final char symbol;

    Cell(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public static Cell fromPlayer(Player player) {
        return switch (player) {
            case HUMAN -> HUMAN;
            case COMPUTER -> COMPUTER;
        };
    }

    public Player toPlayer() {
        return switch (this) {
            case HUMAN -> Player.HUMAN;
            case COMPUTER -> Player.COMPUTER;
            default -> throw new IllegalStateException("Cannot convert EMPTY to Player");
        };
    }
}