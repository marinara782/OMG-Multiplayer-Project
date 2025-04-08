package org.example.game.checkers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CheckersGame {
    private final int[][] board;
    private boolean isRedTurn = false; // player is black, computer is red

    // initialize the board with pieces for checker game
    public CheckersGame() {
        board = new int[8][8];
        initializeBoard();
    }

    // Initialize the board with pieces
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

    // gets board state
    public int[][] getBoard() {
        return board;
    }

    // gets piece at position
    public int getPiece(int row, int col) {
        return board[row][col];
    }

    // gets current player turn
    public boolean isPlayersTurn() {
        return !isRedTurn;
    }

    /**
     * moves a piece from one position to another
     * @param fromRow current row of the piece
     * @param fromCol current column of the piece
     * @param toRow target row of the piece
     * @param toCol target column of the piece
     * @return true when the piece has moved, false when fails
     */
    public boolean movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        int piece = board[fromRow][fromCol];
        if (piece == 0 || (piece > 0) != isRedTurn) return false; // not a valid piece or not player's turn

        int dr = toRow - fromRow;
        int dc = toCol - fromCol;
        boolean isKing = Math.abs(piece) == 2;

        // Check for normal move
        if (Math.abs(dr) == 1 && Math.abs(dc) == 1 && board[toRow][toCol] == 0) {
            if (isKing || dr == (isRedTurn ? 1 : -1)) {
                executeMove(fromRow, fromCol, toRow, toCol, piece);
                return true;
            }
        }

        // Check for jump move
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
     * executes the move on the board
     * @param fromRow previous row of the piece
     * @param fromCol previous column of the piece
     * @param toRow target row of the piece
     * @param toCol target column of the piece
     * @param piece piece to be moved
     */
    private void executeMove(int fromRow, int fromCol, int toRow, int toCol, int piece) {
        board[fromRow][fromCol] = 0;
        board[toRow][toCol] = piece;

        if (piece == 1 && toRow == 7) board[toRow][toCol] = 2;
        if (piece == -1 && toRow == 0) board[toRow][toCol] = -2;

        isRedTurn = !isRedTurn;
    }

    // executes a move for the computer player
    public void computerMove() {
        List<Move> moves = getAllValidMoves(true);
        if (!moves.isEmpty()) {
            Move move = moves.get(new Random().nextInt(moves.size()));
            movePiece(move.fromRow, move.fromCol, move.toRow, move.toCol);
        }
    }

    /**
     * gets all valid moves for the player
     * @param redTurn true if red turn, false if black turn
     * @return list of valid moves
     */
    private List<Move> getAllValidMoves(boolean redTurn) {
        List<Move> moves = new ArrayList<>();
        // loop through the board to find all valid moves for the player
        for (int row = 0; row < 8; row++) {
            // loop through the columns of the board
            for (int col = 0; col < 8; col++) {
                int piece = board[row][col];
                if (piece == 0 || (piece > 0) != redTurn) continue;
                boolean isKing = Math.abs(piece) == 2;
                // determines the possible moves a piece, isKing or not
                int[][] directions = isKing ? new int[][]{{1,1},{1,-1},{-1,1},{-1,-1}} : redTurn ? new int[][]{{1,1},{1,-1}} : new int[][]{{-1,1},{-1,-1}};

                // calculates all possible moves for the piece
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

    /**
     * makes sure the row and column are not out of bounds
     * @param r current row of the piece
     * @param c current column of the piece
     * @return true if the row and column are in bounds, false if out of bounds
     */
    private boolean inBounds(int r, int c) {
        return r >= 0 && r < 8 && c >= 0 && c < 8;
    }

    /**
     * checks if someone has won the game
     * @return true if someone won, false if still playing
     */
    public boolean checkWin() {
        boolean red = false, black = false;
        for (int[] row : board) {
            for (int cell : row) {
                if (cell > 0) red = true;
                if (cell < 0) black = true;
            }
        }
        return !(red && black);
    }

    /**
     * computes the computer move with preview
     * @return array of moves for the computer
     */
    public int[][] computerMoveWithPreview() {
        return new int[0][];
    }

    // placeholder for computer move finalization
    public void finalizeComputerMove() {
    }

    // encapsulates the details of one move in the game
    private static class Move {
        int fromRow, fromCol, toRow, toCol;
        Move(int fr, int fc, int tr, int tc) {
            fromRow = fr; fromCol = fc; toRow = tr; toCol = tc;
        }
    }


    // --- In CheckersGame.java ---
    // Add this method inside the CheckersGame class
    /**
     * Gets all valid moves for a piece at the given position.
     * @param row row of the piece
     * @param col column of the piece
     * @return a list of valid moves, each represented as an array of two integers [newRow, newCol]
     */
    public List<int[]> getValidMoves(int row, int col) {
        List<int[]> validMoves = new ArrayList<>();
        int piece = board[row][col]; // gets the current position of the piece
        if (piece == 0) return validMoves; // No piece at this position

        boolean isPlayerPiece = piece < 0; // true if the player's piece, false if the computer's piece
        boolean isKing = Math.abs(piece) == 2; // true if the piece is a king

        // determines the possible movement direction for a piece based on what type it is
        int[][] directions = isKing ? new int[][]{
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        } : (isPlayerPiece ? new int[][]{
                {-1, -1}, {-1, 1}
        } : new int[][]{
                {1, -1}, {1, 1}
        });

        // finds the valid moves for the piece
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            if (isInBounds(newRow, newCol)) {
                if (board[newRow][newCol] == 0) {
                    validMoves.add(new int[]{newRow, newCol});
                } else if ((isPlayerPiece && board[newRow][newCol] > 0) || (!isPlayerPiece && board[newRow][newCol] < 0)) {
                    // Jump check
                    int jumpRow = newRow + dir[0];
                    int jumpCol = newCol + dir[1];
                    if (isInBounds(jumpRow, jumpCol) && board[jumpRow][jumpCol] == 0) {
                        validMoves.add(new int[]{jumpRow, jumpCol});
                    }
                }
            }
        }

        return validMoves;
    }

    // TODO: same method as `inBounds` but with different name, remove one of them
    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
}

