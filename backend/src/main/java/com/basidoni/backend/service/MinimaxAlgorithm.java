package com.basidoni.backend.service;

import com.basidoni.backend.model.MoveResponse;
import org.springframework.stereotype.Component;

@Component
public class MinimaxAlgorithm {
    private static final char PLAYER = 'X';
    private static final char AI = 'O';


    public static MoveResponse findBestMove(char[][] board) {
        int bestValue = Integer.MIN_VALUE;
        MoveResponse bestMove = null;
        boolean bool = board[2][2] == '\0';
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '\0') { // Check if cell is empty
                    board[i][j] = PLAYER;
                    int moveValue = minimax(board, 0, false);
                    board[i][j] = '\0';

                    if (moveValue > bestValue) {
                        bestMove = new MoveResponse(i, j);
                        bestValue = moveValue;
                    }
                }
            }
        }

        return bestMove;
    }

    static int minimax(char[][] board, int depth, boolean isMaximizing) {
        int score = evaluate(board);

        if (score == 10 || score == -10) {
            return score;
        }

        if (!isMovesLeft(board)) {
            return 0; // Draw
        }

        if (isMaximizing) {
            int best = Integer.MIN_VALUE;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '\0') {
                        board[i][j] = PLAYER;
                        best = Math.max(best, minimax(board, depth + 1, false));
                        board[i][j] = '\0';
                    }
                }
            }

            return best;
        } else {
            int best = Integer.MAX_VALUE;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '\0') {
                        board[i][j] = AI;
                        best = Math.min(best, minimax(board, depth + 1, true));
                        board[i][j] = '\0';
                    }
                }
            }

            return best;
        }
    }

    static boolean isMovesLeft(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '\0') {
                    return true;
                }
            }
        }
        return false;
    }

    static int evaluate(char[][] board) {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != '\0') {
                if (board[i][0] == PLAYER) return 10;
                if (board[i][0] == AI) return -10;
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != '\0') {
                if (board[0][i] == PLAYER) return 10;
                if (board[0][i] == AI) return -10;
            }
        }

        // Check diagonals
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '\0') {
            if (board[0][0] == PLAYER) return 10;
            if (board[0][0] == AI) return -10;
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != '\0') {
            if (board[0][2] == PLAYER) return 10;
            if (board[0][2] == AI) return -10;
        }

        return 0; // No winner yet
    }
}
