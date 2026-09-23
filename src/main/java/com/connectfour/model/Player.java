package com.connectfour.model;

/**
 * Represents a player in Connect Four.
 */
public enum Player {
    HUMAN('R'),  // Red
    COMPUTER('Y'); // Yellow

    private final char symbol;

    Player(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public Player opponent() {
        return this == HUMAN ? COMPUTER : HUMAN;
    }
}