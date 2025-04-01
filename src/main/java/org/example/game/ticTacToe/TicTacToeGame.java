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
        //We also have to initialize the cells of the board, they need to be empty at first, whenever a game starts

       BoardInitialization();
    }

    private void BoardInitialization()
    {
        board = new char[3][3];
        for(short i = 0; 3>i;i++) {
            for (short j = 0; 3 > j; j++) {
                board[i][j] = '/';
            }
        }
    }


    public boolean checkForWin()
    {
        //There are 3 possibilities for a win. We need to check diagonals, columns and rows.

        //Diagonal Part:
        //The board is 3 by 3, and we could only have 2 diagonals.
        //Diagonal 1:
        if((board[0][0] == player) && (board[1][1] == player) && (board[2][2] == player))
            return true; // true meaning, this is a win, if condition is fulfilled

        //Diagonal 2:
        //So we could have a diagonal win from the left side (Diagonal Part 1) or we could have a diagonal win form the right side, which is the following:

        if((board[0][2] == player) && (board[1][1] == player) && (board[2][0] == player))
            return true; // true meaning, this is a win, if condition is fulfilled

        //For Columns
        for(short i = 0; 3 > i;i++)
        {
            if((board[0][i] == player) && board[1][i] == player && board[2][i] == player)
                return true;
        }

        //For Rows
        for(short i = 0; 3 > i;i++)
        {
            if((board[i][0] == player) && board[i][1] == player && board[i][2] == player)
                return true;
        }

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
