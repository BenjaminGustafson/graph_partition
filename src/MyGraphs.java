import org.jgrapht.graph.*;
import org.jgrapht.graph.DefaultEdge;


public class MyGraphs {


    public static SimpleGraph<Integer, DefaultEdge> nPan (int n) {
        SimpleGraph<Integer, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
        //  0-1 is the handle, 1-n is the pan
        for (int i = 0; i <= n; i++) {
            g.addVertex(i);
        }
        for (int i = 0; i < n; i++) {
            g.addEdge(i,i+1);
        }
        g.addEdge(n,1);
        return g;
    }

    static SimpleGraph<Integer, DefaultEdge> nStar (int n) {
        SimpleGraph<Integer, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
        // 0 is the middle
        g.addVertex(0);
        for (int i = 1; i <= n; i++) {
            g.addVertex(i);
            g.addEdge(0,i);
        }
        return g;
    }


    public static SimpleGraph<Integer, DefaultEdge> chair () {
        return arrayToGraph(5, new int[][]{
                {0,1},{0,2},{0,3},{3,4}
                }
        );
    }

    public static SimpleGraph<Integer, DefaultEdge> butterfly () {
        return arrayToGraph(5, new int[][]{
                        {0,1},{0,2},{0,3},{0,4},{1,2},{3,4}
                }
        );
    }

    public static SimpleGraph<Integer, DefaultEdge> dart () {
        return arrayToGraph(5, new int[][]{
                {0,1},{1,2},{1,3},{1,4},{2,3},{3,4}
            }
        );
    }

    public static SimpleGraph<Integer, DefaultEdge> house () {
        return arrayToGraph(5, new int[][]{
                {0,1},{1,2},{2,3},{3,0},{0,4},{3,4}
            }
        );
    }

    public static SimpleGraph<Integer, DefaultEdge> bull () {
        return arrayToGraph(5, new int[][]{
                {0,1},{1,2},{2,0},{0,3},{1,4}
            }
        );
    }

    public static SimpleGraph<Integer, DefaultEdge> cricket () {
        return arrayToGraph(5, new int[][]{
                {0,1},{1,2},{2,0},{2,3},{2,4}
            }
        );
    }

    public static SimpleGraph<Integer, DefaultEdge> arrayToGraph (int n, int [][] arr){
        SimpleGraph<Integer, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
        for (int i = 0; i < n; i++){
            g.addVertex(i);
        }
        for (int [] edge : arr){
            g.addEdge(edge[0], edge[1]);
        }
        return g;
    }


}
