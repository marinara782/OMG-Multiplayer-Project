

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

public class CheckersBoardTest {

    private CheckersGame game;
    private CheckersBoard board;

    @BeforeEach

    @Test
    void testInitialBoardGridSize() {
        GridPane grid = getBoardGrid();
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
        for (int[] row : boardState) {
            Arrays.fill(row, 0);
        }
        boardState[0][1] = -1;
        assertTrue(game.checkWin(), "Game should end when one player has no pieces");
    }

    @Test
    void testDrawBoardConsistency() {
        GridPane grid = getBoardGrid();
        assertTrue(grid.getChildren().stream().allMatch(node -> node instanceof StackPane),
                "All grid elements must be StackPane");
    }

    @Test
    void testKingPieceAppearance() {
        int[][] boardData = game.getBoard();
        boardData[5][0] = -2;
        assertEquals(-2, game.getPiece(5, 0), "Should detect a king piece at (5,0)");
    }

    @Test
    void testHighlightMovesMethodExists() throws Exception {
        board.getClass().getDeclaredMethod("highlightMoves", List.class);
    }

    @Test
    void testClearHighlightsMethodExists() throws Exception {
        board.getClass().getDeclaredMethod("clearHighlights");
    }

    @Test
    void testValidMoveReturnsTrue() {
        assertTrue(game.movePiece(5, 0, 4, 1), "Valid piece move should return true");
    }

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
//Changes will occure if problem occurs again !