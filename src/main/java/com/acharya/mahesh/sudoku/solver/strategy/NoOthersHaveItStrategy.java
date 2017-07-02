package com.acharya.mahesh.sudoku.solver.strategy;

import com.acharya.mahesh.sudoku.solver.model.board.Board;

public class NoOthersHaveItStrategy implements Strategy {

    private final Board BOARD;

    public NoOthersHaveItStrategy(Board board) {
        BOARD = board;
    }

    private boolean optimize(int idx, boolean isRow) {
        boolean optimized = false;
        int[] counts = new int[10];
        for (int i = 0; i < 9; i++) {
            for (int possibility : BOARD.getPossibilities(isRow ? idx : i, isRow ? i : idx)) {
                counts[possibility]++;
            }
        }
        for (int i = 1; i < 10; i++) {
            if (counts[i] == 1) {
                for (int j = 0; j < 9; j++) {
                    if (BOARD.getPossibilities(isRow ? idx : j, isRow ? j : idx).contains(i)) {
                        BOARD.setValue(isRow ? idx : j, isRow ? j : idx, i);
                        optimized = true;
                    }
                }
            }
        }
        return optimized;
    }

    private boolean optimizeCube(int i, int j) {
        int[] counts = new int[10];
        int baseX = i * 3;
        int baseY = j * 3;
        boolean optimized = false;
        for (int x = baseX; x < baseX + 3; x++) {
            for (int y = baseY; y < baseY + 3; y++) {
                for (int possibility : BOARD.getPossibilities(x, y)) {
                    counts[possibility]++;
                }
            }
        }
        for (int c = 1; i < 10; i++) {
            if (counts[c] == 1) {
                for (int x = baseX; x < baseX + 3; x++) {
                    for (int y = baseY; y < baseY + 3; y++) {
                        if (BOARD.getPossibilities(x, y).contains(c)) {
                            BOARD.setValue(x, y, c);
                            optimized = true;
                        }
                    }
                }
            }
        }
        return optimized;
    }

    @Override
    public boolean solve() {
        boolean solved = false;
        boolean hasSolved = false;
        do {
            solved = false;
            for (int i = 0; i < 9; i++) {
                if (optimize(i, true)) {
                    hasSolved = true;
                    solved = true;
                }
            }

            for (int i = 0; i < 9; i++) {
                if (optimize(i, false)) {
                    hasSolved = true;
                    solved = true;
                }
            }

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (optimizeCube(i, j)) {
                        hasSolved = true;
                        solved = true;
                    }
                }
            }
        } while (solved);
        return hasSolved;
    }

}
