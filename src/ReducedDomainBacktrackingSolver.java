/**
 * Name: Antriksh Sharma
 * User: as
 * Date: 1/18/2014
 * Time: 3:28 AM
 * Purpose: Reduced Domain Backtracking Sudoku Solver
 *      It looks at the given puzzle, and determines what possibilities are there for the empty cells (1..9)
 *      It only does this in the beginning, and then starts backtracking for each of those possible values
 */

/**************************************************
 REDUCED DOMAIN BACKTRACKING
 **************************************************/

public class ReducedDomainBacktrackingSolver extends Solver {

    public boolean solve (Sudoku sudoku) {
        if(sudoku == null)
            return false;

        loopCount = 0;
        int[][] domains = DomainUtils.calcDomains(sudoku);
        if(reducedBacktrack(sudoku, domains, 0, 0)) {
            System.out.println("loopCount: " + loopCount);
            return true;
        }
        else {
            System.out.println("loopCount: " + loopCount);
            return false;
        }

    }

    private boolean reducedBacktrack (Sudoku s, int[][] domains, int i, int j) {
        if( i == s.getSize() )
            return true;

        loopCount++;

        if(s.grid[i][j] != 0) {
            if( reducedBacktrack(s, domains, (j == s.getSize()-1) ? i+1 : i, (j==s.getSize()-1) ? 0 : j+1) )
                return true;
        } else {
            int[] domain = DomainUtils.calcDomainArr(domains, i, j);
            for(int val : domain) {
                s.grid[i][j] = val;
                //System.out.println("Set: " + val + " at (" + i + "," + j +")");
                if(Checker.isValid(s)) {
                    if( reducedBacktrack(s, domains, (j == s.getSize()-1) ? i+1 : i, (j == s.getSize()-1) ? 0 : j+1) )
                        return true;
                }
            }
            s.grid[i][j] = 0;
        }

        return false;
    }

}
