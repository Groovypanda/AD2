package elements;

/**
 * This class represents a pair of edges in a 2-connected cubic graph.
 */
public class Pair {
    private Face face; //For every pair there is exactly one face.
    private boolean marked; //Displays if this pair has been marked.
    /**
     * The nodes in this pair.
     * nodes[0] must be the first node, nodes[1] the center node, nodes[2] the last node.
     */
    private PlaneNode[] nodes;
    /**
     * The direction of this pair represents how nodes[2] can be found from nodes[1].
     * If the index of nodes[0] in the list of neighbours of nodes[1] has to be incremented to find nodes[2],
     * the direction is 1, else the direction is -1. This direction is necessary to decide the faces in a graph.
     */
    private int direction;

    /**
     * Constructor for a pair.
     * @param startNode The first node of this pair.
     * @param middleNode The center node of this pair.
     * @param endNode The last node of this pair.
     */
    public Pair(PlaneNode startNode, PlaneNode middleNode, PlaneNode endNode) {
        marked = false;
        nodes = new PlaneNode[3];
        //Set correct checkOrder of Pair. The middleNode must be the center of this pair.
        nodes[0] = startNode;
        nodes[1] = middleNode;
        nodes[2] = endNode;
        //Set the direction of this pair as described in the field declaration.
        if(nodes[2].equals(nodes[1].getNextNode(nodes[0]))){
            direction=-1;
        }
        else {
            direction=1;
        }
        //Add this pair to the individual nodes.
        nodes[0].addPair(this);
        nodes[1].addCenterPair(this); //Indicates nodes[1] is the center of this pair.
        nodes[2].addPair(this);
    }


    public boolean isMarked() { return marked; }

    public void mark() { marked = true; }

    public Face getFace() {
        return face;
    }

    public void setFace(Face face) {
        this.face = face;
    }

    public PlaneNode getCenter() {
        return nodes[1];
    }

    public PlaneNode[] getNodes() {
        return nodes;
    }

    public String toString(){
        String output = "";
        for(int i=0; i<nodes.length-1; i++){
            output += "(" + nodes[i] + ") ";
        }
        output += "(" + nodes[nodes.length-1] + ")";
        return output;
    }

    public boolean equals(PlaneNode startNode, PlaneNode middleNode, PlaneNode endNode) {
        return nodes[1].equals(middleNode) &&
                ((startNode.equals(nodes[0]) && endNode.equals(nodes[2])) || (startNode.equals(nodes[2]) && endNode.equals(nodes[0])));
    }

    public int getDirection(){
        return direction;
    }

    public PlaneNode getEndNode(PlaneNode node) {
        return nodes[0].equals(node) ?  nodes[2] : nodes[0];
    }
}
