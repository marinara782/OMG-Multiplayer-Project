//package org.example.game.checkers;
//
//import java.util.*;
//
///**
// * The CheckersGame class handles all backend logic for a simplified checkers game.
// * This includes move validation, AI moves, win checking, forced captures, and undo support.
// */
//public class CheckersGame {
//
//    private int[][] board;              // The 8x8 game board
//    private boolean isRedTurn = false;  // False = player's (black) turn, True = computer's (red) turn
//    private boolean gameOver = false;   // True if someone has won
//    private final Stack<int[][]> history = new Stack<>(); // Stores board states for undo
//
//    /**
//     * Constructor initializes a new game with the starting board setup.
//     */
//    public CheckersGame() {
//        board = new int[8][8];
//        initializeBoard();
//    }
//
//    /**
//     * Places red pieces on the top 3 rows and black pieces on the bottom 3 rows
//     * on valid (dark-colored) squares.
//     */
//    private void initializeBoard() {
//        for (int row = 0; row < 8; row++) {
//            for (int col = 0; col < 8; col++) {
//                if ((row + col) % 2 != 0) {
//                    if (row < 3) board[row][col] = 1;       // Red piece
//                    else if (row > 4) board[row][col] = -1; // Black piece
//                }
//            }
//        }
//    }
//
//    /** Returns the current state of the board */
//    public int[][] getBoard() {
//        return board;
//    }
//
//    /** Returns the piece at the specified position */
//    public int getPiece(int row, int col) {
//        if (!inBounds(row, col)) throw new IllegalArgumentException("Out of bounds");
//        return board[row][col];
//    }
//
//    /** Returns true if it's the player's (black's) turn */
//    public boolean isPlayersTurn() {
//        return !isRedTurn;
//    }
//
//    /**
//     * Tries to move a piece from (fromRow, fromCol) to (toRow, toCol).
//     * Enforces forced captures if required.
//     */
//    public boolean movePiece(int fromRow, int fromCol, int toRow, int toCol) {
//        if (gameOver) return false;
//
//        int piece = board[fromRow][fromCol];
//        if (piece == 0 || (piece > 0) != isRedTurn) return false;
//
//        int dr = toRow - fromRow;
//        int dc = toCol - fromCol;
//        boolean isKing = Math.abs(piece) == 2;
//        boolean isJump = Math.abs(dr) == 2 && Math.abs(dc) == 2;
//
//        // Enforce jump rule
//        if (!isJump && hasJumpAvailable(isRedTurn)) return false;
//
//        // Normal move
//        if (Math.abs(dr) == 1 && Math.abs(dc) == 1 && board[toRow][toCol] == 0) {
//            if (isKing || dr == (isRedTurn ? 1 : -1)) {
//                saveHistory();
//                executeMove(fromRow, fromCol, toRow, toCol, piece);
//                return true;
//            }
//        }
//
//        // Jump move
//        if (isJump) {
//            int midRow = fromRow + dr / 2;
//            int midCol = fromCol + dc / 2;
//            int midPiece = board[midRow][midCol];
//
//            if (midPiece != 0 && (midPiece > 0) != (piece > 0) && board[toRow][toCol] == 0) {
//                saveHistory();
//                board[midRow][midCol] = 0;
//                executeMove(fromRow, fromCol, toRow, toCol, piece);
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    /**
//     * Moves a piece, promotes to king if needed, switches turns, and checks win condition.
//     */
//    private void executeMove(int fromRow, int fromCol, int toRow, int toCol, int piece) {
//        board[fromRow][fromCol] = 0;
//        board[toRow][toCol] = piece;
//
//        // Promote to King if reaching the last row
//        if (piece == 1 && toRow == 7) board[toRow][toCol] = 2;
//        if (piece == -1 && toRow == 0) board[toRow][toCol] = -2;
//
//        // Switch turn
//        isRedTurn = !isRedTurn;
//
//        // Check for win condition
//        if (checkWin()) {
//            gameOver = true;
//        }
//    }
//
//    /**
//     * Computer (red) randomly chooses one of its valid moves and performs it.
//     */
//    public void computerMove() {
//        if (gameOver) return;
//
//        List<Move> moves = getAllValidMoves(true);
//        if (!moves.isEmpty()) {
//            Move move = moves.get(new Random().nextInt(moves.size()));
//            movePiece(move.fromRow, move.fromCol, move.toRow, move.toCol);
//        }
//    }
//
//    /**
//     * Returns a list of all valid moves for a given side.
//     * If a jump is available, only jump moves are returned.
//     */
//    private List<Move> getAllValidMoves(boolean redTurn) {
//        List<Move> moves = new ArrayList<>();
//        boolean mustJump = hasJumpAvailable(redTurn);
//
//        for (int row = 0; row < 8; row++) {
//            for (int col = 0; col < 8; col++) {
//                int piece = board[row][col];
//                if (piece == 0 || (piece > 0) != redTurn) continue;
//
//                boolean isKing = Math.abs(piece) == 2;
//                int[][] directions = isKing
//                        ? new int[][]{{1, 1}, {1, -1}, {-1, 1}, {-1, -1}}
//                        : redTurn ? new int[][]{{1, 1}, {1, -1}} : new int[][]{{-1, 1}, {-1, -1}};
//
//                for (int[] d : directions) {
//                    int r = row + d[0], c = col + d[1];
//                    int jr = row + 2 * d[0], jc = col + 2 * d[1];
//
//                    // Normal move if no jump is required
//                    if (!mustJump && inBounds(r, c) && board[r][c] == 0)
//                        moves.add(new Move(row, col, r, c));
//
//                    // Jump move
//                    if (inBounds(jr, jc) && board[jr][jc] == 0) {
//                        int mid = board[row + d[0]][col + d[1]];
//                        if (mid != 0 && (mid > 0) != redTurn)
//                            moves.add(new Move(row, col, jr, jc));
//                    }
//                }
//            }
//        }
//
//        return moves;
//    }
//
//    /**
//     * Checks if any capture (jump) move is available for the given side.
//     */
//    private boolean hasJumpAvailable(boolean redTurn) {
//        for (int row = 0; row < 8; row++) {
//            for (int col = 0; col < 8; col++) {
//                int piece = board[row][col];
//                if (piece == 0 || (piece > 0) != redTurn) continue;
//
//                boolean isKing = Math.abs(piece) == 2;
//                int[][] directions = isKing
//                        ? new int[][]{{1, 1}, {1, -1}, {-1, 1}, {-1, -1}}
//                        : redTurn ? new int[][]{{1, 1}, {1, -1}} : new int[][]{{-1, 1}, {-1, -1}};
//
//                for (int[] d : directions) {
//                    int jr = row + 2 * d[0], jc = col + 2 * d[1];
//                    int mr = row + d[0], mc = col + d[1];
//
//                    if (inBounds(jr, jc) && board[jr][jc] == 0) {
//                        int mid = board[mr][mc];
//                        if (mid != 0 && (mid > 0) != redTurn) return true;
//                    }
//                }
//            }
//        }
//
//        return false;
//    }
//
//    /** Returns true if the game is over (no pieces of one side remain) */
//    public boolean checkWin() {
//        boolean redAlive = false, blackAlive = false;
//        for (int[] row : board) {
//            for (int cell : row) {
//                if (cell > 0) redAlive = true;
//                if (cell < 0) blackAlive = true;
//            }
//        }
//        return !(redAlive && blackAlive);
//    }
//
//    /** Returns the winner if the game has ended */
//    public String getWinner() {
//        if (!gameOver) return "None";
//        return isRedTurn ? "Black Wins!" : "Red Wins!";
//    }
//
//    /** Reverts the board to the previous state (undo move) */
//    public void undoMove() {
//        if (!history.isEmpty()) {
//            board = history.pop();
//            isRedTurn = !isRedTurn;
//            gameOver = false;
//        }
//    }
//
//    /** Saves the current board state for undo functionality */
//    private void saveHistory() {
//        int[][] copy = new int[8][8];
//        for (int i = 0; i < 8; i++)
//            copy[i] = board[i].clone();
//        history.push(copy);
//    }
//
//    /**
//     * Returns all valid move destinations for a piece at (row, col)
//     * Used for GUI highlighting.
//     */
//    public List<int[]> getValidMovesFor(int row, int col) {
//        List<int[]> valid = new ArrayList<>();
//        int piece = board[row][col];
//        if (piece == 0 || (piece > 0) != isRedTurn) return valid;
//
//        boolean isKing = Math.abs(piece) == 2;
//        int[][] directions = isKing
//                ? new int[][]{{1, 1}, {1, -1}, {-1, 1}, {-1, -1}}
//                : isRedTurn ? new int[][]{{1, 1}, {1, -1}} : new int[][]{{-1, 1}, {-1, -1}};
//
//        for (int[] d : directions) {
//            int r = row + d[0], c = col + d[1];
//            int jr = row + 2 * d[0], jc = col + 2 * d[1];
//
//            if (inBounds(r, c) && board[r][c] == 0 && !hasJumpAvailable(isRedTurn))
//                valid.add(new int[]{r, c});
//
//            if (inBounds(jr, jc) && board[jr][jc] == 0) {
//                int mid = board[row + d[0]][col + d[1]];
//                if (mid != 0 && (mid > 0) != isRedTurn)
//                    valid.add(new int[]{jr, jc});
//            }
//        }
//
//        return valid;
//    }
//
//    /** Checks if a position is within board boundaries */
//    private boolean inBounds(int r, int c) {
//        return r >= 0 && r < 8 && c >= 0 && c < 8;
//    }
//
//    /** Helper class to represent a move */
//    private static class Move {
//        int fromRow, fromCol, toRow, toCol;
//        Move(int fr, int fc, int tr, int tc) {
//            fromRow = fr;
//            fromCol = fc;
//            toRow = tr;
//            toCol = tc;
//        }
//    }
//}

