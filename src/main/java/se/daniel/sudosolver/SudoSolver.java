package se.daniel.sudosolver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import se.daniel.sudosolver.rule.*;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

class SudoSolver {

    private final Board board;
    private final List<SolverRule> rules;

    SudoSolver(Board board) {
        this.board = board;
        this.rules = List.of(
                new OnlyAvailableNumberRule(),
                new OneAndOnlyInGridRule(),
                new OneAndOnlyInRowRule(),
                new OneAndOnlyInColumnRule()
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

        final var noMoreNumbersFound = board.countUndecidedNumbers() == undecidedNumbersBefore;

        if (noMoreNumbersFound) {

            var backtrackingPos = 0;
            final var coordinatesToBacktrack = range(0, 9)
                    .boxed()
                    .flatMap(r -> range(0, 9).mapToObj(c -> new BoardCoordinate(r, c)))
                    .filter(c -> board.getNumber(c.getRow(), c.getCol()) == 0)
                    .collect(toList());

            System.out.println("No more nubmers found by deducing, switching to backtracking remaining " + coordinatesToBacktrack.size() + " numbers");

            while (backtrackingPos < coordinatesToBacktrack.size()) {

                final var coord = coordinatesToBacktrack.get(backtrackingPos);
                final var number = board.getNumber(coord.getRow(), coord.getCol());

                final var numbersToSet = board.getAvailableNumbers(coord.getRow(), coord.getCol()).tailSet(number + 1);

                if (numbersToSet.isEmpty()) {

                    if (backtrackingPos == 0) {
                        throw new IllegalStateException("Out of numbers to backtrack");
                    }

                    board.setNumber(coord.getRow(), coord.getCol(), 0);
                    backtrackingPos--;

                } else {

                    board.setNumber(coord.getRow(), coord.getCol(), numbersToSet.first());
                    backtrackingPos++;

                }
            }

        }

        return board.countUndecidedNumbers() != 0;
    }

    @Getter
    @AllArgsConstructor
    private class BoardCoordinate {
        private final int row;
        private final int col;
    }
}
