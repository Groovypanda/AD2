package graph;


import nodearray.NodeArray;

/**
 * Created by Jarre on 8/10/2016.
 */
public class Graph {
    private Edge[] edgeArray;
    private Node[] nodes;
    private NodeArray nodeArray;

    //Binomial heap with highest elements at back and always pull from back????

    public Graph(int nodeAmount, int edgeAmount){
        this.edgeArray = new Edge[edgeAmount];
        this.nodes = new Node[nodeAmount];
        nodeArray = new NodeArray(nodeAmount, edgeAmount);
    }

    public void addNode(Node n) {
        nodeArray.addNode(n);
        nodes[n.getNumber()-1] = n;
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
        return nodes;
    }

    public void sort(boolean backwards){ nodeArray.sort(backwards);}
    public Node[] getSortedNodes(){return nodeArray.getSortedNodes();}

    public Edge getEdge(int edgeNumber){ return edgeArray[edgeNumber-1]; }
    private void setEdge(int index, Edge e){ edgeArray[index-1] =  e; }

    public void addAll() {
        nodeArray.addAll();
    }
}
