package org.example.game.checkers;

import java.util.*;

public class CheckersGame {
    private final int[][] board;
    private boolean isRedTurn = false; // player is black, computer is red

    public CheckersGame() {
        board = new int[8][8];
        initializeBoard();
    }

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
        return board[row][col];
    }

    public boolean isPlayersTurn() {
        return !isRedTurn;
    }

    public boolean movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        int piece = board[fromRow][fromCol];
        if (piece == 0 || (piece > 0) != isRedTurn) return false;

        int dr = toRow - fromRow;
        int dc = toCol - fromCol;
        boolean isKing = Math.abs(piece) == 2;

        if (Math.abs(dr) == 1 && Math.abs(dc) == 1 && board[toRow][toCol] == 0) {
            if (isKing || dr == (isRedTurn ? 1 : -1)) {
                executeMove(fromRow, fromCol, toRow, toCol, piece);
                return true;
            }
        }

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

    private void executeMove(int fromRow, int fromCol, int toRow, int toCol, int piece) {
        board[fromRow][fromCol] = 0;
        board[toRow][toCol] = piece;

        if (piece == 1 && toRow == 7) board[toRow][toCol] = 2;
        if (piece == -1 && toRow == 0) board[toRow][toCol] = -2;

        isRedTurn = !isRedTurn;
    }

    public void computerMove() {
        List<Move> moves = getAllValidMoves(true);
        if (!moves.isEmpty()) {
            Move move = moves.get(new Random().nextInt(moves.size()));
            movePiece(move.fromRow, move.fromCol, move.toRow, move.toCol);
        }
    }

    private List<Move> getAllValidMoves(boolean redTurn) {
        List<Move> moves = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                int piece = board[row][col];
                if (piece == 0 || (piece > 0) != redTurn) continue;
                boolean isKing = Math.abs(piece) == 2;
                int[][] directions = isKing ? new int[][]{{1,1},{1,-1},{-1,1},{-1,-1}} : redTurn ? new int[][]{{1,1},{1,-1}} : new int[][]{{-1,1},{-1,-1}};

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

    private boolean inBounds(int r, int c) {
        return r >= 0 && r < 8 && c >= 0 && c < 8;
    }

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

    private static class Move {
        int fromRow, fromCol, toRow, toCol;
        Move(int fr, int fc, int tr, int tc) {
            fromRow = fr; fromCol = fc; toRow = tr; toCol = tc;
        }
    }
}

