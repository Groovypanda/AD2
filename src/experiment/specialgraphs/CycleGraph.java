package experiment.specialgraphs;

import graph.Edge;
import graph.Graph;
import graph.Node;

/**
 * This is a graph in which every node has 2 edges and the graph consists of one cycle.
 */
public class CycleGraph extends Graph {
    /**
     * Constructor for a cycle graph.
     *
     * @param nodeAmount Total amount of nodes
     */
    public CycleGraph(int nodeAmount) {
        super(nodeAmount, nodeAmount);
        Node[] nodes = new Node[nodeAmount];
        nodes[0]=new Node(1);
        addEdge(nodes[0], 1);
        addEdge(nodes[0], nodeAmount);
        for(int i=1; i<nodeAmount; i++){
            nodes[i]=new Node(i+1);
            addEdge(nodes[i], i+1);
            addEdge(nodes[i], i);
        }
        for(int i=0; i<nodeAmount; i++){
            addNode(nodes[i]);
        }
    }
}

