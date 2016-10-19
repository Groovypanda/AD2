package gretig;


import java.util.PriorityQueue;

/**
 * Created by Jarre on 8/10/2016.
 */
public class Graph {
    //Arrays for building the graph efficiently.
    private int nodeAmount;
    private Edge[] edges;

    private PriorityQueue<Node> nodesQueue;

    public Graph(int nodes, int edges){
        nodeAmount = nodes;
        nodesQueue = new PriorityQueue<Node>(nodes);
        this.edges = new Edge[edges];
    }

    public void addNode(Node n){
        nodesQueue.add(n);
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
        for (Node node : nodesQueue) {
            System.out.println(node);
        }
    }

    public PriorityQueue<Node> getNodesQueue() {
        return nodesQueue;
    }

    private Edge getVertex(int number){ return edges[number-1]; }
    private void setVertex(int index, Edge v){ edges[index-1] =  v; }

    public boolean empty() {
        return false;
    }
}
