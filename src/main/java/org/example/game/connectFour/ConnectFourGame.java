package org.example.game.connectFour;

import javafx.scene.paint.Color;
import java.util.Random;

public class ConnectFourGame {
    // Constants for the board dimensions
    public static final int ROWS = 6;
    public static final int COLUMNS = 7;

    // Constants for the game pieces
    public static final int EMPTY = 0;
    public static final int PLAYER_RED = 1;
    public static final int PLAYER_BLUE = 2;

    // Game state
    private final int[][] board;
    private int currentPlayer;
    private boolean gameOver;
    private int winner;

    // Game settings
    private boolean playAgainstComputer;
    private boolean playerIsRed;
    private String difficulty;
    private final Random random = new Random();

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
     * Configure game settings
     *
     * @param playAgainstComputer Whether to play against the computer
     * @param playerIsRed Whether the human player is red
     * @param difficulty The difficulty level for AI (Easy, Medium, Hard)
     */
    public void configureGame(boolean playAgainstComputer, boolean playerIsRed, String difficulty) {
        this.playAgainstComputer = playAgainstComputer;
        this.playerIsRed = playerIsRed;
        this.difficulty = difficulty;
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
     * Determine if it's the human player's turn.
     *
     * @return true if it's the human player's turn, false otherwise
     */
    public boolean isHumanTurn() {
        if (!playAgainstComputer) {
            return true; // In human vs human, all turns are human
        }

        return (playerIsRed && currentPlayer == PLAYER_RED) ||
                (!playerIsRed && currentPlayer == PLAYER_BLUE);
    }

    /**
     * Make a computer move based on the difficulty level.
     *
     * @return The column where the computer made its move
     */
    public int makeComputerMove() {
        if (!playAgainstComputer || isHumanTurn() || gameOver) {
            return -1;
        }

        int column;

        switch (difficulty) {
            case "Hard":
                column = findBestMove();
                break;
            case "Medium":
                // 70% chance to make the best move, 30% chance for a random move
                if (random.nextDouble() < 0.7) {
                    column = findBestMove();
                } else {
                    column = findRandomValidMove();
                }
                break;
            case "Easy":
            default:
                // 30% chance to make the best move, 70% chance for a random move
                if (random.nextDouble() < 0.3) {
                    column = findBestMove();
                } else {
                    column = findRandomValidMove();
                }
                break;
        }

        return column;
    }

    /**
     * Find a random valid move.
     *
     * @return A random column that has space for a piece
     */
    private int findRandomValidMove() {
        // Get all valid moves
        int[] validMoves = new int[COLUMNS];
        int validMoveCount = 0;

        for (int col = 0; col < COLUMNS; col++) {
            if (isValidMove(col)) {
                validMoves[validMoveCount++] = col;
            }
        }

        // Pick a random valid move
        if (validMoveCount > 0) {
            return validMoves[random.nextInt(validMoveCount)];
        }

        // Fallback to column 3 (middle) if something goes wrong
        return 3;
    }

    /**
     * Find the best move using a simple heuristic.
     * This is a simplified AI that:
     * 1. Checks for an immediate win
     * 2. Blocks opponent's immediate win
     * 3. Prefers center columns
     *
     * @return The best column to make a move
     */
    private int findBestMove() {
        // First, check if we can win in the next move
        for (int col = 0; col < COLUMNS; col++) {
            if (isValidMove(col)) {
                // Try this move
                int row = getNextEmptyRow(col);
                board[row][col] = currentPlayer;

                // Check if this move wins
                boolean wins = checkWin(row, col);

                // Undo the move
                board[row][col] = EMPTY;

                if (wins) {
                    return col; // Found a winning move
                }
            }
        }

        // If we can't win, check if opponent can win in their next move and block it
        int opponent = (currentPlayer == PLAYER_RED) ? PLAYER_BLUE : PLAYER_RED;

        for (int col = 0; col < COLUMNS; col++) {
            if (isValidMove(col)) {
                // Try this move for the opponent
                int row = getNextEmptyRow(col);
                board[row][col] = opponent;

                // Check if this move wins for the opponent
                boolean opponentWins = checkWin(row, col);

                // Undo the move
                board[row][col] = EMPTY;

                if (opponentWins) {
                    return col; // Block opponent's winning move
                }
            }
        }

        // If no immediate win or block, prefer the center and columns close to it
        int[] columnPreference = {3, 2, 4, 1, 5, 0, 6}; // Center first, then outward

        for (int col : columnPreference) {
            if (isValidMove(col)) {
                return col;
            }
        }

        // Fallback to a random move
        return findRandomValidMove();
    }

    /**
     * Get the next empty row in a column.
     *
     * @param col The column to check
     * @return The row index of the next empty slot, or -1 if the column is full
     */
    private int getNextEmptyRow(int col) {
        for (int row = ROWS - 1; row >= 0; row--) {
            if (board[row][col] == EMPTY) {
                return row;
            }
        }
        return -1;
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

    /**
     * Get the computer player name based on the difficulty.
     *
     * @return The computer player's name
     */
    public String getComputerName() {
        return "Computer (" + difficulty + ")";
    }

    /**
     * Check if game is set to play against computer.
     */
    public boolean isPlayingAgainstComputer() {
        return playAgainstComputer;
    }

    /**
     * Check if human player is playing as red.
     */
    public boolean isPlayerRed() {
        return playerIsRed;
    }
}