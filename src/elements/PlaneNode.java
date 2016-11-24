package elements;

import datastructures.PlaneArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a node in the PlaneArray. It's always part of one array: M, L or V.
 */
public class PlaneNode {
    private Plane plane;
    private PlaneArray current;
    private int arrayIndex;
    private PlaneNode[] neighbours;
    private List<Pair> pairs; //Pairs of edges with this node as startpoint (or endpoint).
    private Pair[] centerPairs; //Pairs of edges with this node as center.
    private int pairsLength; //Current length of current pairs array.

    public PlaneNode(Plane plane) {
        this.plane = plane;
        arrayIndex = -1;
        current = null;
        pairs = new ArrayList<>() ;
        centerPairs = new Pair[3];
        pairsLength = 0;
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
        return neighbours;
    }

    public void setNeighbours(){
        if(neighbours==null){
            neighbours = new PlaneNode[3];
            int i = 0;
            for(Plane plane: getPlane().getAdjacentPlanes()){
                neighbours[i] = plane.getNode();
                i++;
            }
        }
    }

    public void switchTo(PlaneArray newArray) {
        current.remove(this);
        newArray.add(this);
    }

    public void markPairs() {
        for(Pair pair: pairs){
            pair.mark();
        }
    }

    public void addPair(Pair pair){
        pairs.add(pair);
    }

    public void addCenterPair(Pair pair){
        centerPairs[pairsLength++] = pair;
    }

    public List<Pair> getPairs(){
        return pairs;
    }

    public boolean containsCenterPair(PlaneNode startNode, PlaneNode endNode) {
        for(int i=0; i<pairsLength; i++){
            if(centerPairs[i].equals(startNode, this, endNode)){
                return true;
            }
        }
        return false;
    }

    public Pair findCenterPair(PlaneNode start, PlaneNode end) {
        for(Pair pair: centerPairs){
            if(pair.equals(start, this, end)){
                return pair;
            }
        }
        return null;
    }


    public PlaneNode getNextNode(PlaneNode last, PlaneNode node) {
        int i;
        for(i=0; i<neighbours.length; i++){
            if(neighbours[i].equals(node)){
                break;
            }

            if(last!=null && neighbours[i].equals(last)){
                return last;
            }

        }
        if(i==3){
            return null;
        }
        int nextIndex = i-1;
        if(nextIndex<0){
            nextIndex+=3;
        }
        PlaneNode next = getNeighbours()[nextIndex];
        return next;
    }

    public PlaneNode getPreviousNode(PlaneNode first, PlaneNode node) {
        int i;
        for(i=0; i<neighbours.length; i++){
            if(neighbours[i].equals(node)){
                break;
            }

        }
        int nextIndex = i+1;
        PlaneNode next = getNeighbours()[nextIndex%3];
        return next;
    }
}
