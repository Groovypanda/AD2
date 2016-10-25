package graph;


import nodearray.BinomialHeap;

/**
 * Created by Jarre on 8/10/2016.
 */
public class Graph {
    //Arrays for building the graph efficiently.
    private int size;
    private Edge[] edgeArray;
    private Node[] nodeArray;
    private BinomialHeap heap;

    //Binomial heap with highest elements at back and always pull from back????

    public Graph(int nodes, int edges){
        this.size = nodes;
        this.edgeArray = new Edge[edges];
        this.heap = new BinomialHeap(size);
    }

    public Node[] getNodes(){
        return nodeArray;
    }

    public Node getNode(int index){
        return nodeArray[index];
    }

    public Node peek(){
        return nodeArray[0];
    }

    public void removeNode(Node n){
        int index = n.getIndex();
        if(index!=size-1){
            nodeArray[index] = nodeArray[size-1];
            nodeArray[index].setIndex(index);
        }
        nodeArray[size-1] = null;
        size--;
    }

    public void addNode(Node n) {
        heap.insert(n);
    }

    public void addVertex(Node n, int edge){
        Edge e = getEdge(edge);
        if(e==null){
            e = new Edge(edge);
            setEdge(edge, e);
        }
        n.addEdgeNumber(e.getNumber());
        e.addNode(n);
    }

    public void initialiseNodes() {
        this.nodeArray = heap.getNodes();
    }


    public Edge getEdge(int edgeNumber){ return edgeArray[edgeNumber-1]; }
    private void setEdge(int index, Edge e){ edgeArray[index-1] =  e; }

    public boolean empty() {
        return size==0;
    }

    public int size(){
        return size;
    }

    public BinomialHeap getHeap() {
        return heap;
    }

    public void removeEdge(int edgeNumber) {
        edgeArray[edgeNumber-1] = null;
    }

    public Edge[] getEdges() {
        return edgeArray;
    }
}
