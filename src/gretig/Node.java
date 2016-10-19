package gretig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jarre on 8/10/2016.
 */
public class Node implements Comparable{

    private List<Edge> edges;
    private int edgesAmount;
    private int number;

    public Node(int number){
        edges = new ArrayList<>();
        this.number = number;
    }

    public void setEdgesAmount(int edgesAmount){
        this.edgesAmount = edgesAmount;
    }

    public void addEdge(Edge edge){
        edges.add(edge);
    }

    public int getNumber(){
        return number;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public int getEdgesAmount() {
        return edgesAmount;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Node ");
        sb.append(number);
        sb.append(" (");
        sb.append(edgesAmount);
        sb.append("): [");
        for(int i=0; i<edges.size()-1; i++){
            sb.append(edges.get(i));
            sb.append(", ");
        }
        sb.append(edges.get(edges.size()-1));
        sb.append("]");
        return sb.toString();
    }

    @Override
    public int compareTo(Object o) {
        assert(o instanceof Node);
        Node other = (Node)o;
        if(this.edgesAmount == other.edgesAmount){
            return 0;
        }
        return this.edgesAmount > other.edgesAmount ? -1 : 1;
    }
}
