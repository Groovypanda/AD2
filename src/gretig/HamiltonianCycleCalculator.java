package gretig;

import graph.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jarre on 11/11/2016.
 */
public class HamiltonianCycleCalculator {

    /**
     * Maak 2 cykels, één met te bekijken bogen
     * en een met de volledige cykel
     *
     */

    private Cycle realCycle;
    private Cycle visitableCycle;

    //Unnecessary
    private Cycle cycle;
    private Graph graph;
    private int size;

    public HamiltonianCycleCalculator(Graph graph) {
        this.graph = graph;
        size = 0;
    }

    public Cycle getCycle(){
        Cycle realCycle = new Cycle(graph.getNodes().length);
        Cycle visitableCycle = new Cycle(graph.getNodes().length);
        Edge start = graph.getEdges()[0];
        initiateCycles(realCycle, visitableCycle, start);
        List<CycleEdge> addedCycleEdges = new ArrayList<>();
        while(!realCycle.isComplete() && !visitableCycle.empty()){
            CycleEdge visitableCurrent = visitableCycle.next();
            while(visitableCycle.hasNext()  && !realCycle.isComplete()){
                Edge[] plane = getAdjacentPlane(visitableCurrent.getEdge());
                if(!plane[1].getCommonNode(plane[2]).visited() && !plane[1].isVisited() && !plane[2].isVisited() && realCycle.getSize() < graph.getNodes().length){
                    realCycle.add(visitableCurrent.getPrevious(), new CycleEdge(plane[1]), new CycleEdge(plane[2]), visitableCurrent.getNext());
                    addedCycleEdges.add(new CycleEdge(plane[1], visitableCurrent.getPrevious()));
                    //visitableCycle.add(visitableCurrent.getPrevious(), new CycleEdge(plane[1]), new CycleEdge(plane[2]), visitableCurrent.getNext());
                }
                else {
                    visitableCycle.remove();
                }
                visitableCurrent = visitableCycle.next();
            }
            addedCycleEdges.clear();
        }

        return realCycle;
    }

    public Edge[] getAdjacentPlane(Edge edge){
        Edge[] plane = new Edge[3];
        plane[0] = edge;
        plane[1] = plane[0].getNextEdge(plane[0].getNodes()[0]);
        plane[2] = plane[0].getPreviousEdge(plane[0].getNodes()[1]);
        return plane;
    }


    /**
     * Initiates a new cycle with 3 edges of a graph.
     * @return A list of the edges of the cycle
     */
    public void initiateCycles(Cycle realCycle, Cycle visitableCycle, Edge start){
        //VisitableCycleEdges is a copy of the realCycle, but doesn't need to be doubly linked.
        CycleEdge[] cycleEdges = new CycleEdge[3];
        cycleEdges[0] = new CycleEdge(start);
        cycleEdges[1] = new CycleEdge(start.getNextEdge(start.getNodes()[1]));
        cycleEdges[2] = new CycleEdge(start.getPreviousEdge(start.getNodes()[0]));
        realCycle.add(cycleEdges[0], cycleEdges[1], cycleEdges[2]);
        CycleEdge[] visitableCycleEdges = new CycleEdge[3];
        visitableCycleEdges[0] = new CycleEdge(start);
        visitableCycleEdges[1] = new CycleEdge(start.getNextEdge(start.getNodes()[1]));
        visitableCycleEdges[2] = new CycleEdge(start.getPreviousEdge(start.getNodes()[0]));
        visitableCycle.add(cycleEdges[0]);
        visitableCycle.add(cycleEdges[0], cycleEdges[1]);
        visitableCycle.add(cycleEdges[1], cycleEdges[2]);
    }

    //Recursive algorithm, always check other node in plane. If this hasn't been added, redirect cycle, and check first cycleEdge again
    //CycleEdge indicates the start
    public void addEdges2(CycleEdge cycleEdge){
        Edge[] plane = new Edge[3];
        CycleEdge[] cycleEdges = new CycleEdge[3];
        //The neighbours are used for appending the new CycleEdges, the given cycleEdge will be removed if more nodes can be added.
        CycleEdge neighbour1 = cycleEdge.getNext();
        CycleEdge neighbour2 = cycleEdge.getPrevious();

        //Calculate the plane which has to be checked.
        plane[0] = cycleEdge.getEdge();
        plane[1] = plane[0].getNextEdge(plane[0].getNodes()[0]);
        plane[2] = plane[0].getPreviousEdge(plane[0].getNodes()[1]);


        //First -> thirdPlanarEdge is the other plane.
        if(!plane[1].getCommonNode(plane[2]).visited() && !plane[1].isVisited() && !plane[2].isVisited() && size < graph.getNodes().length){
            cycleEdges[0] = cycleEdge;
            cycleEdges[1] = new CycleEdge(plane[1]);
            cycleEdges[2] = new CycleEdge(plane[2]);
            neighbour2.addNextCycleEdge(cycleEdges[1]);
            cycleEdges[1].addNextCycleEdge(cycleEdges[2]);
            cycleEdges[2].addNextCycleEdge(neighbour1);
            cycle.setFirst(cycleEdges[1]);
            cycle.printCycle();
            size++;
            addEdges2(cycleEdges[1]);
            addEdges2(cycleEdges[2]);
        }
    }

    /**
     * Initiates a new cycle with 3 edges of a graph.
     * @return A list of the edges of the cycle
     */
    public CycleEdge[] initiateCycle2(){
        Edge[] edges = graph.getEdges();
        cycle = new Cycle(graph.getNodes().length);

        //Start algorithm
        CycleEdge[] cycleEdges = new CycleEdge[3];
        Edge current = edges[0];
        cycleEdges[0] = new CycleEdge(edges[0]);
        cycleEdges[1] = new CycleEdge(current.getNextEdge(current.getNodes()[1]));
        cycleEdges[2] = new CycleEdge(current.getPreviousEdge(current.getNodes()[0]));
        cycle.setFirst(cycleEdges[0]);
        cycleEdges[0].addNextCycleEdge(cycleEdges[1]);
        cycleEdges[1].addNextCycleEdge(cycleEdges[2]);
        cycleEdges[2].addNextCycleEdge(cycleEdges[0]);
        size+=3;
        return cycleEdges;
    }


}
