package graph;

/**
 * Created by Jarre on 11/11/2016.
 */
public class CycleEdge {
    private CycleEdge next;
    private Edge current;
    private CycleEdge previous;

    public CycleEdge(Edge edge){
        edge.visit();
        this.next = null;
        current = edge;
    }

    public Edge getEdge() {
        return current;
    }

    public void setNextEdge(CycleEdge edge){
        this.next = edge;
    }

    public CycleEdge getNext(){
        return next;
    }

    public void setPreviousEdge(CycleEdge previous) {
        this.previous = previous;
    }

    public CycleEdge getPrevious() {
        return previous;
    }

    public CycleEdge addNextEdge(Edge newEdge){
        return addEdge(newEdge, 1);
    }

    public CycleEdge addPreviousEdge(Edge newEdge){
        return addEdge(newEdge, -1);
    }



    public void connectNextEdge(CycleEdge other){
        connectEdges(other, 1);
    }

    public void connectPreviousEdge(CycleEdge other){
        connectEdges(other, -1);
    }

    //1 nextEdge, -1 previousEdge
    private CycleEdge addEdge(Edge newEdge, int direction){
        CycleEdge newCycleEdge = new CycleEdge(newEdge);
        if(direction==1){
            connectNextEdge(newCycleEdge);
        }
        else {
            connectPreviousEdge(newCycleEdge);
        }
        newEdge.visit();
        //size++;
        return newCycleEdge;
    }

    //1 means other edge is the next edge, -1 means it's the previous edge
    private void connectEdges(CycleEdge other, int direction){
        if(direction==1){
            setNextEdge(other);
            getNext().setPreviousEdge(this);
        }
        else {
            setPreviousEdge(other);
            getPrevious().setNextEdge(this);
        }
    }

    public String toString(){
        return current.toString();
    }
}
