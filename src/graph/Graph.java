package graph;


import nodearray.SortedNodeArray;

/**
 * <h1>Data structure for a graph</h1>
 * The data structure contains an array of edges, array of nodes and sorted array of nodes.
 * The unsorted array of nodes is redundant, but can be helpful for certain implementations.
 * The structure supports adding nodes and edges, and accessing the nodes (sorted or unsorted).
 */
public class Graph {
    private Edge[] edgeArray; //array of all edges
    private Node[] nodeArray; //array of all nodes
    private SortedNodeArray sortedNodeArray; //sorted array of all nodes
    private boolean hasSorted = false; //checks if the sort method has been called.

    /**
     * Constructor for a graph. It initiates the internal data structures.
     * @param nodeAmount Total amount of nodes
     * @param edgeAmount Total amount of edges
     */
    public Graph(int nodeAmount, int edgeAmount){
        this.edgeArray = new Edge[edgeAmount];
        this.nodeArray = new Node[nodeAmount];
        /**
         * The sortedNodeArray can't be accessed until the sort method is called. The sort method should be called
         * once all nodes are added to the sortedNodeArray.
         */
        sortedNodeArray = new SortedNodeArray(nodeAmount);
    }

    /**
     * Adds a node to the graph.
     * @param node The node which has to be added
     */
    public void addNode(Node node) {
        sortedNodeArray.addNode(node);
        nodeArray[node.getNumber()-1] = node; //node numbers run from 1->n. this has to map on 0->n-1.
    }

    /**
     * Adds a new edge to the graph if the graph doesn't contain the given edge yet.
     * This method also adds the given node to the edge.
     * This method should only be called twice, as there are 2 nodes (endpoints) for each edge.
     * @param node An endpoint of the given edge. The endpoint will be added to the edge and the edge will be added
     *             to the neighbouring edges of the node.
     * @param edgeNumber The number of an edge. The edge will be created an added to the graph if it didn't exist yet.
     */
    public void addEdge(Node node, int edgeNumber){
        Edge e = getEdge(edgeNumber);
        if(e==null){ //Check if the graph contains the edgeNumber yet, if not, create the edge and add it to the graph.
            e = new Edge(edgeNumber);
            setEdge(edgeNumber, e);
        }
        node.addEdge(e); //Add the edge to the node
        e.addNode(node); //Add the endpoint to the edge
    }

    /**
     * @return An unsorted array of nodes. (Order of adding)
     */
    public Node[] getNodes(){ return nodeArray;}

    /**
     * This method should be called after the sort method has been executed.
     * @return A sorted array of nodes. Returns null if the sort method hasn't been executed.
     */
    public Node[] getSortedNodes(){
        if(hasSorted){
            return sortedNodeArray.getSortedNodes();
        }
        else {
            return null;
        }
    }

    /**
     * The method sorts the sortedNodeArray with counting sort by their degree in O(n+k).
     * Note: This method should only be called once. If the method is called multiple times it won't sort again.
     * @param lowestFirst specifies the order in which the sortedNodeArray should be sorted. If lowestFirst is true,
     *                    the nodes with the lowest degree will have the lowest index, if not, the nodes with highest
     *                    degree will have the lowest index.
     */
    public void sort(boolean lowestFirst){
        if(!hasSorted){
            sortedNodeArray.sort(lowestFirst);
            hasSorted = true;
        }
    }

    /**
     * Private method for retrieving an edge from the graph.
     * @param edgeNumber The number of the edge
     * @return The edge, returns null if the edgeArray doesn't contain the edgeNumber.
     */
    private Edge getEdge(int edgeNumber){ return edgeArray[edgeNumber-1]; }

    /**
     * Sets the edge with the given number in the edgeArray.
     * @param edgeNumber The number of the edge
     * @param edge The edge object
     */
    private void setEdge(int edgeNumber, Edge edge){ edgeArray[edgeNumber-1] =  edge; }

}
