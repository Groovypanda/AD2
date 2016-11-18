package elements;

import datastructures.YutsisArray;

/**
 * Represents a node in the YutsisArray. It's always part of one array: M, L or V.
 */
public class PlaneNode {
    private Plane plane;
    private int arrayNumber;
    private int arrayIndex;

    public PlaneNode(Plane plane) {
        this.plane = plane;
        arrayIndex = -1;
        arrayNumber = 0;
    }

    public boolean isPresent(YutsisArray array){
        return array.number == arrayNumber &&  arrayIndex != -1;
    }

    //Returns -1 if not in the given array.
    public int getIndex(YutsisArray array){
        return array.number == arrayNumber ? arrayIndex : -1;
    }

    //If element not in the given array, the array number of this node will be changed.
    public void setIndex(YutsisArray array, int index){
        if(array.number!=arrayNumber){
            arrayNumber = array.number;
        }
        arrayIndex = index;
    }

    /**
     * Returns the amount of neighbours a node has in the given array.
     * @param array A YutsisArray in which the neighbours have to be present.
     * @return The amount of neighbours an element has in the given array.
     */
    public int neighbourAmount(YutsisArray array){
        int amount = 0;
        for(Plane plane: this.plane.getAdjacentPlanes()){ //3 iterations.
            PlaneNode node = plane.getNode();
            if(node.isPresent(array)){
                amount++;
            }
        }
        return amount;
    }

    public PlaneNode getMaxNeighbour(YutsisArray array){
        int max = 0;
        PlaneNode maxNode = null;
        for(Plane plane: this.plane.getAdjacentPlanes()){ //3 iterations.
            if(!plane.getNode().isPresent(YutsisArray.M)){ //If the element is in M, it can't be chosen as max neighbour.
                PlaneNode node = plane.getNode();
                int neighbourAmount = node.neighbourAmount(array);
                if(neighbourAmount>max){
                    max = neighbourAmount;
                    maxNode = node;
                }
            }
        }
        return maxNode;
    }

    public int getArrayNumber(){
        return arrayNumber;
    }

    public String toString(){
        return plane.toString();
    }

    public Plane getPlane() {
        return plane;
    }
}
