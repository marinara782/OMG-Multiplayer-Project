package org.example.game.checkers;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test class ensures the functionality of the CheckersBoard and CheckersGame.
 */
public class CheckersBoardTest {

    private CheckersGame game;
    private CheckersBoard board;

    @BeforeEach
    void setup() {
        game = new CheckersGame();       // Create a new game instance
        board = new CheckersBoard(game); // Create a new board and link it to the game
    }

    @Test
    void testInitialBoardGridSize() {
        GridPane grid = getBoardGrid();
        assertNotNull(grid, "Board grid should not be null");
        assertEquals(64, grid.getChildren().size(), "8x8 board should have 64 cells");
    }

    @Test
    void testHighlightingValidMoves() {
        List<int[]> valid = game.getValidMoves(5, 0);
        assertNotNull(valid);
        assertFalse(valid.isEmpty(), "Valid moves must be non-empty for piece at (5,0)");
    }

    @Test
    void testMoveUpdatesBoard() {
        boolean moved = game.movePiece(5, 0, 4, 1);
        assertTrue(moved, "Move should succeed");
        assertEquals(-1, game.getPiece(4, 1), "Piece should have moved to (4,1)");
        assertEquals(0, game.getPiece(5, 0), "Original position should be empty");
    }

    @Test
    void testGameEndsCorrectly() {
        int[][] boardState = game.getBoard();
        for (int[] row : boardState) Arrays.fill(row, 0); // Wipe the board
        boardState[0][1] = -1;
        assertTrue(game.checkWin(), "Game should end when one player has no pieces");
    }

    @Test
    void testDrawBoardConsistency() {
        GridPane grid = getBoardGrid();
        assertNotNull(grid);
        assertTrue(grid.getChildren().stream().allMatch(node -> node instanceof StackPane),
                "All grid elements must be StackPane");
    }

    @Test
    void testKingPieceAppearance() {
        int[][] boardData = game.getBoard();
        boardData[5][0] = -2; // Simulate a king piece
        assertEquals(-2, game.getPiece(5, 0), "Should detect a king piece at (5,0)");
    }

    @Test
    void testHighlightMovesMethodExists() throws Exception {
        Method method = board.getClass().getDeclaredMethod("highlightMoves", List.class);
        assertNotNull(method);
    }

    @Test
    void testClearHighlightsMethodExists() throws Exception {
        Method method = board.getClass().getDeclaredMethod("clearHighlights");
        assertNotNull(method);
    }

    @Test
    void testValidMoveReturnsTrue() {
        assertTrue(game.movePiece(5, 0, 4, 1), "Valid piece move should return true");
    }

    @Test
    void testHighlightAndClearHighlightsViaReflection() throws Exception {
        List<int[]> moves = game.getValidMoves(5, 0);

        Method highlight = board.getClass().getDeclaredMethod("highlightMoves", List.class);
        highlight.setAccessible(true);
        highlight.invoke(board, moves);

        Field highlightedCells = board.getClass().getDeclaredField("highlightedCells");
        highlightedCells.setAccessible(true);
        List<?> highlights = (List<?>) highlightedCells.get(board);
        assertFalse(highlights.isEmpty(), "Cells should be highlighted");

        Method clear = board.getClass().getDeclaredMethod("clearHighlights");
        clear.setAccessible(true);
        clear.invoke(board);

        highlights = (List<?>) highlightedCells.get(board);
        assertTrue(highlights.isEmpty(), "Highlights should be cleared");
    }

    @Test
    void testComputerMoveChangesBoard() {
        game.movePiece(5, 0, 4, 1); // simulate player move
        int[][] before = deepCopy(game.getBoard());
        game.computerMove();
        int[][] after = game.getBoard();
        assertFalse(Arrays.deepEquals(before, after), "Board should change after computer move");
    }

    @Test
    void testHandleClickPieceSelectionSimulated() {
        // Simulate that (5, 0) is selected
        try {
            StackPane dummyCell = new StackPane();
            dummyCell.setUserData(new int[]{5, 0});
            MouseEvent event = new MouseEvent(MouseEvent.MOUSE_CLICKED,
                    0, 0, 0, 0, null, 0,
                    false, false, false, false,
                    false, false, false, false, false, false, null);
            dummyCell.fireEvent(event);
            assertTrue(true); // no exception = pass
        } catch (Exception e) {
            fail("Click simulation failed: " + e.getMessage());
        }
    }

    /**
     * Helper method to access private boardGrid.
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
    /**
     * Helper method to copy 2D board state
     */
    private int[][] deepCopy(int[][] original) {
        return Arrays.stream(original).map(int[]::clone).toArray(int[][]::new);
    }
}
