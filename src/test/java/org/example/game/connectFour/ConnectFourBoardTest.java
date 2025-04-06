package org.example.game.connectFour;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConnectFourBoardTest {

    @Test
    void testCreateBoardDimensions() {
        int rows = 6;
        int columns = 7;
        int[][] board = ConnectFourBoard.createBoard(rows, columns);

        // Check if the board has the expected number of rows
        Assertions.assertEquals(rows, board.length, "Board should have " + rows + " rows");

        // Check if each row has the expected number of columns
        for (int i = 0; i < rows; i++) {
            Assertions.assertEquals(columns, board[i].length, "Row " + i + " should have " + columns + " columns");
        }
    }

    @Test
    void testBoardInitialization() {
        int rows = 6;
        int columns = 7;
        int[][] board = ConnectFourBoard.createBoard(rows, columns);

        // Verify that all cells are initialized to the empty value
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Assertions.assertEquals(ConnectFourBoard.Empty, board[i][j],
                        "Cell (" + i + ", " + j + ") should be initialized to Empty");
            }
        }
    }

    @Test
    void testNewBoardIsFresh() {
        int rows = 6;
        int columns = 7;
        int[][] board1 = ConnectFourBoard.createBoard(rows, columns);
        int[][] board2 = ConnectFourBoard.createBoard(rows, columns);

        // Modify the first board
        board1[0][0] = ConnectFourBoard.Red;

        // The second board should not be affected by modifications to the first board
        Assertions.assertEquals(ConnectFourBoard.Empty, board2[0][0],
                "A new board should be independent and initialized to Empty");
    }
}
