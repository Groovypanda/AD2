package graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jarre on 8/10/2016.
 */
public class Node implements Comparable{

    private List<Edge> edges;
    private int number;
    private int index;
    private boolean visited = false;

    public Node(int number){
        edges = new ArrayList<>();
        this.number = number;
        index = number-1;
    }

    public void addEdge(Edge edge){
        edges.add(edge);
    }

    public void visitEdge(Edge edge){ edge.setVisited(); }

    public int getNumber(){
        return number;
    }

    public int getIndex() { return index; }

    public void setIndex(int index) { this.index = index; }

    public List<Edge> getEdges() {
        return edges;
    }

    public int getEdgesAmount() { return edges.size(); }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Node ");
        sb.append(number);
        sb.append(" (");
        sb.append(getEdgesAmount());
        sb.append("): [");
        for(int i = 0; i< edges.size()-1; i++){
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
        if(getEdgesAmount() == other.getEdgesAmount()){
            return 0;
        }
        return getEdgesAmount() > other.getEdgesAmount() ? -1 : 1;
    }

    public boolean equals(Object o){
        return o instanceof Node && ((Node) o).getNumber() == this.getNumber();
    }

    public void visitEdges() {
        for(Edge edge: edges){
            visitEdge(edge);
        }
    }

    public void visit() {
        visited = true;
    }

    public boolean visited() {
        return visited;
    }
}
