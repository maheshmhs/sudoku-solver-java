package com.acharya.mahesh.sudoku.solver.model.board;

import java.util.LinkedList;
import java.util.List;

import com.acharya.mahesh.sudoku.solver.model.cell.Cell;

public class Board {
    private final Cell[][] CELLS;

    private final List<Cell> QUEUE;

    private int VALUES_POPULATED = 0;

    public Board() {
        CELLS = new Cell[9][9];
        QUEUE = new LinkedList<Cell>();
        initializeCells();
    }

    private int getBaseIndex(int idx) {
        return (idx / 3) * 3;
    }

    public Cell getNextCell() {
        if (!QUEUE.isEmpty()) {
            return QUEUE.get(0);
        }
        return null;
    }

    public List<Integer> getPossibilities(int row, int column) {
        return CELLS[row][column].getPossiblities();
    }

    public int getValuesPopulated() {
        return VALUES_POPULATED;
    }

    public boolean hasValue(int row, int column) {
        return CELLS[row][column].getValue() > 0 && CELLS[row][column].getValue() < 9;
    }

    private void initializeCells() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                CELLS[i][j] = new Cell(i, j);
            }
        }
    }

    public boolean isValid(boolean complete) {
        for (int i = 0; i < 9; i++) {
            int[] rows = new int[10];
            int[] cols = new int[10];

            for (int j = 0; j < 9; j++) {
                rows[CELLS[i][j].getValue()]++;
                cols[CELLS[j][i].getValue()]++;
            }
            if (!isValid(rows, complete)) {
                return false;
            }
            if (!isValid(cols, complete)) {
                return false;
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int[] cubes = new int[10];
                for (int x = (i * 3); x < (i * 3) + 3; x++) {
                    for (int y = (j * 3); y < (j * 3) + 3; y++) {
                        cubes[CELLS[x][y].getValue()]++;
                    }
                }
                if (!isValid(cubes, complete)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(int[] counts, boolean complete) {
        for (int i = 1; i < 10; i++) {
            if (counts[i] > 1 || (complete && counts[i] < 1)) {
                return false;
            }
        }
        return true;
    }

    public void print() {
        System.out.println("Values Populated:\t" + VALUES_POPULATED);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(CELLS[i][j].getValue() + " ");
            }
            System.out.println();
        }
        System.out.println("\n");
    }

    private void removePossibilities(int x, int y, int value) {
        for (int i = 0; i < 9; i++) {
            removePossibilityFromCell(i, y, value);
            removePossibilityFromCell(x, i, value);
        }
        int cellX = getBaseIndex(x);
        int cellY = getBaseIndex(y);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int xCord = cellX + i;
                int yCord = cellY + j;
                removePossibilityFromCell(xCord, yCord, value);
            }
        }
    }

    private void removePossibilityFromCell(int x, int y, int value) {
        if (CELLS[x][y].removePossibility(value)) {

            if (CELLS[x][y].getNumberOfPossibilities() == 1) {
                QUEUE.remove(CELLS[x][y]);
                QUEUE.add(0, CELLS[x][y]);
            } else if (!QUEUE.contains(CELLS[x][y])) {
                QUEUE.add(CELLS[x][y]);
            }

        }
    }

    public void setValue(int x, int y, int value) {
        Cell cell = CELLS[x][y];
        if (!cell.canHold(value)) {
            throw new IllegalArgumentException("value can't be set at (" + x + "," + y + "):"
                    + value);
        }
        removePossibilities(x, y, value);
        cell.setValue(value);
        VALUES_POPULATED++;
    }

}
