package se.daniel.sudosolver.rule;

import se.daniel.sudosolver.Board;

public interface SolverRule {

    void apply(Board board, int row, int col);

}
