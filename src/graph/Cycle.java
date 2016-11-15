package graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Jarre on 11/11/2016.
 */
public class Cycle implements Iterator<CycleEdge> {

    private final int maxSize;
    private int size;
    private CycleEdge first;
    private CycleEdge current;
    private boolean visitedFirst = false;
    int cycleEdgeIndex;

    public Cycle(int amount){
        first = null;
        size = 0;
        cycleEdgeIndex = 0;
        maxSize = amount;
    }

    public void setFirst(CycleEdge cycleEdge){
        first = cycleEdge;
    }

    @Override
    public boolean hasNext() {
        return !visitedFirst || !current.equals(first);
    }

    @Override
    public CycleEdge next() {
        if(current==null){
            visitedFirst = false;
            current = first;
            return current;
        }
        current = current.getNext();
        if(first.equals(current)){
            visitedFirst = true;
        }
        return current;
    }

    @Override
    public void remove(){
        if(current == null){
            current = first;
        }
        CycleEdge previous = current.getPrevious();
        CycleEdge next = current.getNext();
        if(previous!=null){
            previous.connectNextEdge(next);
        }
        if(first.equals(current)){
            first = next;
        }
        size--;
    }

    public boolean isComplete(){
        return size == maxSize;
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

    public void printCycle() {
        System.out.println("====== Cykel ======");
        CycleEdge current = next();
        Node second = current.getNext().getEdge().getCommonNode(current.getEdge());
        Node first = current.getEdge().getNeighbour(second);
        System.out.print(first.getNumber() + " ");
        System.out.print(second.getNumber() + " ");
        while(hasNext()){
            current = next();
            Edge currentEdge = current.getEdge();
            Edge nextEdge = current.getNext().getEdge();
            Node currentNode = currentEdge.getCommonNode(nextEdge);
            System.out.print(currentNode.getNumber() + " ");
        }
        System.out.println();
        System.out.println("====== Edges ======");
        for(Edge edge: getEdges()){
            System.out.println(edge);
        }
    }

    public void add(CycleEdge current) {
        first = current;
        size++;
    }

    public void add(CycleEdge previous, CycleEdge current) {
        previous.connectNextEdge(current);
        size++;
    }

    public void add(CycleEdge previous, CycleEdge current, CycleEdge next){
        first = previous;
        previous.addNextCycleEdge(current);
        current.addNextCycleEdge(next);
        next.addNextCycleEdge(previous);
        size+=3;
    }

    public void add(CycleEdge previous, CycleEdge current1, CycleEdge current2, CycleEdge next){
        first = previous;
        previous.addNextCycleEdge(current1);
        current1.addNextCycleEdge(current2);
        current2.addNextCycleEdge(next);
        size++;
    }

    public boolean empty(){
        return first == null;
    }

    public int getSize() {
        return size;
    }
}
