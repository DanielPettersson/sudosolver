package se.daniel.sudosolver;

import lombok.AllArgsConstructor;

import java.io.PrintStream;
import java.util.Arrays;

@AllArgsConstructor
class BoardRenderer {

    private final PrintStream out;

    void render(Board board) {

        Arrays.stream(board.getRows()).forEach(row -> {
            Arrays.stream(row).mapToObj(v -> v == 0 ? "*" : Integer.toString(v)).forEach(out::print);
            out.println("");
        });

        out.println("---------");
    }

}
