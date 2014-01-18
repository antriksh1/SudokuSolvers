/**
 * Name: Antriksh Sharma
 * User: as
 * Date: 1/16/2014
 * Time: 2:11 AM
 * Purpose: main() which drives the Sudoku Solving
 */

public class Driver {

    public static void main (String[] args) {
        SudokuParser parsed = new SudokuParser("hard.txt");

        for(String s : parsed.getStringsList()) {
            Sudoku puzzle = new Sudoku(s, parsed.getSize());

            System.out.println("Unsolved:");
            puzzle.print();

            //Solver solver = new SimpleBacktrackingSolver();
            //Solver solver = new ReducedDomainBacktrackingSolver();
            Solver solver = new ConstraintPropagationBacktrackingSolver();

            if(solver.solve(puzzle)) {
                System.out.println("Solved:");
                puzzle.print();
            } else {
                System.out.println("No Solution Found");
                puzzle.print();
            }

            if(Checker.check(puzzle)) {
                System.out.println(">> PASS");
            } else {
                System.out.println(">> FAIL");
                break;
            }

            System.out.println();
        }

    }
}
