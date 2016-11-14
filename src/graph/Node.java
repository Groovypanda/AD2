package graph;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is  a blueprint for the nodes.
 * It offers a list of instructions: adding adjacent edges, retrieving these edges, retrieving the degree,
 * visiting a node (marking the node as visited), checking if the node has been visited, visiting the neighbours,
 * retrieving an approximation of the coverage of a node (this is an upper bound for the coverage a certain node can offer to a graph)
 * and changing the coverage.
 * @author Jarre Knockaert
 */
public class Node {

    private List<Edge> edges; //List of adjacent edges.
    private int number; //Unique identifier for a node.
    /**
     * Visited indicates if a node a has been visited.
     * A node should be visited if he is part of the dominant set or a neighbour of a node
     * which is part of the dominant set.
     */
    private boolean visited = false;
    //An upper bound for the coverage of a graph a node can offer when it is added to the dominant set.
    private int coverage;

    /**
     * Constructor for a node, it initiates an empty list of adjacent edges.
     * @param number The unique identifier of a node.
     */
    public Node(int number){
        edges = new ArrayList<>();
        this.number = number;
        coverage = 1; //Every node covers itself, so the cover has to be atleast one.
    }

    /**
     * Adds an edge to the list of the adjacent edges. This node should be an endpoint of the given edge.
     * @param edge An edge which contains this node as endpoint.
     */
    public void addEdge(Edge edge){
        edges.add(edge);
        edge.setEdgeIndex(this, edges.size()-1);
        coverage++; //Every adjacent edge indicates a neighbour and thus a higher coverage.
    }

    /**
     * @return An upperbound of the coverage a certain node can offer in a graph.
     */
    public int getCoverage(){
        return coverage;
    };

    /**
     * Returns the actual coverage for a certain node.
     * @return
     */
    public int getActualCoverage(){
        int coverage = 0;
        for(Edge edge: edges){
            Node w = edge.getNeighbour(this);
            if(!w.visited()){
                coverage++;
            }
        }
        if(!visited){
            coverage++;
        }
        return coverage;
    }

    /**
     * Decrement the coverage with one. This method should be called when a neighbour was added to the dominating set.
     */
    public void decrementCoverage(){
        coverage--;
    }

    /**
     * Clear the coverage of this node. This method should be called when this node was added to the dominating set.
     */
    public void clearCoverage(){
        coverage = 0;
    }

    /**
     * @return A unique identifier to this node.
     */
    public int getNumber(){
        return number;
    }

    /**
     * @return A list of adjacent edges.
     */
    public List<Edge> getEdges() {
        return edges;
    }

    /**
     * @return The degree (amount of adjacent edges) of this node.
     */
    public int getDegree() { return edges.size(); }

    /**
     * Visits a node if it hasn't been visited before and sets visited to true.
     * This method should be called if this node or a neighbour is added to the dominant set.
     * @return Returns 1 if this node has been visited.
     */
    public int visit() {
        if(!visited()){
            visited = true;
            return 1;
        }
        return 0;
    }

    /**
     * Calls the visit method on all neighbours
     * @return Amount of nodes which hadn't been visited yet.
     */
    public int visitNeighbours(){
        int coverage = 0;
        for (Edge edge : getEdges()) {
            Node neighbour = edge.getNeighbour(this);
            neighbour.decrementCoverage();
            coverage+=neighbour.visit();
        }
        return coverage;
    }

    /**
     * @return True if the node has been visited, false otherwise.
     */
    public boolean visited() {
        return visited;
    }

    /**
     * @return A representation of the node as string.
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Node ");
        sb.append(number);
        sb.append(" (");
        sb.append(getDegree());
        sb.append("): [");
        for(int i = 0; i< edges.size()-1; i++){
            sb.append(edges.get(i));
            sb.append(", ");
        }
        sb.append(edges.get(edges.size()-1));
        sb.append("]");
        return sb.toString();
    }

    /**
     * Compares this object with another object, they're equal if their identifier (number) is equal.
     * @param o The object which has to compared with.
     * @return If the objects are equal.
     */
    public boolean equals(Object o){
        return o instanceof Node && ((Node) o).getNumber() == this.getNumber();
    }

    /**
     * Resets a node to initialconditions.
     */
    public void reset(){
        visited = false;
        coverage = edges.size()+1;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
