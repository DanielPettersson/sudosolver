package se.daniel.sudosolver;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static java.util.stream.IntStream.rangeClosed;

public class Board {

    @Getter
    private final int[][] rows;

    Board(int[][] rows) {
        this.rows = rows;

        verifyBoardDimensions();
        verifyValidBoardNumbers();
    }

    public void setNumber(int row, int col, int number) {

        if (number < 0 || number > 9) {
            throw new IllegalArgumentException(number + " is outside of range");
        }

        rows[row][col] = number;
    }

    public int getNumber(int row, int col) {
        return rows[row][col];
    }

    public SortedSet<Integer> getAvailableNumbers(int row, int col) {

        final var gridNumbers = getGridNumbers(row, col);
        final var rowNumbers = getRowNumbers(row);
        final var columnNumbers = getColumnNumbers(col);

        final var allNumbers = rangeClosed(1, 9).boxed().collect(Collectors.toCollection(TreeSet::new));
        allNumbers.removeAll(gridNumbers);
        allNumbers.removeAll(rowNumbers);
        allNumbers.removeAll(columnNumbers);

        return allNumbers;
    }

    public List<Integer> getRowNumbers(int row) {
        return Arrays.stream(rows[row])
                .filter(v -> v != 0)
                .boxed()
                .collect(toList());
    }

    public List<Integer> getColumnNumbers(int col) {
        return Arrays.stream(rows)
                .mapToInt(row -> row[col])
                .filter(v -> v != 0)
                .boxed()
                .collect(toList());
    }

    public List<Integer> getGridNumbers(int row, int col) {
        final var gridRow = row / 3;
        final var gridCol = col / 3;

        return range(gridRow * 3, gridRow * 3 + 3)
                .flatMap(r -> Arrays.stream(rows[r], gridCol * 3, gridCol * 3 + 3))
                .filter(v -> v != 0)
                .boxed()
                .collect(toList());
    }

    long countUndecidedNumbers() {
        return range(0, 9).flatMap(r -> Arrays.stream(rows[r])).filter(n -> n == 0).count();
    }

    private void verifyBoardDimensions() {

        if(rows.length != 9) {
            throw new IllegalArgumentException("There should be 9 rows in sudoku");
        }

        Arrays.stream(rows).forEach(row ->  {
            if(row.length != 9) {
                throw new IllegalArgumentException("There should be 9 columns in sudoku");
            }
        });
    }

    void verifyValidBoardNumbers() {

        range(0, 9).forEach(r -> {
                if (isAnyNumbersDuplicates(getRowNumbers(r))) {
                    throw new IllegalStateException("Row " + r + " has duplicate numbers");
                }
        });

        range(0, 9).forEach(c -> {
            if (isAnyNumbersDuplicates(getColumnNumbers(c))) {
                throw new IllegalStateException("Column " + c + " has duplicate numbers");
            }
        });

        range(0, 3).forEach(gr -> range(0, 3).forEach(gc -> {
            if(isAnyNumbersDuplicates(getGridNumbers(gr * 3, gc * 3))) {
                throw new IllegalStateException("Grid " + gr + "," + gc + " has duplicate numbers");
            }
        }));

    }

    private boolean isAnyNumbersDuplicates(List<Integer> list) {
        final var lump = new HashSet<Integer>();

        for (Integer n : list) {
            if (!lump.add(n)) {
                return true;
            }
        }

        return false;
    }
}
