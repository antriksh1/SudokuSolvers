/**
 * Name: Antriksh Sharma
 * User: as
 * Date: 1/16/2014
 * Time: 7:20 PM
 * Purpose: Parses the text file with Sudoku puzzle
 */

import java.util.*;
import java.io.*;

public final class SudokuParser {

    String filename;
    int size;
    ArrayList<String> stringsList;

    // private c-tor, so it can't be instantiated
    public SudokuParser (String filename) {
        this.filename = filename;

        File inFile = new File(filename);

        stringsList = new ArrayList<String>();

        try {
            Scanner scanner = new Scanner(inFile);
            if(scanner.hasNextInt()) {
                size = scanner.nextInt();
            }
            if(scanner.hasNextLine()) {
                String emptyNewLine = scanner.nextLine();
            }
            while(scanner.hasNextLine()) {
                String s = scanner.nextLine();
                stringsList.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getSize() {
        return size;
    }

    public ArrayList<String> getStringsList() {
        return (this.stringsList);
    }
}
