package gretig.dualgraph;

import gretig.datastructures.PlaneNodeArray;
import gretig.graph.Plane;

/**
 * Represents a node in the DualGraph. This node is a plane in the original graph as the nodes of the dualgraph are planes.
 * Every node is part of just one PlaneNodeArray.
 * Note: All methods run in constant time.
 */

public class PlaneNode {
    private Plane plane; //The node represents this plane.
    private PlaneNodeArray current; //The array this node belongs to in order to check fast which array contains this node.
    private int arrayIndex; //The index of node in order to remove nodes in constant time from the planearray.
    private PlaneNode[] neighbours; //The neighbours of this node in the dual graph.
    private Pair[] pairs; //Pairs of edges with this node as center.
    private int pairsLength; //Current length of current pairs array.

    /**
     * Initializes a new node representing the given plane.
     * @param plane
     */
    public PlaneNode(Plane plane) {
        this.plane = plane;
        arrayIndex = -1; //Initially the node isn't part of a PlaneNodeArray.
        current = null;
        pairs = new Pair[3]; //Every node is the center of 3 pairs.
        pairsLength = 0;
    }

    /**
     * Checks if the node is in the given array
     * @param array
     * @return True if the node is in the given array.
     */
    public boolean isPresent(PlaneNodeArray array){
        return current != null && array.number == current.number;
    }

    /**
     * Gets the index of this node in the given array. Returns -1 if the node isn't in the given array.
     * @param array
     * @return The index of this node in the given array. -1 if this node isn't in the array.
     */
    public int getIndex(PlaneNodeArray array){
        return isPresent(array) ? arrayIndex : -1;
    }

    /**
     * Sets the index of this node in the given array. The array field of this node is changed if it doesn't equal the given array.
     * @param array The (new) array of this node.
     * @param index The new index of this node.
     */
    public void setIndex(PlaneNodeArray array, int index){
        if(!isPresent(array)){
            current = array;
        }
        arrayIndex = index;
    }

    /**
     * Returns the amount of neighbours a node has in the given array.
     * @param array A PlaneNodeArray in which the neighbours have to be present.
     * @return The amount of neighbours an element has in the given array.
     */
    public int neighbourAmount(PlaneNodeArray array){
        int amount = 0;
        for(PlaneNode node: getNeighbours()){
            if(node.isPresent(array)){
                amount++;
            }
        }
        return amount;
    }

    /**
     * @return The plane this node represents
     */
    public Plane getPlane() {
        return plane;
    }

    /**
     * @return The neighbours of this node in the dualgraph.
     */
    public PlaneNode[] getNeighbours() {
        return neighbours;
    }

    /**
     * Removes this node from its current array and adds it to the given array.
     * @param newArray The new array for this node.
     */
    public void switchTo(PlaneNodeArray newArray) {
        current.remove(this);
        newArray.add(this);
    }

    /**
     * Adds a pair of edges of which this node is the center.
     * @param pair The pair of edges.
     */
    public void addPair(Pair pair){
            pairs[pairsLength++] = pair;
    }

    /**
     * @return The pairs of which this node is the center.
     */
    public Pair[] getPairs() {
        return pairs;
    }

    /**
     * Checks if this node is the center the pair with the given endpoints.
     * @param endpoint1
     * @param endpoint2
     * @return True if the node contains a pair with the given endpoints.
     */
    public boolean containsCenterPair(PlaneNode endpoint1, PlaneNode endpoint2) {
        for(int i=0; i<pairsLength; i++){
            if(pairs[i].equals(endpoint1, this, endpoint2)){
                return true;
            }
        }
        return false;
    }

    /**
     * @param endpoint1
     * @param endpoint2
     * @return The pair with the given nodes as endpoints. Null if no such pair was found.
     */
    public Pair findCenterPair(PlaneNode endpoint1, PlaneNode endpoint2) {
        for(Pair pair: pairs){
            if(pair.equals(endpoint1, this, endpoint2)){
                return pair;
            }
        }
        return null;
    }


    /**
     * @param node
     * @return The neighbour after the given node in the array of neighbours.
     */
    public PlaneNode getNextNode(PlaneNode node) {
        //The next node has the index of the given node - 1 as the nodes in the array are sorted anti-clockwise.
        return getAdjacentNode(-1, node);
    }

    public PlaneNode getPreviousNode(PlaneNode node) {
        //The previous node has the index of the given node +1 as the nodes in the array are sorted anti-clockwise.
        return getAdjacentNode(1, node);
    }

    /**
     * @param direction
     * @param node
     * @return The node with index of node + direction in the array of neighbours.
     */
    private PlaneNode getAdjacentNode(int direction, PlaneNode node){
        int i=0;
        while(!neighbours[i].equals(node)){
            i++;
            if(i==3){
                return null;
            }
        }
        //+3 and %3 to make sure i is in the correct interval.
        return getNeighbours()[(i+3+direction)%3];
    }

    /**
     * Sets the neighbours of this node.
     * @param orderedNeighbours
     */
    public void setNeighbours(PlaneNode[] orderedNeighbours) {
        this.neighbours = orderedNeighbours;
    }

    /**
     * Checks if this pair is the center of any pair which isn't marked and doesn't have endpoints in the given array.
     * @param M
     * @return The Face of the pair which isn't marked and doesn't lead to endpoints in M, null if this is a cut vertex.
     */
    public Face isCutVertex(PlaneNodeArray M){
        for(int j=0; j<pairs.length; j++){
            Pair pair = pairs[j];
            PlaneNode[] nodes = pair.getEndNodes();
            if(!nodes[0].isPresent(M) && !nodes[1].isPresent(M) && !pair.isMarked()){
                return  pair.getFace();
            }
        }
        return null;
    }

    public String toString(){
        return plane.toString();
    }

    /**
     * Resets this node to its initial conditions.
     */
    public void reset() {
        current = null;
        arrayIndex = -1;
    }

    public boolean equals(Object other){
        return other instanceof PlaneNode && plane.equals(((PlaneNode) other).getPlane());
    }
}
