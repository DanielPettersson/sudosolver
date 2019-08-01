package se.daniel.sudosolver.rule;

import se.daniel.sudosolver.Board;

public abstract class AbstractSolverRule implements SolverRule {

    @Override
    public void apply(Board board, int row, int col) {
        if (board.getNumber(row, col) == 0) {
            apply0(board, row, col);
        }
    }

    protected abstract void apply0(Board board, int row, int col);
}
