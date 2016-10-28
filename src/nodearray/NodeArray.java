package nodearray;

import graph.Node;

import java.util.Stack;

//Based on wikipedia algorithm
public class NodeArray {
    private int n;
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

    public Node[] getSortedNodes(){
        return sortedNodeArray;
    }

    //Add node with edgesAmount configured.
    public void addNode(Node node){
        nodes[node.getEdgesAmount()].push(node);
        count[node.getEdgesAmount()]++;
    }

    public void sort(boolean backwards){
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
                if(backwards){
                    //Lowest first.
                    sortedNodeArray[index] = node;
                }
                else {
                    sortedNodeArray[k-1-index] = node;
                }
                count[node.getEdgesAmount()]+=1;
            }
        }
    }
}