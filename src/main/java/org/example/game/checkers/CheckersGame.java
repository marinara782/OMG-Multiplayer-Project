package org.example.game.checkers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Core logic for the Checkers game.
 * Supports single-player (vs. AI) and multiplayer (same device) modes.
 * Board values:
 * - 0 = empty
 * - 1 = red piece
 * - 2 = red king
 * - -1 = black piece
 * - -2 = black king
 */
public class CheckersGame {
    private final int[][] board;
    private boolean isRedTurn = false;
    private final boolean isMultiplayer;

    /**
     * Default constructor, initializes a single-player game.
     */
    public CheckersGame() {
        this(false);
    }

    /**
     * Constructs a new CheckersGame instance with specified game mode.
     *
     * @param isMultiplayer true if multiplayer, false for vs computer
     */
    public CheckersGame(boolean isMultiplayer) {
        this.isMultiplayer = isMultiplayer;
        board = new int[8][8];
        initializeBoard();
    }

    /**
     * @return true if the game is multiplayer mode
     */
    public boolean isMultiplayer() {
        return isMultiplayer;
    }

    /**
     * Initializes the board with red pieces at the top and black pieces at the bottom.
     * Only dark squares are used for pieces.
     */
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

    /**
     * @return the internal 8x8 game board
     */
    public int[][] getBoard() {
        return board;
    }

    /**
     * Gets the piece at a given board position.
     *
     * @param row row index
     * @param col column index
     * @return piece code (positive for red, negative for black, 0 for empty)
     */
    public int getPiece(int row, int col) {
        return board[row][col];
    }

    /**
     * @return true if it’s the black player’s turn (used for player turns)
     */
    public boolean isPlayersTurn() {
        return !isRedTurn;
    }


    /**
     * Attempts to move a piece from one position to another.
     *
     * @param fromRow starting row
     * @param fromCol starting column
     * @param toRow target row
     * @param toCol target column
     * @return true if the move was valid and executed
     */
    public boolean movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        if (!inBounds(fromRow, fromCol) || !inBounds(toRow, toCol)) return false;
        int piece = board[fromRow][fromCol];
        if (piece == 0 || (piece > 0) != isRedTurn) return false;

        int dr = toRow - fromRow;
        int dc = toCol - fromCol;
        boolean isKing = Math.abs(piece) == 2;

        // Simple diagonal move
        if (Math.abs(dr) == 1 && Math.abs(dc) == 1 && board[toRow][toCol] == 0) {
            if (isKing || dr == (isRedTurn ? 1 : -1)) {
                executeMove(fromRow, fromCol, toRow, toCol, piece);
                return true;
            }
        }

