/**
 * Name: Antriksh Sharma
 * User: as
 * Date: 1/16/2014
 * Time: 2:13 AM
 * Purpose: <Insert here>
 */
public class Sudoku {
    private int size;
    int[][] grid; // package private

    Sudoku (String raw, int size) {
        this.size = size;
        this.grid = new int[size][size];

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if('.' == raw.charAt(i*size + j))
                    grid[i][j] = 0;
                else
                    grid[i][j] = (raw.charAt(i*size + j) - '0');
            }
        }
    }

    public void print() {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if(grid[i][j] == 0)
                    System.out.print("_ ");
                else
                    System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int getSize() {
        return size;
    }

}
