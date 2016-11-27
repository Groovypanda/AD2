package gretig;

import graph.Graph;
import input.BinaryInputReader;

import java.io.IOException;
import java.util.List;

/**
 * This class can be used to print a hamiltonian cycle if one is found. The input should have the binary format
 * SIMPLE EDGE CODE. This SIMPLE EDGE CODE should contain one or more planar graphs.
 * @author Jarre Knockaert
 */
public class Hamilton {
    /**
     * Computes and prints a hamiltonian cycle in the given input graph. These graphs should be formatted as SEC.
     * If no hamiltonian cycle is found, "Geen cykel gevonden" will be printed.
     * @param args Specifies the program arguments, however these aren't used.
     */
    public static void main(String[] args) {
        BinaryInputReader reader = new BinaryInputReader(); //An object for processing the standard input.
        try {
            //Read the graph
            List<Graph> graphs = reader.readByteArray(); //The SEC is read and transformed into a list of graphs.
            for(Graph graph: graphs){
                HamiltonianCycleCalculator calculator = new HamiltonianCycleCalculator(graph);
                //Print the hamiltonian cycles.
                calculator.printCycle(calculator.calculateCycle());
            }
        } catch (IOException e) {
            System.err.println("Couldn't read the given input.");
        }
    }
}
