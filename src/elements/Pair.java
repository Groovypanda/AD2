package elements;

import java.awt.datatransfer.DataFlavor;

/**
 * Created by Jarre on 23/11/2016.
 */
public class Pair {
    private Face face; //Remark 5
    private boolean marked;
    private PlaneNode[] nodes;
    private int direction;

    public Pair(PlaneNode startNode, PlaneNode middleNode, PlaneNode endNode) {
        marked = false;
        nodes = new PlaneNode[3];
        //Set correct order of Pair
        setNodeOrder(startNode, middleNode, endNode);
        //Set directions.
        if(nodes[2].equals(nodes[1].getNextNode(null, nodes[0]))){
            direction=-1;
        }
        else {
            direction=1;
        }
        /*
        else if(startNode.getNextNode(null, middleNode).equals(endNode)){
            System.out.println("Order1");

        }
        else {
            System.out.println("Order2");
            nodes[0] = startNode;
            nodes[1] = middleNode;
            nodes[2] = endNode;
        }
        */
        nodes[0].addPair(this);
        nodes[1].addCenterPair(this);
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

    public PlaneNode getEndPoint(PlaneNode start) {
        return start.equals(nodes[0]) ? nodes[1] : nodes[0];
    }

    public boolean equals(PlaneNode startNode, PlaneNode middleNode, PlaneNode endNode) {
        return nodes[1].equals(middleNode) &&
                ((startNode.equals(nodes[0]) && endNode.equals(nodes[2])) || (startNode.equals(nodes[2]) && endNode.equals(nodes[0])));
    }

    public void setNodeOrder(PlaneNode startNode, PlaneNode middleNode, PlaneNode endNode){
        nodes[0] = startNode;
        nodes[1] = middleNode;
        nodes[2] = endNode;
        if(!endNode.equals(middleNode.getNextNode(null, startNode).equals(endNode))){
            nodes[0] = endNode;
            nodes[2] = startNode;
        }
    }

    public int getDirection(){
        return direction;
    }

    public PlaneNode getEndNode(PlaneNode node) {
        return nodes[0].equals(node) ?  nodes[2] : nodes[0];
    }
}
