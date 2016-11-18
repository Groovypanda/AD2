import graph.*;
import gretig.HamiltonianCycleCalculator;
import input.BinaryFileReader;
import org.codehaus.groovy.runtime.powerassert.SourceText;

import java.io.IOException;
import java.util.List;

/**
 * A class purely used for testing purposes.
 * It basically contains a main function and a few help methods.
 * @author Jarre Knockaert
 */
public class Main {
    static int graphNumber = 0;
    public static void main(String[] args) {
        //Weird result if starting with edge 0?
        List<Graph> graphs = readGraphs("testset/triang10.sec");
        boolean extended = false;
        for(Graph g: graphs){
            //System.out.println("================ GRAPH " + ++graphNumber + " ================");
            HamiltonianCycleCalculator calculator = new HamiltonianCycleCalculator(g);
            //if(++graphNumber==4){
                Cycle cycle = calculator.getCycle();
                System.out.println("Size: " + cycle.getSize() + "/" + g.getNodes().length + " nodes");
                if(cycle.getSize()!=g.getNodes().length){
                    System.out.println("==== Incorrect ====");
                }
            //}

            //showGraph(g);
            //System.out.println("===========");
            //cycle.printCycle(extended);
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
