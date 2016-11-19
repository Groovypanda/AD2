package datastructures;

import elements.Plane;
import elements.PlaneNode;

/**
 * A datastructure representing an array with PlaneNodes in which all operations are constant.
 */
public enum YutsisArray {
    V(0), L(1), M(2), D(3);

    //V: Contains all nodes which aren't in L or M.
    //L: Contains all nodes such that every node in L has one adjacent node to M.
    //M: One disjunct part of the Yutsis decomposition.
    //D: The other disjunct part of the Yutsis decomposition.

    public final int number;
    private int length; //Length is maximum 2n-4 (amount of planes).
    private int size;
    private PlaneNode[] elements;

    YutsisArray(int number){
        this.number = number;
    }

    public void initialize(int size){
        this.size = size;
        this.length = 0;
        elements = new PlaneNode[size];
    }

    public void add(PlaneNode node){
        elements[length] = node;
        node.setIndex(this, length);
        length++;
    }

    public void remove(PlaneNode node){
        if(node.isPresent(this)){
            int nodeIndex = node.getIndex(this);
            if(nodeIndex!=length-1){ //Fills the spot of the removed node with the last element.
                PlaneNode last = elements[length-1];
                elements[nodeIndex] = last;
                last.setIndex(this, nodeIndex);
            }
            node.setIndex(this, -1);
            elements[--length]=null;
        }

    }

    public PlaneNode get(int index){
        return elements[index];
    }

    public int length(){
        return length;
    }

    public PlaneNode[] getNodes() {
        return elements;
    }

    public String toString(){
        String output = "";
        for(int i=0; i<length-1; i++){
            output += "(" + elements[i] + "), ";
        }
        if(length>0){
            output+= "(" + elements[length-1] + ")";
        }
        return output;
    }
    /*
    public void connectNodes(){
        for(PlaneNode node: elements){
            for(Plane plane: node.getPlane().getAdjacentPlanes()){
                PlaneNode neighbour = plane.getNode();
                if(neighbour.isPresent(this)){
                    node.addNeighbour(neighbour);
                }
            }
        }
    }
    */
}
