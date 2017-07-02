package com.acharya.mahesh.sudoku.solver.strategy;

import com.acharya.mahesh.sudoku.solver.model.board.Board;
import com.acharya.mahesh.sudoku.solver.model.cell.Cell;

public class NoOthersPossibleStrategy implements Strategy {

    private final Board BOARD;

    public NoOthersPossibleStrategy(Board board) {
        BOARD = board;
    }

    public boolean canSolve() {
        return BOARD.getNextCell() != null && BOARD.getNextCell().getNumberOfPossibilities() == 1;
    }

    @Override
    public boolean solve() {
        boolean solved = false;
        while (canSolve()) {
            Cell cell = BOARD.getNextCell();
            int value = cell.getFirstPossibility();
            if (value > 0) {
                solved = true;
                BOARD.setValue(cell.getX(), cell.getY(), value);
            }
        }
        return solved;
    }

}
