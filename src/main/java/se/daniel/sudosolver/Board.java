package se.daniel.sudosolver;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

class Board {

    @Getter
    private final int[][] rows;

    Board(int[][] rows) {
        this.rows = rows;

        verifyBoardDimensions();
        verifyValidBoardNumbers();
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

    private void verifyValidBoardNumbers() {

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
