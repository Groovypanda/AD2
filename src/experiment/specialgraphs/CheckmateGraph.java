package experiment.specialgraphs;

import datastructures.Graph;
import elements.Node;

/**
 * A checkmate elements is a elements which has the form of a checkmate, and every node is a tile.
 * The nodes of 2 neighbouring tiles are connected by an edge.
 */
public class CheckmateGraph extends Graph{

    public CheckmateGraph() {
        super(64, 112); //8*8 = 64 nodes and 7*7*2 + 2*7 = 112 edges
        Node[] nodes = new Node[getNodes().length];
        for(int i=0; i<nodes.length; i++){
            nodes[i] = new Node(i+1);
        }
        //Edge under a node is the nodenumber + 8*7 (56 edges right to every node.)
        //Edge right to a node is the nodenumber
        //Edge left to a node is the nodenumber-1
        //Edge above a node is nodenumber + 8*7 - 8
        //First 56 edges represent the horizontal edges
        //Other 56 edges represent the vertical edges
        int left = 1;
        int right = 1;
        int up = 57;
        int down = 57;
        for(int i=0; i<nodes.length; i++){
            int nodeNumber = i+1;
            if(nodeNumber%8!=0){
                addEdge(nodes[i], right);
                right++;
            }
            if((nodeNumber-1)%8!=0){
                addEdge(nodes[i], left);
                left++;
            }
            if(nodeNumber>8){
                addEdge(nodes[i], up);
                up++;
            }
            if(nodeNumber<57){
                addEdge(nodes[i], down);
                down++;
            }
        }
        for(Node node: nodes){
            addNode(node);
        }
    }
}
