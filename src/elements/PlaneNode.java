package elements;

import datastructures.PlaneArray;

/**
 * Represents a node in the PlaneArray. It's always part of one array: M, L or V.
 */
public class PlaneNode {
    private Plane plane;
    private PlaneArray current;
    private int arrayIndex;
    private PlaneNode[] neighbours;
    private PlaneEdgePair[] pairs; //Pairs of edges containing this node.
    private int pairsLength; //Current length of current pairs array.
    private PlaneEdge[] adjacentEdges;
    private int edgesLength; //Current length of ajacentEdges array.
    private Face[] faces;
    private int facesLength; //Current length of Face array.

    public PlaneNode(Plane plane) {
        this.plane = plane;
        arrayIndex = -1;
        current = null;
        pairs = new PlaneEdgePair[3];
        adjacentEdges = new PlaneEdge[3];
        faces = new Face[3];
        pairsLength = 0;
        edgesLength = 0;
        facesLength = 0;
    }

    public boolean isPresent(PlaneArray array){
        return current == null ? false : array.number == current.number;
    }

    //Returns -1 if not in the given array.
    public int getIndex(PlaneArray array){
        return isPresent(array) ? arrayIndex : -1;
    }

    public void setIndex(PlaneArray array, int index){
        if(!isPresent(array)){
            current = array;
        }
        arrayIndex = index;
    }

    /**
     * Returns the amount of neighbours a node has in the given array.
     * @param array A PlaneArray in which the neighbours have to be present.
     * @return The amount of neighbours an element has in the given array.
     */
    public int neighbourAmount(PlaneArray array){
        int amount = 0;
        for(PlaneNode node: getNeighbours()){ //3 iterations.
            if(node.isPresent(array)){
                amount++;
            }
        }
        return amount;
    }

    public String toString(){
        return plane.toString();
    }

    public Plane getPlane() {
        return plane;
    }

    public PlaneNode[] getNeighbours() {
        //Only create neighbour array once when necessary.
        if(neighbours==null){
            neighbours = new PlaneNode[3];
            int i = 0;
            for(Plane plane: getPlane().getAdjacentPlanes()){
                neighbours[i++] = plane.getNode();
            }
        }
        return neighbours;
    }

    //Returns if this node is addable to M
    public boolean isAddable(){
        return true;
    }

    public void switchTo(PlaneArray newArray) {
        current.remove(this);
        newArray.add(this);
    }

    public void markPairs() {
        for(PlaneEdgePair pair: pairs){
            pair.mark();
        }
    }

    public void addEdge(PlaneEdge planeEdge) {
        adjacentEdges[edgesLength++]=planeEdge;
    }

    public void addPair(PlaneEdgePair pair){
        pairs[pairsLength++]=pair;
    }

    public PlaneEdge[] getAdjacentEdges(){
        return adjacentEdges;
    }

    public PlaneEdgePair[] getAdjacentPairs(){
        return pairs;
    }

    public PlaneEdge getEdgeTo(PlaneNode second) {
        for(PlaneEdge edge1: adjacentEdges){
            for(PlaneEdge edge2: second.adjacentEdges){
                if(edge1.equals(edge2)){
                    return edge1;
                }
            }
        }
        return null;
    }

    /**
     * Returns the planeEdgePair with this node as center with the given pair of edges.
     * @param edge1
     * @param edge2
     * @return
     */
    public PlaneEdgePair getPlaneEdgePair(PlaneEdge edge1, PlaneEdge edge2) {
        for(int i=0; i<pairsLength;i++){
            if(pairs[i].equals(edge1, edge2)){
                return pairs[i];
            }
        }
        return null;
    }

    public void addFace(Face face) {
        faces[facesLength++] = face;
    }

    //Check if this node has a pair with these edges.
    public boolean containsPlaneEdgePair(PlaneEdge edge1, PlaneEdge edge2){
        boolean found = false;
        for(int i=0; i<pairsLength &&!found; i++){
            PlaneEdgePair pair = pairs[i];
            if(pair.equals(edge1, edge2)){
                found=true;
            }
        }
        return found;
    }

    public PlaneNode getNeighbour(PlaneEdge edge) {
        PlaneNode[] nodes = edge.getEndpoints();
        return nodes[0].equals(this) ? nodes[1] : nodes[0];
    }
}
