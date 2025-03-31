package org.example.game.connectFour;

public class ConnectFourBoard {
    private static ConnectFourBoard Game;
    public static final int EMP = Game.EMP;
    /**
     * Connect-L Red Piece
     */
    public static final int RED = Game.RED;
    /**
     * Connect-L Blue Piece
     */
    public static final int BLU = Game.BLU;

    //Students should enter their functions below here

    // return a 2d array filled with 0s
    public static int[][] createBoard(int rows, int columns){
        int[][] boardArray = new int[rows][columns];
        for (int i=0; i<rows; i++){
            for (int j = 0; j<columns; j++){
                boardArray[i][j] = 0;
            }
        }
        return boardArray;
    }
}
