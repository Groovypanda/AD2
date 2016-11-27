package gretig.graph;

import gretig.dualgraph.PlaneNode;

/**
 * Represents a plane in a planar triangulation. A plane is a node in the dual graph of this graph.
 * Every plane contains a node, this node is the corresponding node of this plane in the dual graph.
 * Note: All operations in a plane are constant.
 */
public class Plane {
    private PlaneNode node; //The node of this plane in the dual graph.
    private Plane[] neighbours; //The adjacent plane, 2 planes are adjacent if they have a common edge.
    private int neighbourIndex = 0; //The amount of currently set neighbours.
    protected static int PLANESIZE = 3; //The amount of nodes and edges of this plane.
    private Edge[] edges; //The edges of this plane

    /**
     * Initializes a new Plane, creates corresponding node in the dual graph
     * and adds this plane to the adjacent planes of an edge.
     * @param edges The edges of this plane
     */
    public Plane(Edge[] edges) {
        this.edges = edges;
        for(Edge edge: edges){
            edge.addPlane(this); //Adds this plane to the edges of this plane.
        }
        neighbours = new Plane[PLANESIZE];
        //Bidirectional association with plane. We need to be able to extract the PlaneNode from a plane, and the
        //Plane from a PlaneNode because the Plane is made in the DualGraph but doesn't need the functionality of a
        //PlaneNode. However once the yutsis decomposition begins, we need to be able to extract all the information
        //from the planeNode, while being able to use the functionality of the Plane object.
        this.node = new PlaneNode(this);
    }

    /**
     * @return The edges of this plane.
     */
    public Edge[] getEdges() {
        return edges;
    }

    /**
     * Checks if this plane has the given edges.
     * @param edges The given edges
     * @return Returns if this plane has the given edges
     */
    public boolean equalEdges(Edge[] edges) { //Constant time, checks 9 things.
        for(Edge edge1: edges){ //3 iterations
            boolean hasEdge = false;
            for(Edge edge2: getEdges()){ //3 iterations
                if(edge1.equals(edge2)){
                    hasEdge = true;
                }
            }
            if(!hasEdge){
                return false;
            }
        }
        return true;
    }

    /**
     * Adds an adjacent plane to this plane.
     * @param plane The neighbouring plane.
     */
    public void addAdjacentPlane(Plane plane){
        if(neighbourIndex < PLANESIZE){
            for(int i=0; i<neighbourIndex; i++){
                if(neighbours[i].equals(plane)){
                    return; //Don't add the plane if it's already in the adjacent planes.
                }
            }
            neighbours[neighbourIndex++] = plane;
        }
    }

    /**
     * @return The adjacent planes to this plane. (Planes with a common edge).
     */
    public Plane[] getAdjacentPlanes(){
        return neighbours;
    }

    /**
     * @return The node corresponding to this plane.
     */
    public PlaneNode getNode(){
        return node;
    }

    /**
     * The edge connecting 2 planes.
     * @param adjacentPlane An adjacent plane (which means this and the given plane have a common edge).
     * @return The common edge of these planes, if the planes aren't adjacent this method returns null.
     */
    public Edge getCommonEdge(Plane adjacentPlane){
        for(Edge edge1: edges){
            for(Edge edge2: adjacentPlane.edges){
                if(edge1.equals(edge2)){
                    return edge2;
                }
            }
        }
        return null; //The 2 planes don't have a common edge.
    }

    /**
     * Initializes the node of this plane with its correct neighbours in a correct order.
     */
    public void initialiseNode() {
        int i=0;
        PlaneNode[] orderedNeighbours = new PlaneNode[3];
        for(Edge edge: edges){
            orderedNeighbours[i++] = edge.getAdjacentPlane(this).getNode();
        }
        node.setNeighbours(orderedNeighbours);
    }

    public boolean equals(Object o){ //Constant time. This method will iterate 3 times.
        if(!(o instanceof  Plane)){
            return false;
        }
        Plane other = (Plane) o;
        for(int i=0; i<PLANESIZE; i++){
            Edge edge1 = edges[i];
            Edge edge2 = other.edges[i];
            if(!edge1.equals(edge2)){
                return false;
            }
        }
        return true;
    }

    public String toString(){
        Node[] nodes = new Node[PLANESIZE];
        int nodeAmount = 0;
        for(Edge edge: edges){
            if(nodeAmount<3){
                for(Node node1: edge.getEndPoints()){
                    boolean add = true;
                    for(Node node2: nodes){
                        if(node1.equals(node2)){
                            add = false;
                        }
                    }
                    if(add){
                        nodes[nodeAmount++] = node1;
                    }
                }
            }
        }
        String output = "";
        for(int i=0; i<nodes.length-1; i++){
            output += nodes[i].getNumber() + " ";
        }
        output += nodes[nodes.length-1].getNumber();
        return output;
    }
}
