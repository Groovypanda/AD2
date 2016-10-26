package nodearray;

import graph.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

//Based on wikipedia algorithm
public class NodeArray {
    private int n;
    private int start = 0;
    private int currentIndex = 0;
    private int k;
    private int[] count;
    private Stack<Node>[] nodes;
    private Node[] sortedNodeArray;

    public NodeArray(int nodeAmount, int verticesAmount) {
        k = nodeAmount;
        n = 2*verticesAmount;
        count = new int[k];
        //Every edge has 2 ends.
        nodes = new Stack[n];
        for(int i=0; i<n; i++){
            nodes[i] = new Stack<>();
        }
        sortedNodeArray = new Node[k];
    }

    public Node pull(){
        Node n = sortedNodeArray[start];
        sortedNodeArray[start] = null;
        start++;
        return n;
    }

    public void remove(Node node){
        sortedNodeArray[node.getIndex()] = null;
    }

    public Node[] getNodes(){
        return sortedNodeArray;
    }

    //Add node with edgesAmount configured.
    public void addNode(Node node){
        //node.setIndex(node.getNumber()-1);
        nodes[node.getEdgesAmount()].push(node);
        count[node.getEdgesAmount()]++;
    }

    public void sort(){
        //Calculate starting indices
        int total = 0;
        for(int i = 0; i< k; i++){
            int oldCount = count[i];
            count[i]=total;
            total+=oldCount;
        }

        //Sort the nodes
        for(Stack<Node> nodesStack: this.nodes){
            while(!nodesStack.isEmpty()){
                Node node = nodesStack.pop();
                int index = count[node.getEdgesAmount()];
                //Reverse order: highest first
                sortedNodeArray[k-1-index] = node;
                count[node.getEdgesAmount()]+=1;
            }
        }
    }
}