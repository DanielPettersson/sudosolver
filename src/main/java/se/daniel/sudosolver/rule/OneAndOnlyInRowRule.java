package se.daniel.sudosolver.rule;

import se.daniel.sudosolver.Board;

import java.util.HashSet;
import java.util.Set;

import static java.util.stream.IntStream.range;

public class OneAndOnlyInRowRule extends AbstractOneAndOnlyRule {

    @Override
    protected Set<Integer> getOthersAvailableNumbers(Board board, int row, int col) {

        final var ret = new HashSet<Integer>();

        range(0, 9).forEach(
                c -> {
                    if (c != col) {
                        ret.addAll(board.getAvailableNumbers(row, c, false));
                    }
                }
        );

        return ret;
    }
}
