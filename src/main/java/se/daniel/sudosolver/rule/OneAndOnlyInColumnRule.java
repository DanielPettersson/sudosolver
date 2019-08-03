package se.daniel.sudosolver.rule;

import se.daniel.sudosolver.Board;

import java.util.HashSet;
import java.util.Set;

import static java.util.stream.IntStream.range;

public class OneAndOnlyInColumnRule extends AbstractOneAndOnlyRule {

    @Override
    protected Set<Integer> getOthersAvailableNumbers(Board board, int row, int col) {

        final var ret = new HashSet<Integer>();

        range(0, 9).forEach(
                r -> {
                    if (r != row) {
                        ret.addAll(board.getAvailableNumbers(r, col));
                    }
                }
        );

        return ret;
    }
}
