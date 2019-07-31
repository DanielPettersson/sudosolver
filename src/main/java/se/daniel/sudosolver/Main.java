package se.daniel.sudosolver;

public class Main {

    public static void main(String[] args) {

        final var rows = new int[9][9];
        rows[0] = new int[] {0,0,0,9,2,0,1,7,8};
        rows[1] = new int[] {8,0,1,7,0,0,9,5,0};
        rows[2] = new int[] {0,0,9,4,0,0,0,3,0};
        rows[3] = new int[] {0,0,3,0,0,0,0,0,6};
        rows[4] = new int[] {5,1,2,0,0,0,4,9,3};
        rows[5] = new int[] {6,0,0,0,0,0,7,0,0};
        rows[6] = new int[] {0,9,0,0,0,7,3,0,0};
        rows[7] = new int[] {0,6,4,0,0,9,8,0,7};
        rows[8] = new int[] {3,8,7,0,1,6,0,0,0};

        final var board = new Board(rows);
        final var solver = new SudoSolver(board);
        final var renderer = new BoardRenderer(System.out);

        while (!board.isAllNumbersSet()) {

            renderer.render(board);
            solver.step();
        }

    }
}
