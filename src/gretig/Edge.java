package gretig;

/**
 * Created by Jarre on 8/10/2016.
 */
public class Edge {
    int number;
    Node nodes[];

    public Edge(int number){
        this.number = number;
        //Every vertex connects 2 nodes.
        nodes = new Node[2];
    }

    public void addNode(Node node){
        assert(nodes[1]==null);
        if(nodes[0]==null){
            nodes[0] = node;
        } else {
            nodes[1] = node;
        }
    }

    //Returns the 2 connected nodes by this vertex.
    public Node[] getNodes(){
        return nodes;
    }

    public String toString(){
        return "Edge " + Integer.toString(number) + " (" + Integer.toString(nodes[0].getNumber()) + "," + Integer.toString(nodes[1].getNumber()) + ")";
    }
}
