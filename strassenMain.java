import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class strassenMain {
	static List<ArrayList<ArrayList<Integer>>> read(File filename) {
		ArrayList<ArrayList<Integer>> A = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> B = new ArrayList<ArrayList<Integer>>();

		String thisLine;

		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));

			while ((thisLine=br.readLine()) != null) {
				if (thisLine.trim().equals("")) {
					break;
				} else {
					ArrayList<Integer> line = new ArrayList<Integer>();
					String [] lineArray = thisLine.split("\t");
					for (String num : lineArray) 
						line.add(Integer.parseInt(num));
					A.add(line);
				}
			}

			while ((thisLine = br.readLine())!= null) {
				if (thisLine.trim().equals("")) {
					break;
				} else {
					ArrayList<Integer> line = new ArrayList<Integer>();
					String [] lineArray = thisLine.split("\t");
					for (String num : lineArray) 
						line.add(Integer.parseInt(num));
					B.add(line);
				}
			}

			br.close();
		} catch (IOException ioe) {
			System.err.println(ioe);
		}

		List<ArrayList<ArrayList<Integer>>> result = new LinkedList<ArrayList<ArrayList<Integer>>>();
		result.add(A);
		result.add(B);
		return result;
	}

	public static void printMatrix(int [][] C) {
		for (int i=0; i<C.length; i++) {
			for (int j=0; j<C.length; j++)
				System.out.print(C[i][j]);
			System.out.println("");
		}
	}

	public static void main(String [] args) {
		List<ArrayList<ArrayList<Integer>>> matrices = read(new File(args[1]));
		int [][] A = ArrayList.toArray(matrices.get(0));
		int [][] B = ArrayList.toArray(matrices.get(1));
		int [][] C = strassenMatrixMultiplier.strassen(A, B);
		printMatrix(C);
	}
}