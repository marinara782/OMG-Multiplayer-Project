package game.checkers;

public class CheckersBoard {
    private Piece[][] board; // 2D array representing the checkers board

    public CheckersBoard() {
        // Constructor to initialize the board with pieces
        board = new Piece[8][8]; // Standard 8x8 checkers board could be 10x10 too
        setupBoard(); // Call method to setup the initial board configuration
    }
    //Initialized the board with the pieces in their starting positions
    private void setupBoard() {
        // Set up pieces on the board (arrange checkers on their initial positions)
    }
    //Displays the current state of the board
    public void displayBoard() {
        // Logic to display the board (for console or GUI)
    }
    // Getter method to retrieve the board array
    public Piece[][] getBoard() {
        return board;
    }
}