        // Jump move
        if (Math.abs(dr) == 2 && Math.abs(dc) == 2) {
            int midRow = fromRow + dr / 2;
            int midCol = fromCol + dc / 2;
            int midPiece = board[midRow][midCol];
            if (midPiece != 0 && (midPiece > 0) != (piece > 0) && board[toRow][toCol] == 0) {
                board[midRow][midCol] = 0;
                executeMove(fromRow, fromCol, toRow, toCol, piece);
                return true;
            }
        }
        return false;
    }

    /**
     * Performs the actual piece movement and handles king promotion.
     *
     * @param fromRow source row
     * @param fromCol source col
     * @param toRow destination row
     * @param toCol destination col
     * @param piece the moving piece
     */
    private void executeMove(int fromRow, int fromCol, int toRow, int toCol, int piece) {
        board[fromRow][fromCol] = 0;
        board[toRow][toCol] = piece;
        if (piece == 1 && toRow == 7) board[toRow][toCol] = 2;
        if (piece == -1 && toRow == 0) board[toRow][toCol] = -2;
        isRedTurn = !isRedTurn;
    }

    /**
     * Makes a random valid move on behalf of the computer.
     */
    public void computerMove() {
        List<Move> moves = getAllValidMoves(true);
        if (!moves.isEmpty()) {
            Move move = moves.get(new Random().nextInt(moves.size()));
            movePiece(move.fromRow, move.fromCol, move.toRow, move.toCol);
        }
    }

    /**
     * Returns a list of all valid moves for the current turn (used for AI).
     *
     * @param redTurn whether it's red’s turn
     * @return list of valid moves
     */
    private List<Move> getAllValidMoves(boolean redTurn) {
        List<Move> moves = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                int piece = board[row][col];
                if (piece == 0 || (piece > 0) != redTurn) continue;
                boolean isKing = Math.abs(piece) == 2;
                int[][] directions = isKing ? new int[][]{{1,1},{1,-1},{-1,1},{-1,-1}} : redTurn ? new int[][]{{1,1},{1,-1}} : new int[][]{{-1,1},{-1,-1}};

                for (int[] d : directions) {
                    int r = row + d[0], c = col + d[1];
                    if (inBounds(r, c) && board[r][c] == 0) {
                        moves.add(new Move(row, col, r, c));
                    }
                    int jr = row + 2 * d[0], jc = col + 2 * d[1];
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
     * Returns all valid destination positions for a specific piece.
     *
     * @param row row of the selected piece
     * @param col column of the selected piece
     * @return list of valid move destinations
     */
    public List<int[]> getValidMoves(int row, int col) {
        List<int[]> validMoves = new ArrayList<>();
        int piece = board[row][col];
        if (piece == 0) return validMoves;

        boolean isPlayerPiece = piece < 0;
        boolean isKing = Math.abs(piece) == 2;
        int[][] directions = isKing ? new int[][]{{-1,-1},{-1,1},{1,-1},{1,1}} :
                isPlayerPiece ? new int[][]{{-1,-1},{-1,1}} :
                        new int[][]{{1,-1},{1,1}};

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (inBounds(newRow, newCol)) {
                if (board[newRow][newCol] == 0) {
                    validMoves.add(new int[]{newRow, newCol});
                } else if ((isPlayerPiece && board[newRow][newCol] > 0) || (!isPlayerPiece && board[newRow][newCol] < 0)) {
                    int jumpRow = newRow + dir[0], jumpCol = newCol + dir[1];
                    if (inBounds(jumpRow, jumpCol) && board[jumpRow][jumpCol] == 0) {
                        validMoves.add(new int[]{jumpRow, jumpCol});
                    }
                }
            }
        }

        return validMoves;
    }

    /**
     * Checks if a given board position is within the 8x8 grid.
     *
     * @param row row index
     * @param col column index
     * @return true if position is within bounds
     */
    private boolean inBounds(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    /**
     * Checks if either side has won (i.e., the other has no pieces).
     *
     * @return true if one player has no pieces left
     */
    public boolean checkWin() {
        boolean redHasPieces = false, blackHasPieces = false;

        for (int[] row : board) {
            for (int cell : row) {
                if (cell > 0) redHasPieces = true;
                if (cell < 0) blackHasPieces = true;
            }
        }

        if (!redHasPieces || !blackHasPieces) {
            return true; // One side has no pieces
        }

        boolean redHasMoves = hasAnyMoves(true);
        boolean blackHasMoves = hasAnyMoves(false);

        return !redHasMoves || !blackHasMoves;
    }

    /**
     * Placeholder for potential future feature: previewing AI move.
     * @return empty array (not implemented)
     */
    public int[][] computerMoveWithPreview() {
        return new int[0][];
    }

    /**
     * Placeholder for finalizing a staged AI move (not implemented).
     */
    public void finalizeComputerMove() {
    }


    /**
     * Internal helper class representing a move.
     */
    private static class Move {
        int fromRow, fromCol, toRow, toCol;
        Move(int fr, int fc, int tr, int tc) {
            fromRow = fr; fromCol = fc; toRow = tr; toCol = tc;
        }
    }

    public boolean hasAnyMoves(boolean redTurn) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                int piece = board[row][col];
                if (piece == 0 || (piece > 0) != redTurn) continue;
                if (!getValidMoves(row, col).isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }
}


