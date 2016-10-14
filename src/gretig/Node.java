package gretig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jarre on 8/10/2016.
 */
public class Node {
    private List<Edge> vertices;
    private int number;

    public Node(int number){
        vertices = new ArrayList<>();
        this.number = number;
    }

    public void addVertex(Edge edge){
        vertices.add(edge);
    }

    public List<Edge> getVertices(){
        return vertices;
    }

    public int getNumber(){
        return number;
    }

    public String toString(){
        return Integer.toString(number);
    }
}
