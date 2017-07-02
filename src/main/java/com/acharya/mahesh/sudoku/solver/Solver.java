package com.acharya.mahesh.sudoku.solver;

import java.util.ArrayList;
import java.util.List;

import com.acharya.mahesh.sudoku.solver.model.board.Board;
import com.acharya.mahesh.sudoku.solver.strategy.NoOthersHaveItStrategy;
import com.acharya.mahesh.sudoku.solver.strategy.NoOthersPossibleStrategy;
import com.acharya.mahesh.sudoku.solver.strategy.Strategy;

public class Solver {

    private final Board BOARD;

    private List<Strategy> STRATEGIES;

    public Solver(Board board) {
        BOARD = board;
        STRATEGIES = new ArrayList<Strategy>();
        STRATEGIES.add(new NoOthersPossibleStrategy(board));
        STRATEGIES.add(new NoOthersHaveItStrategy(board));
    }

    public void solve() {
        boolean canSolve = false;
        do {
            canSolve = false;
            for (Strategy strategy : STRATEGIES) {
                if (strategy.solve()) {
                    canSolve = true;
                }
            }
        } while (canSolve);

        BOARD.print();
    }

}
