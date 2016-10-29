package nodearray;

import graph.Node;

import java.util.Stack;

/**
 * This class represents a data structure. After calling the sort method, it contains a sorted list.
 * The add operation is performed in constant time and the sort method is performed in O(n+k) time.
 * Sorting is based on the degree of a node.
 * Sorting should only be performed once all nodes are added.
 * @author Jarre Knockaert
 */
public class SortedNodeArray {
    private int n; //Maximum degree +1
    private int k; //Amount of nodes
    private int[] count; //At which says how many nodes of a certain degree (the index) there are.
    private Stack<Node>[] nodes; //An array of stacks, in the stack of index i, all nodes of degree i are stored.
    private Node[] sortedNodeArray; //After sorting, this array contains the sorted nodes.

    /**
     * The constructor for a sorted node array. It creates an SortedNodeArray object to which nodes can be added.
     * @param nodeAmount The total amount of nodes which will be added. The size of the SortedNodeArray.
     */
    public SortedNodeArray(int nodeAmount) {
        k = nodeAmount;
        /**
         * Degree can go from 1->nodeAmount-1. (Example: if there are 5 nodes, the degree could go up to 4
         * of a certain node.) We need an array of size maximum degree + 1, this is exactly the nodeAmount.
         */
        n = nodeAmount;
        count = new int[k];
        nodes = new Stack[n];
        for(int i=0; i<n; i++){ //Create an empty stack for each degree in which we can save the nodes of that degree.
            nodes[i] = new Stack<>();
        }
        sortedNodeArray = new Node[k];
    }

    /**
     * This method should only be performed after adding and sorting the nodes.
     * @return an array with sorted nodes. If the sort method hasn't been executed, it will return null.
     */
    public Node[] getSortedNodes(){
        return sortedNodeArray;
    }

    //Add node with edgesAmount configured.

    /**
     * Add a node to the SortedNodeArray. This node can't be retrieved before sorting this object.
     * @param node The node which has to be added.
     */
    public void addNode(Node node){
        /**
         * Add the node with a certain degree in the stack which contains nodes of that degree and increment
         * the counter of that degree.
         */
        nodes[node.getDegree()].push(node); //O(1)
        count[node.getDegree()]++;
    }

    /**
     * Sort the nodes with counting sort based on their degree in O(n+k) time. (n equals k in our case)
     * @param backwards If backwards is true the sorting will be reversed, lower degree nodes will have a lower index.
     */
    public void sort(boolean backwards){
        //Calculate starting indices for nodes of every degree.
        int total = 0;
        for(int i = 0; i< k; i++){
            int oldCount = count[i];
            count[i]=total;
            total+=oldCount;
        }

        //Sort the nodes by their degree
        for(Stack<Node> nodesStack: this.nodes){
            while(!nodesStack.isEmpty()){
                Node node = nodesStack.pop(); //Get the next node with a certain degree.  Pop has complexity O(1).
                int index = count[node.getDegree()];
                //Reverse order: highest first
                if(backwards){
                    sortedNodeArray[index] = node; //Lowest degree first.
                }
                else {
                    sortedNodeArray[k-1-index] = node; //Highest degree first
                }
                count[node.getDegree()]+=1; //Increase the index of a node with the same degree.
            }
        }
    }
}