package com.basidoni.backend.service;

import com.basidoni.backend.model.MoveResponse;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {
    private static final char PLAYER = 'X';
    private static final char AI = 'O';

    public MoveResponse getBestMoveToMove(char[][] state) {
        MoveResponse bestMove = null;
        int bestScore = Integer.MIN_VALUE;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (state[row][col] == AI) {
                    int[][] adjacent = getAdjacentCells(row, col);
                    for (int[] cell : adjacent) {
                        int r = cell[0];
                        int c = cell[1];
                        if (state[r][c] == '\0') {
                            char[][] tempState = deepCopyBoard(state);
                            tempState[r][c] = AI;
                            tempState[row][col] = '\0';
                            int score = minimax(tempState, 0, false);
                            if (score > bestScore) {
                                bestScore = score;
                                bestMove = new MoveResponse(row, col, r, c);
                            }
                        }
                    }
                }
            }
        }
        return bestMove;
    }

    private int minimax(char[][] state, int depth, boolean isMaximizing) {
        char winner = checkWinner(state);
        if (winner == AI) return 10 - depth;
        if (winner == PLAYER) return depth - 10;
        if (isBoardFull(state)) return 0;

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (state[row][col] == '\0') {
                    state[row][col] = isMaximizing ? AI : PLAYER;
                    int score = minimax(state, depth + 1, !isMaximizing);
                    state[row][col] = '\0';
                    bestScore = isMaximizing ? Math.max(score, bestScore) : Math.min(score, bestScore);
                }
            }
        }
        return bestScore;
    }

    private boolean isBoardFull(char[][] state) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (state[row][col] == '\0') {
                    return false;
                }
            }
        }
        return true;
    }

    private char checkWinner(char[][] state) {
        int[][][] lines = {
                {{0, 0}, {0, 1}, {0, 2}},
                {{1, 0}, {1, 1}, {1, 2}},
                {{2, 0}, {2, 1}, {2, 2}},
                {{0, 0}, {1, 0}, {2, 0}},
                {{0, 1}, {1, 1}, {2, 1}},
                {{0, 2}, {1, 2}, {2, 2}},
                {{0, 0}, {1, 1}, {2, 2}},
                {{0, 2}, {1, 1}, {2, 0}},
        };

        for (int[][] line : lines) {
            char a = state[line[0][0]][line[0][1]];
            char b = state[line[1][0]][line[1][1]];
            char c = state[line[2][0]][line[2][1]];

            if (a != '\0' && a == b && a == c) {
                return a;
            }
        }
        return '\0';
    }

    public static int[][] getAdjacentCells(int row, int col) {
        List<int[]> directions = new ArrayList<>();
        int[][] cardinalDirections = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : cardinalDirections) {
            directions.add(new int[]{row + dir[0], col + dir[1]});
        }
        if ((row == 0 && col == 0) || (row == 0 && col == 2) || (row == 2 && col == 0) || (row == 2 && col == 2)) {
            directions.add(new int[]{row + 1, col + 1});
        }
        if (row == 1 && col == 1) {
            directions.add(new int[]{row, col});
            directions.add(new int[]{row, col + 2});
            directions.add(new int[]{row + 2, col});
            directions.add(new int[]{row + 2, col + 2});
        }

        int validCount = 0;
        for (int[] dir : directions) {
            int r = dir[0], c = dir[1];
            if (r >= 0 && r < 3 && c >= 0 && c < 3) {
                validCount++;
            }
        }

        int[][] validDirections = new int[validCount][2];
        int index = 0;
        for (int[] dir : directions) {
            int r = dir[0], c = dir[1];
            if (r >= 0 && r < 3 && c >= 0 && c < 3) {
                validDirections[index++] = dir;
            }
        }
        return validDirections;
    }

    private char[][] deepCopyBoard(char[][] original) {
        char[][] copy = new char[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(original[i], 0, copy[i], 0, 3);
        }
        return copy;
    }
}
