import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.Multigraph;
import org.jgrapht.graph.SimpleGraph;

import java.util.ArrayList;

public class Main {

    public static void main (String[] args){
        //ArrayList<Integer> degrees = degreeVector(g);
        //System.out.println("g's degrees " + degrees);
        //int d = degrees.stream().reduce(0, Decomposition::gcd);
        Decomposer d = new Decomposer(MyGraphs.cricket(),1);
        d.partition();
        //System.out.println(MyGraphs.nPan(3).toString());
    }

    private static Multigraph<Integer, DefaultEdge> createMkn(int m, int n)
    {
        Multigraph<Integer, DefaultEdge> mkn = new Multigraph<>(DefaultEdge.class);

        for (int i = 0; i < n; i++){
            mkn.addVertex(i);
        }

        for (int i = 0; i < n; i++){
            for (int j = i+1; j < n; j++){
                for (int k = 0; k < m; k++) {
                    mkn.addEdge(i,j);
                }
            }
        }

        return mkn;
    }

    private static ArrayList<Integer> degreeVector(SimpleGraph<Integer, DefaultEdge> g){
        ArrayList<Integer> degrees = new ArrayList<Integer>();
        for (int v : g.vertexSet()){
            degrees.add(g.degreeOf(v));
        }
        return degrees;
    }

}
