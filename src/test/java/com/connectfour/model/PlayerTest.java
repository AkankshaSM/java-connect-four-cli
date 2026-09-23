package com.connectfour.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    @Test
    void testHumanSymbol() {
        assertEquals('R', Player.HUMAN.getSymbol());
    }

    @Test
    void testComputerSymbol() {
        assertEquals('Y', Player.COMPUTER.getSymbol());
    }

    @Test
    void testHumanOpponentIsComputer() {
        assertEquals(Player.COMPUTER, Player.HUMAN.opponent());
    }

    @Test
    void testComputerOpponentIsHuman() {
        assertEquals(Player.HUMAN, Player.COMPUTER.opponent());
    }
}
