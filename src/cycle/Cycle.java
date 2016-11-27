package cycle;

import graph.Edge;
import graph.Node;
import gretig.HamiltonianCycleCalculator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Important: a class only used by the first algorithm.
 * Implements iterator as it's possible to iterate over its cycleEdges.
 * This class has 3 kinds of edges as explained in the hamiltonianCycleCalculatorOld class.
 * This class represents a (not necessarily hamiltonian) cycle in a graph.
 * The cycle is implemented as a linked list.
 * If an edge is prepended with tmp (=temporary), it means it's not immediately set as the actual version of this edge,
 * and will only become that version later. (If it hasn't changed by reaching the update method).
 */
public class Cycle implements Iterator<CycleEdge> {

    private final int maxSize; //Maximum size of this cycle.
    private int size; //Current size of this cycle.
    private CycleEdge head; //First edge in this cycle.
    private CycleEdge current; //Current edge in this cycle. (When iterating over its cycleEdges)
    private CycleEdge tail; //Last edge in this cycle.

    //These booleans are used for iterating trough the edges.
    private boolean visitedRealHead = false;
    private boolean visitedVisibleTail = false;
    private boolean visitedRealTail = false;
    private boolean visitedVisibleHead = false;

    //These fields indicate the visible and tmpvisible version of the head, current and tail field.
    //There's also a tmpRealHead and -tail.
    private CycleEdge visibleHead;
    private CycleEdge visibleCurrent;
    private CycleEdge visibleTail;
    private CycleEdge tmpRealHead;
    private CycleEdge tmpRealTail;
    private CycleEdge tmpVisibleHead;
    private CycleEdge tmpVisibleTail;

    /**
     * Initialises a new empty cycle.
     * @param amount Maximum amount of edges in this cycle.
     */
    public Cycle(int amount){
        head = null;
        size = 0;
        maxSize = amount;
    }

    /**
     * Sets the head of this cycle.
     * @param cycleEdge The new head.
     */
    public void setHead(CycleEdge cycleEdge){
        head = cycleEdge;
    }

    /**
     * Tells if current has a next edge which isn't the head.
     * @return True if iteration hasn't finished.
     */
    public boolean hasNext() {
        return !visitedRealTail;
    }

    /**
     * Gets the next edge in this cycle.
     * @return the next edge.
     */
    public CycleEdge next() {
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

    /**
     * @return True if the cycle is full.
     */
    public boolean isComplete(){
        return size == maxSize;
    }


    /**
     * @return All of the edges in this cycle.
     */
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

    /**
     * Prints the visible edges of this cycle to stdout.
     */
    public void printVisibleCycle(){
        CycleEdge current = visibleHead.getVisibleNext();
        System.out.println("====== Visible edges: ======");
        System.out.println(visibleHead.getEdge());
        while(!current.getEdge().equals(visibleHead.getEdge())){
            System.out.println(current);
            current = current.getVisibleNext();

        }
    }

    /**
     * Prints the nodes of this cycle to stdout.
     */
    public void printCycle() {
        List<Edge> edges = getEdges();
        Edge edge = edges.get(0);
        Edge nextEdge = edges.get(1);
        Node second = edge.getCommonNode(nextEdge);
        Node first = edge.getNeighbour(second);
        System.out.print(first.getNumber() + " ");
        System.out.print(second.getNumber() + " ");
        for(int i=1; i<edges.size()-1;i++){
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
    }

    /**
     * Creates an array of cycleNodes which represents the cycle
     * @return The array of cycleNodes, null if not hamiltonian.
     */
    public CycleNode[] cycleToCycleNodes(){
        if(size!=maxSize){
            return null;
        }
        List<Edge> edges = getEdges();
        CycleNode[] nodes = new CycleNode[maxSize];
        int i = 0;
        Edge edge = edges.get(0);
        Edge nextEdge = edges.get(1);
        Node next = edge.getCommonNode(nextEdge);
        Node current = edge.getNeighbour(next);
        CycleNode currentCycleNode = new CycleNode(current);
        CycleNode nextCycleNode = new CycleNode(next);
        currentCycleNode.addNeighbour(nextCycleNode);
        nextCycleNode.addNeighbour(currentCycleNode);
        nodes[i++] = currentCycleNode;
        nodes[i++] = nextCycleNode;
        for(int j=1; j<edges.size()-1;j++){
            edge = edges.get(j);
            if(j+1==edges.size()){
                nextEdge = edges.get(0);
            }
            else {
                nextEdge = edges.get(j+1);
            }
            currentCycleNode = nextCycleNode;
            next = edge.getCommonNode(nextEdge);
            nextCycleNode = new CycleNode(next);
            currentCycleNode.addNeighbour(nextCycleNode);
            nextCycleNode.addNeighbour(currentCycleNode);
            nodes[i++] = nextCycleNode;
        }
        nodes[0].addNeighbour(nodes[i-1]);
        nodes[i-1].addNeighbour(nodes[0]);
        return nodes;
    }

    /**
     * Adds the current edge to this cycle and connects the current edge with the previous and next edge.
     * @param previous The previous edge of current
     * @param current The new edge
     * @param next The next edge of current
     */
    public void add(CycleEdge previous, CycleEdge current, CycleEdge next){
        previous.addNextCycleEdge(current);
        current.addNextCycleEdge(next);
        next.addNextCycleEdge(previous);
        size+=3;
    }

    /**
     * Adds 2 new edges to this cycle and connects these with each other.
     * @param previous The previous edge of current1.
     * @param current1 The first new edge.
     * @param current2 The second new edge.
     * @param next The edge next of current2.
     */
    public void add(CycleEdge previous, CycleEdge current1, CycleEdge current2, CycleEdge next){
        previous.addNextCycleEdge(current1);
        current1.addNextCycleEdge(current2);
        current2.addNextCycleEdge(next);
        size++;
    }

    public int getSize() {
        return size;
    }

    /**
     * Returns whether the iteration trough the visible next edges has finished or not.
     * @return True if there is a next visible edges.
     */
    public boolean hasVisibleNext() {
        return !visitedVisibleTail;
    }

    /**
     * @return The next visible edge.
     */
    public CycleEdge visibleNext() {
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

    /**
     * @return The first visible edge of this cycle.
     */
    public CycleEdge getVisibleHead() { return visibleHead; }

    /**
     * Sets the new visible head;
     * @param visibleHead The new visible head
     */
    public void setVisibleHead(CycleEdge visibleHead) { this.visibleHead = visibleHead; }

    /**
     * Sets the new visible tail
     * @param visibleTail The new visible tail
     */
    public void setVisibleTail(CycleEdge visibleTail) { this.visibleTail = visibleTail; }

    /**
     * @return The actual head of this cycle.
     */
    public CycleEdge getRealHead() { return head; }

    /**
     * Sets a new temporary head for this cycle.
     * @param tmpRealHead The new head .
     */
    public void setTmpRealHead(CycleEdge tmpRealHead) { this.tmpRealHead = tmpRealHead; }

    /**
     * Sets a new temporary tail for this cycle.
     * @param tmpRealTail
     */
    public void setTmpRealTail(CycleEdge tmpRealTail) { this.tmpRealTail = tmpRealTail; }

    /**
     * Sets a new temporary visible head for this cycle.
     * @param tmpVisibleHead
     */
    public void setTmpVisibleHead(CycleEdge tmpVisibleHead) { this.tmpVisibleHead = tmpVisibleHead; }

    /**
     * Sets a new temporary visible tail for this cycle.
     * @param tmpVisibleTail
     */
    public void setTmpVisibleTail(CycleEdge tmpVisibleTail) { this.tmpVisibleTail = tmpVisibleTail; }

    /**
     * Updates all of the fields which have a temporary version with their temporary version.
     * Use this once an iteration in the algorithm has ended.
     * This also resets the current field to the head.
     */
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

    /**
     * Sets the tail of this cycle.
     * @param tail
     */
    public void setTail(CycleEdge tail) {
        this.tail = tail;
    }

    /**
     * @return The temporary visible head of this cycle.
     */
    public CycleEdge getTmpVisibleHead() {
        return tmpVisibleHead;
    }

    /**
     * @return The temporary head of this cycle.
     */
    public CycleEdge getTmpRealHead() {
        return tmpRealHead;
    }

    /**
     * @return The tail of this cycle.
     */
    public CycleEdge getRealTail() {
        return tail;
    }

    /**
     * @return The temporary tail of this cycle.
     */
    public CycleEdge getTmpRealTail() {
        return tmpRealTail;
    }

    /**
     * @return The temporary visible tail of this cycle.
     */
    public CycleEdge getTmpVisibleTail() {
        return tmpVisibleTail;
    }

    /**
     * @return The visible tail of this cycle.
     */
    public CycleEdge getVisibleTail() {
        return visibleTail;
    }
}
