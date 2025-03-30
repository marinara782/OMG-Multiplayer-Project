package org.example.game.connectFour;

public class ConnectFourGame {

    public int player;
    public int rows;
    public int columns;
    public int goal;
    public int[][] board;

    public ConnectFourGame(int player, int rows, int columns, int goal) {
        this.player = player;
        this.rows = rows;
        this.columns = columns;
        this.goal = goal;
    }

    public int[][] getBoard() {
        return this.board;
    }

    public int getPlayer() {
        return this.player;
    }


}
