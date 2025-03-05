package org.example.game.ticTacToe;
import java.util.Random;

//public class TicTacToeGame {
//    public boolean checkForWin() {
//        return false;
//    }
//
//    public boolean isBoardFull() {
//        return false;
//    }
//
//    public boolean getOpponentSymbol() {
//        return false;
//    }
//
//    public int[] getAIMove() {
//        return new int[0];
//    }
//
//    public boolean isNetworkGame() {
//        return false;
//    }
//
//    public char[] getPlayerSymbol() {
//        return new char[0];
//    }
//
//    public boolean makeMove(int i, int i1) {
//        return false;
//    }
//
//    public boolean isPlayerTurn() {
//        return false;
//    }
//
//    public char getBoardValue(int row, int col) {
//        return 0;
//    }
//
//    public char getCurrentPlayer() {
//        return 0;
//    }
//
//    public char checkGameStatus() {
//        return 0;
//    }
//}


public class TicTacToeGame {
    private char[][] board;
    private char currentPlayer;
    private boolean isPlayerTurn;
    private boolean isNetworkGame;

    public TicTacToeGame() {
        board = new char[3][3];
        // Initialize board with empty spaces
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col] = ' ';
            }
        }
        // X always starts first
        currentPlayer = 'X';
        isPlayerTurn = true;
        isNetworkGame = false;
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
            return getOpponentSymbol() ? 'X' : 'O';
        }

        if (isBoardFull()) {
            return 'D'; // Draw
        }

        return ' '; // Game continues
    }

    public boolean getOpponentSymbol() {
        return currentPlayer == 'O';
    }

    public int[] getAIMove() {
        // Simple AI: Find the first empty cell
        Random random = new Random();
        while (true) {
            int row = random.nextInt(3);
            int col = random.nextInt(3);

            if (board[row][col] == ' ') {
                return new int[]{row, col};
            }
        }
    }

    public boolean isNetworkGame() {
        return isNetworkGame;
    }

    public char[] getPlayerSymbol() {
        return new char[]{'X', 'O'};
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
}