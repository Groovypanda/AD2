package nodearray;

import graph.Node;

//Based on wikipedia algorithm
public class NodeArray {
    private int start;
    private int n;
    private int k;
    private int[] count;
    private Node[] nodeArray;
    private Node[] sortedNodeArray;

    public NodeArray(int length) {
        n = length;
        k = length - 1; //Amount of edges is maximum n-1
        count = new int[k];
        nodeArray = new Node[n];
        sortedNodeArray = new Node[n];
    }

    public Node pull(){
        Node n = sortedNodeArray[start];
        sortedNodeArray[start] = null;
        start++;
        return n;
    }

    public void remove(Node n){
        sortedNodeArray[n.getIndex()] = null;
    }

    public void addNode(Node n){
        n.setIndex(n.getNumber()-1);
        nodeArray[n.getIndex()] = n;
    }

    public void incrementCount(Node n){
        count[n.getNumber()-1]++;
    }

    public void sort(){
        //Calculate starting indices
        int total = 0;
        for(int i=0; i<k; i++){
            int oldCount = count[i];
            count[i]=total;
            total+=oldCount;
        }

        //Sort the nodeArray
        for(int i=0; i<n; i++){
            int index = count[i];
            Node n = nodeArray[i];
            n.setIndex(index);
            sortedNodeArray[index] = n;
            count[i]+=1;
        }
    }
}
