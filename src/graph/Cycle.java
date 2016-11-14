package graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jarre on 11/11/2016.
 */
public class Cycle {

    private CycleEdge first;
    private int size;

    public Cycle(){
        first = null;
        size = 0;
    }

    public List<Edge> getEdges(){
        CycleEdge current = first.getNext();
        List<Edge> edges = new ArrayList<>();
        edges.add(first.getEdge());
        while(!current.getEdge().equals(first.getEdge())){
            edges.add(current.getEdge());
            current = current.getNext();

        }
        return edges;
    }

    public CycleEdge addEdge(Edge newEdge){
        if(first==null){
            first = new CycleEdge(newEdge);
            newEdge.visit();
            //size++;
            return first;
        }
        return null;
    }

    public void printCycle() {
        System.out.println("Cykel");
        for(Edge edge: getEdges()){
            System.out.println(edge);
        }
        System.out.println("=======");
    }

    public void setFirst(CycleEdge first) {
        this.first = first;
    }
}
