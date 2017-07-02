package com.acharya.mahesh.sudoku.solver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.acharya.mahesh.sudoku.solver.model.board.Board;

public class Main {

    public static void main(String[] args) {
        Board board = new Board();
        File file = new File(
                "c:/wsp/sudoku.solver/src/main/java/com/acharya/mahesh/sudoku/solver/question.txt");
        try {
            Scanner sc = new Scanner(file);
            int i = 0;
            while (sc.hasNextLine()) {
                for (int j = 0; j < 9; j++) {
                    int val = sc.nextInt();
                    if (val > 0) {
                        board.setValue(i, j, val);
                    }
                }
                i++;
            }

        } catch (FileNotFoundException exp) {
            exp.printStackTrace();
        }
        board.print();
        System.out.println("board is valid " + board.isValid(false));
        new Solver(board).solve();
        System.out.println("board is valid " + board.isValid(true));
    }
}
