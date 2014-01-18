/**
 * Name: Antriksh Sharma
 * User: as
 * Date: 1/16/2014
 * Time: 2:12 AM
 * Purpose: Checks to see if a Sudoku is Valid (wven with empty spots), and if it is solved and valid (no empty spots)
 */

public final class Checker {

    //private static boolean[] already = new boolean[10];// {true, false, false, false, false, false, false, false, false, false, false};

    // private c-tor, so it can't be instantiated
    private Checker() {
        throw new AssertionError();
    }

    private static boolean rowsValid(Sudoku sudoku) {
        boolean[] already;

        for(int i = 0; i < sudoku.getSize(); i++) {
            already = new boolean[sudoku.getSize() + 1];
            for(int j = 0; j < sudoku.getSize(); j++) {
                if(sudoku.grid[i][j] != 0) {
                    if(already[sudoku.grid[i][j]]) {
                        //System.out.println("Returning false in rowsValid(): " + board[i][j] + ", i: " + i + ", j: " + j);
                        return false;
                    }
                    already[sudoku.grid[i][j]] = true;
                }
            }
        }
        return true;
    }

    private static boolean colsValid(Sudoku sudoku) {
        boolean[] already;

        for(int i = 0; i < sudoku.getSize(); i++) {
            already = new boolean[sudoku.getSize() + 1];
            for(int j = 0; j < sudoku.getSize(); j++) {
                if(sudoku.grid[j][i] != 0) {
                    if(already[sudoku.grid[j][i]]) {
                        return false;
                    }
                    already[sudoku.grid[j][i]] = true;
                }
            }
        }
        return true;
    }

    private static boolean boxesValid(Sudoku sudoku) {
        boolean[] already;

        for(int rowIndex = 0; rowIndex <= (sudoku.getSize()/3)*2; rowIndex += sudoku.getSize()/3) {
            for(int colIndex = 0; colIndex <= (sudoku.getSize()/3)*2; colIndex += sudoku.getSize()/3) {
                already = new boolean[sudoku.getSize() + 1];

                for(int i = rowIndex; i < rowIndex + sudoku.getSize()/3; i++) {
                    for(int j = colIndex; j < colIndex + sudoku.getSize()/3; j++) {
                        if(sudoku.grid[i][j] != 0) {
                            if(already[sudoku.grid[i][j]]) {
                                return false;
                            }
                            already[sudoku.grid[i][j]] = true;
                        }
                    }
                }

            }
        }

        return true;
    }

    public static boolean isValid(Sudoku sudoku) {
        return (rowsValid(sudoku) && colsValid(sudoku) && boxesValid(sudoku));
    }

    public static boolean check(Sudoku sudoku) {

        // check if unsolved / empty spaces
        for(int i = 0; i < sudoku.getSize(); i++) {
            for(int j = 0; j < sudoku.getSize(); j++) {
                if(sudoku.grid[i][j] == 0)
                    return false;
            }
        }

        // check if valid
        return isValid(sudoku);
    }
}
