package com.sergeykotov.chess;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class Chess {
    private static final int X_DIMENSION = 3;
    private static final int Y_DIMENSION = 2;
    private static final int FIGURE_COUNT = 2;
    private static final Figure FIGURE = Figure.KNIGHT;

    static {
        Cell.initializeCellPool(X_DIMENSION, Y_DIMENSION);
    }

    private static Set<Combination> getCombinations() {
        Set<Combination> combinations = new HashSet<>();
        for (Cell cell : Cell.getCellPool()) {
            combinations.add(new Combination(cell));
        }
        for (int i = 1; i < FIGURE_COUNT; i++) {
            Set<Combination> currentCombinations = new HashSet<>(combinations);
            combinations.clear();
            for (Combination currentCombination : currentCombinations) {
                for (Cell cell : currentCombination.newCells()) {
                    Combination combination = new Combination(currentCombination);
                    combination.offer(cell, FIGURE);
                    combinations.add(combination);
                }
            }
        }
        return combinations.stream().filter(c -> c.size() == FIGURE_COUNT).collect(Collectors.toSet());
    }

    private static String visualise(Combination combination) {
        StringBuilder cells = new StringBuilder(combination + System.lineSeparator());
        for (int y = 0; y < Y_DIMENSION; y++) {
            for (int x = 0; x < X_DIMENSION; x++) {
                String cell = combination.contains(x, y) ? "1 " : "0 ";
                cells.append(cell);
            }
            cells.append(System.lineSeparator());
        }
        return cells.toString();
    }

    public static String visualise(Set<Combination> combinations) {
        return combinations.stream().
                map(Chess::visualise).
                reduce((c1, c2) -> c1 + System.lineSeparator() + c2).orElse("");
    }

    public static void main(String[] args) {
        System.out.println("searching combinations...");
        Set<Combination> combinations = getCombinations();
        int count = combinations.size();
        String figures = FIGURE.toString().toLowerCase() + "s";
        String newLine = System.lineSeparator();
        String view = visualise(combinations);
        String format = "there are %d %d x %d chess board cell combinations to allocate %d non-attacking %s:%s%s";
        String output = String.format(format, count, X_DIMENSION, Y_DIMENSION, FIGURE_COUNT, figures, newLine, view);
        System.out.println(output);
    }
}