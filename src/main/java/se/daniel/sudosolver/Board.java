package se.daniel.sudosolver;

import java.util.Arrays;

class Board {

    private final int[][] board;

    Board(int[][] board) {
        this.board = board;

        verifyBoardDimensions();
    }

    private void verifyBoardDimensions() {

        if(board.length != 9) {
            throw new IllegalArgumentException("There should be 9 rows in sudoku");
        }

        Arrays.stream(board).forEach(row ->  {
            if(row.length != 9) {
                throw new IllegalArgumentException("There should be 9 columns in sudoku");
            }
        });
    }
}
