package org.example.game.checkers;

import java.util.*;

public class CheckersGame {

    private final int[][] board;
    private boolean isRedTurn = false; // false = player's turn (black), true = computer's turn (red)

    public CheckersGame() {
        board = new int[8][8];
        initializeBoard(); // Set up pieces in starting positions
    }

    // Initializes the board with red and black pieces on the correct squares
    private void initializeBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 != 0) { // dark squares only
                    if (row < 3) board[row][col] = 1;      // Red pieces on top
                    else if (row > 4) board[row][col] = -1; // Black pieces at bottom
                }
            }
        }
    }

    // Exposes the entire board array (used by GUI to render board)
    public int[][] getBoard() {
        return board;
    }

    // Returns the piece value at a given position
    public int getPiece(int row, int col) {
        return board[row][col];
    }

    // Returns true if it's the player's turn (black)
    public boolean isPlayersTurn() {
        return !isRedTurn;
    }

    /**
     * Attempts to move a piece from (fromRow, fromCol) to (toRow, toCol).
     * Supports normal and jump moves. Also handles king promotion.
     * @return true if the move was successful
     */
    public boolean movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        int piece = board[fromRow][fromCol];
        if (piece == 0 || (piece > 0) != isRedTurn) return false;

        int dr = toRow - fromRow;
        int dc = toCol - fromCol;
        boolean isKing = Math.abs(piece) == 2;

        // Regular move (1 diagonal)
        if (Math.abs(dr) == 1 && Math.abs(dc) == 1 && board[toRow][toCol] == 0) {
            if (isKing || dr == (isRedTurn ? 1 : -1)) {
                executeMove(fromRow, fromCol, toRow, toCol, piece);
                return true;
            }
        }

        // Jump move (2 diagonal)
        if (Math.abs(dr) == 2 && Math.abs(dc) == 2) {
            int midRow = fromRow + dr / 2;
            int midCol = fromCol + dc / 2;
            int midPiece = board[midRow][midCol];

            // Check jump is valid (opponent's piece in between)
            if (midPiece != 0 && (midPiece > 0) != (piece > 0) && board[toRow][toCol] == 0) {
                board[midRow][midCol] = 0; // remove captured piece
                executeMove(fromRow, fromCol, toRow, toCol, piece);
                return true;
            }
        }

        return false; // Invalid move
    }

    // Performs the actual move and promotes to king if needed
    private void executeMove(int fromRow, int fromCol, int toRow, int toCol, int piece) {
        board[fromRow][fromCol] = 0;
        board[toRow][toCol] = piece;

        // Promotion to king (2 = red king, -2 = black king)
        if (piece == 1 && toRow == 7) board[toRow][toCol] = 2;
        if (piece == -1 && toRow == 0) board[toRow][toCol] = -2;

        isRedTurn = !isRedTurn; // Switch turn
    }

    /**
     * Simulates the computer's move using a random valid move
     */
    public void computerMove() {
        List<Move> moves = getAllValidMoves(true); // red = computer
        if (!moves.isEmpty()) {
            Move move = moves.get(new Random().nextInt(moves.size()));
            movePiece(move.fromRow, move.fromCol, move.toRow, move.toCol);
        }
    }

    // Returns a list of all legal moves for a given side
    private List<Move> getAllValidMoves(boolean redTurn) {
        List<Move> moves = new ArrayList<>();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                int piece = board[row][col];
                if (piece == 0 || (piece > 0) != redTurn) continue;

                boolean isKing = Math.abs(piece) == 2;
                int[][] directions = isKing ?
                        new int[][]{{1,1},{1,-1},{-1,1},{-1,-1}} :
                        redTurn ? new int[][]{{1,1},{1,-1}} : new int[][]{{-1,1},{-1,-1}};

                for (int[] d : directions) {
                    int r = row + d[0];
                    int c = col + d[1];
                    if (inBounds(r, c) && board[r][c] == 0) {
                        moves.add(new Move(row, col, r, c));
                    }

                    int jr = row + 2 * d[0];
                    int jc = col + 2 * d[1];
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

    // Check if a coordinate is within the board
    private boolean inBounds(int r, int c) {
        return r >= 0 && r < 8 && c >= 0 && c < 8;
    }

    /**
     * Checks if the game is over (one player has no pieces left)
     */
    public boolean checkWin() {
        boolean red = false, black = false;
        for (int[] row : board) {
            for (int cell : row) {
                if (cell > 0) red = true;
                if (cell < 0) black = true;
            }
        }
        return !(red && black); // if only one player has pieces → win
    }

    // Represents a possible move
    private static class Move {
        int fromRow, fromCol, toRow, toCol;
        Move(int fr, int fc, int tr, int tc) {
            fromRow = fr; fromCol = fc; toRow = tr; toCol = tc;
        }
    }
}