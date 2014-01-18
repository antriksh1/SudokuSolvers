/**
 * Name: Antriksh Sharma
 * User: as
 * Date: 1/18/2014
 * Time: 3:32 AM
 * Purpose: Utility methods to do bitwise calculations on domain of each cell
 *      e.g. if a cell can have the values: 1, 2, 4, the domain will be: 0b000001011
 */

import java.util.Arrays;

public final class DomainUtils {

    // private c-tor, so it can't be instantiated
    private DomainUtils() {
        throw new AssertionError();
    }

    // package private
    static int[] calcDomainArr(int[][] domains, int i, int j) {
        int count = Integer.bitCount(domains[i][j]);
        int[] arr = new int[count];

        int ai = 0;
        for(int val = 1; val <= domains.length; val++) {
            if( (domains[i][j] & (1 << (val-1))) > 0 )
                arr[ai++] = val;
        }

        return arr;
    }

    static int[][] calcDomains(Sudoku sudoku) {
        int[][] domains = new int[sudoku.getSize()][sudoku.getSize()];

        for(int i = 0; i < sudoku.getSize(); i++) {
            for(int j = 0; j < sudoku.getSize(); j++) {
                domains[i][j] = calcDomain(sudoku, i, j);
            }
        }

        return domains;
    }

    static int calcDomain(Sudoku sudoku, int i, int j) {
        if(sudoku.grid[i][j] != 0) {
            return ( 1 << (sudoku.grid[i][j] - 1));
        }

        int domain = ~((~0) << sudoku.getSize());

        // row
        for(int c = 0; c < sudoku.getSize(); c++) {
            if(sudoku.grid[i][c] > 0)
                domain &= ( ~(1 << (sudoku.grid[i][c] - 1)) );
        }

        // col
        for(int r = 0; r < sudoku.getSize(); r++) {
            if(sudoku.grid[r][j] > 0)
                domain &= ( ~(1 << (sudoku.grid[r][j] - 1)) );
        }

        // box
        int third = sudoku.getSize()/3;
        int rStart = i - i%third;
        int cStart = j - j%third;
        for(int r = rStart; r < (rStart + third); r++) {
            for(int c = cStart; c < (cStart + third); c++) {
                if(sudoku.grid[r][c] > 0)
                    domain = domain & ( ~(1 << (sudoku.grid[r][c] - 1)) );
            }
        }

        return domain;
    }

    static boolean areDomainsUnique(int[][] domains) {
        for(int i = 0; i < domains.length; i++) {
            for(int j = 0; j < domains[i].length; j++) {
                if(Integer.bitCount(domains[i][j]) != 1)
                    return false;
            }
        }

        return true;
    }

    static int[][] deepCopyDomains(int[][] domains) {
        int[][] newDomains = new int[domains.length][];

        for(int i = 0; i < domains.length; i++) {
            newDomains[i] = Arrays.copyOf(domains[i], domains[i].length);
        }

        return newDomains;
    }

    static boolean updateDomains (Sudoku s, int[][] domains, int val, int i, int j) { // smart

        // check if this assignment will reduce some other peer cell's domain to zero
        // i.e. if another peer cell  has the same val

        // rows
        for(int r = 0; r < s.getSize(); r++) {
            if(r != i) {
                if(s.grid[r][j] == val)
                    return false;
            }
        }

        // cols
        for(int c = 0; c < s.getSize(); c++) {
            if(c != j) {
                if(s.grid[i][c] == val)
                    return false;
            }
        }

        // box
        int third = s.getSize()/3;
        int rStart = i - i%third;
        int cStart = j - j%third;
        for(int r = rStart; r < (rStart + third); r++) {
            for(int c = cStart; c < (cStart + third); c++) {
                if(!(r == i && c == j)) {
                    if(s.grid[r][c] == val)
                        return false;
                }
            }
        }

        setDomainAt(domains, val, i, j); // dumb assigner
        reducePeersDomainsAt(domains, val, i, j); // dumb assigner

        return true;
    }

    static void setDomainAt(int[][] domains, int val, int i, int j) {
        domains[i][j] = ( 1 << (val - 1));
    }

    static void reducePeersDomainsAt(int[][] domains, int val, int i, int j) {
        // rows
        for(int r = 0; r < domains.length; r++) {
            if(r != i) {
                domains[r][j] &= ( ~(1 << (val - 1)) );
            }
        }

        // cols
        for(int c = 0; c < domains[i].length; c++) {
            if(c != j) {
                domains[i][c] &= ( ~(1 << (val - 1)) );
            }
        }

        // box
        int third = domains.length/3;
        int rStart = i - i%third;
        int cStart = j - j%third;
        for(int r = rStart; r < (rStart + third); r++) {
            for(int c = cStart; c < (cStart + third); c++) {
                if(!(r == i && c == j)) {
                    domains[r][c] &= ( ~(1 << (val - 1)) );
                }
            }
        }
    }

    static void fillRemainingGrid(Sudoku s, int[][] domains) {
        for(int i = 0; i < domains.length; i++) {
            for(int j = 0; j < domains[i].length; j++) {
                if(s.grid[i][j] == 0) {
                    int val = 1;
                    while( val <= domains.length && domains[i][j] != (1 << (val-1)) ) {
                        val++;
                    }
                    s.grid[i][j] = val;
                }
            }
        }
    }
}