package org.example.game.checkers;

import java.util.*;

/**
 * Core logic class for a simplified Checkers game.
 * Manages board state, move validation, AI moves, forced captures, undo functionality, and game state tracking.
 */
public class CheckersGame {

    private int[][] board;                    // 8x8 board matrix: positive = red, negative = black
    private boolean isRedTurn = false;        // false = player's turn, true = computer's turn
    private boolean gameOver = false;         // true if the game has ended
    private final Stack<int[][]> history = new Stack<>(); // Stack to support undo functionality

    /** Constructor: Initializes the board with default starting positions. */
    public CheckersGame() {
        board = new int[8][8];
        initializeBoard();
    }

    /** Places red pieces in the top 3 rows and black pieces in the bottom 3 rows. */
    private void initializeBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 != 0) {
                    if (row < 3) board[row][col] = 1;
                    else if (row > 4) board[row][col] = -1;
                }
            }
        }
    }

    public int[][] getBoard() {
        return board;
    }

    public int getPiece(int row, int col) {
        if (!inBounds(row, col)) throw new IllegalArgumentException("Out of bounds");
        return board[row][col];
    }

    public boolean isPlayersTurn() {
        return !isRedTurn;
    }

    /**
     * Handles player or AI move attempt.
     * Validates moves, enforces forced capture, and performs piece movement.
     */
    public boolean movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        if (gameOver) return false;

        int piece = board[fromRow][fromCol];
        if (piece == 0 || (piece > 0) != isRedTurn) return false;

        int dr = toRow - fromRow;
        int dc = toCol - fromCol;
        boolean isKing = Math.abs(piece) == 2;
        boolean isJump = Math.abs(dr) == 2 && Math.abs(dc) == 2;

        // Enforce jump rule if applicable
        if (!isJump && hasJumpAvailable(isRedTurn)) return false;

        // Normal move
        if (Math.abs(dr) == 1 && Math.abs(dc) == 1 && board[toRow][toCol] == 0) {
            if (isKing || dr == (isRedTurn ? 1 : -1)) {
                saveHistory();
                executeMove(fromRow, fromCol, toRow, toCol, piece);
                return true;
            }
        }

        // Jump (capture) move
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

    /** Executes the move and promotes pieces to kings when needed. */
    private void executeMove(int fromRow, int fromCol, int toRow, int toCol, int piece) {
        board[fromRow][fromCol] = 0;
        board[toRow][toCol] = piece;

        // Promote to King
        if (piece == 1 && toRow == 7) board[toRow][toCol] = 2;
        if (piece == -1 && toRow == 0) board[toRow][toCol] = -2;

        isRedTurn = !isRedTurn;

        if (checkWin()) gameOver = true;
    }

    /** Makes a random valid move for the computer (red side). */
    public void computerMove() {
        if (gameOver) return;

        List<Move> moves = getAllValidMoves(true);
        if (!moves.isEmpty()) {
            Move move = moves.get(new Random().nextInt(moves.size()));
            movePiece(move.fromRow, move.fromCol, move.toRow, move.toCol);
        }
    }

    /**
     * Returns all valid moves for the specified turn.
     * If a jump is available, only jump moves are returned.
     */
    private List<Move> getAllValidMoves(boolean redTurn) {
        List<Move> moves = new ArrayList<>();
        boolean mustJump = hasJumpAvailable(redTurn);

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

                    if (!mustJump && inBounds(r, c) && board[r][c] == 0)
                        moves.add(new Move(row, col, r, c));

                    if (inBounds(jr, jc) && board[jr][jc] == 0) {
                        int mid = board[row + d[0]][col + d[1]];
                        if (mid != 0 && (mid > 0) != redTurn)
                            moves.add(new Move(row, col, jr, jc));
                    }
                }
            }
        }

        return moves;
    }

    /** Checks if any piece has a forced capture available. */
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
                    int mr = row + d[0], mc = col + d[1];

                    if (inBounds(jr, jc) && board[jr][jc] == 0) {
                        int mid = board[mr][mc];
                        if (mid != 0 && (mid > 0) != redTurn) return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean checkWin() {
        boolean redAlive = false, blackAlive = false;
        for (int[] row : board) {
            for (int cell : row) {
                if (cell > 0) redAlive = true;
                if (cell < 0) blackAlive = true;
            }
        }
        return !(redAlive && blackAlive);
    }

    public String getWinner() {
        if (!gameOver) return "None";
        return isRedTurn ? "Black Wins!" : "Red Wins!";
    }

    /** Undoes the last move by restoring the previous board state. */
    public void undoMove() {
        if (!history.isEmpty()) {
            board = history.pop();
            isRedTurn = !isRedTurn;
            gameOver = false;
        }
    }

    /** Saves a deep copy of the current board state for undo. */
    private void saveHistory() {
        int[][] copy = new int[8][8];
        for (int i = 0; i < 8; i++)
            copy[i] = board[i].clone();
        history.push(copy);
    }

    /**
     * Returns a list of valid destination cells for a selected piece.
     * Used by the GUI to highlight moves.
     */
    public List<int[]> getValidMovesFor(int row, int col) {
        List<int[]> valid = new ArrayList<>();
        int piece = board[row][col];
        if (piece == 0 || (piece > 0) != isRedTurn) return valid;

        boolean isKing = Math.abs(piece) == 2;
        int[][] directions = isKing
                ? new int[][]{{1, 1}, {1, -1}, {-1, 1}, {-1, -1}}
                : isRedTurn ? new int[][]{{1, 1}, {1, -1}} : new int[][]{{-1, 1}, {-1, -1}};

        for (int[] d : directions) {
            int r = row + d[0], c = col + d[1];
            int jr = row + 2 * d[0], jc = col + 2 * d[1];

            if (inBounds(r, c) && board[r][c] == 0 && !hasJumpAvailable(isRedTurn))
                valid.add(new int[]{r, c});

            if (inBounds(jr, jc) && board[jr][jc] == 0) {
                int mid = board[row + d[0]][col + d[1]];
                if (mid != 0 && (mid > 0) != isRedTurn)
                    valid.add(new int[]{jr, jc});
            }
        }

        return valid;
    }

    /** Utility method to check if coordinates are within bounds. */
    private boolean inBounds(int r, int c) {
        return r >= 0 && r < 8 && c >= 0 && c < 8;
    }

    /** Simple helper class to represent a move. */
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
