import datastructures.Graph;
import elements.*;
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
    static int graphNumber = 0;
    public static void main(String[] args) {
        //Weird result if starting with edge 0?
        List<Graph> graphs = readGraphs("klein/triang_alle_12.sec");
        for(Graph g: graphs){
            System.out.println("================ GRAPH " + ++graphNumber + " ================");
            HamiltonianCycleCalculator calculator = new HamiltonianCycleCalculator(g);
            System.out.println(calculator.i);
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
