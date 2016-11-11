import graph.Cycle;
import graph.Edge;
import graph.Graph;
import graph.Node;
import gretig.HamiltonianCycleCalculator;
import input.BinaryFileReader;

import java.io.IOException;
import java.util.List;

/**
 * A class purely used for testing purposes.
 * It basically contains a main function and a few help methods.
 * @author Jarre Knockaert
 */
public class Main {
    static int i = 0;
    public static void main(String[] args) {
        List<Graph> graphs = readGraphs("testset/triang5.sec");
        for(Graph g: graphs){
            HamiltonianCycleCalculator calculator = new HamiltonianCycleCalculator(g);
            //System.out.println("===========");
            //showGraph(g);
            //System.out.println("===========");
            Cycle cycle = calculator.getCycle();
            int i =0;
            for(Edge edge: cycle.getEdges()){
                i++;
                //System.out.println(edge);
            }
            System.out.println("------------");
            System.out.println("Size: " + i);
            System.out.println("Graph size: " + g.getNodes().length);
        }
    }

    public static List<Graph> readGraphs(String filename) {
        BinaryFileReader bfr = new BinaryFileReader();
        try {
            return bfr.readByteArray(bfr.readBinaryFile(filename));
        } catch (IOException e) {
            System.out.println("Failed reading the given file.");
            return null;
        }

    }

    public static void showGraph(Graph graph){
        for(Node n: graph.getNodes()){
            System.out.println(n);
        }
    }
}
