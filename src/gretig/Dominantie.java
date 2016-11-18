package gretig;

import datastructures.Graph;
import elements.Node;

import input.BinaryInputReader;

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
        BinaryInputReader reader = new BinaryInputReader(); //An object for processing the standard input.
        try {
            List<Graph> graphs = reader.readByteArray(); //The SEC is read and transformed into a list of graphs.
            for(Graph graph: graphs){
                //Create an object which can compute the dominant set.
                DominatingSetCalculator setCalculator = new DominatingSetCalculator(graph);
                //Computes an approximation of the dominant list of the given elements.
                List<Node> dominantList = setCalculator.getDominantList();
                //int nodesSize = elements.getNodes().length;
                //int dominantlistSize = dominantList.planeAmount();
                //System.out.print(dominantlistSize + "/" + nodesSize + " (" + ((double)dominantlistSize/(double)nodesSize)*100 + "%): ");
                for(Node node: dominantList){ //Print nodes to stdout
                    System.out.print(node.getNumber());
                    System.out.print(" ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            System.err.println("Couldn't read the given input.");
        }
    }

}

