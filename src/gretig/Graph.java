package gretig;


import java.util.List;

/**
 * Created by Jarre on 8/10/2016.
 */
public class Graph {
    private Node[] nodes;
    private Edge[] vertices;

    public Graph(int nodes, int edges){
        this.nodes = new Node[nodes];
        this.vertices = new Edge[edges  ];
    }

    public Node[] getNodes(){
        return nodes;
    }

    public List<Edge> getVerticesFromNode(int node){
        //ArrayList start counting at 0 while nodes start counting at 1, so result -1.
        return nodes[node-1].getVertices();
    }

    public void addVertex(int node, int vertex){
        Node n = getNode(node);
        Edge v = getVertex(vertex);
        if(n==null) {
            n = new Node(node);
            setNode(node, n);
        }
        if(v==null){
            v = new Edge(vertex);
            setVertex(vertex, v);
        }
        n.addVertex(v);
        v.addNode(n);
    }

    public void printNodes(){
        for(Node node: nodes){
            System.out.println(node + ": " + node.getVertices());
        }
    }

    public void printVertices(){
        for(Edge edge : vertices){
            System.out.println(edge);
        }
    }

    private Edge getVertex(int number){ return vertices[number-1]; }
    private Node getNode(int number){ return nodes[number-1]; }
    private void setVertex(int index, Edge v){ vertices[index-1] =  v; }
    private void setNode(int index, Node node){ nodes[index-1] = node; }
}
