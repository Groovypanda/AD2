package cycle;

import graph.Edge;

/**
 * Created by Jarre on 11/11/2016.
 */
public class CycleEdge {
    private Edge current;
    private CycleEdge realPrevious;
    private CycleEdge realNext;
    private CycleEdge visibleNext;
    private CycleEdge visiblePrevious;
    private CycleEdge tmpVisibleNext;
    private CycleEdge tmpVisiblePrevious;

    public CycleEdge(Edge edge){
        edge.visit();
        this.realNext = null;
        current = edge;
    }

    public CycleEdge(Edge edge, CycleEdge realPrevious){
        this(edge);
        realPrevious.connectNextEdge(this);
    }

    public Edge getEdge() {
        return current;
    }

    public void setNextEdge(CycleEdge edge){
        this.realNext = edge;
    }

    public CycleEdge getRealNext(){ return realNext; }

    public void setPreviousEdge(CycleEdge previous) {
        this.realPrevious = previous;
    }

    public CycleEdge getRealPrevious() { return realPrevious; }

    public void addNextCycleEdge(CycleEdge newCycleEdge){
        addCycleEdge(newCycleEdge, 1);
    }

    public void connectNextEdge(CycleEdge other){
        connectEdges(other, 1);
    }

    public void connectPreviousEdge(CycleEdge other){
        connectEdges(other, -1);
    }

    //1 nextEdge, -1 previousEdge
    private void addCycleEdge(CycleEdge newCycleEdge, int direction){
        if(direction==1){
            connectNextEdge(newCycleEdge);
        }
        else {
            connectPreviousEdge(newCycleEdge);
        }
        newCycleEdge.getEdge().visit();
    }

    //1 means other edge is the realNext edge, -1 means it's the realPrevious edge
    private void connectEdges(CycleEdge other, int direction){
        if(direction==1){
            setNextEdge(other);
            getRealNext().setPreviousEdge(this);
        }
        else {
            setPreviousEdge(other);
            getRealPrevious().setNextEdge(this);
        }
    }

    public String toString(){
        return current.toString();
    }

    public CycleEdge getVisibleNext() {
        return visibleNext;
    }

    public void setVisibleNext(CycleEdge visibleNext){
        this.visibleNext = visibleNext;
    }

    public void setVisiblePrevious(CycleEdge visiblePrevious) {
        this.visiblePrevious = visiblePrevious;
    }

    public CycleEdge getTmpVisibleNext() {
        return tmpVisibleNext;
    }

    public void setTmpVisibleNext(CycleEdge tmpVisibleNext) {
        this.tmpVisibleNext = tmpVisibleNext;
    }

    public CycleEdge getTmpVisiblePrevious() {
        return tmpVisiblePrevious;
    }

    public void setTmpVisiblePrevious(CycleEdge tmpVisiblePrevious) {
        this.tmpVisiblePrevious = tmpVisiblePrevious;
    }

    public void update() {
        CycleEdge previous = getTmpVisiblePrevious();
        CycleEdge next = getTmpVisibleNext();
        setVisiblePrevious(previous);
        setVisibleNext(next);
        next.setVisiblePrevious(this);
        previous.setVisibleNext(this);
    }
}
