package game.checkers;

public class CheckersGame {
    private CheckersBoard board;
    private Player currentPlayer;// Represents the current player (PLAYER_1 or PLAYER_2)

    // Constructor that initializes the game by creating a new board and setting Player 1 to start
    public CheckersGame() {
        this.board = new CheckersBoard(); // Create a new checkers board
        this.currentPlayer = Player.PLAYER_1; // Start with Player 1
    }
    // Starts the game
    public void startGame() {
        // Logic to start and manage the game (e.g., alternate turns)
    }
    // What checks the move making actions
    // Takes start and end coordinates (startX, startY, endX, endY) as input
    public void makeMove(int startX, int startY, int endX, int endY) {
        // Logic to make a move, check validity, update board, etc.
    }
    // Changes the active player after each move
    public void changeTurn() {
        // Change to the next player
        currentPlayer = (currentPlayer == Player.PLAYER_1) ? Player.PLAYER_2 : Player.PLAYER_1;
    }
    // Checks if the game is over
    public boolean isGameOver() {
        // Logic to check if the game is over (winner or draw)
        return false; // Placeholder
    }
}