package se.daniel.sudosolver.rule;

import se.daniel.sudosolver.Board;

import java.util.HashSet;
import java.util.Set;

import static java.util.stream.IntStream.range;

public class OneAndOnlyInGridRule extends AbstractOneAndOnlyRule {

    @Override
    protected Set<Integer> getOthersAvailableNumbers(Board board, int row, int col) {

        final var gridRow = row / 3;
        final var gridCol = col / 3;

        final var ret = new HashSet<Integer>();

        range(gridRow * 3, gridRow * 3 + 3).forEach(
                r -> range(gridCol * 3, gridCol * 3 + 3).forEach(
                        c -> {
                            if (r != row || c != col) {
                                ret.addAll(board.getAvailableNumbers(r, c, false));
                            }
                        }
                )
        );

        return ret;
    }
}
