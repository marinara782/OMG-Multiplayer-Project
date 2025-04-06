package org.example.game.connectFour;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConnectFourGameTest {

    //makeMove() Tests
    @Test
    public void validMoveTest_makeMove(){
        ConnectFourGame game = new ConnectFourGame(1,6,7,4, false);
        boolean result = game.makeMove(5, 0);
        assertTrue(result, "should return true because valid move");
        assertEquals(1, game.getBoard()[5][0], "cell should be assigned to player 1");
    }

    @Test
    public void occupiedCellMove_makeMove(){
        ConnectFourGame game = new ConnectFourGame(1,6,7,4, false);
        game.makeMove(5, 0);
        boolean result = game.makeMove(5, 0);
        assertFalse(result, "should return false because cell is occupied");
    }

    @Test
    public void testDifferentPlayerMoves_makeMove(){
        ConnectFourGame game = new ConnectFourGame(1,6,7,4, false);
        game.makeMove(5, 0);
        game.switchTurn();
        game.makeMove(5, 1);
        assertEquals(1, game.getBoard()[5][0], "cell should be assigned to player 1");
        assertEquals(2, game.getBoard()[5][1], "cell should be assigned to player 2");
    }

    @Test
    public void testDifferentColumnMoves_makeMove(){
        ConnectFourGame game = new ConnectFourGame(1,6,7,4, false);
        assertTrue(game.makeMove(5, 0), "should return true");
        assertTrue(game.makeMove(5, 1), "should return true");
        assertEquals(1, game.getBoard()[5][0], "cell should be assigned to player 1");
        assertEquals(1, game.getBoard()[5][1], "cell should be also be assigned to player 1");
    }

    @Test
    public void testInvalidMove_makeMove(){
        ConnectFourGame game = new ConnectFourGame(1,6,7,4, false);
        game.makeMove(5, 0);
        boolean result = game.makeMove(5, 0);
        assertFalse(result, "should return false because 5,0 is already assigned to player 1");
        assertEquals(1, game.getBoard()[5][0], "cell should be still be assigned to player 1");

    }

    //SwitchTurn tests

    @Test
    public void switchFromRedToBlue_switchMove(){
        ConnectFourGame game = new ConnectFourGame(1,6,7,4, false);
        game.switchTurn();
        assertEquals(ConnectFourBoard.Blue, game.getPlayer(), "should return BLUE");
    }

    @Test
    public void switchFromBlueToRed_switchMove(){
        ConnectFourGame game = new ConnectFourGame(2,6,7,4, false);
        game.switchTurn();
        assertEquals(ConnectFourBoard.Red, game.getPlayer(), "should return RED");
    }

    @Test
    public void switchAnEmptyCell_switchMove(){
        ConnectFourGame game = new ConnectFourGame(0,6,7,4, false);
        game.switchTurn();
        assertEquals(ConnectFourBoard.Empty, game.getPlayer(), "The cell should still be empty");
    }

    //checkWinnerHorizontal tests

    @Test
    public void winHorizontal_checkWinnerHorizontal(){
        ConnectFourGame game = new ConnectFourGame(1,6,7,4, false);
        int[][] board = game.getBoard();
        for(int i = 0; i < game.getGoal(); i++){
            board[0][i] = 1;
        }
        assertTrue(game.checkWinnerHorizontal());
    }

    @Test
    public void oneLessThanGoal_checkWinnerHorizontal(){
        ConnectFourGame game = new ConnectFourGame(1,6,7,4, false);
        int[][] board = game.getBoard();
        for(int i = 0; i < game.getGoal() - 1; i++){
            board[i][0] = 1;
        }
        assertFalse(game.checkWinnerHorizontal());
    }

    @Test
    public void goalBreakInMiddle_checkWinnerHorizontal(){
        ConnectFourGame game = new ConnectFourGame(1,6,7,4, false);
        int[][] board = game.getBoard();
        board[2][0] = 1;
        board[2][1] = 1;
        board[2][2] = 2;
        board[2][3] = 1;
        board[2][4] = 1;
        assertFalse(game.checkWinnerHorizontal());

    }

    @Test
    public void checkWinInMiddle_checkWinnerHorizontal(){
        ConnectFourGame game = new ConnectFourGame(1,6,7,4, false);
        int[][] board = game.getBoard();
        board[4][1] = 1;
        board[4][2] = 1;
        board[4][3] = 1;
        board[4][4] = 1;
        assertTrue(game.checkWinnerHorizontal());
    }

    @Test
    public void checkWinOnEmptyBoard_checkWinnerHorizontal(){
        ConnectFourGame game = new ConnectFourGame(1,6,7,4, false);
        assertFalse(game.checkWinnerHorizontal());
    }

    @Test
    public void checkWinnerOnDraw_checkWinnerHorizontal(){
        ConnectFourGame game = new ConnectFourGame(1,4,5,4, false);
        int[][] board = game.getBoard();
        int[][] drawnBoard = {
                {1,2,1,2,1},
                {2,2,1,1,1},
                {1,1,2,2,2},
                {1,2,1,1,2},
        };
        for(int i = 0; i < game.getRows(); i++){
            for(int j = 0; j < game.columns; j++){
                board[i][j] = drawnBoard[i][j];
            }
        }
        game.player = 1;
        assertFalse(game.checkWinnerHorizontal());

    }

    //checkWinnerVertical tests

    @Test
    public void winVertical_checkWinnerVertical(){
        ConnectFourGame game = new ConnectFourGame(1,6,7,4, false);
        int[][] board = game.getBoard();
        for(int i = 0; i < game.getGoal(); i++){
            board[i][0] = 1;
        }
        assertTrue(game.checkWinnerVertical());
    }

    @Test
    public void oneLessThanGoal_checkWinnerVertical(){
        ConnectFourGame game = new ConnectFourGame(1,6,7,4, false);
        int[][] board = game.getBoard();
        for(int i = 0; i < game.getGoal() - 1; i++){
            board[i][0] = 1;
        }
        assertFalse(game.checkWinnerVertical());
    }

    @Test
    public void goalBreakInMiddle_checkWinnerVertical(){
        ConnectFourGame game = new ConnectFourGame(1,6,7,4, false);
        int[][] board = game.getBoard();
        board[0][1] = 1;
        board[1][1] = 1;
        board[2][1] = 2;
        board[3][1] = 1;
        board[4][1] = 1;
        assertFalse(game.checkWinnerVertical());

    }

    @Test
    public void checkWinInMiddle_checkWinnerVertical(){
        ConnectFourGame game = new ConnectFourGame(1,6,7,4, false);
        int[][] board = game.getBoard();
        board[2][4] = 1;
        board[3][4] = 1;
        board[4][4] = 1;
        board[5][4] = 1;
        assertTrue(game.checkWinnerVertical());
    }

    @Test
    public void checkVerticalWinOnEmptyBoard_checkWinnerVertical(){
        ConnectFourGame game = new ConnectFourGame(1,6,7,4, false);
        assertFalse(game.checkWinnerVertical());
    }

    @Test
    public void checkVerticalWinnerOnDraw_checkWinnerVertical(){
        ConnectFourGame game = new ConnectFourGame(1,4,5,4, false);
        int[][] board = game.getBoard();
        int[][] drawnBoard = {
                {1,2,1,2,1},
                {2,2,1,1,1},
                {1,1,2,2,2},
                {1,2,1,1,2},
        };
        for(int i = 0; i < game.getRows(); i++){
            for(int j = 0; j < game.columns; j++){
                board[i][j] = drawnBoard[i][j];
            }
        }
        game.player = 1;
        assertFalse(game.checkWinnerVertical());

    }


    //checkWinnerDiagonal tests

    @Test
    public void winDiagonalLeftToRight_checkWinnerDiagonal(){
        ConnectFourGame game = new ConnectFourGame(1,6,7,4, false);
        int[][] board = game.getBoard();
        board[1][1] = 1;
        board[2][2] = 1;
        board[3][3] = 1;
        board[4][4] = 1;
        assertTrue(game.checkWinnerDiagonal());
    }
    @Test
    public void winDiagonalRightToLeft_checkWinnerDiagonal(){
        ConnectFourGame game = new ConnectFourGame(1,6,7,4, false);
        int[][] board = game.getBoard();
        board[5][0] = 1;
        board[4][1] = 1;
        board[3][2] = 1;
        board[2][3] = 1;
        assertTrue(game.checkWinnerDiagonal());
    }

    @Test
    public void oneLessThanGoal_checkWinnerDiagonal(){
        ConnectFourGame game = new ConnectFourGame(1,6,7,4, false);
        int[][] board = game.getBoard();
        board[0][0] = 1;
        board[1][1] = 1;
        board[2][2] = 1;
        assertFalse(game.checkWinnerDiagonal());
    }

    @Test
    public void goalBreakInMiddle_checkWinnerDiagonal(){
        ConnectFourGame game = new ConnectFourGame(1,6,7,4, false);
        int[][] board = game.getBoard();
        board[1][1] = 1;
        board[2][2] = 1;
        board[3][3] = 2;
        board[4][4] = 1;
        board[5][5] = 1;
        assertFalse(game.checkWinnerDiagonal());

    }

    @Test
    public void checkWinInMiddle_checkWinnerDiagonal(){
        ConnectFourGame game = new ConnectFourGame(1,6,7,4, false);
        int[][] board = game.getBoard();
        board[2][2] = 1;
        board[3][3] = 1;
        board[4][4] = 1;
        board[5][5] = 1;
        assertTrue(game.checkWinnerDiagonal());
    }

    @Test
    public void checkDiagonalWinOnEmptyBoard_checkWinnerDiagonal(){
        ConnectFourGame game = new ConnectFourGame(1,6,7,4, false);
        assertFalse(game.checkWinnerDiagonal());
    }

    @Test
    public void checkDiagonalWinnerOnDraw_checkWinnerDiagonal(){
        ConnectFourGame game = new ConnectFourGame(1,4,5,4, false);
        int[][] board = game.getBoard();
        int[][] drawnBoard = {
                {1,2,1,2,1},
                {2,2,1,1,1},
                {1,1,2,2,2},
                {1,2,1,1,2},
        };
        for(int i = 0; i < game.getRows(); i++){
            for(int j = 0; j < game.columns; j++){
                board[i][j] = drawnBoard[i][j];
            }
        }
        game.player = 1;
        assertFalse(game.checkWinnerDiagonal());

    }



}
