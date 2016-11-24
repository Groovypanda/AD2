package elements;

/**
 * This class represents a pair of edges in a 2-connected cubic graph.
 */
public class Pair {
    private Face face; //For every pair there is exactly one face.
    private boolean marked; //Displays if this pair has been marked.
    /**
     * The direction of this pair represents how the second endpoint can be found from the center.
     * If the index of the first endpoint in the list of neighbours of the center has to be incremented to find nodes[2],
     * the direction is 1, else the direction is -1. This direction is necessary to decide the faces in a graph.
     */
    private int direction;
    private PlaneNode center;
    private PlaneNode[] endNodes;

    /**
     * Constructor for a pair.
     * @param endpoint1 The first endpoint of this pair.
     * @param middleNode The center node of this pair.
     * @param endpoint2 The last endpoint of this pair.
     */
    public Pair(PlaneNode endpoint1, PlaneNode middleNode, PlaneNode endpoint2) {
        marked = false;
        endNodes = new PlaneNode[2];
        endNodes[0] = endpoint1;
        endNodes[1] = endpoint2;
        center = middleNode;
        //Set the direction of this pair as described in the field declaration.
        if(endpoint2.equals(center.getNextNode(endpoint1))){
            direction=-1;
        }
        else {
            direction=1;
        }
        //Add this pair to the individual nodes.
        center.addPair(this);
    }

    /**
     * @return The marked status of this pair
     */
    public boolean isMarked() { return marked; }

    /**
     * Marks this pair
     */
    public void mark() { marked = true; }

    /**
     * @return The unique face of this pair.
     */
    public Face getFace() {
        return face;
    }

    /**
     * Sets the face of this pair.
     * @param face
     */
    public void setFace(Face face) {
        this.face = face;
    }

    /**
     * @return The center node of this pair.
     */
    public PlaneNode getCenter() {
        return center;
    }

    public boolean equals(PlaneNode startNode, PlaneNode middleNode, PlaneNode endNode) {
        return center.equals(middleNode) &&
                ((startNode.equals(endNodes[0]) && endNode.equals(endNodes[1])) ||
                        (startNode.equals(endNodes[1]) && endNode.equals(endNodes[0])));
    }

    /**
     * If the index of the first endpoint in the list of neighbours of the center has to be incremented to find nodes[2],
     * the direction is 1, else the direction is -1. This direction is necessary to decide the faces in a graph.
     * @return The direction of this pair.
     */
    public int getDirection(){
        return direction;
    }

    /**
     * @return The endpoint of this pair.
     */
    public PlaneNode[] getEndNodes() {
        return endNodes;
    }

    public void unmark() {
        marked = false;
    }
}
