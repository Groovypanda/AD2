package graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jarre on 8/10/2016.
 */
public class Node implements Comparable{

    private List<Integer> edgeNumbers;
    private int edgesAmount;
    private int number;
    private int index;

    public Node(int number){
        edgeNumbers = new ArrayList<>();
        this.number = number;
        index = number-1;
    }

    public void setEdgesAmount(int edgesAmount){
        this.edgesAmount = edgesAmount;
    }

    public void addEdgeNumber(int edge){
        edgeNumbers.add(edge);
    }

    public int getNumber(){
        return number;
    }

    public int getIndex() { return index; }

    public void setIndex(int index) { this.index = index; }

    public List<Integer> getEdgeNumbers() {
        return edgeNumbers;
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
        for(int i = 0; i< edgeNumbers.size()-1; i++){
            sb.append(edgeNumbers.get(i));
            sb.append(", ");
        }
        sb.append(edgeNumbers.get(edgeNumbers.size()-1));
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

    public boolean equals(Object o){
        return o instanceof Node && ((Node) o).getNumber() == this.getNumber();
    }
}
