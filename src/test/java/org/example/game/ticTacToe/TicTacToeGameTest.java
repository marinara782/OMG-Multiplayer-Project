package org.example.game.ticTacToe;

import org.example.game.ticTacToe.TicTacToeGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TicTacToeGameTest
{
    TicTacToeGame game;

    @BeforeEach
    void setUp() {
        game = new TicTacToeGame(false);
    }

    @Test void testCheckWinRowWin() {
        game.makeMove(0, 0, 'X');
        game.makeMove(0, 1, 'X');
        game.makeMove(0, 2, 'X');
        assertTrue(game.checkForWin('X'));
    }

    @Test void testCheckNoWin() {
        game.makeMove(0, 0, 'X');
        game.makeMove(0, 1, 'O');
        game.makeMove(0, 2, 'X');
        assertFalse(game.checkForWin('X'));
    }

    @Test void testIsBoardFullFullBoard() {
        fillBoardCompletely();
        assertTrue(game.isBoardFull());
    }

    @Test void testIsBoardFullNotFullBoard() {
        game.makeMove(0, 0, 'X');
        assertFalse(game.isBoardFull());
    }

    @Test void testGetAIMoveCenterOpen() {
        int[] move = game.getAIMove();
        assertArrayEquals(new int[]{1, 1}, move);
    }

    @Test void testGetAIMoveWhenBoardFull() {
        fillBoardCompletely();
        assertNull(game.getAIMove());
    }

    @Test void testIsPlayerTurn() {
        game.isPlayerTurn();
        assertEquals('X', game.getCurrentPlayer());
    }

    @Test void testIsOpponentTurn() {
        game.isOpponentTurn();
        assertEquals('O', game.getCurrentPlayer());
    }

    @Test void testMakeTopLeftXMove() {
        game.makeMove(0, 0, 'X');
        assertEquals('X', game.getBoardValue(0, 0));
    }

    @Test void testMakeBottomRightOMove() {
        game.makeMove(2, 2, 'O');
        assertEquals('O', game.getBoardValue(2, 2));
    }

    @Test void testGetBoardValueValidOpenCell() {
        game.makeMove(1, 1, 'X');
        assertEquals('X', game.getBoardValue(1, 1));
    }

    @Test void testGetBoardValueInvalidCell() {
        assertEquals(0, game.getBoardValue(-1, 5));
    }

    @Test void testIsPlayerAndComputerWhenTrue() {
        TicTacToeGame g2 = new TicTacToeGame(true);
        assertTrue(g2.isPlayerAndComputer());
    }

    @Test void testIsPlayerAndComputerWhenFalse() {
        TicTacToeGame g3 = new TicTacToeGame(false);
        assertFalse(g3.isPlayerAndComputer());
    }

    @Test void testTwoPlayersGameFalse() {
        game.twoPlayersGame();
        assertFalse(game.isPlayerAndComputer());
    }

    @Test void testTwoPlayersGameWhenCurrentPlayerX() {
        game.twoPlayersGame();
        assertEquals('X', game.getCurrentPlayer());
    }

    @Test void testGetPlayerSymbol() {
        assertEquals('X', game.getPlayerSymbol());
    }

    @Test void testGetOpponentSymbol() {
        assertEquals('O', game.getOpponentSymbol());
    }

    @Test void testBoardInitializationWhenClearsBoard() {
        game.makeMove(0, 0, 'X');
        game.BoardInitialization();
        assertEquals('/', game.getBoardValue(0, 0));
    }

    @Test void testBoardInitializationWhenAllEmpty() {
        game.BoardInitialization();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                assertEquals('/', game.getBoardValue(i, j));
    }

    private void fillBoardCompletely() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                game.makeMove(i, j, 'X');
    }

    @Test
    void testGetAIMoveCornerPosition() {
        game.makeMove(1, 1, 'X');
        int[] move = game.getAIMove();
        assertNotNull(move);
        assertTrue((move[0] == 0 && move[1] == 0) ||
                (move[0] == 0 && move[1] == 2) ||
                (move[0] == 2 && move[1] == 0) ||
                (move[0] == 2 && move[1] == 2));
    }

    @Test
    void testGetAIMoveTopRightCornePosition() {
        game.makeMove(0, 0, 'X'); // block top-left
        game.makeMove(2, 0, 'X'); // block bottom-left
        game.makeMove(2, 2, 'X'); // block bottom-right
        game.makeMove(1, 1, 'X'); // block center
        game.makeMove(0, 1, 'X');
        game.makeMove(1, 0, 'X');
        game.makeMove(1, 2, 'X');
        game.makeMove(2, 1, 'X');

        // Only (0,2) is left
        int[] move = game.getAIMove();
        assertArrayEquals(new int[]{0, 2}, move);
    }


    @Test
    void testCheckForWinLeftToRightDiagonal() {
        game.makeMove(0, 0, 'X');
        game.makeMove(1, 1, 'X');
        game.makeMove(2, 2, 'X');
        assertTrue(game.checkForWin('X'));
    }
    @Test
    void testGetAIMoveTopLeftCorner() {
        game.makeMove(1, 1, 'X'); // center is taken
        game.makeMove(0, 2, 'X'); // take another corner
        game.makeMove(2, 0, 'X'); // take another corner
        game.makeMove(2, 2, 'X'); // take another corner

        int[] move = game.getAIMove();
        assertArrayEquals(new int[]{0, 0}, move);
    }

    @Test
    void testGetAIMoveTopRightCorner() {
        game.makeMove(0, 0, 'X'); // top-left
        game.makeMove(2, 0, 'X'); // bottom-left
        game.makeMove(2, 2, 'X'); // bottom-right
        game.makeMove(1, 1, 'X'); // center
        game.makeMove(0, 1, 'X');
        game.makeMove(1, 0, 'X');
        game.makeMove(1, 2, 'X');
        game.makeMove(2, 1, 'X');
        int[] move = game.getAIMove();
        assertArrayEquals(new int[]{0, 2}, move);
    }




    @Test
    void testGetAIMoveBottomLeftCorner() {
        game.makeMove(0, 0, 'X');
        game.makeMove(0, 2, 'X');
        game.makeMove(2, 2, 'X');
        game.makeMove(1, 1, 'X');
        game.makeMove(0, 1, 'X');
        game.makeMove(1, 0, 'X');
        game.makeMove(1, 2, 'X');
        game.makeMove(2, 1, 'X');
        int[] move = game.getAIMove();
        assertArrayEquals(new int[]{2, 0}, move);
    }




    @Test
    void testGetAIMoveBottomRightCorner() {
        // Fill ALL other corners and center so only (2,2) is left
        game.makeMove(0, 0, 'X'); // top-left
        game.makeMove(0, 2, 'X'); // top-right
        game.makeMove(2, 0, 'X'); // bottom-left
        game.makeMove(1, 1, 'X'); // center
        game.makeMove(0, 1, 'X'); // fill edge
        game.makeMove(1, 0, 'X');
        game.makeMove(1, 2, 'X');
        game.makeMove(2, 1, 'X');
        int[] move = game.getAIMove();
        assertArrayEquals(new int[]{2, 2}, move);
    }






    @Test
    void testCheckForWinRightToLeftDiagonal() {
        game.makeMove(0, 2, 'X');
        game.makeMove(1, 1, 'X');
        game.makeMove(2, 0, 'X');
        assertTrue(game.checkForWin('X'));
    }

    @Test
    void testGetAIMoveNullWhenFull() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                game.makeMove(i, j, (i + j) % 2 == 0 ? 'X' : 'O'); // mix X and O

        assertNull(game.getAIMove());
    }


    @Test
    void testGetAIMoveAIWin() {
        game.makeMove(0, 0, 'O');
        game.makeMove(0, 1, 'O');
        // leave (0, 2) open so AI can win
        int[] move = game.getAIMove();
        assertArrayEquals(new int[]{0, 2}, move);
    }

    @Test
    void testGetAIMoveAIBlocksPlayer() {
        game.makeMove(2, 0, 'X');
        game.makeMove(2, 1, 'X');
        game.makeMove(1, 1, 'O'); // fill center so AI can't go there
        int[] move = game.getAIMove();
        assertArrayEquals(new int[]{2, 2}, move);
    }

    @Test
    void testCheckForWinColumnWin() {
        game.makeMove(0, 2, 'O');
        game.makeMove(1, 2, 'O');
        game.makeMove(2, 2, 'O');
        assertTrue(game.checkForWin('O'));
    }


    @Test
    void testGetAIMoveFirstOpenCell() {
        // Fill all corners and center
        game.makeMove(0, 0, 'X');
        game.makeMove(0, 2, 'X');
        game.makeMove(2, 0, 'X');
        game.makeMove(2, 2, 'X');
        game.makeMove(1, 1, 'X');

        int[] move = game.getAIMove();
        assertNotNull(move);
        assertTrue(game.getBoardValue(move[0], move[1]) == '/'); // make sure it's an empty cell
    }

    @Test
    void testGetAIMoveTopRightLeft() {
        // Fill all except (0,2)
        game.makeMove(0, 0, 'X');
        game.makeMove(0, 1, 'X');
        game.makeMove(1, 0, 'X');
        game.makeMove(1, 1, 'X');
        game.makeMove(1, 2, 'X');
        game.makeMove(2, 0, 'X');
        game.makeMove(2, 1, 'X');
        game.makeMove(2, 2, 'X');

        int[] move = game.getAIMove();
        assertArrayEquals(new int[]{0, 2}, move);
    }


    @Test
    void testGetAIMoveBottomLeftLeft() {
        game.makeMove(0, 0, 'X');
        game.makeMove(0, 1, 'X');
        game.makeMove(0, 2, 'X');
        game.makeMove(1, 0, 'X');
        game.makeMove(1, 1, 'X');
        game.makeMove(1, 2, 'X');
        game.makeMove(2, 1, 'X');
        game.makeMove(2, 2, 'X');

        int[] move = game.getAIMove();
        assertArrayEquals(new int[]{2, 0}, move);
    }


    @Test
    void testGetAIMoveBottomRightRight() {
        game.makeMove(0, 0, 'X');
        game.makeMove(0, 1, 'X');
        game.makeMove(0, 2, 'X');
        game.makeMove(1, 0, 'X');
        game.makeMove(1, 1, 'X');
        game.makeMove(1, 2, 'X');
        game.makeMove(2, 0, 'X');
        game.makeMove(2, 1, 'X');

        int[] move = game.getAIMove();
        assertArrayEquals(new int[]{2, 2}, move);
    }


    @Test
    void testGetAIMoveFirstOpenSideCell() {
        // Fill all corners and center
        game.makeMove(0, 0, 'X');
        game.makeMove(0, 2, 'X');
        game.makeMove(2, 0, 'X');
        game.makeMove(2, 2, 'X');
        game.makeMove(1, 1, 'X');

        // AI should now fall to the last for-loop to pick first empty cell (e.g. 0,1)
        int[] move = game.getAIMove();
        assertArrayEquals(new int[]{0, 1}, move);  // covers fallback loop
    }

    @Test
    void testMakeAISwitchToPlayerTurn() {
        // Clear the board just in case
        game.BoardInitialization();

        // Switch to AI's turn
        game.isOpponentTurn();

        // Get the expected AI move before it makes it
        int[] move = game.getAIMove();

        // Make the AI move
        // Using reflection since the method is private and you said not to make changes to TicTacToeGame
        try {
            var method = TicTacToeGame.class.getDeclaredMethod("makeAIMove");
            method.setAccessible(true); // make private method accessible
            method.invoke(game);
        } catch (Exception e) {
            fail("Reflection failed: " + e.getMessage());
        }

        // Check if AI made the move
        assertEquals('O', game.getBoardValue(move[0], move[1]));

        // Check if turn switched back to player
        assertEquals('X', game.getCurrentPlayer());
    }

    @Test
    void testGetAIMoveForLoopFallback() {
        // Block all corners and center
        game.makeMove(0, 0, 'X');
        game.makeMove(0, 2, 'X');
        game.makeMove(2, 0, 'X');
        game.makeMove(2, 2, 'X');
        game.makeMove(1, 1, 'X');

        // First empty fallback: (0,1)
        int[] move = game.getAIMove();
        assertArrayEquals(new int[]{0, 1}, move);
    }














}
