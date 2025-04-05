package org.example.game.connectFour;

public class ConnectFourGame {

    public int player;
    public int rows;
    public int columns;
    public int goal;
    public int[][] board;
    private boolean vsComputer;

    public ConnectFourGame(int player, int rows, int columns, int goal, boolean vsComputer) {
        this.player = player;
        this.rows = rows;
        this.columns = columns;
        this.goal = goal;
        this.vsComputer = vsComputer;
        this.board = ConnectFourBoard.createBoard(rows, columns);
    }


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

    public boolean makeMove(int row, int column) {
        if (this.board[row][column] == ConnectFourBoard.Empty) {
            this.board[row][column] = player;
            return true;
        }
        return false;
    }

    public void switchTurn() {
        if (player == ConnectFourBoard.Red) {
            player = ConnectFourBoard.Blue;
        } else if (player == ConnectFourBoard.Blue) {
            player = ConnectFourBoard.Red;
        }
    }

    public boolean checkWinnerHorizontal() {

        int counter = 0;
        for (int row = 0; row < rows; row++) {
            counter = 0;
            for (int column = 0; column < columns; column++) {
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

    public boolean checkWinnerDiagonal() {

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

    public int getAvailableRow(int col) {
        for (int r = rows - 1; r >= 0; r--) {
            if (board[r][col] == ConnectFourBoard.Empty) {
                return r;
            }
        }
        return -1; // Column is full
    }


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

    public boolean isVsComputer() {
        return vsComputer;
    }

    // because we're using GUI for now we can keep this method empty
//    public void printBoard(){
//
//    }
}

