package org.example.game.connectFour;


public class ConnectFourBoard{

    public static final int Empty = 0;
    public static final int Red = 1;
    public static final int Blue = 2;

    /**
     * initialize the board with pieces for connect four game with given rows and columns
     * @param rows 
     * @param columns
     * @return a 2D array representing the board
     */
    public static int[][] createBoard(int rows, int columns) {
        int[][] board = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board[i][j] = Empty;
            }
        }
        return board;
    }
}