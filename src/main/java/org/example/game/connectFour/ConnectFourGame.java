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
        int counter = 0;
        for (int row = 0; row <= rows - goal; row++) {
            for (int col = 0; col <= columns - goal; col++) {
                counter = 0;
                for (int i = 0; i < goal; i++) {
                    if (this.board[row + i][col + i] == player) {
                        counter++;
                    } else {
                        break;
                    }
                }
                if (counter == goal) {
                    return true;
                }
            }
        }

        for (int row = goal - 1; row < rows; row++) {
            for (int col = 0; col <= columns - goal; col++) {
                counter = 0;
                for (int i = 0; i < goal; i++) {
                    if (this.board[row - i][col + i] == player) {
                        counter++;
                    } else {
                        break;
                    }
                }
                if (counter == goal) {
                    return true;
                }
            }
        }
        return false;
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
            return false;
        }

// because we're using GUI for now we can keep this method empty
//    public void printBoard(){
//
//    }
    return checkDraw();
    }

    public boolean isVsComputer() {
        return vsComputer;
    }
}

