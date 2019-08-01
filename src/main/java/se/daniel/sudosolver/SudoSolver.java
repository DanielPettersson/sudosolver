package se.daniel.sudosolver;

import se.daniel.sudosolver.rule.OnlyAvailableNumberRule;
import se.daniel.sudosolver.rule.SolverRule;

import java.util.List;

import static java.util.stream.IntStream.range;

class SudoSolver {

    private final Board board;
    private final List<SolverRule> rules;

    SudoSolver(Board board) {
        this.board = board;
        this.rules = List.of(
                new OnlyAvailableNumberRule()
        );
    }

    boolean step() {

        final var undecidedNumbersBefore = board.countUndecidedNumbers();

        range(0, 9).forEach(
                row -> range(0, 9).forEach(
                        col -> rules.forEach(
                                rule -> rule.apply(board, row, col)
                        )
                )
        );

        board.verifyValidBoardNumbers();

        return board.countUndecidedNumbers() < undecidedNumbersBefore;
    }
}
