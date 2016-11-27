package gretig;

import gretig.graph.Graph;
import gretig.graph.Node;

import gretig.calculators.DominatingSetCalculator;
import gretig.input.BinaryInputReader;

import java.io.IOException;
import java.util.List;

/**
 * This class can be used to print an approximation of the minimal dominating set for a given input for each of the
 * given graphs.The input should have the binary format SIMPLE EDGE CODE. This SIMPLE EDGE CODE should contain one or more
 * planar graphs.
 * @author Jarre Knockaert
 */
public class Dominantie {

    /**
     * Computes and prints the nodes of an approximation of the minimal dominating set for all of the given
     * graphs which are specified in the standard input. These graphs should be formatted as SEC.
     * @param args Specifies the program arguments, however these aren't used.
     */
    public static void main(String[] args) {
        BinaryInputReader reader = new BinaryInputReader(); //An object for processing the standard 1
        try {
            List<Graph> graphs = reader.readByteArray(); //The SEC is read and transformed into a list of graphs.
            for(Graph graph: graphs){
                //Create an object which can compute the dominant set.
                DominatingSetCalculator setCalculator = new DominatingSetCalculator(graph);
                //Computes an approximation of the dominant list of the given 1
                List<Node> dominantList = setCalculator.getDominantList();
                for(Node node: dominantList){ //Print nodes to stdout
                    System.out.print(node.getNumber());
                    System.out.print(" ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            System.err.println("Couldn't read the given gretig.input.");
        }
    }

}

