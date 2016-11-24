package elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jarre on 23/11/2016.
 */
public class Face {
    //private Pair[] boundaries;
    //private PlaneNode[] nodes;
    private List<Pair> boundaries;



    public Face() {
        boundaries = new ArrayList<>();
    }

    public List<Pair> getBoundaries(){
        return boundaries;
    }

    public void markBoundaries(){
        for(Pair pair: boundaries){
            pair.mark();
        }
    }

    public void addBoundary(Pair pair){
        boundaries.add(pair);
        pair.setFace(this);
    }

    public void print(){
        for(Pair pair: boundaries){
            System.out.println(pair);
        }
        System.out.println("===============");
    }

    /*
    public Face(Pair first) {
        length=0;
        boundaries = new Pair[4];
        boundaries[length++] = first;
        //Find the 2 pairs with overlapping edges, the center of their pair is an endpoint of this pair.
        nodes = new PlaneNode[4]; //Nodes in this face.
        PlaneNode[] firstNodes = first.getNodes();
        nodes[0] = firstNodes[0];
        nodes[1] = firstNodes[1];
        nodes[2] = firstNodes[2];
        boolean found = false;
        for(int i=0; i<3 && !found ; i++){ //Search for the last node in this face.
            PlaneNode node1 = nodes[0].getNeighbours()[i];
            for(int j=0; j<3 && !found; j++){
                PlaneNode node2 = nodes[2].getNeighbours()[j];
                if(node1.equals(node2) && !node1.equals(nodes[1])){
                    nodes[3] = node1;
                    found = true;
                }
            }
        }
        //If not found, it means this face doesn't have a 4th node, and thus only has 3 nodes OR
        //it's part of one of the 2 outer faces.
        if(nodes[3]==null){
            boundaries[length++] = nodes[0].findCenterPair(nodes[1], nodes[2]);
            boundaries[length++] = nodes[2].findCenterPair(nodes[1], nodes[0]);
        }
        else {
            boundaries[length++] = nodes[0].findCenterPair(nodes[1], nodes[3]);
            boundaries[length++] = nodes[2].findCenterPair(nodes[1], nodes[3]);
            boundaries[length++] = nodes[3].findCenterPair(nodes[0], nodes[2]);
        }
        for(int i=0; i<length;i++){
            Pair pair = boundaries[i];
            PlaneNode node = nodes[i];
            pair.setFace(this);
            node.addFace(this);
        }
        System.out.println(this);
    }
    */
}
