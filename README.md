# Java Connect Four CLI

A command-line Connect Four game written in Java 21 using Maven. Play as RED (human) against YELLOW (computer with random legal moves).

## Features

- Human vs Computer (random legal moves)
- 7 columns (1-7), 6 rows
- Printed board with column headers
- Invalid input handling (non-numeric, out of range)
- Full column rejection
- Win detection: horizontal, vertical, diagonal (both directions)
- Draw detection
- Quit anytime with 'q' or 'quit'
- Clean separation of game rules (model/game) from terminal I/O
- JUnit 5 tests

## Requirements

- Java 21+
- Maven 3.6+

## Build

```bash
mvn compile
```

## Run Tests

```bash
mvn test
```

## Play

```bash
mvn exec:java -Dexec.mainClass=com.connectfour.Main
```

Or build a JAR and run:

```bash
mvn package
java -jar target/java-connect-four-cli-1.0.0.jar
```

## How to Play

1. Run the game using one of the commands above.
2. You are **RED (R)** and go first.
3. The computer is **YELLOW (Y)**.
4. Enter a column number **1-7** to drop your disc.
5. The disc falls to the lowest empty slot in that column.
6. First to connect four horizontally, vertically, or diagonally wins.
7. If the board fills completely with no winner, it's a draw.
8. Type **'q'** or **'quit'** at any prompt to exit.

## Example Game

```
==================================
     CONNECT FOUR - Java CLI      
==================================
You are RED (R). Computer is YELLOW (Y).
You go first. Enter column 1-7.
Type 'q' or 'quit' to exit.

  1 2 3 4 5 6 7 
|               |
|               |
|               |
|               |
|               |
|               |
+---------------+

--- Your turn (RED) ---
Enter column (1-7) or 'q' to quit: 4
Move accepted

  1 2 3 4 5 6 7 
|               |
|               |
|               |
|               |
|               |
|       R       |
+---------------+

--- Computer's turn (YELLOW) ---
Computer chooses column 2.
Move accepted

  1 2 3 4 5 6 7 
|               |
|               |
|               |
|               |
|       Y       |
|       R       |
+---------------+
```

## Project Structure

```
src/main/java/com/connectfour/
├── Main.java                 # Entry point
├── model/
│   ├── Board.java           # Board logic (drop, win check, full check)
│   ├── Cell.java            # Cell state (EMPTY, HUMAN, COMPUTER)
│   └── Player.java          # Player enum (HUMAN, COMPUTER)
├── game/
│   ├── GameEngine.java      # Core game rules and state machine
│   └── ComputerPlayer.java  # Random legal move selector
└── io/
    └── ConsoleIO.java       # All terminal input/output

src/test/java/com/connectfour/
├── model/
│   ├── BoardTest.java       # Board logic tests
│   ├── CellTest.java        # Cell enum tests
│   └── PlayerTest.java      # Player enum tests
└── game/
    ├── GameEngineTest.java  # GameEngine tests
    └── ComputerPlayerTest.java # ComputerPlayer tests
```

## Design Notes

- **Separation of concerns**: Game rules (`Board`, `GameEngine`, `ComputerPlayer`) are completely separate from I/O (`ConsoleIO`). This makes the game logic testable without a terminal.
- **Board coordinates**: Row 0 = bottom, Row 5 = top. Column 0 = leftmost (user column 1).
- **Win checking**: After each move, only the last move position is checked for a connect-four in 4 directions.
- **Computer player**: Chooses uniformly at random from all non-full columns.

## License

MIT License
