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
    private CycleEdge head;
    private CycleEdge current;
    private CycleEdge tail;

    private boolean visitedHead = false;

    private CycleEdge visibleHead;
    private CycleEdge visibleCurrent;
    private CycleEdge visibleTail;

    public Cycle(int amount){
        head = null;
        size = 0;
        maxSize = amount;
    }

    public void setHead(CycleEdge cycleEdge){
        head = cycleEdge;
    }

    @Override
    public boolean hasNext() {
        return !current.equals(tail);
    }

    @Override
    public CycleEdge next() {
        if(current==null){
            current = head;
            return current;
        }
        current = current.getRealNext();
        if(head.equals(current)){
        }
        return current;
    }

    /*
    @Override
    public void remove(){
        if(current == null){
            current = head;
        }
        CycleEdge previous = current.getRealPrevious();
        CycleEdge next = current.getRealNext();
        if(previous!=null){
            previous.connectNextEdge(next);
        }
        if(head.equals(current)){
            head = next;
        }
        size--;
    }
    */

    public boolean isComplete(){
        return size == maxSize;
    }


    public List<Edge> getEdges(){
        CycleEdge current = head.getRealNext();
        List<Edge> edges = new ArrayList<>();
        edges.add(head.getEdge());
        while(!current.getEdge().equals(head.getEdge())){
            edges.add(current.getEdge());
            current = current.getRealNext();

        }
        return edges;
    }

    public void printCycle() {
        System.out.println("====== Cykel ======");
        CycleEdge current = next();
        Node second = current.getRealNext().getEdge().getCommonNode(current.getEdge());
        Node first = current.getEdge().getNeighbour(second);
        System.out.print(first.getNumber() + " ");
        System.out.print(second.getNumber() + " ");
        while(hasNext()){
            current = next();
            Edge currentEdge = current.getEdge();
            Edge nextEdge = current.getRealNext().getEdge();
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
        head = current;
        tail = current;
        size++;
    }

    public void add(CycleEdge previous, CycleEdge current) {
        //SET HEAD AND TAIL?
        previous.connectNextEdge(current);
        size++;
    }

    public void add(CycleEdge previous, CycleEdge current, CycleEdge next){
        head = previous;
        tail = previous.getRealPrevious();
        previous.addNextCycleEdge(current);
        current.addNextCycleEdge(next);
        next.addNextCycleEdge(previous);
        size+=3;
    }

    public void add(CycleEdge previous, CycleEdge current1, CycleEdge current2, CycleEdge next){
        head = previous;
        tail = previous.getRealPrevious();
        previous.addNextCycleEdge(current1);
        current1.addNextCycleEdge(current2);
        current2.addNextCycleEdge(next);
        size++;
    }

    public boolean empty(){
        return head == null;
    }

    public int getSize() {
        return size;
    }

    public boolean hasVisibleNext() {
        if(current == null){
            current = visibleHead;
        }
        boolean hasNext =  !visitedHead || !current.equals(visibleHead);
        if(!hasNext){
            visitedHead = false;
        }
        return hasNext;
    }

    public CycleEdge visibleNext() {
        current = current.getVisibleNext();
        if(current.equals(visibleHead)){
            visitedHead = true;
        }
        return current;
    }

    public CycleEdge getVisibleHead() {
        return visibleHead;
    }

    public void setVisibleHead(CycleEdge visibleHead) {
        this.visibleHead = visibleHead;
    }

    public CycleEdge getVisibleTail() {
        return visibleTail;
    }

    public void setVisibleTail(CycleEdge visibleTail) {
        this.visibleTail = visibleTail;
    }
}
