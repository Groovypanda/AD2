package dominantie_experiment.specialgraphs;

import graph.Graph;
import graph.Node;

/**
 * A graph used as counterexample for many optimistic predictions.
 */
public class PetersonGraph extends Graph {
    /**
     * Constructor for a graph. It initiates the internal data structures.
     */
    public PetersonGraph() {
        super(10, 15);
        Node[] nodes = new Node[getNodes().length];
        for(int i =0; i < nodes.length; i++){
            nodes[i] = new Node(i+1);
        }
        //first create the graph.
        for(int i=0; i < 5; i++){
            /**
             * 1, 4, 6
             * 2, 5, 7
             * 3, 1 ,
             * 4, 2
             * 5, 3
             */
            addEdge(nodes[i], i+1);
            addEdge(nodes[i], (i+3)%5+1);
            addEdge(nodes[i], i+6);
        }
        addEdge(nodes[5], 15);
        addEdge(nodes[5], 11);
        addEdge(nodes[5], 6);
        for(int i=6; i < nodes.length; i++){
            addEdge(nodes[i], i+1);
            addEdge(nodes[i], i+5);
            addEdge(nodes[i], i+6);
        }
        for(int i=0; i<nodes.length; i++){
            addNode(nodes[i]);
        }
    }
}
