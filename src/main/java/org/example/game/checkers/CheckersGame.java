package org.example.game.checkers;

/**
 * Handles the internal logic for Checkers gameplay.
 * Includes board setup, turn tracking, and access methods.
 */
public class CheckersGame {

    // Board representation:
    //  0 = empty, 1 = red piece, -1 = black piece
    private int[][] board;

    // false = black's turn, true = red's turn
    private boolean isRedTurn;

    /**
     * Constructor initializes the board and sets the starting turn.
     */
    public CheckersGame() {
        board = new int[8][8];
        isRedTurn = false; // Black goes first
        initializeBoard();
    }

    /**
     * Sets up the initial piece positions on the board.
     * Red at the top, black at the bottom.
     */
    private void initializeBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 == 1) {
                    if (row < 3) {
                        board[row][col] = 1;  // Red pieces
                    } else if (row > 4) {
                        board[row][col] = -1; // Black pieces
                    }
                }
            }
        }
    }

    /**
     * Returns the value at a board position.
     * @param row Row index
     * @param col Column index
     * @return 1 (red), -1 (black), or 0 (empty)
     */
    public int getBoardValue(int row, int col) {
        return board[row][col];
    }

    /**
     * Checks whose turn it is.
     * @return true if red's turn, false if black's
     */
    public boolean isRedTurn() {
        return isRedTurn;
    }

    /**
     * Placeholder for future move logic.
     */
    public void makeMove(int row, int col) {
        // TODO: Implement logic to move
    }

    /**
     * Placeholder for future win-checking logic.
     */
    public boolean checkForWin() {
        // TODO: Implement logic to check for a win
        return false;
    }

    /**
     * Placeholder for draw detection logic.
     */
    public boolean isBoardFull() {
        // TODO: Implement logic to check if board is full
        return false;
    }
}