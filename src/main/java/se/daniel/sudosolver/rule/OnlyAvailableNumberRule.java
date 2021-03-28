package se.daniel.sudosolver.rule;

import se.daniel.sudosolver.Board;

public class OnlyAvailableNumberRule extends AbstractSolverRule {

    @Override
    public void apply0(Board board, int row, int col) {


        final var availableNumbers = board.getAvailableNumbers(row, col, false);

        if (availableNumbers.size() == 1) {
            final var number = availableNumbers.iterator().next();
            board.setNumber(row, col, number);

            System.out.println(getClass().getSimpleName() + " set " + row + "," + col + " to " + number);
        }

    }
}
