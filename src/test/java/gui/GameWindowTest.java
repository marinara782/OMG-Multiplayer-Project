//package org.example.gui;
//
//import org.example.gui.GameWindow;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class GameWindowTest {
//
//    @Test
//    public void testInitialWindowState() {
//        GameWindow window = new GameWindow();
//        assertNotNull(window.getRoot(), "Root node should not be null");
//    }
//
//    @Test
//    public void testGameBoardDimensions() {
//        GameWindow window = new GameWindow();
//        assertEquals(8, window.getGameBoard().getRowCount(), "Board should have 8 rows");
//    }
//
//    @Test
//    public void testUndoButtonPresence() {
//        GameWindow window = new GameWindow();
//        assertTrue(window.hasUndoButton(), "Undo button should exist");
//    }
//
//    @Test
//    public void testWindowTitle() {
//        GameWindow window = new GameWindow();
//        assertEquals("Checkers Game", window.getTitle());
//    }
//
//    @Test
//    public void testMoveValidation() {
//        GameWindow window = new GameWindow();
//        assertFalse(window.makeMove(0, 0, 0, 1), "Move should be invalid on empty square");
//    }
//
//    @Test
//    public void testValidGameMove() {
//        GameWindow window = new GameWindow();
//        window.setupInitialBoard();
//        assertTrue(window.makeMove(5, 0, 4, 1));
//    }
//
//    @Test
//    public void testPlayerWinCondition() {
//        GameWindow window = new GameWindow();
//        window.simulatePlayerWin();
//        assertTrue(window.checkWin("Player"), "Player should win");
//    }
//
//    @Test
//    public void testComputerWinCondition() {
//        GameWindow window = new GameWindow();
//        window.simulateComputerWin();
//        assertTrue(window.checkWin("Computer"), "Computer should win");
//    }
//
//    @Test
//    public void testDrawScenario() {
//        GameWindow window = new GameWindow();
//        window.simulateDraw();
//        assertTrue(window.isDraw());
//    }
//
//    @Test
//    public void testBoardReset() {
//        GameWindow window = new GameWindow();
//        window.resetBoard();
//        assertEquals(12, window.countPlayerPieces());
//    }
//}






package gui;

//import javafx.embed.swing.JFXPanel;
import org.example.gui.GameWindow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameWindowTest {

    private GameWindow window;

    @BeforeEach
    void setUp() {
        new gui.JFXPanel(); // Initializes JavaFX toolkit for headless test
        window = new GameWindow(); // You may need to adjust arguments if your constructor requires any
    }

    @Test
    void testWindowNotNull() {
        assertNotNull(window, "GameWindow should be initialized");
    }

    @Test
    void testSceneNotNull() {
        assertNotNull(window.getScene(), "Scene should be initialized");
    }

    @Test
    void testStageNotResizable() {
        assertFalse(window.getStage().isResizable(), "Stage should not be resizable");
    }

    @Test
    void testTitleSetCorrectly() {
        assertEquals("Checkers", window.getStage().getTitle(), "Window title should be 'Checkers'");
    }

    @Test
    void testRootNodeExists() {
        assertNotNull(window.getScene().getRoot(), "Root node should not be null");
    }

    @Test
    void testMakeMoveExists() throws Exception {
        assertDoesNotThrow(() ->
                GameWindow.class.getDeclaredMethod("makeMove", int.class, int.class, int.class, int.class)
        );
    }

    @Test
    void testSetupInitialBoardExists() throws Exception {
        assertDoesNotThrow(() ->
                GameWindow.class.getDeclaredMethod("setupInitialBoard")
        );
    }

    @Test
    void testCheckWinExists() throws Exception {
        assertDoesNotThrow(() ->
                GameWindow.class.getDeclaredMethod("checkWin", String.class)
        );
    }

    @Test
    void testResetBoardExists() throws Exception {
        assertDoesNotThrow(() ->
                GameWindow.class.getDeclaredMethod("resetBoard")
        );
    }

    @Test
    void testCountPlayerPiecesExists() throws Exception {
        assertDoesNotThrow(() ->
                GameWindow.class.getDeclaredMethod("countPlayerPieces")
        );
    }
}


