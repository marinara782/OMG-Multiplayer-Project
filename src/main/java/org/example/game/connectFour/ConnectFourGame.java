package org.example.game.connectFour;

public class ConnectFourGame {

    public int player;
    public int rows;
    public int columns;
    public int goal;
    public int[][] board;
    private boolean vsComputer;
    private boolean gameOver = false;


    /**
     * Constructor for ConnectFourGame
     * @param player 1 for Red, 2 for Blue
     * @param rows
     * @param columns
     * @param goal
     * @param vsComputer true when playing against a computer, false when playing against another user player
     */
    public ConnectFourGame(int player, int rows, int columns, int goal, boolean vsComputer) {
        this.player = player;
        this.rows = rows;
        this.columns = columns;
        this.goal = goal;
        this.vsComputer = vsComputer;
        this.board = ConnectFourBoard.createBoard(rows, columns);
    }


    // getters
    public int[][] getBoard() {
        return this.board;
    }

    public int getPlayer() {
        return this.player;
    }

    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.columns;
    }

    public int getGoal() {
        return this.goal;
    }

    /**
     * * Make a move on the board
     * @param row
     * @param column
     * @return true when moved, false when not moved
     */
    public boolean makeMove(int row, int column) {
        if (gameOver) return false; // Prevent moves if game is over

        if (this.board[row][column] == ConnectFourBoard.Empty) {
            this.board[row][column] = player;

            // Check win condition
            if (checkWinnerHorizontal() || checkWinnerVertical() || checkWinnerDiagonal()) {
                gameOver = true;
            } else if (checkDraw()) {
                gameOver = true;
            }

            return true;
        }
        return false;
    }

    /**
     * Switch the player turn
     */
    public void switchTurn() {
        if (player == ConnectFourBoard.Red) {
            player = ConnectFourBoard.Blue;
        } else if (player == ConnectFourBoard.Blue) {
            player = ConnectFourBoard.Red;
        }
    }

    /**
     * check if someone has won the game horizontally
     * @return true if someone has won using horizontal condition, false otherwise
     */
    public boolean checkWinnerHorizontal() {

        int counter;
        for (int row = 0; row < rows; row++) { // check each row
            counter = 0; // reset counter for each row
            for (int column = 0; column < columns; column++) { // check each column
                if (this.board[row][column] == player) {
                    counter++;
                    if (counter >= goal) { //  goal is 4
                        return true;
                    }
                } else {
                    counter = 0;
                }
            }
        }
        return false;
    }

    /**
     * check if someone has won the game vertically
     * @return true if someone has won using vertical condition, false otherwise
     */
    public boolean checkWinnerVertical() {
        int counter = 0;
        for (int column = 0; column < columns; column++) {
            counter = 0;
            for (int row = 0; row < rows; row++) {
                if (this.board[row][column] == player) {
                    counter++;
                    if (counter >= goal) {
                        return true;
                    }
                } else {
                    counter = 0;
                }
            }
        }
        return false;
    }

    /**
     * check if someone has won the game diagonally
     * @return true if someone won using diagonal condition, false otherwise
     */
    public boolean checkWinnerDiagonal() {
        // Check for diagonal win (top-left to bottom-right)
        for (int row = 0; row <= rows - goal; row++) {
            for (int col = 0; col <= columns - goal; col++) {
                boolean result = true;
                for (int i = 0; i < goal; i++) {
                    if (this.board[row + i][col + i] != player) {
                        result = false;
                        break;
                    }
                }
                if (result) {
                    return true;
                }
            }
        }
        // Check for diagonal win (bottom-left to top-right)
        for (int row = goal - 1; row < rows; row++) {
            for (int col = 0; col <= columns - goal; col++) {
                boolean result = true;
                for (int i = 0; i < goal; i++) {
                    if (this.board[row - i][col + i] != player) {
                        result = false;
                        break;
                    }
                }
                if (result) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * checks if win with the given move in the given column
     * @param col
     * @return true if the move leads to a win, false otherwise
     */
    public boolean canWinWithMove(int col) {
        // We simulate dropping a piece in 'col' and see if it creates a win.
        int row = getAvailableRow(col);
        if (row == -1) return false; // Column full

        // Temporarily place piece
        int original = board[row][col];
        board[row][col] = player;

        // Check if we win
        boolean canWin = checkWinnerHorizontal() || checkWinnerVertical() || checkWinnerDiagonal();

        // Revert the board
        board[row][col] = original;

        return canWin;
    }

    /**
     * get the available row in the given column
     * @param col
     * @return the available row in the given column, -1 if the column is full
     */
    public int getAvailableRow(int col) {
        for (int r = rows - 1; r >= 0; r--) {
            if (board[r][col] == ConnectFourBoard.Empty) {
                return r;
            }
        }
        return -1; // Column is full
    }

    /**
     * check if game is draw
     * @return
     */
    public boolean checkDraw() {

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (this.board[row][column] == ConnectFourBoard.Empty) {
                    return false;
                }

            }
        }

        if (!checkWinnerHorizontal() && !checkWinnerVertical() && !checkWinnerDiagonal()) {
            return true;
        }
        return false;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean state) {
        this.gameOver = state;
    }

    /**
     * getter method that returns the vsComputer boolean value
     * @return true if playing against computer, false if playing against another user player
     */
    public boolean isVsComputer() {
        return vsComputer;
    }

}

