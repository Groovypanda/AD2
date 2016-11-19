package elements;

/**
 * Created by Jarre on 11/11/2016.
 */
public class CycleEdge2 {
    private Edge current;
    private CycleEdge2 realPrevious;
    private CycleEdge2 realNext;
    private CycleEdge2 visibleNext;
    private CycleEdge2 visiblePrevious;
    private CycleEdge2 tmpVisibleNext;
    private CycleEdge2 tmpVisiblePrevious;

    public CycleEdge2(Edge edge){
        edge.visit();
        this.realNext = null;
        current = edge;
    }

    public CycleEdge2(Edge edge, CycleEdge2 realPrevious){
        this(edge);
        realPrevious.connectNextEdge(this);
    }

    public Edge getEdge() {
        return current;
    }

    public void setNextEdge(CycleEdge2 edge){
        this.realNext = edge;
    }

    public CycleEdge2 getRealNext(){ return realNext; }

    public void setPreviousEdge(CycleEdge2 previous) {
        this.realPrevious = previous;
    }

    public CycleEdge2 getRealPrevious() { return realPrevious; }

    public void addNextCycleEdge(CycleEdge2 newCycleEdge){
        addCycleEdge(newCycleEdge, 1);
    }

    public void connectNextEdge(CycleEdge2 other){
        connectEdges(other, 1);
    }

    public void connectPreviousEdge(CycleEdge2 other){
        connectEdges(other, -1);
    }

    //1 nextEdge, -1 previousEdge
    private void addCycleEdge(CycleEdge2 newCycleEdge, int direction){
        if(direction==1){
            connectNextEdge(newCycleEdge);
        }
        else {
            connectPreviousEdge(newCycleEdge);
        }
        newCycleEdge.getEdge().visit();
    }

    //1 means other edge is the realNext edge, -1 means it's the realPrevious edge
    private void connectEdges(CycleEdge2 other, int direction){
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

    public CycleEdge2 getVisibleNext() {
        return visibleNext;
    }

    public void setVisibleNext(CycleEdge2 visibleNext){
        this.visibleNext = visibleNext;
    }

    public void setVisiblePrevious(CycleEdge2 visiblePrevious) {
        this.visiblePrevious = visiblePrevious;
    }

    public CycleEdge2 getTmpVisibleNext() {
        return tmpVisibleNext;
    }

    public void setTmpVisibleNext(CycleEdge2 tmpVisibleNext) {
        this.tmpVisibleNext = tmpVisibleNext;
    }

    public CycleEdge2 getTmpVisiblePrevious() {
        return tmpVisiblePrevious;
    }

    public void setTmpVisiblePrevious(CycleEdge2 tmpVisiblePrevious) {
        this.tmpVisiblePrevious = tmpVisiblePrevious;
    }

    public void update() {
        CycleEdge2 previous = getTmpVisiblePrevious();
        CycleEdge2 next = getTmpVisibleNext();
        setVisiblePrevious(previous);
        setVisibleNext(next);
        next.setVisiblePrevious(this);
        previous.setVisibleNext(this);
    }
}
