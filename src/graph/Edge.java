package graph;

import graph.Node;

/**
 * Created by Jarre on 8/10/2016.
 */
public class Edge {
    private int number;
    private Node nodes[];
    private boolean visited = false;
    int visitedAmount = 0; //This is purely for checking an edge can't be visited more than twice in the algorithm.

    public Edge(int number){
        this.number = number;
        //Every vertex connects 2 heap.
        nodes = new Node[2];
    }

    public void addNode(Node node){
        assert(nodes[1]==null);
        if(nodes[0]==null){
            nodes[0] = node;
        } else {
            nodes[1] = node;
        }
    }

    public String toString(){
        return "Edge " + Integer.toString(number) + " (" + Integer.toString(nodes[0].getNumber()) + "," + Integer.toString(nodes[1].getNumber()) + ")";
    }

    public Node getNeighbour(Node v) {
        if(v.equals(nodes[0])){
            return nodes[1];
        }
        return nodes[0];
    }
}
