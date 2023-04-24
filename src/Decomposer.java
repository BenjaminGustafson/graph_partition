import org.jgrapht.graph.*;

import java.util.*;

public final class Decomposer {

    ArrayList<ArrayList<ArrayList<Integer>>> solutions;
    SimpleGraph<Integer, DefaultEdge> g;
    int copies;
    int n;
    int e;
    int m;
    boolean done;
    int runCount;

    public Decomposer(SimpleGraph<Integer,DefaultEdge> g, int m){
        this.g = g;
        this.m = m;
        solutions = new ArrayList<>();
        n = g.vertexSet().size();
        e = g.edgeSet().size();
        copies = (n*(n-1)/2) * m / e;

        done = false;
        runCount = 0;
    }
    private static int gcd(int a, int b) {
        if (b==0) return a;
        return gcd(b,a%b);
    }

    void partition(){
        int[][] matrix = new int[copies][n];
        int[][] dmatrix = new int[copies][n];
        ArrayList<Integer> unusedIs = new ArrayList<>();
        Multigraph<Integer, DefaultEdge> h = new Multigraph<>(DefaultEdge.class);
        for (int i =0; i < n; i ++) {
            matrix[0][i] = i;
            dmatrix[0][i] = g.degreeOf(i);
            unusedIs.add(i);
            h.addVertex(i);
        }
        for (DefaultEdge e : g.edgeSet()){
            h.addEdge(g.getEdgeSource(e), g.getEdgeTarget(e));
        }
        partitionHelper(h, matrix, dmatrix,1, 0, unusedIs);
        System.out.println("No solutions found.");
        System.out.println(runCount + " function calls");
    }


    // Sorting to avoid symmetry
    // Not allowed to have indices decreasing
    // 4 1 2 3
    // 2 1 3 4
    // Or
    // 2 3 1 4
    // 2 1 4 3
    // When should this be checked?

    private void partitionHelper(Multigraph<Integer,DefaultEdge> mkn, int[][] matrix, int[][] dmatrix, int i, int j, ArrayList<Integer> unusedIndices){
        //          System.out.println("_______________________________");
//          System.out.println("i " + i + " j " + j);
//          for (int r = 0; r < copies; r++) {
//              for (int c = 0; c < n; c++) {
//                  System.out.print(matrix[r][c] + "\t");
//              }
//              System.out.print("\n");
//          }
//          System.out.println("degree matrix");
//          for (int r = 0; r < copies; r++) {
//              for (int c = 0; c < n; c++) {
//                  System.out.print(dmatrix[r][c] + "\t");
//              }
//              System.out.print("\n");
//          }
//          System.out.println("unused " + unusedIndices.toString());
//          System.out.println(" mkn " + mkn.toString());

        if (done){
            return;
        }
        runCount ++;
        if (runCount % 1000000 == 0){
            System.out.println(runCount);
        }

        // Check that the vertex degrees do not exceed m(n-1)
        int columnSum = 0;
        for (int row = 0; row <= i && row < copies; row++){
            columnSum += dmatrix[row][(j-1+n)%n];
        }
        //System.out.println("Column sum " + columnSum);
        if (columnSum > m * (n-1)){// || (columnSum == m*(n-1) && i < copies-1)) {
            //System.out.println("column sum " + columnSum + " too large ");
            return;
        }

        //Check that the edges all fit in mKn
        //this only needs to be checked when we finish a row in the matrix
        if (j == 0 && i > 1) {
            //System.out.println("Checking edges");
            boolean complete = true;
            for (int a = 0; a < n; a++) {
                for (int b = a + 1; b < n; b++) {
                    int edgeCount = mkn.getAllEdges(a, b).size();
                    if (edgeCount > m) {
                        //System.out.println("Too many edges (" + a + ", " + b + ") " + edgeCount + " > " + m);
                        return;
                    } else if (edgeCount < m) {
                        complete = false;
                    }
                }
            }

            if (complete){
                for (int r = 0; r < copies; r++) {
                    for (int c = 0; c < n; c++) {
                        System.out.print(matrix[r][c] + "\t");
                    }
                    System.out.print("\n");
                }
                System.out.println("graph "  + mkn.toString());
                System.out.println("SUCCESS!");
                done = true;
                return;
            }
        }

        // Error case
        if (i >= copies) {
            System.out.println("Error: finished without success or failure");
            for (int r = 0; r < copies; r++) {
                for (int c = 0; c < n; c++) {
                    System.out.print(matrix[r][c] + "\t");
                }
                System.out.print("\n");
            }
            System.out.println(" mkn " + mkn.toString());
            for (int a = 0; a < n; a++){
                for (int b = 0; b < n; b++){
                    int edgeCount = mkn.getAllEdges(a,b).size();
                    System.out.println("edge count (" + a + ", " + b + ") " + edgeCount);
                }
            }
            return;
        }


        for (int k = 0; k < unusedIndices.size(); k++){
            int [][] newMatrix = new int[copies][n];
            int [][] newDMatrix = new int[copies][n];
            for (int x = 0; x < copies; x++){
                newMatrix[x] = matrix[x].clone();
                newDMatrix[x] = dmatrix[x].clone();
            }
            ArrayList<Integer> newUnused = new ArrayList<>(unusedIndices);
            Multigraph<Integer,DefaultEdge> newMkn = (Multigraph<Integer, DefaultEdge>) mkn.clone();
            newMatrix[i][j] = newUnused.remove(k);
            newDMatrix[i][j] = dmatrix[0][newMatrix[i][j]];
            int newJ = j + 1;
            int newI = i;
            if (newJ >= n){
                for (DefaultEdge e : g.edgeSet()){
                    newMkn.addEdge(newMatrix[i][g.getEdgeSource(e)], newMatrix[i][g.getEdgeTarget(e)]);
                }
                newJ = 0;
                newI ++;
                newUnused = new ArrayList<>();
                for (int x = 0; x < n; x ++)
                    newUnused.add(x);
            }
            partitionHelper(newMkn, newMatrix, newDMatrix, newI, newJ, newUnused);
        }
    }


}
