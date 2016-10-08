package gretig;


import java.util.List;

/**
 * Created by Jarre on 8/10/2016.
 */
public class Graph {
    private Node[] nodes;
    private Vertex[] vertices;

    public Graph(int nodes, int vertices){
        this.nodes = new Node[nodes];
        this.vertices = new Vertex[vertices];
    }

    public List<Vertex> getVerticesFromNode(int node){
        //ArrayList start counting at 0 while nodes start counting at 1, so result -1.
        return nodes[node-1].getVertices();
    }

    public void addVertex(int node, int vertex){
        Node n = getNode(node);
        Vertex v = getVertex(vertex);
        if(n==null) {
            n = new Node(node);
            setNode(node, n);
        }
        if(v==null){
            v = new Vertex(vertex);
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
        for(Vertex vertex: vertices){
            System.out.println(vertex);
        }
    }

    private Vertex getVertex(int number){ return vertices[number-1]; }
    private Node getNode(int number){ return nodes[number-1]; }
    private void setVertex(int index, Vertex v){ vertices[index-1] =  v; }
    private void setNode(int index, Node node){ nodes[index-1] = node; }
}
