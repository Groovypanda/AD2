package cycle;

import graph.Node;

/**
 * Represents a node in a hamiltonian cycle.
 */
public class CycleNode {
    private CycleNode[] neighbours; //The 2 adjacent neighbours in this cycle of this node.
    private Node node;  //The node which this CycleNode represents;

    /**
     * Constructor for a CycleNode
     * @param node The node which the cyclenode represent.
     */
    public CycleNode(Node node) {
        this.node = node;
        neighbours = new CycleNode[2];
    }

    /**
     * @return The 2 adjacent neighbours of the cycle this node belongs to.
     */
    public CycleNode[] getNeighbours() {
        return neighbours;
    }

    /**
     * Adds a neighbour of this node in the cycle.
     * @param cycleNode The neighbour of this node in the cycle
     */
    public void addNeighbour(CycleNode cycleNode){
        if(neighbours[0] == null){
            neighbours[0]=cycleNode;
        }
        else if(neighbours[1] == null && !neighbours[0].equals(cycleNode)){
            neighbours[1]=cycleNode;
        }
    }

    /**
     * @param previous The previous neighbour of this node in the cycle.
     * @return The next neighbour of this node in the cycle.
     */
    public CycleNode getNext(CycleNode previous){
        return !previous.equals(neighbours[0]) ? neighbours[0] : neighbours[1];
    }

    public boolean equals(Object other){
        return other instanceof CycleNode && ((CycleNode) other).node.equals(node);
    }

    public String toString(){
        return Integer.toString(node.getNumber());
    }

    public Node getNode() {
        return node;
    }
}
