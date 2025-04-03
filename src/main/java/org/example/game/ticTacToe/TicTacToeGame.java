package org.example.game.ticTacToe;

public class TicTacToeGame {

    private char[][] board;
    private char player;
    private char opponent;
    private int sizeOfTheBoard;
    private boolean playerTurn = True;
    private boolean opponentTurn = False;

    public TicTacToeGame()
    {
        this.sizeOfTheBoard = 3;
        this.board = new char[3][3];
        //We also have to initialize the cells of the board, they need to be empty at first, whenever a game starts
        BoardInitialization();
        randomizePlayersSymbols();
    }

    public void randomizePlayersSymbols()
    {
        int randomNum = (int)(Math.random() * 2) + 1;

        if(randomNum == 1)
        {
            player = 'X';
            opponent = 'O';
        }
        else
        {
            player = 'O';
            opponent = 'X';
        }
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


    public boolean checkForWin(char symbol)
    {
        //There are 3 possibilities for a win. We need to check diagonals, columns and rows.

        //Diagonal Part:
        //The board is 3 by 3, and we could only have 2 diagonals.
        //Diagonal 1:
        if((board[0][0] == symbol) && (board[1][1] == symbol) && (board[2][2] == symbol))
            return true; // true meaning, this is a win, if condition is fulfilled

        //Diagonal 2:
        //So we could have a diagonal win from the left side (Diagonal Part 1) or we could have a diagonal win form the right side, which is the following:

        if((board[0][2] == symbol) && (board[1][1] == symbol) && (board[2][0] == symbol))
            return true; // true meaning, this is a win, if condition is fulfilled

        //For Columns
        for(short i = 0; 3 > i;i++)
        {
            if((board[0][i] == symbol) && board[1][i] == symbol && board[2][i] == symbol)
                return true;
        }

        //For Rows
        for(short i = 0; 3 > i;i++)
        {
            if((board[i][0] == symbol) && board[i][1] == symbol && board[i][2] == symbol)
                return true;
        }

        return false;
    }
    public boolean checkForWin()
    {
        return (checkForWin(opponent) || checkForWin(player));
    }

    public boolean isBoardFull()
    {
        //We return true if the board is full
        //And we return false if its not full, so if at least one cell is empty, we return false

        for(short row = 0; 3>row;row++)
        {
            for(short col = 0; 3>col;col++)
            {
                if(board[row][col] == '/')
                    return false;
                else
                    continue;
            }
        }
        //The above for loop goes through every single cell on board and checks if its empty or not.
        //A cell is empty if it equals "/".
        //If at any point we find a cell that equals "/", then we return false, meaning the board is not full as there is at least one spot empty
        //Our goal is not to check how many cells are empty, but rather, to see if there is any empty cell

        return true;
        //if no cells are empty, then we don't execute the return false statement which would get us out of the function, and therefore we reach the return true statement
        //If we reach the return true statement, then it means the board is full, and there are no empty cells.
    }

    public char getOpponentSymbol()
    {
        return opponent;

    }

    public int[] getAIMove()
    {
        if (isBoardFull())
            return null;
        for(short i = 0; 3>i;i++)
        {
            for(short j = 0; 3>j;j++)
            {
                if(board[i][j] == '/')
                    return new int[]{i, j};
                else
                    continue;
            }
        }
        return null;
    }

    public boolean isNetworkGame() //NOT DONE
    {
        return false;
    }

    public char getPlayerSymbol() {
        return opponent;
    }

    public boolean makeMove(int row, int col, String turn)
    {
        if (row < 0 || row >= 3 || col < 0 || col >= 3 || board[row][col] != '/') {
            return false; // Invalid move
        }

        if (turn == "player") {
            board[row][col] = player;
            playerTurn = false; // Switch turn
            opponentTurn = true; // Switch turn
        }

        if (turn == "opponent") {
            board[row][col] = opponent;
            opponentTurn = false; // Switch turn
            playerTurn = true; // Switch turn
        }
        return true;
    }

    public boolean isPlayerTurn() {

    }

    public boolean isOpponentTurn(){

    }

    public char getBoardValue(int row, int col)
    {
        return player; // plavceholder
    }


}
