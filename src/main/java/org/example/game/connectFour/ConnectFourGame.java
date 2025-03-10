package org.example.game.connectFour;

import javafx.scene.paint.Color;

public class ConnectFourGame {
    // Constants for the board dimensions
    public static final int ROWS = 6;
    public static final int COLUMNS = 7;

    // Constants for the game pieces
    public static final int EMPTY = 0;
    public static final int PLAYER_RED = 1;
    public static final int PLAYER_BLUE = 2;

    // Game state
    private int[][] board;
    private int currentPlayer;
    private boolean gameOver;
    private int winner;

    public ConnectFourGame() {
        // Initialize the game
        board = new int[ROWS][COLUMNS];
        resetGame();
    }

    /**
     * Reset the game to its initial state.
     */
    public void resetGame() {
        // Clear the board
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                board[row][col] = EMPTY;
            }
        }
        // Red always starts
        currentPlayer = PLAYER_RED;
        gameOver = false;
        winner = EMPTY;
    }

    /**
     * Make a move for the current player in the specified column.
     *
     * @param column The column where the player wants to drop their piece
     * @return The row where the piece landed, or -1 if the move is invalid
     */
    public int makeMove(int column) {
        if (gameOver || column < 0 || column >= COLUMNS) {
            return -1;
        }

        // Find the first empty row in the column (from bottom to top)
        for (int row = ROWS - 1; row >= 0; row--) {
            if (board[row][column] == EMPTY) {
                // Place the current player's piece
                board[row][column] = currentPlayer;

                // Check if this move results in a win
                if (checkWin(row, column)) {
                    gameOver = true;
                    winner = currentPlayer;
                } else if (isBoardFull()) {
                    // If no win and the board is full, it's a draw
                    gameOver = true;
                } else {
                    // Switch player
                    switchPlayer();
                }

                return row;
            }
        }

        // Column is full
        return -1;
    }

    /**
     * Switch to the other player.
     */
    private void switchPlayer() {
        currentPlayer = (currentPlayer == PLAYER_RED) ? PLAYER_BLUE : PLAYER_RED;
    }

    /**
     * Check if the board is full.
     *
     * @return true if the board is full, false otherwise
     */
    private boolean isBoardFull() {
        for (int col = 0; col < COLUMNS; col++) {
            if (board[0][col] == EMPTY) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if the last move resulted in a win.
     *
     * @param row The row of the last move
     * @param col The column of the last move
     * @return true if there's a win, false otherwise
     */
    private boolean checkWin(int row, int col) {
        int player = board[row][col];

        // Check horizontal
        if (countConsecutive(row, col, 0, 1, player) + countConsecutive(row, col, 0, -1, player) - 1 >= 4) {
            return true;
        }

        // Check vertical
        if (countConsecutive(row, col, 1, 0, player) + countConsecutive(row, col, -1, 0, player) - 1 >= 4) {
            return true;
        }

        // Check diagonal (top-left to bottom-right)
        if (countConsecutive(row, col, 1, 1, player) + countConsecutive(row, col, -1, -1, player) - 1 >= 4) {
            return true;
        }

        // Check diagonal (top-right to bottom-left)
        if (countConsecutive(row, col, 1, -1, player) + countConsecutive(row, col, -1, 1, player) - 1 >= 4) {
            return true;
        }

        return false;
    }

    /**
     * Count the number of consecutive pieces of the same player in a given direction.
     *
     * @param row The starting row
     * @param col The starting column
     * @param rowDelta The row direction (e.g., 1 for down, -1 for up, 0 for horizontal)
     * @param colDelta The column direction (e.g., 1 for right, -1 for left, 0 for vertical)
     * @param player The player to check for
     * @return The number of consecutive pieces
     */
    private int countConsecutive(int row, int col, int rowDelta, int colDelta, int player) {
        int count = 0;

        while (row >= 0 && row < ROWS && col >= 0 && col < COLUMNS && board[row][col] == player) {
            count++;
            row += rowDelta;
            col += colDelta;
        }

        return count;
    }

    /**
     * Get the current player.
     *
     * @return The current player (PLAYER_RED or PLAYER_BLUE)
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Check if the game is over.
     *
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Get the winner of the game.
     *
     * @return The winner (PLAYER_RED, PLAYER_BLUE, or EMPTY if it's a draw)
     */
    public int getWinner() {
        return winner;
    }

    /**
     * Get the piece at a specific position on the board.
     *
     * @param row The row of the position
     * @param col The column of the position
     * @return The piece at the position (EMPTY, PLAYER_RED, or PLAYER_BLUE)
     */
    public int getPieceAt(int row, int col) {
        if (row >= 0 && row < ROWS && col >= 0 && col < COLUMNS) {
            return board[row][col];
        }
        return EMPTY;
    }

    /**
     * Check if a move in the specified column is valid.
     *
     * @param column The column to check
     * @return true if the move is valid, false otherwise
     */
    public boolean isValidMove(int column) {
        return !gameOver && column >= 0 && column < COLUMNS && board[0][column] == EMPTY;
    }

    /**
     * Get a string representation of the current player.
     *
     * @return The current player's name (Red or Blue)
     */
    public String getCurrentPlayerName() {
        return (currentPlayer == PLAYER_RED) ? "Red" : "Blue";
    }

    /**
     * Get a color representation of the current player.
     *
     * @return The current player's color
     */
    public Color getCurrentPlayerColor() {
        return (currentPlayer == PLAYER_RED) ? Color.RED : Color.BLUE;
    }
}