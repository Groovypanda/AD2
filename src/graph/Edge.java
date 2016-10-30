package graph;
/**
 * The class is a simple blueprint for all edges.
 * It contains some simple methods for representing an edge, getting the neighbour of a node and adding nodes.
 * @author Jarre Knockaert
 */
public class Edge {
    private int number;
    private Node nodes[]; //An array of the endpoints of the edge. Every edge has 2 endpoints.

    /**
     * A constructor for an edge. It creates an empty array of 2 nodes, the endpoints of the edge.
     * @param number A unique number to identify the edge.
     */
    public Edge(int number){
        this.number = number;
        nodes = new Node[2];
    }

    /**
     * A constructor for an edge with the given endpoints.
     * @param number A unique number to identify the edge.
     * @param endpoint1 The first endpoint of the edge.
     * @param endpoint2 The second endpoint of the edge.
     */
    public Edge(int number, Node endpoint1, Node endpoint2){
        this(number);
        addNode(endpoint1);
        addNode(endpoint2);
    }

    /**
     * This method adds the given node (endpoint) to this edge.
     * @param node An endpoint of the edge
     * @throws IndexOutOfBoundsException If more than 2 nodes are added.
     */
    public void addNode(Node node){
        if(nodes[1]!=null){
            throw new IndexOutOfBoundsException("An edge can only connect 2 endpoints.");
        }
        if(nodes[0]==null){
            nodes[0] = node;
        } else {
            nodes[1] = node;
        }
    }

    /**
     * @return The string representation of an edge.
     */
    public String toString(){
        return "Edge " + Integer.toString(number) + " (" + Integer.toString(nodes[0].getNumber()) + "," + Integer.toString(nodes[1].getNumber()) + ")";
    }

    /**
     * This function returns the neighbour of a certain node by following this edge.
     * @param v The node of which we want to find the neighbour by following this edge.
     * @return The neighbour of the node by following this edge.
     */
    public Node getNeighbour(Node v) {
        if(v.equals(nodes[0])){
            return nodes[1];
        }
        return nodes[0];
    }
}
