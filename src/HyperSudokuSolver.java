import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class HyperSudokuSolver {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	static int numNodesExpanded = 0;
	
	public static void main(String[] args) throws IOException {
		
		FileWriter writer = new FileWriter(args[1]);
		String newLine = System.getProperty("line.separator");
		
		 int[][] puzzle = readPuzzle(args);

		         
	        
	        if (solvePuzzle(0,0,puzzle))    // solves in place
	        {

	        	writePuzzle(puzzle,writer,newLine);
	        }
	            
	        else
	            System.out.println("Unable to find a solution");

			  	writer.close();

	}
	
	static boolean solvePuzzle(int i, int j, int[][] grid1) {
        if (i == 9) {
            i = 0;
            if (++j == 9)
                return true;
        }
        if (grid1[i][j] != 0)  
            return solvePuzzle(i+1,j,grid1);

        for (int value = 1; value <= 9; ++value) {
            if (check(i,j,grid1,value)) {
                grid1[i][j] = value;
                numNodesExpanded++;
                if (solvePuzzle(i+1,j,grid1))
                    return true;
            }
        }
        grid1[i][j] = 0; 
        return false;
    }

    static boolean check(int i, int j,int[][] grid2, int value2) {
    	
    	Integer[] box1i = {1,2,3};
     	Integer[] box1j = {1,2,3};
    	Integer[] box2j = {5,6,7};
    	Integer[] box2i = {1,2,3};
    	Integer[] box3j = {1,2,3};
    	Integer[] box3i = {5,6,7};
    	Integer[] box4i = {5,6,7};
    	Integer[] box4j = {5,6,7};
      	   	
    	
        for (int k = 0; k < 9; ++k) 
            if (value2 == grid2[k][j])
                return false;

        for (int k = 0; k < 9; ++k)
            if (value2 == grid2[i][k])
                return false;

        int rowPos = (i / 3)*3;
        int colPos = (j / 3)*3;
        for (int k = 0; k < 3; ++k)
            for (int m = 0; m < 3; ++m)
                if (value2 == grid2[rowPos+k][colPos+m])
                    return false;
       
		if (Arrays.asList(box1i).contains(i)
				&& Arrays.asList(box1j).contains(j)) {
			
			rowPos = 1; // hyperbox 1
			colPos = 1;
			for (int k = 0; k < 3; ++k)
				for (int m = 0; m < 3; ++m)
					if (value2 == grid2[rowPos + k][colPos + m])
						return false;
		}
        
		else if (Arrays.asList(box2i).contains(i)
				&& Arrays.asList(box2j).contains(j)) {
			
			rowPos = 1; // hyperbox 2
			colPos = 5;
			for (int k = 0; k < 3; ++k)
				for (int m = 0; m < 3; ++m)
					if (value2 == grid2[rowPos + k][colPos + m])
						return false;
		}
        
		else if (Arrays.asList(box3i).contains(i)
				&& Arrays.asList(box3j).contains(j)) {

			rowPos = 5; // hyperbox 3
			colPos = 1;
			for (int k = 0; k < 3; ++k)
				for (int m = 0; m < 3; ++m)
					if (value2 == grid2[rowPos + k][colPos + m])
						return false;
		}

		else if (Arrays.asList(box4i).contains(i)
				&& Arrays.asList(box4j).contains(j)) {

			rowPos = 5; // hyperbox 4
			colPos = 5;
			for (int k = 0; k < 3; ++k)
				for (int m = 0; m < 3; ++m)
					if (value2 == grid2[rowPos + k][colPos + m])
						return false;
		}

        return true; 
    }

	
static int[][] readPuzzle(String[] args) throws FileNotFoundException {
	int number = 0;
	char k = 'x'; 
	String line = null;
	 int[][] inputPuzzle = new int[9][9];
    	
    	    if(args.length < 1) {
    	        System.out.println("Invalid input arguments");
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
