/**
 * Name: Antriksh Sharma
 * User: as
 * Date: 1/18/2014
 * Time: 3:35 AM
 * Purpose: Backtracking Sudoku Solver with Constraint Propagation
 *      When we pick a value for a cell, we reduce its domain to that value only, and
 *      we also reduce the domain of its peers, i.e. other cells in its row, column, and box
 *      If there's a conflict found, we backtrack
 */

/*************************************************
 CONSTRAINT PROPAGATION & BACKTRACKING
 *************************************************/

public class ConstraintPropagationBacktrackingSolver extends Solver {

    public boolean solve (Sudoku sudoku) {
        if(sudoku == null)
            return false;

        loopCount = 0;
        int[][] domains = DomainUtils.calcDomains(sudoku);
        if(backrackWithConstraints(sudoku, domains)) {
            System.out.println("loopCount: " + loopCount);
            return true;
        }
        else {
            System.out.println("loopCount: " + loopCount);
            return false;
        }
    }

    private static class Indices {
        int r;
        int c;
        Indices(int i, int j) {
            r = i;
            c = j;
        }
    }

    private boolean backrackWithConstraints (Sudoku s, int[][] domains) {

        if(DomainUtils.areDomainsUnique(domains)) {
            DomainUtils.fillRemainingGrid(s, domains);
            return true;
        }

        loopCount++;

        Indices minIndices = findMinDomainLenIndices(s, domains); // only finds empty cells
        int[] domain = DomainUtils.calcDomainArr(domains, minIndices.r, minIndices.c);
        for(int val : domain) {
            s.grid[minIndices.r][minIndices.c] = val;
            //System.out.println("Set: " + val + " at (" + minIndices.r + "," + minIndices.c +")");
            int[][] copiedDomains = DomainUtils.deepCopyDomains(domains);
            if(DomainUtils.updateDomains(s, copiedDomains, val, minIndices.r, minIndices.c)) { // no conflict
                if( backrackWithConstraints(s, copiedDomains) )
                    return true;
            }
        }
        s.grid[minIndices.r][minIndices.c] = 0;

        return false;
    }


    private static Indices findMinDomainLenIndices(Sudoku s, int[][] domains) {
        int minI = -1;
        int minJ = -1;

        int minLen = Integer.MAX_VALUE;

        for(int i = 0; i < domains.length; i++) {
            for(int j = 0; j < domains[i].length; j++) {
                if(s.grid[i][j] == 0 && Integer.bitCount(domains[i][j]) < minLen) {
                    minLen = Integer.bitCount(domains[i][j]);
                    minI = i;
                    minJ = j;
                }

            }
        }

        return (new Indices(minI, minJ));
    }


}
