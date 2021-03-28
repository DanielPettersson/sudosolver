package se.daniel.sudosolver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import se.daniel.sudosolver.rule.*;

import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

class SudoSolver {

    private static final List<SolverRule> rules = List.of(
            new OnlyAvailableNumberRule(),
            new OneAndOnlyInGridRule(),
            new OneAndOnlyInRowRule(),
            new OneAndOnlyInColumnRule()
    );
    
    private final Board board;
    
    SudoSolver(Board board) {
        this.board = board;
    }

    boolean step() {

        final boolean solvedAnyCell = applyRules(board);

        if (!solvedAnyCell) {

            var backtrackingPos = 0;
            var backtrackingIterations = 0;
            final var coordinatesToBacktrack = range(0, 9)
                    .boxed()
                    .flatMap(r -> range(0, 9).mapToObj(c -> new BoardCoordinate(r, c)))
                    .filter(c -> board.getNumber(c.getRow(), c.getCol()) == 0)
                    .sorted(comparing(c -> board.getAvailableNumbers(c.getRow(), c.getCol()).size()))
                    .collect(toList());

            
            System.out.println("No more numbers found by deducing, switching to backtracking remaining " + coordinatesToBacktrack.size() + " numbers");

            while (backtrackingPos < coordinatesToBacktrack.size()) {

                final var coord = coordinatesToBacktrack.get(backtrackingPos);
                final var number = board.getNumber(coord.getRow(), coord.getCol());

                final var numbersToSet = board.getAvailableNumbers(coord.getRow(), coord.getCol()).tailSet(number + 1);

                if (numbersToSet.isEmpty()) {

                    if (backtrackingPos == 0) {
                        throw new IllegalStateException("Unsolvable sudoku");
                    }

                    board.setNumber(coord.getRow(), coord.getCol(), 0);
                    backtrackingPos--;

                } else {

                    board.setNumber(coord.getRow(), coord.getCol(), numbersToSet.first());
                    backtrackingPos++;
                }

                backtrackingIterations++;
                
                if (backtrackingIterations > 3000000) {
                    throw new IllegalStateException("Could not find solution in reasonable time. Backtracked " + backtrackingPos + " of " + coordinatesToBacktrack.size());
                }
            }

            System.out.println("Backtracked solution in " + backtrackingIterations + " steps");
            
        }

        return board.countUndecidedNumbers() != 0;
    }
    
    private static boolean applyRules(final Board board) {

        final var undecidedNumbersBefore = board.countUndecidedNumbers();
        
        range(0, 9).forEach(
                row -> range(0, 9).forEach(
                        col -> rules.forEach(
                                rule -> rule.apply(board, row, col)
                        )
                )
        );

        return board.countUndecidedNumbers() < undecidedNumbersBefore;
    }

    @Getter
    @AllArgsConstructor
    private static class BoardCoordinate {
        private final int row;
        private final int col;
    }
}
