package graph;


import nodearray.NodeArray;

/**
 * Created by Jarre on 8/10/2016.
 */
public class Graph {
    //Arrays for building the graph efficiently.
    private int size;
    private Edge[] edgeArray;
    private NodeArray nodes;

    //Binomial heap with highest elements at back and always pull from back????

    public Graph(int nodeAmount, int edgeAmount){
        this.size = nodeAmount;
        this.edgeArray = new Edge[edgeAmount];
        this.nodes = new NodeArray(size, edgeAmount);
    }

    public Node pull(){
        return nodes.pull();
    }

    public void addNode(Node n) {
        nodes.addNode(n);
    }

    public void addVertex(Node n, int edge){
        Edge e = getEdge(edge);
        if(e==null){
            e = new Edge(edge);
            setEdge(edge, e);
        }
        n.addEdge(e);
        e.addNode(n);
    }

    public Node[] getNodes(){
        return nodes.getNodes();
    }

    public void sort(){nodes.sort();}
    public Edge getEdge(int edgeNumber){ return edgeArray[edgeNumber-1]; }
    public Edge[] getEdges(){ return edgeArray; }
    private void setEdge(int index, Edge e){ edgeArray[index-1] =  e; }
    public int size(){ return size; }
    public void removeEdge(int edgeNumber) {
        edgeArray[edgeNumber-1] = null;
    }
}
