package datastructures;

import dualgraph.PlaneNode;

/**
 * A datastructure representing an array with PlaneNodes.
 * Adding, removing and lookup are operate in constant time.
 * When referring to a node, this is a PlaneNode.
 *
 * The arrays used in the algorithm have the following properties:
 * V: Contains all nodes which aren't in any L[x] or M.
 * L[x]: Contains all nodes such that every node in L has one adjacent node to M and X adjacent nodes which aren't in M.
 * M: One disjunct part of the Yutsis decomposition containing a tree.
 *
 * The class also keeps track of a lastAdded element. However this is an approximation and might be incorrect in certain
 * circumstances. The algorithm performs better with this array than with a stack, as it allows for linear lookups.
 */
public class PlaneNodeArray {

    public final int number; //A unique identifier of this array.
    private static int index; //Indicates the number of the last PlaneNodeArray.
    private int length; //Indicates the length of this array
    private PlaneNode[] elements; //The graph of this array.
    private PlaneNode lastAdded; //The last added element to this array.
    private String name; //Used for the representation of this array

    /**
     * A constructor for a new PlaneNodeArray.
     * @param size The maximum size of this array.
     */
    public PlaneNodeArray(String name, int size){
        this.number = index++;
        this.length = 0;
        elements = new PlaneNode[size];
        this.name = name;
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

    /**
     * Removes a node from this array. If the node isn't in the array, nothing will happen.
     * This function also updates the lastAdded field.
     * @param node
     */
    public void remove(PlaneNode node){
        if(node.isPresent(this)){
            int nodeIndex = node.getIndex(this); //Get the index of this node in the array.
            if(nodeIndex!=length-1){ //Fills the spot of the removed node with the last element.
                PlaneNode last = elements[length-1];
                elements[nodeIndex] = last;
                last.setIndex(this, nodeIndex);
            }
            node.setIndex(this, -1); //Resets the index of the node.
            elements[--length]=null; //Remove a spot.
            if(node.equals(lastAdded)){ //Update lastAdded if necessary.
                if(length>0){
                    lastAdded = elements[length-1];
                }
                else {
                    lastAdded = null;
                }
            }
        }
    }

    /**
     * Returns a node with the given index.
     * @param index
     * @return The node with the given index.
     */
    public PlaneNode get(int index){
        return elements[index];
    }

    /**
     * @return The length of this array.
     */
    public int length(){
        return length;
    }

    /**
     * @return All of the nodes of this array.
     */
    public PlaneNode[] getNodes() {
        return elements;
    }

    public String toString(){
        String output = name + " ";
        for(int i=0; i<length-1; i++){
            output += "(" + elements[i] + "), ";
        }
        if(length>0){
            output+= "(" + elements[length-1] + ")";
        }
        return output;
    }


    /**
     * @return True if this array is empty.
     */
    public boolean empty() {
        return length==0;
    }

    /**
     * Removes and returns the last added element to this array.
     * @return The last added element.
     */
    public PlaneNode pull() {
        PlaneNode node;
        if(lastAdded != null){
            node = lastAdded;
            lastAdded = null;
            remove(node);
        }
        else {
            node =elements[0];
            remove(node);
        }
        return node;
    }
}
