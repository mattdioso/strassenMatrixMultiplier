public class strassenMatrixMultiplier {
	static int LEAF_SIZE=1;

	public static int [][] squareMatrixMultiply(int [][] A, int [][] B) {
		int n=A.length;
		int [][] C = new int[n][n];
		for (int i=0; i<n; i++) {
			for (int k=0; k<n; k++) {
				for (int j=0; j<n; j++)
					C[i][j]=A[i][k] + B[k][j];
			}
		}
		return C;
	}

	public static int [][] addMatrices(int [][] A, int [][] B) {
		int [][] C = new int[A.length][A.length];
		for (int i=0; i<A.length-1; i++) {
			for (int j=0; j<A.length-1; j++)
				C[i][j] = A[i][j]+B[i][j];
		}
		return C;
	}

	public static int [][] subtractMatrices(int [][] A, int [][] B) {
		int [][] C = new int[A.length][A.length];
		for (int i=0; i<A.length-1; i++) {
			for (int j=0; j<A.length-1; j++)
				C[i][j]=A[i][j]-B[i][j];
		}
		return C;
	}

	public int [][] scalarMultipleMatrix(int scale, int [][] m) {
		int [][] C = new int[m.length][m.length];
		for (int i=0; i<m.length-1; i++) {
			for (int j=0; j<m.length-1; j++)
				C[i][j]=scale*m[i][j];
		}
		return C;
	}

	private static int nextPowerOfTwo(int n) {
		int log2 = (int) Math.ceil(Math.log(n)/ Math.log(2));
		return (int) Math.pow(2, log2);
	}

	public static int [][] strassen(int [][] A, int [][] B) {
		int n = A.length;
		int m = nextPowerOfTwo(n);
		int [][] newA = new int[m][m];
		int [][] newB = new int[m][m];

		for (int i =0; i<n; i++) {
			for (int j =0; j<n; j++) {
				newA[i][j]=A[i][j];
				newB[i][j]=B[i][j];
			}
		}

		int [][] Cprep = strassenRecurse(newA, newB);
		int [][] C = new int[n][n];
		for (int i =0; i<n; i++) {
			for (int j=0; j<n; j++) {
				C[i][j] = Cprep[i][j];
			}
		}

		return C;
	}

	public static int [][] strassenRecurse(int [][] A, int [][] B) {
		int n = A.length;
		if (n<=LEAF_SIZE)
			return squareMatrixMultiply(A, B);
		else {
			int new_size= n/2;

			int [][] a11 = new int[new_size][new_size];
			int [][] a12 = new int[new_size][new_size];
			int [][] a21 = new int[new_size][new_size];
			int [][] a22 = new int[new_size][new_size];
			
			int [][] b11 = new int[new_size][new_size];
			int [][] b12 = new int[new_size][new_size];
			int [][] b21 = new int[new_size][new_size];
			int [][] b22 = new int[new_size][new_size];

			int [][] aResult = new int[new_size][new_size];
			int [][] bResult = new int[new_size][new_size];

			for (int i=0; i<new_size; i++) {
				for (int j=0; j<new_size;j++) {
					a11[i][j] = A[i][j];
					a12[i][j] = A[i+new_size][j];
					a21[i][j] = A[i][j+new_size];
					a22[i][j] = A[i+new_size][j+new_size];

					b11[i][j] = B[i][j];
					b12[i][j] = B[i+new_size][j];
					b21[i][j] = B[i][j+new_size];
					b22[i][j] = B[i+new_size][j+new_size];
				}
			}

			bResult = subtractMatrices(b12, b22);
			int [][] p1 = strassenRecurse(a11, bResult);

			aResult = addMatrices(a11, a12);
			int [][] p2 = strassenRecurse(aResult, b22);

			aResult = addMatrices(a21, a22);
			int [][] p3 = strassenRecurse(aResult, b11);

			bResult = subtractMatrices(b21, b12);
			int [][] p4 = strassenRecurse(a22, bResult);

			aResult = addMatrices(a11, a22);
			bResult = addMatrices(b11, b22);
			int [][] p5 = strassenRecurse(aResult, bResult);

			aResult = subtractMatrices(a12, a22);
			bResult = addMatrices(b21, b22);
			int [][] p6 = strassenRecurse(aResult, bResult);

			aResult = subtractMatrices(a11, a21);
			bResult = addMatrices(b11, b12);
			int [][] p7 = strassenRecurse(aResult, bResult);

			aResult = addMatrices(p5, p4);
			bResult = addMatrices(p2, p6);
			int [][] c11 = subtractMatrices(aResult, bResult);

			int [][] c12 = addMatrices(p1, p2);

			int [][] c21 = addMatrices(p3, p4);

			aResult = addMatrices(p5, p1);
			bResult = subtractMatrices(p3, p7);
			int [][] c22 = subtractMatrices(aResult, bResult);

			int [][] C = new int[n][n];

			for (int i=0; i<new_size; i++) {
				for (int j=0; j<new_size; j++) {
					C[i][j] = c11[i][j];
					C[i][j+new_size] = c12[i][j];
					C[i+new_size][j] = c21[i][j];
					C[i+new_size][j+new_size] = c22[i][j];
				}
			}

			return C;
		}
	}
}