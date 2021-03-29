package se.daniel.sudosolver.rule;

import se.daniel.sudosolver.Board;

import java.util.*;

import static java.util.stream.Collectors.*;
import static java.util.stream.IntStream.range;
import static java.util.stream.Stream.concat;

public class OnlyAvailableNumberRule extends AbstractSolverRule {

    @Override
    public void apply0(Board board, int row, int col) {

        final var availableNumbers = board.getAvailableNumbers(row, col);

        if (availableNumbers.size() > 1) {

            final List<SortedSet<Integer>> rowAvailable = range(0, 9).boxed().filter(c -> c != col).map(c -> board.getAvailableNumbers(row, c)).collect(toList());
            final List<SortedSet<Integer>> colAvailable = range(0, 9).boxed().filter(r -> r != row).map(r -> board.getAvailableNumbers(r, col)).collect(toList());
            final List<SortedSet<Integer>> gridAvailable = getAvailableInGrid(board, row, col);

            final Set<Integer> rowTuples = findTuples(rowAvailable);
            final Set<Integer> colTuples = findTuples(colAvailable);
            final Set<Integer> gridTuples = findTuples(gridAvailable);
            
            availableNumbers.removeAll(rowTuples);
            availableNumbers.removeAll(colTuples);
            availableNumbers.removeAll(gridTuples);

        }

        if (availableNumbers.size() == 1) {
            final var number = availableNumbers.iterator().next();
            board.setNumber(row, col, number);

            System.out.println(getClass().getSimpleName() + " set " + row + "," + col + " to " + number);
        }

    }
    
    private List<SortedSet<Integer>> getAvailableInGrid(Board board, int row, int col) {

        final var gridRow = row / 3;
        final var gridCol = col / 3;

        final var ret = new ArrayList<SortedSet<Integer>>();

        range(gridRow * 3, gridRow * 3 + 3).forEach(
                r -> range(gridCol * 3, gridCol * 3 + 3).forEach(
                        c -> {
                            if (r != row || c != col) {
                                ret.add(board.getAvailableNumbers(r, c));
                            }
                        }
                )
        );

        return ret;
    }
    
    public static Set<Integer> findTuples(List<SortedSet<Integer>> available) {

        final Map<SortedSet<Integer>, List<SortedSet<Integer>>> identicalAvailable = available.stream().collect(toMap(
                a -> a,
                List::of,
                (a, b) -> concat(a.stream(), b.stream()).collect(toList())
        ));

        return identicalAvailable
                .values()
                .stream()
                .filter(l -> l.size() > 1)
                .filter(l -> l.size() == l.get(0).size())
                .flatMap(Collection::stream)
                .flatMap(Collection::stream)
                .collect(toSet());


    }
}
