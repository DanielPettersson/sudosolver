package se.daniel.sudosolver;

public class Main {

    public static void main(String[] args) {

        final var rows = new int[9][9];

        rows[0] = new int[] {0,0,7, 0,0,0, 3,0,2};
        rows[1] = new int[] {2,0,0, 0,0,5, 0,1,0};
        rows[2] = new int[] {0,0,0, 8,0,1, 4,0,0};

        rows[3] = new int[] {0,1,0, 0,9,6, 0,0,8};
        rows[4] = new int[] {7,6,0, 0,0,0, 0,4,9};
        rows[5] = new int[] {0,0,0, 0,0,0, 0,0,0};

        rows[6] = new int[] {0,0,0, 1,0,3, 0,0,0};
        rows[7] = new int[] {8,0,1, 0,6,0, 0,0,0};
        rows[8] = new int[] {0,0,0, 7,0,0, 0,6,3};

        final var board = new Board(rows);
        final var solver = new SudoSolver(board);
        final var renderer = new BoardRenderer(board, System.out);

        final var startMillis = System.currentTimeMillis();

        do {
            renderer.render();
        } while (solver.step());

        renderer.render();

        System.out.println("Solved sudoku in " + (System.currentTimeMillis() - startMillis) + "ms");

    }
}
