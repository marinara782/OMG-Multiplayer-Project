package org.example.game.ticTacToe;

public class TicTacToeGame {

    private char[][] board;
    private char currentPlayer;
    private char player = 'X';
    private char opponent = 'O';
    private boolean playerAndComputer;
    private int sizeOfTheBoard;

    /**
     * * Constructor for TicTacToeGame which initializes the game board and sets the current player.
     * @param isComputerGame boolean true if against a computer, false if against another user player
     */
    public TicTacToeGame(boolean isComputerGame)
    {
        this.sizeOfTheBoard = 3;
        this.currentPlayer = player;
        this.playerAndComputer = false;
        this.board = new char[3][3];
        //The player's selected game mode which is a paramater in the constructor set through the main menu dialogue box is now set to our local variable here
        this.playerAndComputer = isComputerGame;;
        //We also have to initialize the cells of the board, they need to be empty at first, whenever a game starts
        BoardInitialization();
        //randomizePlayersSymbols();
    }


    // initializes the board with empty cells
    public void BoardInitialization()
    {
        board = new char[3][3];
        for(short i = 0; 3>i;i++) {
            for (short j = 0; 3 > j; j++) {
                board[i][j] = '/';
            }
        }
    }

    // set the game against another player
    public void twoPlayersGame()
    {
        currentPlayer = player;
        playerAndComputer = false;
    }


    //This method returns whether or not the game is against the computer
    public boolean isPlayerAndComputer(){
        return playerAndComputer;
    }

    //This method returns whose turn it is(Player's or opponent's)
    public char getCurrentPlayer(){
        return currentPlayer;
    }

    /**
     * check for win if find 3 symbols in a row, column or diagonal
     * @param symbol the symbol to check for
     * @return true if the given symbol is found in a row, column or diagonal, false otherwise
     */
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

    /**
     * method checks if the game board is full
     * @return true if the board is full, false otherwise
     */
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

    // getter for opponent symbol
    public char getOpponentSymbol()
    {
        return opponent;

    }

    /**
     * determines the next move for the AI opponent
     * @return an array of two integers which represents the row and column of the selected move, or null if no other moves possible
     */
    public int[] getAIMove()
    {
        if (isBoardFull())
            return null; // no possible moves since board is full

        for(short i = 0; 3>i;i++)
        {
            for(short j = 0; 3>j;j++)
            {
                if(board[i][j] == '/')
                {
                    board[i][j] = opponent;
                    if(checkForWin(opponent))
                    {
                        board[i][j] = '/';
                        return new int[]{i, j};
                    }
                    board[i][j] = '/';
                }
            }
        }

        if(board[1][1] == '/')
            return new int[]{1, 1}; //  if center is empty, select it

        // check if player can win in the next move then block it
        for(short i = 0; 3>i;i++)
        {
            for(short j = 0; 3>j;j++)
            {
                if(board[i][j] == '/')
                {
                    board[i][j] = player;
                    if(checkForWin(player))
                    {
                        board[i][j] = '/';
                        return new int[]{i, j};
                    }
                    board[i][j] = '/';
                }
            }
        }
        // choose the available corner cells
        if(board[0][0] == '/')
            return new int[]{0, 0};
        if(board[0][2] == '/')
            return new int[]{0, 2};
        if(board[2][0] == '/')
            return new int[]{2, 0};
        if(board[2][2] == '/')
            return new int[]{2, 2};

        // choose the remaining available cells
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

    //This method is used to make the AI move, it gets the AI move from the getAIMove method and makes the move on the board
    private void makeAIMove()
    {
        int[] move = getAIMove();
        if(move == null)
            return;

        board[move[0]][move[1]] = opponent;
        currentPlayer = player;
    }

    public char getPlayerSymbol() {
        return player;
    }

    //This method makes the move made by the player on the GUI on the 2d array so we can use it for game logic
    public void makeMove(int row, int col,char player)
    {
        board[row][col] = player;
    }

    //This method is used to switch it to the player's turn
    public void isPlayerTurn() {
        currentPlayer = player;
    }

    //This method is used to switch it to the opponent's turn
    public void isOpponentTurn(){
        currentPlayer = opponent;
    }

    //This was mainly used as a debugging method to make sure the GUI board positions lined up with the 2d board's positions
    public char getBoardValue(int row, int col)
    {
        if (row < 0 || row >= 3 || col < 0 || col >= 3) {
            return 0; // Return an invalid character
        }
        return board[row][col];
    }

}
