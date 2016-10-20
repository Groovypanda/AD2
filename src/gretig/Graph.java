package gretig;


import java.util.PriorityQueue;

/**
 * Created by Jarre on 8/10/2016.
 */
public class Graph {
    //Arrays for building the graph efficiently.
    private int size;
    private int maxSize;
    private int start = 0;
    private Edge[] edges;
    private Node[] nodes;

    //Binomial heap with highest elements at back and always pull from back????

    public Graph(int nodes, int edges){
        size = nodes;
        maxSize = nodes;
        this.edges = new Edge[edges];
        this.nodes = new Node[nodes];
    }

    public Node getNode(int index){
        return nodes[index];
    }

    public void addNode(Node n){
        //only remove if there hasn't been removed yet.
        assert(size==maxSize);
        nodes[n.getIndex()] = n;
    }

    public void remove(Node n){
        int index = n.getIndex();
        nodes[index] = nodes[size-1];
        nodes[index].setIndex(index);
        size--;
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

    public void printNodes() {
        for (Node node : nodes) {
            System.out.println(node);
        }
    }

    public Node[] getNodes() {
        return nodes;
    }

    private Edge getVertex(int number){ return edges[number-1]; }
    private void setVertex(int index, Edge v){ edges[index-1] =  v; }

    public boolean empty() {
        return size==0;
    }

    public int size(){
        return size;
    }
}
