package org.example.game.checkers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class CheckersGameTest {

    private CheckersGame game;

    @BeforeEach
    public void setup() {
        game = new CheckersGame();
    }

    @Test
    public void testInitialBoardSetup() {
        int[][] board = game.getBoard();
        // Red pieces
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 != 0) {
                    assertEquals(1, board[row][col], "Red piece expected at " + row + "," + col);
                }
            }
        }

        // Black pieces
        for (int row = 5; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 != 0) {
                    assertEquals(-1, board[row][col], "Black piece expected at " + row + "," + col);
                }
            }
        }
    }

    @Test
    public void testSimpleValidMove() {
        // Black moves piece from (5, 0) to (4, 1)
        boolean moved = game.movePiece(5, 0, 4, 1);
        assertTrue(moved);
        assertEquals(-1, game.getPiece(4, 1));
        assertEquals(0, game.getPiece(5, 0));
    }

    @Test
    public void testInvalidMoveToOccupiedSquare() {
        // Try moving to a square that already has a piece
        boolean moved = game.movePiece(5, 0, 2, 1); // already has a red piece
        assertFalse(moved);
    }

    @Test
    public void testInvalidMoveWrongDirection() {
        // Try to move black piece backwards (not a king)
        game.movePiece(5, 0, 4, 1); // Valid move
        boolean moved = game.movePiece(4, 1, 5, 0); // Invalid (backward)
        assertFalse(moved);
    }

    @Test
    public void testCaptureMove() {
        // Setup manual board for a capture
        int[][] board = game.getBoard();
        board[4][1] = 1;   // Red opponent piece
        board[5][0] = -1;  // Black player piece
        board[3][2] = 0;   // Landing space

        boolean captured = game.movePiece(5, 0, 3, 2);
        assertTrue(captured);
        assertEquals(0, board[4][1]); // Captured piece gone
        assertEquals(-1, board[3][2]); // Piece moved
    }

    @Test
    public void testKingPromotion() {
        // Setup piece about to reach back row
        int[][] board = game.getBoard();
        board[1][2] = -1; // Black piece near top
        board[0][3] = 0;  // Destination
        board[1][2] = -1;
        game.movePiece(1, 2, 0, 3);
        assertEquals(-2, board[0][3]); // Should now be king
    }

    @Test
    public void testWinCondition() {
        // Remove all red pieces
        int[][] board = game.getBoard();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 8; col++) {
                board[row][col] = 0;
            }
        }

        assertTrue(game.checkWin());
    }

    @Test
    public void testKingMovesBackward() {
        game.movePiece(5, 0, 4, 1); // Black move to switch turn to Red

        int[][] board = game.getBoard();
        board[4][3] = 2; // Manually place a red king
        board[5][2] = 0;

        boolean moved = game.movePiece(4, 3, 5, 2); // Try moving red king backward
        assertTrue(moved, "Red king should be able to move backward");
        assertEquals(2, board[5][2], "Red king should be at the new position");
        assertEquals(0, board[4][3], "Old position should be empty");
    }

    @Test
    public void testInvalidMoveOntoOwnPiece() {
        int[][] board = game.getBoard();
        // Move black piece forward
        game.movePiece(5, 0, 4, 1);

        // Try to move onto another black piece
        boolean result = game.movePiece(4, 1, 3, 2); // Already occupied by another black
        assertFalse(result);
    }

    @Test
    public void testRedCannotMoveBlackPiece() {
        // It's black's turn (default)
        boolean result = game.movePiece(2, 1, 3, 2); // Red's piece
        assertFalse(result);
    }

    @Test
    public void testInvalidOutOfBoundsMove() {
        boolean result = game.movePiece(5, 0, 8, 1); // Off the board
        assertFalse(result);
    }

    @Test
    public void testGameOverWhenNoPiecesLeft() {
        int[][] board = game.getBoard();

        // Clear all red pieces
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 8; col++) {
                board[row][col] = 0;
            }
        }

        boolean win = game.checkWin();
        assertTrue(win, "Game should be over when red has no pieces.");
    }
    @Test
    public void testComputerMakesAMove() {
        // Move black first to switch turn
        game.movePiece(5, 0, 4, 1);
        // Now red's turn (AI)
        game.computerMove();
        // Board should still be valid, and turn switched
        assertTrue(game.isPlayersTurn());
    }

    @Test
    public void testJumpCaptureNotAllowedIfLandingOccupied() {
        int[][] board = game.getBoard();
        board[5][0] = -1;
        board[4][1] = 1;   // red piece
        board[3][2] = -1;  // already occupied

        boolean moved = game.movePiece(5, 0, 3, 2); // capture blocked
        assertFalse(moved);
    }

    @Test
    public void testInvalidKingMoveDiagonalTooFar() {
        game.movePiece(5, 0, 4, 1); // Black moves
        int[][] board = game.getBoard();
        board[4][3] = 2; // Red king
        boolean moved = game.movePiece(4, 3, 1, 0); // Too far, no jump
        assertFalse(moved);
    }

    @Test
    public void testWinConditionWhenBoardIsEmpty() {
        int[][] board = game.getBoard();
        // Remove all pieces
        for (int r = 0; r < 8; r++) {
            Arrays.fill(board[r], 0);
        }
        assertTrue(game.checkWin(), "Game should be over if board is empty");
    }

    @Test
    public void testBlackKingPromotion() {
        int[][] board = game.getBoard();
        board[1][2] = -1;
        board[0][3] = 0;
        game.movePiece(1, 2, 0, 3);
        assertEquals(-2, board[0][3], "Black piece should be promoted to king");
    }
}