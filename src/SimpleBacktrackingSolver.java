/**
 * Name: Antriksh Sharma
 * User: as
 * Date: 1/18/2014
 * Time: 3:22 AM
 * Purpose: Sudoku Solver with Simple Backtracking
 *      It tries everything from 1 to 9, and if a conflict is found, it backtracks, updates, and moves forward
 */

/**************************************************
 SIMPLE BACKTRACKING
 **************************************************/

public class SimpleBacktrackingSolver extends Solver {

    public boolean solve (Sudoku sudoku) {
        if(sudoku == null)
            return false;

        loopCount = 0;
        if(backtrack(sudoku, 0, 0)) {
            System.out.println("loopCount: " + loopCount);
            return true;
        }
        else {
            System.out.println("loopCount: " + loopCount);
            return false;
        }
    }

    private boolean backtrack (Sudoku s, int i, int j) {
        if( i == s.getSize() )
            return true;

        loopCount++;

        if(s.grid[i][j] != 0) {
            if( backtrack(s, (j == s.getSize()-1) ? i+1 : i, (j==s.getSize()-1) ? 0 : j+1) )
                return true;
        } else {
            for(int val = 1; val <= s.getSize(); val++) {
                s.grid[i][j] = val;
                //System.out.println("Set: " + val + " at (" + i + "," + j +")");
                if(Checker.isValid(s)) {
                    if( backtrack(s, (j == s.getSize()-1) ? i+1 : i, (j==s.getSize()-1) ? 0 : j+1) )
                        return true;
                }
            }
            s.grid[i][j] = 0;
        }

        return false;
    }
}
