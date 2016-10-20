package gretig;


import binomialheap.BinomialHeap;

/**
 * Created by Jarre on 8/10/2016.
 */
public class Graph {
    //Arrays for building the graph efficiently.
    private int size;
    private Edge[] edgeArray;
    private Node[] nodeArray;
    BinomialHeap heap;

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

    public void remove(Node n){
        int index = n.getIndex();
        nodeArray[index] = null;
        if(index!=size-1){
            nodeArray[index] = nodeArray[size-1];
            nodeArray[index].setIndex(index);
        }
        size--;
    }

    public void addNode(Node n) {
        heap.insert(n);
    }

    public void addVertex(Node n, int vertex){
        Edge v = getVertex(vertex);
        if(v==null){
            v = new Edge(vertex);
            setVertex(vertex, v);
        }
        n.addEdge(v);
        v.addNode(n);
    }

    public void initialiseNodes() {
        this.nodeArray = heap.getNodes();
    }


    private Edge getVertex(int number){ return edgeArray[number-1]; }
    private void setVertex(int index, Edge v){ edgeArray[index-1] =  v; }

    public boolean empty() {
        return size==0;
    }

    public int size(){
        return size;
    }

    public BinomialHeap getHeap() {
        return heap;
    }
}
