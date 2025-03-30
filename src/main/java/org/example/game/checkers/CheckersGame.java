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

    // Currently selected piece for movement (set by GUI click)
    private int selectedRow = -1;
    private int selectedCol = -1;

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
     * Selects a piece to move. This should be triggered by GUI click.
     * Only selects if it's the current player's piece.
     */
    public void selectPiece(int row, int col) {
        if (!isValidPosition(row, col)) return;

        int piece = board[row][col];
        if ((isRedTurn && piece > 0) || (!isRedTurn && piece < 0)) {
            selectedRow = row;
            selectedCol = col;
        }
    }

    /**
     * Attempts to move a selected piece to the given position.
     * Only supports basic forward diagonal moves for non-king pieces.
     */
    public void makeMove(int row, int col) {
        if (selectedRow == -1 || selectedCol == -1) return;

        int piece = board[selectedRow][selectedCol];

        // Ensure it's the correct player's piece
        if ((isRedTurn && piece <= 0) || (!isRedTurn && piece >= 0)) return;

        // Ensure target is in bounds and empty
        if (!isValidPosition(row, col) || board[row][col] != 0) return;

        int direction = isRedTurn ? 1 : -1;

        // Check for valid diagonal move (1 step forward)
        if (row == selectedRow + direction && Math.abs(col - selectedCol) == 1) {
            board[row][col] = piece;
            board[selectedRow][selectedCol] = 0;

            selectedRow = -1;
            selectedCol = -1;

            isRedTurn = !isRedTurn; // Switch turns
        }
    }

    /**
     * Validates if a position is on the board.
     */
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
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