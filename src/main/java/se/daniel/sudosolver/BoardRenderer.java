package se.daniel.sudosolver;

import lombok.AllArgsConstructor;

import java.io.PrintStream;

@AllArgsConstructor
class BoardRenderer {

    private final Board board;
    private final PrintStream out;

    void render() {

        out.println("-------------------------");

        for (int row = 0; row < 9; row++) {

            out.print("| ");
            for (int col = 0; col < 9; col++) {
                final var v = board.getRows()[row][col];
                out.print(v == 0 ? "* " : v + " ");
                if (col % 3 == 2) out.print("| ");
            }
            out.println("");
            if (row % 3 == 2) out.println("-------------------------");
        }

        out.println("");
    }

}
