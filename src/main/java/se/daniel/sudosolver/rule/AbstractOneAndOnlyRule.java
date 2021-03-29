package se.daniel.sudosolver.rule;

import se.daniel.sudosolver.Board;

import java.util.Set;

public abstract class AbstractOneAndOnlyRule extends AbstractSolverRule {

    @Override
    public void apply0(Board board, int row, int col) {

        final var othersAvailableNumbers = getOthersAvailableNumbers(board, row, col);

        board.getAvailableNumbers(row, col).forEach(n -> {

            if (!othersAvailableNumbers.contains(n)) {
                board.setNumber(row, col, n);
                System.out.println(getClass().getSimpleName() + " set " + row + "," + col + " to " + n);
            }

        });

    }

    protected abstract Set<Integer> getOthersAvailableNumbers(Board board, int row, int col);
}
