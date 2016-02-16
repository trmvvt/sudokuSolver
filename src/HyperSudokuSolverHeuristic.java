import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

class cell {
	int counter1;
	int counter2;

	cell(int a, int b) {
		counter1 = a;
		counter2 = b;
	}

}

public class HyperSudokuSolverHeuristic {

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	static int numNodesExpanded = 0;

	public static void main(String[] args) throws IOException {

		FileWriter writer = new FileWriter(args[1]);
		String newLine = System.getProperty("line.separator");
		
		int[][] puzzle = readPuzzle(args);

		if (solvePuzzle(puzzle)){
			writePuzzle(puzzle,writer,newLine);
		}			
		else
			System.out.println("Unable to solve puzzle");
		  	writer.close();
	}

	static cell minFinder(int[][] grid) {

		int mincounter_i = 0;
		int mincounter_j = 0;
		int counter = 0;
		int minRemVal = 0;
		int keeptrack = 0;


		for (int k = 0; k < 9; ++k){
			// row index
			for (int m = 0; m < 9; ++m) { // col index

				if (grid[k][m] == 0) {
					keeptrack++;
					for (int value = 1; value <= 9; ++value){
						if (check(k, m, value, grid)) {
							counter++;

						}
					}

					if (keeptrack == 1) {
						minRemVal = counter;
						mincounter_i = k;
						mincounter_j = m;
					}

					if (counter < minRemVal) {
						minRemVal = counter;
						mincounter_i = k;
						mincounter_j = m;
					}

					counter = 0;
					
				}
			}
		}
		return new cell(mincounter_i, mincounter_j);
	}

	static boolean solvePuzzle(int[][] grid1) {
		cell obj;
		int i = 0;
		int j = 0;
		int flag = 0;

		// do this in a loop as long as there are more zeros left
		// count zeros on initial loop itself
		obj = minFinder(grid1);
		i = obj.counter1;
		j = obj.counter2;


		for (int value = 1; value <= 9; ++value) {
			if (check(i, j, value, grid1)) {
				grid1[i][j] = value;
				numNodesExpanded++;

				if (solvePuzzle(grid1))
					return true;
			}
		}
		for (int m=0; m<9; ++m){
			for (int n=0; n<9; ++n){
				if(grid1[m][n] == 0)
					flag = 1;
			}
		}
		if(flag==1){
			grid1[i][j] = 0;
		    return false;}
		else{
			return true;
		}
			
	}

	static boolean check(int i, int j, int val, int[][] checkGrid) {

		Integer[] box1i = { 1, 2, 3 };
		Integer[] box1j = { 1, 2, 3 };
		Integer[] box2j = { 5, 6, 7 };
		Integer[] box2i = { 1, 2, 3 };
		Integer[] box3j = { 1, 2, 3 };
		Integer[] box3i = { 5, 6, 7 };
		Integer[] box4i = { 5, 6, 7 };
		Integer[] box4j = { 5, 6, 7 };

		for (int k = 0; k < 9; ++k)
			
			if (val == checkGrid[k][j])
				return false;

		for (int k = 0; k < 9; ++k)
		
			if (val == checkGrid[i][k])
				return false;

		int boxRowOffset = (i / 3) * 3;
		int boxColOffset = (j / 3) * 3;
		for (int k = 0; k < 3; ++k)
			
			for (int m = 0; m < 3; ++m)
				if (val == checkGrid[boxRowOffset + k][boxColOffset + m])
					return false;

		if (Arrays.asList(box1i).contains(i)
				&& Arrays.asList(box1j).contains(j)) {

			boxRowOffset = 1; // hyperbox 1
			boxColOffset = 1;
			for (int k = 0; k < 3; ++k)
				for (int m = 0; m < 3; ++m)
					if (val == checkGrid[boxRowOffset + k][boxColOffset + m])
						return false;
		}

		else if (Arrays.asList(box2i).contains(i)
				&& Arrays.asList(box2j).contains(j)) {

			boxRowOffset = 1; // hyperbox 2
			boxColOffset = 5;
			for (int k = 0; k < 3; ++k)
				for (int m = 0; m < 3; ++m)
					if (val == checkGrid[boxRowOffset + k][boxColOffset + m])
						return false;
		}

		else if (Arrays.asList(box3i).contains(i)
				&& Arrays.asList(box3j).contains(j)) {

			boxRowOffset = 5; // hyperbox 3
			boxColOffset = 1;
			for (int k = 0; k < 3; ++k)
				for (int m = 0; m < 3; ++m)
					if (val == checkGrid[boxRowOffset + k][boxColOffset + m])
						return false;
		}

		else if (Arrays.asList(box4i).contains(i)
				&& Arrays.asList(box4j).contains(j)) {

			boxRowOffset = 5; // hyperbox 4
			boxColOffset = 5;
			for (int k = 0; k < 3; ++k)
				for (int m = 0; m < 3; ++m)
					if (val == checkGrid[boxRowOffset + k][boxColOffset + m])
						return false;
		}

		return true;
	}

	static int[][] readPuzzle(String[] args) throws FileNotFoundException {
		int number = 0;
		char k = 'x'; //change
		String line = null;
		int[][] problem = new int[9][9];

		if (args.length < 1) {
			System.out.println("Invalid input arguments");
			System.exit(1);
		}

		Scanner reader = new Scanner(new FileInputStream(args[0]));
		while (reader.hasNextLine()) {
			line = reader.nextLine();
			line = line.replaceAll("\\s", "");
			for (int j = 0; j < 9;) {
				for (int i = 0; i < 9; i++) {
					k = line.charAt(i);
					if (k == '-') {
						number = 0;
						
						problem[j][i] = number;
					} else {
						number = Character.getNumericValue(k);
						problem[j][i] = number;
					}
				}
				j++;
				if (j < 9) {
					line = reader.nextLine();
					line = line.replaceAll("\\s", "");
				}
			}
		}
		return problem;

	}
	static void writePuzzle(int[][] solution, FileWriter f0, String newLine) throws IOException {

			for (int i = 0; i < 9; i++) {

				for (int j = 0; j < 9; j++) {

					f0.write(Integer.toString(solution[i][j]));
					f0.write(' ');
				}
				f0.write(newLine);

			}

		}
}
