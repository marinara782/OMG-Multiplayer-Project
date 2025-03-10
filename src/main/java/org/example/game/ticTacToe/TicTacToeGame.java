package org.example.game.ticTacToe;
import java.util.Random;

public class TicTacToeGame {
    private final char[][] board;
    private char currentPlayer;
    private boolean isPlayerTurn;
    private final boolean isNetworkGame;
    private final char playerSymbol;
    private final char aiSymbol;

    public TicTacToeGame(char playerSymbol, GameModeSelection.GameMode gameMode) {

        this.playerSymbol = playerSymbol;
        System.out.println("This is the player symbol that is in TicTacToeGame : " + playerSymbol);

        // Modify AI symbol handling based on game mode
        if (gameMode == GameModeSelection.GameMode.HUMAN_VS_COMPUTER) {
            // Set AI symbol to the opposite of player's symbol for computer mode
            this.aiSymbol = (playerSymbol == 'X') ? 'O' : 'X';
        } else {
            // For human vs human, we'll set aiSymbol to a placeholder
            this.aiSymbol = ' ';
        }

        board = new char[3][3];
        // Initialize board with empty spaces
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col] = ' ';
            }
        }

        // Adjust initial player based on player's chosen symbol
        currentPlayer = 'X'; // X always starts first
        isPlayerTurn = (currentPlayer == playerSymbol);
        isNetworkGame = (gameMode == GameModeSelection.GameMode.HUMAN_VS_HUMAN);
    }

    public boolean makeMove(int row, int col) {
        // Check if move is valid
        if (row < 0 || row >= 3 || col < 0 || col >= 3 || board[row][col] != ' ') {
            return false;
        }

        // Place the current player's symbol
        board[row][col] = currentPlayer;

        // Switch players
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        isPlayerTurn = !isPlayerTurn;

        return true;
    }

    public boolean checkForWin() {
        // Check rows
        for (int row = 0; row < 3; row++) {
            if (board[row][0] != ' ' &&
                    board[row][0] == board[row][1] &&
                    board[row][1] == board[row][2]) {
                return true;
            }
        }

        // Check columns
        for (int col = 0; col < 3; col++) {
            if (board[0][col] != ' ' &&
                    board[0][col] == board[1][col] &&
                    board[1][col] == board[2][col]) {
                return true;
            }
        }

        // Check diagonals
        if (board[0][0] != ' ' &&
                board[0][0] == board[1][1] &&
                board[1][1] == board[2][2]) {
            return true;
        }

        if (board[0][2] != ' ' &&
                board[0][2] == board[1][1] &&
                board[1][1] == board[2][0]) {
            return true;
        }

        return false;
    }

    public boolean isBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public char checkGameStatus() {
        if (checkForWin()) {
            // The previous player (who just moved) was the winner
            return getOpponentSymbol() ? currentPlayer : playerSymbol;
        }

        if (isBoardFull()) {
            return 'D'; // Draw
        }

        return ' '; // Game continues
    }

    public boolean getOpponentSymbol() {
        return currentPlayer != playerSymbol;
    }

    public int[] getAIMove() {
        // Simple AI: Find a random empty cell
        Random random = new Random();
        int attempts = 0;
        while (attempts < 9) { // Prevent infinite loop
            int row = random.nextInt(3);
            int col = random.nextInt(3);

            if (board[row][col] == ' ') {
                return new int[]{row, col};
            }
            attempts++;
        }
        // Fallback in case no empty cell is found
        return null;
    }

    public boolean isNetworkGame() {
        return isNetworkGame;
    }

    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }

    public char getBoardValue(int row, int col) {
        if (row < 0 || row >= 3 || col < 0 || col >= 3) {
            return ' ';
        }
        return board[row][col];
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public char getPlayerSymbol() {
        return playerSymbol;
    }

    public char getAISymbol() {
        return aiSymbol;
    }
}