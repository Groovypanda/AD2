package datastructures;

import elements.PlaneNode;

/**
 * A datastructure representing an array with PlaneNodes.
 * All of the operations in this datastructure are (and must be) constant.
 * When referring to a node, this is a PlaneNode.
 *
 * The arrays used in the algorithm have the following properties:
 * V: Contains all nodes which aren't in any L[x] or M.
 * L[x]: Contains all nodes such that every node in L has one adjacent node to M and X adjacent nodes which aren't in M.
 * M: One disjunct part of the Yutsis decomposition containing a tree.
 * D: The other disjunct part of the Yutsis decomposition containing a tree.
 */
public class PlaneArray {

    public final int number; //A unique identifier of this array.
    private static int index; //Indicates the number of the last PlaneArray.
    private int length; //Indicates the length of this array
    private PlaneNode[] elements; //The elements of this array.
    private PlaneNode lastAdded; //The last added elements to this array.

    /**
     * A constructor for a new PlaneArray.
     * @param size The maximum size of this array.
     */
    public PlaneArray(int size){
        this.number = index++;
        this.length = 0;
        elements = new PlaneNode[size];
    }

    /**
     * Add a new node to this array.
     * @param node
     */
    public void add(PlaneNode node){
        elements[length] = node;
        node.setIndex(this, length);
        lastAdded = node;
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
            if(node.equals(lastAdded)){
                if(length>0){
                    lastAdded = elements[length-1];
                }
                else {
                    lastAdded = null;
                }
            }
        }
        else {
            System.out.println("Fatal error");
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

    public PlaneNode peek() {
        PlaneNode node = null;
        if(lastAdded != null){
            node = lastAdded;
        }
        return node;
    }
}
