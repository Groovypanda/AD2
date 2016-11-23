package elements;
/**
 * The class is a simple blueprint for all edges.
 * It contains some simple methods for representing an edge, getting the neighbour of a node and adding nodes.
 * @author Jarre Knockaert
 */
public class Edge {
    private int number;
    private Node[] nodes; //An array of the endpoints of the edge. Every edge has 2 endpoints.
    private int[] edgeIndex; //Displays for each node the index of this edge in the edgeList of that node.
    private boolean visited; //Tells if a node has been visited.
    private Plane[] adjacentPlanes; //2 adjacent planes of an edge.

    /**
     * A constructor for an edge. It creates an empty array of 2 nodes, the endpoints of the edge.
     * @param number A unique number to identify the edge.
     */
    public Edge(int number){
        this.number = number;
        nodes = new Node[2];
        edgeIndex = new int[2];
        adjacentPlanes = new Plane[2];
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

    /**
     * Gives the index of a node in the array of nodes in this edge.
     * @param node The node of which the index should be returned
     * @return The index of the given node in the array of nodes of this edge.
     */
    private int getNodeIndex(Node node){
        if(nodes[0].equals(node)){
            return 0;
        }
        else {
            return 1;
        }
    }

    /**
     * Saves the index of this edge in the list of edges in the node object. This function is only necessary for the second algorithm.
     * @param node The node with this edge
     * @param edgeIndex The index of this edge in the list of nodes of the given node
     */
    public void setEdgeIndex(Node node, int edgeIndex) {
        this.edgeIndex[getNodeIndex(node)] = edgeIndex;
    }

    //-1 if previous, 1 if next
    private Edge getAdjacentEdge(Node node, int next){
        int index = edgeIndex[getNodeIndex(node)]; //Get the index of this node in the list of the edges of that node
        int size = node.getEdges().size();
        int adjacentEdgeIndex = (index + next) % size; //Next edge has this index increment with one.
        if(adjacentEdgeIndex<0){
            adjacentEdgeIndex+=size;
        }
        return node.getEdges().get(adjacentEdgeIndex);
    }

    public Edge getNextEdge(Node node){
        return getAdjacentEdge(node, 1);
    }

    public Edge getPreviousEdge(Node node){
        return getAdjacentEdge(node, -1);
    }

    public void visit() {
        visited = true;
        for(Node node: nodes){
            node.setVisited(true);
        }
    }

    public Node[] getNodes() {
        return nodes;
    }

    public boolean isVisited() {
        return visited;
    }

    public Node getCommonNode(Edge other){
        for(Node node: nodes){
            for(Node otherNode: other.getNodes()){
                if(node.equals(otherNode)){
                    return node;
                }
            }
        }
        return null;
    }

    /**
     * Adds an adjacent plane if a plane can be added.
     * @param plane The plane to be added.
     */
    public void addPlane(Plane plane){
        if(adjacentPlanes[0]==null){
            adjacentPlanes[0] = plane;
        }
        else {
            adjacentPlanes[1] = plane;
        }
    }

    /**
     * @return If a plane can be added to the adjacentplanes.
     */
    public boolean isFull(){
        return adjacentPlanes[1] != null;
    }

    public Plane[] getAdjacentPlanes() {
        return adjacentPlanes;
    }

    public void setVisted(boolean visited) { this.visited = visited; }

    public Node getOtherNode(Node node) {
        return node.equals(nodes[0]) ? nodes[1] : nodes[0];
    }

}
