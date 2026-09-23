package com.connectfour.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {
    @Test
    void testFromPlayerHuman() {
        assertEquals(Cell.HUMAN, Cell.fromPlayer(Player.HUMAN));
    }

    @Test
    void testFromPlayerComputer() {
        assertEquals(Cell.COMPUTER, Cell.fromPlayer(Player.COMPUTER));
    }

    @Test
    void testToPlayerHuman() {
        assertEquals(Player.HUMAN, Cell.HUMAN.toPlayer());
    }

    @Test
    void testToPlayerComputer() {
        assertEquals(Player.COMPUTER, Cell.COMPUTER.toPlayer());
    }

    @Test
    void testToPlayerEmptyThrows() {
        assertThrows(IllegalStateException.class, () -> Cell.EMPTY.toPlayer());
    }

    @Test
    void testSymbols() {
        assertEquals(' ', Cell.EMPTY.getSymbol());
        assertEquals('R', Cell.HUMAN.getSymbol());
        assertEquals('Y', Cell.COMPUTER.getSymbol());
    }
}
