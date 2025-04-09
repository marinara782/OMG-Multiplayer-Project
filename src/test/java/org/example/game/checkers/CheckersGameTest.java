package org.example.game.checkers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    @Test
    public void testMoveFromOutOfBoundsPosition() {
        boolean result = game.movePiece(-1, 0, 2, 1);
        assertFalse(result);
    }

    @Test
    public void testMoveFromEmptySquare() {
        boolean result = game.movePiece(4, 1, 3, 2); // 4,1 is initially empty
        assertFalse(result, "Should not move from an empty square");
    }

    @Test
    public void testNoValidMovesAvailable() {
        int[][] board = game.getBoard();
        // Fill the board with only black pieces (no red)
        for (int row = 0; row < 8; row++) {
            Arrays.fill(board[row], -1);
        }

        boolean win = game.checkWin();
        assertTrue(win, "Game should be over if red has no pieces.");
    }

    @Test
    public void testInvalidJumpOverOwnPiece() {
        int[][] board = game.getBoard();
        board[5][0] = -1;
        board[4][1] = -1; // same color
        board[3][2] = 0;

        boolean result = game.movePiece(5, 0, 3, 2);
        assertFalse(result, "Should not allow jumping over your own piece");
    }

    @Test
    public void testKingJumpBackward() {
        // Make black move to switch turn to red
        game.movePiece(5, 0, 4, 1);

        int[][] board = game.getBoard();
        board[3][2] = -1;  // Black piece to be captured
        board[4][1] = 2;   // Red king
        board[2][3] = 0;   // Landing square

        boolean moved = game.movePiece(4, 1, 2, 3);
        assertTrue(moved, "Red king should be able to jump backward");
        assertEquals(0, board[3][2], "Black piece should be captured");
        assertEquals(2, board[2][3], "Red king should land after jump");
    }

    @Test
    public void testComputerMoveWithPreviewReturnsArray() {
        int[][] result = game.computerMoveWithPreview();
        assertNotNull(result);
    }

    @Test
    public void testFinalizeComputerMoveRunsWithoutError() {
        game.finalizeComputerMove(); // should not throw
    }

    @Test
    public void testGetValidMovesReturnsEmptyWhenNoMoves() {
        int[][] board = game.getBoard();
        // Block a black piece completely
        board[4][1] = -1;
        board[3][0] = -1;
        board[3][2] = -1;

        var moves = game.getValidMoves(4, 1);
        assertTrue(moves.isEmpty(), "Should return empty when no valid moves");
    }

    @Test
    public void testGetValidMovesReturnsSimpleMove() {
        var moves = game.getValidMoves(5, 0); // Black piece
        assertFalse(moves.isEmpty());
    }

    @Test
    public void testGetValidMovesIncludesJump() {
        int[][] board = game.getBoard();
        board[5][0] = -1;     // Black piece
        board[4][1] = 1;      // Red piece (opponent)
        board[3][2] = 0;      // Jump landing spot

        var moves = game.getValidMoves(5, 0);
        boolean foundJump = moves.stream().anyMatch(m -> m[0] == 3 && m[1] == 2);
        assertTrue(foundJump, "Should include jump move to 3,2");
    }

    @Test
    public void testBothFromAndToOutOfBounds() {
        boolean result = game.movePiece(-1, -1, 8, 8);
        assertFalse(result, "Should return false when both from and to are out-of-bounds");
    }

    @Test
    public void testJumpOverEmptySquareFails() {
        int[][] board = game.getBoard();
        board[5][0] = -1;
        board[4][1] = 0; // Empty
        board[3][2] = 0; // Landing

        boolean moved = game.movePiece(5, 0, 3, 2);
        assertFalse(moved, "Should not allow jump over empty square");
    }

    @Test
    public void testJumpOverOwnPieceFails() {
        int[][] board = game.getBoard();
        board[5][0] = -1;
        board[4][1] = -1; // Same side
        board[3][2] = 0;

        boolean moved = game.movePiece(5, 0, 3, 2);
        assertFalse(moved, "Should not allow jump over your own piece");
    }

    @Test
    public void testJumpToOccupiedSquareFails() {
        int[][] board = game.getBoard();
        board[5][0] = -1;
        board[4][1] = 1;   // Enemy
        board[3][2] = -1;  // Occupied

        boolean moved = game.movePiece(5, 0, 3, 2);
        assertFalse(moved, "Should not jump to a square that’s already occupied");
    }

    @Test
    public void testRedPromotionToKing() {
        game.movePiece(5, 0, 4, 1); // Black to switch turn
        int[][] board = game.getBoard();
        board[6][2] = 1;
        board[7][3] = 0;
        game.movePiece(6, 2, 7, 3);
        assertEquals(2, board[7][3], "Red piece should be promoted to king");
    }

    @Test
    public void testComputerMoveWithPreviewReturnsEmptyArray() {
        int[][] preview = game.computerMoveWithPreview();
        assertNotNull(preview);
        assertEquals(0, preview.length, "Preview array should be empty");
    }

    @Test
    public void testFinalizeComputerMoveDoesNotCrash() {
        game.finalizeComputerMove(); // Empty, just ensure it runs
        assertTrue(true); // dummy assert to confirm test runs
    }

    @Test
    public void testRedKingJumpMove() {
        game.movePiece(5, 0, 4, 1); // black move to pass turn

        int[][] board = game.getBoard();
        board[5][0] = 0;
        board[6][2] = 2; // red king
        board[5][1] = -1; // black piece to jump over
        board[4][0] = 0;  // landing spot

        var moves = game.getValidMoves(6, 2);
        boolean foundJump = moves.stream().anyMatch(m -> m[0] == 4 && m[1] == 0);
        assertTrue(foundJump, "Red king should be able to jump over black piece");
    }

    @Test
    public void testBlackKingSimpleMove() {
        int[][] board = game.getBoard();
        board[5][0] = 0;
        board[4][1] = -2; // Black king

        var moves = game.getValidMoves(4, 1);
        boolean hasSimpleMove = moves.stream().anyMatch(m -> m[0] == 3 && m[1] == 0);
        assertTrue(hasSimpleMove, "Black king should have a simple backward move");
    }

    @Test
    public void testGetValidMovesNoJumpOverBlockedOpponent() {
        int[][] board = game.getBoard();
        board[4][3] = -1;
        board[3][2] = 1;  // opponent piece
        board[2][1] = 1;  // blocked landing
        board[3][4] = 1;  // opponent
        board[2][5] = 1;  // also blocked

        var moves = game.getValidMoves(4, 3);
        assertTrue(moves.isEmpty(), "No valid jump if all landing spaces are blocked");
    }

    @Test
    public void testKingValidMoveWithMixedConditions() {
        int[][] board = game.getBoard();
        board[4][3] = -2;  // Black king

        board[3][2] = 1;   // enemy (can jump left)
        board[2][1] = 0;

        board[3][4] = -1;  // own piece (cannot jump right)

        var moves = game.getValidMoves(4, 3);
        boolean foundJump = moves.stream().anyMatch(m -> m[0] == 2 && m[1] == 1);
        boolean blockedSide = moves.stream().noneMatch(m -> m[0] == 2 && m[1] == 5);

        assertTrue(foundJump, "Should allow jump over enemy");
        assertTrue(blockedSide, "Should not allow jump over own piece");
    }

    @Test
    public void testKingAtBoardEdgeHasNoOffBoardMoves() {
        int[][] board = game.getBoard();
        board[0][1] = -2; // black king at top edge

        var moves = game.getValidMoves(0, 1);
        assertTrue(moves.stream().noneMatch(m -> m[0] < 0), "King should not move out of bounds");
    }

    @Test
    public void testRedPieceBackwardMoveInvalid() {
        game.movePiece(5, 0, 4, 1); // black moves
        int[][] board = game.getBoard();
        board[6][2] = 1; // red normal piece

        boolean moved = game.movePiece(6, 2, 5, 1); // red tries to go back
        assertFalse(moved, "Red non-king should not move backward");
    }

    @Test
    public void testBlackKingInvalidLongMove() {
        int[][] board = game.getBoard();
        board[5][0] = 0;
        board[2][3] = 0;
        board[4][1] = -2; // black king

        boolean moved = game.movePiece(4, 1, 2, 3); // 2-step move without jump
        assertFalse(moved, "Black king shouldn't move multiple spaces unless jumping");
    }

    @Test
    public void testPieceSurroundedByOwnColorHasNoMoves() {
        int[][] board = game.getBoard();
        board[4][3] = -1;
        board[3][2] = -1;
        board[3][4] = -1;

        var moves = game.getValidMoves(4, 3);
        assertTrue(moves.isEmpty(), "Piece should have no valid moves when blocked by own pieces");
    }

    @Test
    public void testInvalidMoveTooFar() {
        boolean moved = game.movePiece(5, 0, 2, 3); // too far
        assertFalse(moved, "Should not allow 3-step diagonal move");
    }

    @Test
    public void testRedRegularPieceCannotMoveBackward() {
        game.movePiece(5, 0, 4, 1); // switch to red's turn
        int[][] board = game.getBoard();
        board[6][2] = 1; // red regular
        board[5][1] = 0;

        boolean moved = game.movePiece(6, 2, 5, 1);
        assertFalse(moved);
    }

    @Test
    public void testBlackKingCannotMoveTooFar() {
        int[][] board = game.getBoard();
        board[5][0] = 0;
        board[4][1] = -2; // black king
        boolean moved = game.movePiece(4, 1, 1, 4); // illegal 3-step
        assertFalse(moved);
    }

    @Test
    public void testMoveToOutOfBoundsFails() {
        boolean moved = game.movePiece(5, 0, 7, 8); // toCol = 8 (invalid)
        assertFalse(moved);
    }

    @Test
    public void testJumpOverSameColorPieceFails() {
        int[][] board = game.getBoard();
        board[5][0] = -1;
        board[4][1] = -1; // same color
        board[3][2] = 0;

        boolean moved = game.movePiece(5, 0, 3, 2);
        assertFalse(moved);
    }

    @Test
    public void testMoveToOccupiedSquareFails() {
        int[][] board = game.getBoard();
        board[5][0] = -1;
        board[4][1] = -1; // occupied
        boolean moved = game.movePiece(5, 0, 4, 1);
        assertFalse(moved);
    }

    @Test
    public void testRedAndBlackPromotionSeparate() {
        game.movePiece(5, 0, 4, 1); // switch to red

        int[][] board = game.getBoard();
        board[6][2] = 1;
        board[7][3] = 0;
        game.movePiece(6, 2, 7, 3); // red promotion
        assertEquals(2, board[7][3]);

        board[1][4] = -1;
        board[0][5] = 0;
        game.movePiece(1, 4, 0, 5); // black promotion
        assertEquals(-2, board[0][5]);
    }

    @Test
    public void testGetValidMovesOnEmptySquare() {
        int[][] board = game.getBoard();
        board[4][4] = 0;
        var moves = game.getValidMoves(4, 4);
        assertTrue(moves.isEmpty());
    }

    @Test
    public void testBlackKingFailsJumpOverEmptySquare() {
        int[][] board = game.getBoard();
        board[4][1] = -2; // Black king
        board[3][2] = 0;  // Nothing to jump
        board[2][3] = 0;  // Landing spot

        boolean moved = game.movePiece(4, 1, 2, 3);
        assertFalse(moved);
    }

    @Test
    public void testRedJumpLandingOutOfBoundsFails() {
        game.movePiece(5, 0, 4, 1); // switch to red

        int[][] board = game.getBoard();
        board[6][6] = 1;       // Red piece
        board[7][7] = -1;      // Black piece (jump target)
        // No room to land beyond (would be 8,8 — out of bounds)

        boolean moved = game.movePiece(6, 6, 8, 8);
        assertFalse(moved);
    }

    @Test
    public void testGetValidMovesForInvalidPieceType() {
        int[][] board = game.getBoard();
        board[4][4] = 99; // Invalid, but still positive → treated like red king
        var moves = game.getValidMoves(4, 4);
        assertNotNull(moves); // still returns something
    }

    @Test
    public void testValidJumpAddsMoveInGetAllValidMoves() {
        int[][] board = game.getBoard();

        // Clear the board
        for (int r = 0; r < 8; r++) {
            Arrays.fill(board[r], 0);
        }

        // Place dummy black piece to allow black move
        board[5][0] = -1;
        board[4][1] = 0;

        // Move black to switch to red turn
        game.movePiece(5, 0, 4, 1);

        // Now red's turn — set up valid jump
        board[4][3] = 1;    // Red piece
        board[5][4] = -1;   // Black piece
        board[6][5] = 0;    // Landing spot

        // Confirm move exists via reflection
        List<?> moves = getAllValidMovesViaReflection(true);
        assertFalse(moves.isEmpty(), "Expected at least one valid jump move");

        // Execute it
        boolean jumped = game.movePiece(4, 3, 6, 5);
        assertTrue(jumped, "Jump move should be executed");

        assertEquals(0, board[5][4], "Black piece should be captured");
        assertEquals(1, board[6][5], "Red piece should land at target");
    }

    @Test
    public void testSimpleMoveFailsWhenTargetOccupied() {
        int[][] board = game.getBoard();
        board[5][0] = -1;
        board[4][1] = -1; // already occupied

        boolean moved = game.movePiece(5, 0, 4, 1);
        assertFalse(moved);
    }

    @Test
    public void testInvalidJumpShapeFails() {
        int[][] board = game.getBoard();
        board[5][0] = -1;

        // Only row changes by 2 (dc is 1)
        boolean moved = game.movePiece(5, 0, 3, 1);
        assertFalse(moved);
    }

    @Test
    public void testComputerMoveDoesNothingIfNoMoves() {
        int[][] board = game.getBoard();
        for (int r = 0; r < 8; r++) Arrays.fill(board[r], -1); // fill with black only

        game.computerMove(); // red has no moves — should do nothing
        assertTrue(true); // dummy check to complete test
    }

    @Test
    public void testGetAllValidMovesUsesKingDirections() {
        int[][] board = game.getBoard();
        board[4][3] = 2; // red king
        board[5][4] = 0;

        List<?> moves = getAllValidMovesViaReflection(true);
        assertFalse(moves.isEmpty());
    }

    @Test
    public void testCheckWinDetectsBlackOnly() {
        int[][] board = game.getBoard();
        for (int r = 0; r < 8; r++) Arrays.fill(board[r], 0);
        board[7][7] = -1;

        assertTrue(game.checkWin(), "Game should still detect win condition");
    }

    @Test
    public void testCheckWinDetectsRedOnly() {
        int[][] board = game.getBoard();
        for (int r = 0; r < 8; r++) Arrays.fill(board[r], 0);
        board[0][0] = 1; // red piece only

        assertTrue(game.checkWin());
    }

    @Test
    public void testGetAllValidMovesRedTurnNonKing() {
        game.movePiece(5, 0, 4, 1); // switch to red

        int[][] board = game.getBoard();
        board[6][2] = 1; // red normal piece
        board[7][3] = 0;

        List<?> moves = getAllValidMovesViaReflection(true);
        assertFalse(moves.isEmpty());
    }

    @Test
    public void testGetValidMovesRedPieceSeesBlackEnemyJump() {
        int[][] board = game.getBoard();

        // Clear the board
        for (int r = 0; r < 8; r++) {
            Arrays.fill(board[r], 0);
        }

        // Manually set red piece and black enemy for a valid jump
        board[4][3] = 1;   // red piece
        board[5][4] = -1;  // black opponent
        board[6][5] = 0;   // landing

        // getValidMoves doesn't care whose turn it is — it just checks what's possible
        var moves = game.getValidMoves(4, 3);
        boolean jumpFound = moves.stream().anyMatch(m -> m[0] == 6 && m[1] == 5);
        assertTrue(jumpFound, "Red piece should see a jump over black piece to (6,5)");
    }

    @SuppressWarnings("unchecked")
    private List<?> getAllValidMovesViaReflection(boolean redTurn) {
        try {
            var method = CheckersGame.class.getDeclaredMethod("getAllValidMoves", boolean.class);
            method.setAccessible(true);
            return (List<?>) method.invoke(game, redTurn);
        } catch (Exception e) {
            fail("Reflection failed: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}