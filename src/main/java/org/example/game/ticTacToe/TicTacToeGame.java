package org.example.game.ticTacToe;

public class TicTacToeGame {

    private char[][] board;
    private char player;
    private int sizeOfTheBoard;

    public TicTacToeGame()
    {
        this.sizeOfTheBoard = 3;
        this.player = 'O';
        this.board = new char[3][3];
        //We also have to initialize the cells of the board, they need to be empty at first

        for(short i = 0; 3>i;i++)
        {
            for(short j = 0; 3 > i; i++)
            {
                board[i][j] = '/';
            }
        }
    }


    public boolean checkForWin() {
        return false;
    }

    public boolean isBoardFull() {
        return false;
    }

    public boolean getOpponentSymbol() {
        return false;
    }

    public int[] getAIMove() {
        return new int[0];
    }

    public boolean isNetworkGame() {
        return false;
    }

    public char[] getPlayerSymbol() {
        return new char[0];
    }

    public boolean makeMove(int i, int i1) {
        return false;
    }

    public boolean isPlayerTurn() {
        return false;
    }

    public char getBoardValue(int row, int col) {
        return 0;
    }


}
