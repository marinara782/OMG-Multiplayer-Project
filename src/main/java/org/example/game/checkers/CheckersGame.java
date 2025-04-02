package org.example.game.checkers;

import java.util.*;

/**
 * The CheckersGame class implements the core logic for a simplified checkers game.
 * It includes board setup, piece movement logic, forced captures, undo support,
 * basic AI, and win checking.
 */
public class CheckersGame {

    /** The 8x8 checkers board */
    private int[][] board;

    /** True if it's Red's (computer's) turn; false if it's Black's (player's) turn */
    private boolean isRedTurn = false;

    /** Stack to store previous board states for undo functionality */
    private final Stack<int[][]> history = new Stack<>();

    /** Flag to indicate whether the game is over */
    private boolean gameOver = false;

    /** Constructs a new checkers game and sets up the initial board */
    public CheckersGame() {
        board = new int[8][8];
        initializeBoard();
    }

    /** Initializes the board with red and black pieces in starting positions */
    private void initializeBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 != 0) {
                    if (row < 3) board[row][col] = 1;       // Red piece
                    else if (row > 4) board[row][col] = -1; // Black piece
                }
            }
        }
    }

    /** Returns the current board state */
    public int[][] getBoard() {
        return board;
    }

    /** Returns the piece at the specified row and column */
    public int getPiece(int row, int col) {
        if (!inBounds(row, col)) {
            throw new IllegalArgumentException("Row or column out of bounds.");
        }
        return board[row][col];
    }

    /** Returns true if it's the player's (black's) turn */
    public boolean isPlayersTurn() {
        return !isRedTurn;
    }

    /**
     * Attempts to move a piece on the board.
     * Enforces forced capture rules and validates moves.
     */
    public boolean movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        if (gameOver) return false;

        int piece = board[fromRow][fromCol];
        if (piece == 0 || (piece > 0) != isRedTurn) return false;

        int dr = toRow - fromRow;
        int dc = toCol - fromCol;
        boolean isKing = Math.abs(piece) == 2;
        boolean isJump = Math.abs(dr) == 2 && Math.abs(dc) == 2;

        // Enforce forced capture if available
        if (!isJump && hasJumpAvailable(isRedTurn)) {
            return false; // Must perform a capture
        }

        // Normal move
        if (Math.abs(dr) == 1 && Math.abs(dc) == 1 && board[toRow][toCol] == 0) {
            if (isKing || dr == (isRedTurn ? 1 : -1)) {
                saveHistory();
                executeMove(fromRow, fromCol, toRow, toCol, piece);
                return true;
            }
        }

        // Capture move
        if (isJump) {
            int midRow = fromRow + dr / 2;
            int midCol = fromCol + dc / 2;
            int midPiece = board[midRow][midCol];

            if (midPiece != 0 && (midPiece > 0) != (piece > 0) && board[toRow][toCol] == 0) {
                saveHistory();
                board[midRow][midCol] = 0;
                executeMove(fromRow, fromCol, toRow, toCol, piece);
                return true;
            }
        }

        return false;
    }

    /**
     * Executes a move and promotes to King if needed.
     */
    private void executeMove(int fromRow, int fromCol, int toRow, int toCol, int piece) {
        board[fromRow][fromCol] = 0;
        board[toRow][toCol] = piece;

        // King promotion
        if (piece == 1 && toRow == 7) board[toRow][toCol] = 2;
        if (piece == -1 && toRow == 0) board[toRow][toCol] = -2;

        isRedTurn = !isRedTurn;

        // Check win condition after move
        if (checkWin()) {
            gameOver = true;
        }
    }

    /** Random AI move for the red side */
    public void computerMove() {
        if (gameOver) return;

        List<Move> moves = getAllValidMoves(true);
        if (!moves.isEmpty()) {
            Move move = moves.get(new Random().nextInt(moves.size()));
            movePiece(move.fromRow, move.fromCol, move.toRow, move.toCol);
        }
    }

    /**
     * Returns a list of valid moves for the current turn.
     * If a capture is available, only capture moves are returned.
     */
    private List<Move> getAllValidMoves(boolean redTurn) {
        List<Move> moves = new ArrayList<>();
        boolean jumpRequired = hasJumpAvailable(redTurn);

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                int piece = board[row][col];
                if (piece == 0 || (piece > 0) != redTurn) continue;

                boolean isKing = Math.abs(piece) == 2;
                int[][] directions = isKing
                        ? new int[][]{{1, 1}, {1, -1}, {-1, 1}, {-1, -1}}
                        : redTurn ? new int[][]{{1, 1}, {1, -1}} : new int[][]{{-1, 1}, {-1, -1}};

                for (int[] d : directions) {
                    int r = row + d[0], c = col + d[1];
                    int jr = row + 2 * d[0], jc = col + 2 * d[1];

                    // Normal move
                    if (!jumpRequired && inBounds(r, c) && board[r][c] == 0) {
                        moves.add(new Move(row, col, r, c));
                    }

                    // Jump move
                    if (inBounds(jr, jc) && board[jr][jc] == 0) {
                        int mid = board[row + d[0]][col + d[1]];
                        if (mid != 0 && (mid > 0) != redTurn) {
                            moves.add(new Move(row, col, jr, jc));
                        }
                    }
                }
            }
        }

        return moves;
    }

    /**
     * Returns true if a jump (capture) is available for the given turn.
     */
    private boolean hasJumpAvailable(boolean redTurn) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                int piece = board[row][col];
                if (piece == 0 || (piece > 0) != redTurn) continue;

                boolean isKing = Math.abs(piece) == 2;
                int[][] directions = isKing
                        ? new int[][]{{1, 1}, {1, -1}, {-1, 1}, {-1, -1}}
                        : redTurn ? new int[][]{{1, 1}, {1, -1}} : new int[][]{{-1, 1}, {-1, -1}};

                for (int[] d : directions) {
                    int jr = row + 2 * d[0], jc = col + 2 * d[1];
                    int midRow = row + d[0], midCol = col + d[1];

                    if (inBounds(jr, jc) && board[jr][jc] == 0) {
                        int mid = board[midRow][midCol];
                        if (mid != 0 && (mid > 0) != redTurn) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /** Checks if the given coordinates are within the board bounds */
    private boolean inBounds(int r, int c) {
        return r >= 0 && r < 8 && c >= 0 && c < 8;
    }

    /**
     * Checks if the game has been won (only one color remains).
     */
    public boolean checkWin() {
        boolean redExists = false, blackExists = false;
        for (int[] row : board) {
            for (int cell : row) {
                if (cell > 0) redExists = true;
                if (cell < 0) blackExists = true;
            }
        }
        return !(redExists && blackExists);
    }

    /** Returns the winner if the game is over */
    public String getWinner() {
        if (!gameOver) return "None";
        return isRedTurn ? "Black Wins!" : "Red Wins!";
    }

    /** Allows the player to undo their last move (if any) */
    public void undoMove() {
        if (!history.isEmpty()) {
            board = history.pop();
            isRedTurn = !isRedTurn;
            gameOver = false;
        }
    }

    /** Saves a copy of the board state to support undo functionality */
    private void saveHistory() {
        int[][] copy = new int[8][8];
        for (int i = 0; i < 8; i++) {
            copy[i] = board[i].clone();
        }
        history.push(copy);
    }

    /** A helper class representing a valid move */
    private static class Move {
        int fromRow, fromCol, toRow, toCol;
        Move(int fr, int fc, int tr, int tc) {
            fromRow = fr;
            fromCol = fc;
            toRow = tr;
            toCol = tc;
        }
    }
}
