package elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Jarre on 11/11/2016.
 */
public class Cycle implements Iterator<CycleEdge2> {

    private final int maxSize;
    private int size;
    private CycleEdge2 head;
    private CycleEdge2 current;
    private CycleEdge2 tail;

    private boolean visitedRealHead = false;
    private boolean visitedVisibleTail = false;
    private boolean visitedRealTail = false;
    private boolean visitedVisibleHead = false;

    private CycleEdge2 visibleHead;
    private CycleEdge2 visibleCurrent;
    private CycleEdge2 visibleTail;
    private CycleEdge2 tmpRealHead;
    private CycleEdge2 tmpRealTail;
    private CycleEdge2 tmpVisibleHead;
    private CycleEdge2 tmpVisibleTail;

    public Cycle(int amount){
        head = null;
        size = 0;
        maxSize = amount;
    }

    public void setHead(CycleEdge2 cycleEdge){
        head = cycleEdge;
    }

    public boolean hasNext() {
        return !visitedRealTail;
    }

    public CycleEdge2 next() {
        if(!visitedRealHead){
            current = head;
            visitedRealHead = true;
        }
        else {
            current = current.getRealNext();
        }
        if(current.equals(tail)){
            if(visitedRealTail){
                current = head;
                visitedRealTail = false;
                visitedRealHead = false;
            }
            else {
                visitedRealTail = true;
            }
        }
        return current;
    }

    public boolean isComplete(){
        return size == maxSize;
    }


    public List<Edge> getEdges(){
        CycleEdge2 current = head.getRealNext();
        List<Edge> edges = new ArrayList<>();
        edges.add(head.getEdge());
        while(!current.getEdge().equals(head.getEdge())){
            edges.add(current.getEdge());
            current = current.getRealNext();

        }
        return edges;
    }

    public void printVisibleCycle(){
        CycleEdge2 current = visibleHead.getVisibleNext();
        System.out.println("====== Visible edges: ======");
        System.out.println(visibleHead.getEdge());
        while(!current.getEdge().equals(visibleHead.getEdge())){
            System.out.println(current);
            current = current.getVisibleNext();

        }
    }

    public void printCycle(boolean extended) {
        System.out.println("====== Nodes ======");
        List<Edge> edges = getEdges();
        Edge edge = edges.get(0);
        Edge nextEdge = edges.get(1);
        Node second = edge.getCommonNode(nextEdge);
        Node first = edge.getNeighbour(second);
        System.out.print(first.getNumber() + " ");
        System.out.print(second.getNumber() + " ");
        for(int i=1; i<edges.size();i++){
            edge = edges.get(i);
            if(i+1==edges.size()){
                nextEdge = edges.get(0);
            }
            else {
                nextEdge = edges.get(i+1);
            }
            Node currentNode = edge.getCommonNode(nextEdge);
            System.out.print(currentNode.getNumber() + " ");
        }
        System.out.println();
        if(extended) {
            System.out.println("====== Edges ======");
            for (Edge edge1 : edges) {
                System.out.println(edge1);
            }
        }
    }

    public void add(CycleEdge2 current) {
        head = current;
        tail = current;
        size++;
    }

    public void add(CycleEdge2 previous, CycleEdge2 current) {
        previous.connectNextEdge(current);
        size++;
    }

    public void add(CycleEdge2 previous, CycleEdge2 current, CycleEdge2 next){
        previous.addNextCycleEdge(current);
        current.addNextCycleEdge(next);
        next.addNextCycleEdge(previous);
        size+=3;
    }

    public void add(CycleEdge2 previous, CycleEdge2 current1, CycleEdge2 current2, CycleEdge2 next){
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
        return !visitedVisibleTail;
    }

    public CycleEdge2 visibleNext() {
        if(!visitedVisibleHead){
            visibleCurrent = visibleHead;
            visitedVisibleHead = true;
        }
        else {
            visibleCurrent = visibleCurrent.getVisibleNext();
        }
        if(visibleCurrent.equals(visibleTail)){
            visitedVisibleTail = true;
        }
        return visibleCurrent;
    }

    public CycleEdge2 getVisibleHead() { return visibleHead; }

    public void setVisibleHead(CycleEdge2 visibleHead) { this.visibleHead = visibleHead; }

    public void setVisibleTail(CycleEdge2 visibleTail) { this.visibleTail = visibleTail; }

    public CycleEdge2 getRealHead() { return head; }

    public void setTmpRealHead(CycleEdge2 tmpRealHead) { this.tmpRealHead = tmpRealHead; }

    public void setTmpRealTail(CycleEdge2 tmpRealTail) { this.tmpRealTail = tmpRealTail; }

    public void setTmpVisibleHead(CycleEdge2 tmpVisibleHead) { this.tmpVisibleHead = tmpVisibleHead; }

    public void setTmpVisibleTail(CycleEdge2 tmpVisibleTail) { this.tmpVisibleTail = tmpVisibleTail; }

    public void update() {
        if(tmpRealHead!=null) {
            head = tmpRealHead;
            tail = tmpRealTail;
        }
        if(tmpVisibleHead!=null) {
            visibleHead = tmpVisibleHead;
            visibleTail = tmpVisibleTail;
        }
        current = head;
        visibleCurrent = visibleHead;
        visitedVisibleTail = false;
        visitedVisibleHead = false;
    }

    public void setTail(CycleEdge2 tail) {
        this.tail = tail;
    }

    public CycleEdge2 getTmpVisibleHead() {
        return tmpVisibleHead;
    }

    public CycleEdge2 getTmpRealHead() {
        return tmpRealHead;
    }

    public CycleEdge2 getRealTail() {
        return tail;
    }

    public CycleEdge2 getTmpRealTail() {
        return tmpRealTail;
    }

    public CycleEdge2 getTmpVisibleTail() {
        return tmpVisibleTail;
    }

    public CycleEdge2 getVisibleTail() {
        return visibleTail;
    }
}
