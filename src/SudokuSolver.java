import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SudokuSolver {

	
	public static void main(String[] args) throws IOException {
		
		long startTime = System.currentTimeMillis();

		FileWriter writer = new FileWriter(args[1]);
		String newLine = System.getProperty("line.separator");
		
		 int[][] puzzle = readPuzzle(args);

	        if (solvePuzzle(0,0,puzzle))  {  
	        	writePuzzle(puzzle, writer, newLine);
	        }
	        else
	            System.out.println("Unable to find a solution");
	        
			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			writer.close();	        

	}
	
	static boolean solvePuzzle(int i, int j, int[][] grid1) {
        if (i == 9) {
            i = 0;
            if (++j == 9)
                return true;
        }
        if (grid1[i][j] != 0)  // skip filled cells
            return solvePuzzle(i+1,j,grid1);

        for (int value = 1; value <= 9; ++value) {
            if (check(i,j,grid1,value)) {
                grid1[i][j] = value;
                if (solvePuzzle(i+1,j,grid1))
                    return true;
            }
        }
        grid1[i][j] = 0; 
        return false;
    }

    static boolean check(int i, int j,int[][] grid2, int value1) {
        
    	//check row
    	for (int k = 0; k < 9; ++k)  
            if (value1 == grid2[k][j])
                return false;

    	//check col
        for (int k = 0; k < 9; ++k) 
            if (value1 == grid2[i][k])
                return false;

        //check box, multiples of 3
        int rosPos = (i / 3)*3;
        int colPos = (j / 3)*3;
        for (int k = 0; k < 3; ++k) 
            for (int m = 0; m < 3; ++m)
                if (value1 == grid2[rosPos+k][colPos+m])
                    return false;

        return true;
    }

	
static int[][] readPuzzle(String[] args) throws FileNotFoundException {
	int number = 0;
	char k = 'x'; //bad, change
	String line = null;
	 int[][] inputPuzzle = new int[9][9];
    	
    	//read in line by line, characters one by one and assign to matrix
    	    if(args.length < 1) {
    	        System.out.println("Incorrect input parameters");
    		System.exit(1);
    	    }
    	   
    	    Scanner reader = new Scanner(new FileInputStream(args[0]));
    	    while(reader.hasNextLine()){
    	    	line = reader.nextLine();
    	    	line = line.replaceAll("\\s", "");
    	    	for(int j = 0;j<9;){
    	    	for(int i=0; i<9; i++){    	    		
    	    		k = line.charAt(i);
    	    		  if(k == '-') {
    	    			  number = 0;   	    		  	    		  
    	    		  inputPuzzle[j][i] = number;  
    	    		  } 
    	    		  else{
    	    			  number = Character.getNumericValue(k);
    	    		  inputPuzzle[j][i] = number;    	    		  
    	    		  }
    	    	} 
    	    	j++;   
    	    	if(j < 9){
    	    		line = reader.nextLine();
        	    	line = line.replaceAll("\\s", "");	
    	    	}    	    	
    	    	}
    	    }
    	    return inputPuzzle;
    	     	    
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


