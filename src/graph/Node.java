package graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jarre on 8/10/2016.
 */
public class Node {

    private List<Edge> edges;
    private int number;
    private boolean visited = false;
    private int coverage;

    public Node(int number){
        edges = new ArrayList<>();
        this.number = number;
        coverage = 1; //Every node covers itself.
    }

    public void addEdge(Edge edge){
        edges.add(edge);
        coverage++;
    }

    public int getCoverage(){
        return coverage;
    };

    public void decrementCoverage(){
        coverage--;
    }

    public void clearCoverage(){
        coverage = 0;
    }

    public int getNumber(){
        return number;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public int getEdgesAmount() { return edges.size(); }

    public void visit() {
        visited = true;
    }

    public boolean visited() {
        return visited;
    }

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

    public boolean equals(Object o){
        return o instanceof Node && ((Node) o).getNumber() == this.getNumber();
    }

}
