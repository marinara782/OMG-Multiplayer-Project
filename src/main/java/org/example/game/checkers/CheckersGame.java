package org.example.game.checkers;

/**
 * The CheckersGame class contains the core game logic for a standard 8x8 Checkers match.
 * This class is independent of the GUI and is responsible for managing the internal game state.
 */
public class CheckersGame {

    // 2D array representing the game board.
    //  0 = empty square
    //  1 = red piece
    // -1 = black piece
    private int[][] board;

    // Boolean to track whose turn it is.
    // false = Black's turn (starts first), true = Red's turn
    private boolean isRedTurn;

    /**
     * Constructor initializes the game board and sets the starting turn.
     */
    public CheckersGame() {
        board = new int[8][8]; // 8x8 standard Checkers board
        isRedTurn = false;     // Black always starts first
        initializeBoard();
    }

    /**
     * Places red and black pieces in their standard starting positions.
     * Only dark squares (where (row + col) % 2 == 1) are used in Checkers.
     */
    private void initializeBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 == 1) { // Only dark squares are playable
                    if (row < 3) {
                        board[row][col] = 1;  // Red pieces at the top
                    } else if (row > 4) {
                        board[row][col] = -1; // Black pieces at the bottom
                    }
                    // Middle rows (3–4) are left empty
                }
            }
        }
    }

    /**
     * Returns the piece value at a specific board position.
     * Used by the GUI or other logic to read board state.
     *
     * @param row Row index (0–7)
     * @param col Column index (0–7)
     * @return 1 for red, -1 for black, 0 for empty
     */
    public int getBoardValue(int row, int col) {
        return board[row][col];
    }

    /**
     * Returns true if it's red's turn, false if it's black's turn.
     * This helps GUI and logic determine allowed moves.
     *
     * @return true if red's turn, false if black's turn
     */
    public boolean isRedTurn() {
        return isRedTurn;
    }
}