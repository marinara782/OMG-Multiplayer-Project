package org.example.game.checkers;

public class CheckersBoard {
    private final int SIZE = 8;
    private int[][] board;

    public CheckersBoard() {
        board = new int[SIZE][SIZE];
        setupBoard();
    }

    private void setupBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                // Only use dark squares
                if ((row + col) % 2 == 1) {
                    if (row < 3) {
                        board[row][col] = 1; // 1 = Red pieces (opponent)
                    } else if (row > 4) {
                        board[row][col] = 2; // 2 = Black pieces (player)
                    } else {
                        board[row][col] = 0; // 0 = Empty playable square
                    }
                } else {
                    board[row][col] = -1; // -1 = Light square, not used
                }
            }
        }
    }

    public void printBoard() {
        for (int[] row : board) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}
