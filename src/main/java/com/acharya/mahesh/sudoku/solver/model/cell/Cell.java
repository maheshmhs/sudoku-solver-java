package com.acharya.mahesh.sudoku.solver.model.cell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cell {

    private final List<Integer> POSSIBILITIES;

    private int VALUE;

    private int X;

    private int Y;

    public Cell(int x, int y) {
        X = x;
        Y = y;
        VALUE = 0;
        POSSIBILITIES = new ArrayList<Integer>(9);
        POSSIBILITIES.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
    }

    public boolean canHold(int num) {
        if (num <= 0 || num > 9) {
            return false;
        }
        return POSSIBILITIES.contains(num);
    }

    public int getFirstPossibility() {
        if (POSSIBILITIES.size() > 0) {
            return POSSIBILITIES.get(0);
        }
        return 0;
    }

    public int getNumberOfPossibilities() {
        return POSSIBILITIES.size();
    }

    public List<Integer> getPossiblities() {
        return POSSIBILITIES;
    }

    public int getValue() {
        return VALUE;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public boolean removePossibility(Integer num) {
        return POSSIBILITIES.remove(num);
    }

    public void setValue(int num) {
        if (VALUE > 0 || num <= 0 || num > 9) {
            throw new IllegalArgumentException("value is invalid :" + num);
        }
        VALUE = num;
        POSSIBILITIES.clear();
    }

}
