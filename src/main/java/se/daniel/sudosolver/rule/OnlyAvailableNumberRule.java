package se.daniel.sudosolver.rule;

import se.daniel.sudosolver.Board;

import static java.util.stream.Collectors.toSet;
import static java.util.stream.IntStream.rangeClosed;

public class OnlyAvailableNumberRule extends AbstractSolverRule {

    @Override
    public void apply0(Board board, int row, int col) {

        final var gridNumbers = board.getGridNumbers(row, col);
         final var rowNumbers = board.getRowNumbers(row);
        final var columnNumbers = board.getColumnNumbers(col);

        final var allNumbers = rangeClosed(1, 9).boxed().collect(toSet());
        allNumbers.removeAll(gridNumbers);
        allNumbers.removeAll(rowNumbers);
        allNumbers.removeAll(columnNumbers);

        if (allNumbers.size() == 1) {
            final var number = allNumbers.iterator().next();
            board.setNumber(row, col, number);

            System.out.println(getClass().getSimpleName() + " set " + row + "," + col + " to " + number);
        }

    }
}
