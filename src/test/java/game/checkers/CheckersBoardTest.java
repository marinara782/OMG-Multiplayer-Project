package game.checkers;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.example.game.checkers.CheckersBoard;
import org.example.game.checkers.CheckersGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test class ensures the functionality of the CheckersBoard and CheckersGame.
 */
public class CheckersBoardTest {

    private CheckersGame game;
    private CheckersBoard board;

    /**
     * Set up fresh instances of CheckersBoard and CheckersGame before each test.
     * Board is linked to the game.
     */
    @BeforeEach
    void setup() {
        game = new CheckersGame();       // Create a new game instance
        board = new CheckersBoard(game); // Create a new board and link it to the game
    }

    /**
     * Checks that the board contains 64 cells (8x8 grid).
     */
    @Test
    void testInitialBoardGridSize() {
        GridPane grid = getBoardGrid();
        assertNotNull(grid, "Board grid should not be null");
        assertEquals(64, grid.getChildren().size(), "8x8 board should have 64 cells");
    }

    /**
     * Verifies that valid moves exist for a piece at a specific position.
     */
    @Test
    void testHighlightingValidMoves() {
        List<int[]> valid = game.getValidMoves(5, 0);
        assertNotNull(valid);  // Ensure it returns something
        assertFalse(valid.isEmpty(), "Valid moves must be non-empty for piece at (5,0)");
    }

    /**
     * Ensures that moving a piece updates the game board as expected.
     */
    @Test
    void testMoveUpdatesBoard() {
        boolean moved = game.movePiece(5, 0, 4, 1);
        assertTrue(moved, "Move should succeed");
        assertEquals(-1, game.getPiece(4, 1), "Piece should have moved to (4,1)");
        assertEquals(0, game.getPiece(5, 0), "Original position should be empty");
    }

    /**
     * Verifies the game detects win conditions when a player has no pieces.
     */
    @Test
    void testGameEndsCorrectly() {
        int[][] boardState = game.getBoard();
        for (int[] row : boardState) {
            Arrays.fill(row, 0); // Wipe the board clean
        }
        boardState[0][1] = -1; // Give one piece to player 1
        assertTrue(game.checkWin(), "Game should end when one player has no pieces");
    }

    /**
     * Ensures the visual grid is made up of StackPane nodes.
     */
    @Test
    void testDrawBoardConsistency() {
        GridPane grid = getBoardGrid();
        assertNotNull(grid, "GridPane should not be null");
        assertTrue(grid.getChildren().stream().allMatch(node -> node instanceof StackPane),
                "All grid elements must be StackPane");
    }

    /**
     * Verifies king pieces are correctly recognized in game state.
     */
    @Test
    void testKingPieceAppearance() {
        int[][] boardData = game.getBoard();
        boardData[5][0] = -2; // Simulate a king piece
        assertEquals(-2, game.getPiece(5, 0), "Should detect a king piece at (5,0)");
    }

    /**
     * Reflection test to ensure `highlightMoves` method exists.
     */
    @Test
    void testHighlightMovesMethodExists() throws Exception {
        board.getClass().getDeclaredMethod("highlightMoves", List.class);
    }

    /**
     * Reflection test to ensure `clearHighlights` method exists.
     */
    @Test
    void testClearHighlightsMethodExists() throws Exception {
        board.getClass().getDeclaredMethod("clearHighlights");
    }

    /**
     * Confirm that moving a valid piece returns true from the game logic.
     */
    @Test
    void testValidMoveReturnsTrue() {
        assertTrue(game.movePiece(5, 0, 4, 1), "Valid piece move should return true");
    }

    /**
     * Helper method to use reflection to access the private boardGrid field in CheckersBoard.
     * This is because apparently someone thought keeping it private was a fun challenge.
     */
    private GridPane getBoardGrid() {
        try {
            Field field = CheckersBoard.class.getDeclaredField("boardGrid");
            field.setAccessible(true);
            return (GridPane) field.get(board);
        } catch (Exception e) {
            fail("Could not access boardGrid: " + e.getMessage());
            return null;
        }
    }
}

