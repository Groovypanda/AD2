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
        Cycle cycle = new Cycle(graph.getNodes().length);
        initiateCycle(cycle, graph.getEdges()[0]);
        List<CycleEdge> addedCycleEdges = new ArrayList<>();
        List<CycleEdge> removedCycleEdges = new ArrayList<>();
        while(!cycle.isComplete() && !cycle.empty()){
            while(cycle.hasVisibleNext()  && !cycle.isComplete()){
                CycleEdge visibleCurrent = cycle.visibleNext();
                Edge[] plane = getAdjacentPlane(visibleCurrent.getEdge());
                if(!plane[1].getCommonNode(plane[2]).visited() && !plane[1].isVisited() && !plane[2].isVisited() && cycle.getSize() < graph.getNodes().length){
                    CycleEdge current1 = new CycleEdge(plane[1]);
                    CycleEdge current2 = new CycleEdge(plane[2]);
                    cycle.add(visibleCurrent.getRealPrevious(), current1, current2, visibleCurrent.getRealNext());
                    current1.setTmpVisiblePrevious(visibleCurrent);
                    current1.setTmpVisibleNext(current2);
                    current2.setTmpVisiblePrevious(current1);
                    current2.setTmpVisibleNext(visibleCurrent.getVisibleNext());
                    addedCycleEdges.add(current1);
                    addedCycleEdges.add(current2);
                }
                else {
                    removedCycleEdges.add(visibleCurrent);
                }
            }
            //Samen evenveel iteraties als vorige while loop.
            for(CycleEdge addedCycleEdge: addedCycleEdges){
                //NULLPOINTER
                addedCycleEdge.setVisiblePrevious(addedCycleEdge.getTmpVisiblePrevious());
                addedCycleEdge.setVisibleNext(addedCycleEdge.getTmpVisibleNext());
            }
            for(CycleEdge removedCycleEdge: removedCycleEdges){
                removedCycleEdge.getVisiblePrevious().setVisibleNext(removedCycleEdge.getVisibleNext());
                if(removedCycleEdge.equals(cycle.getVisibleHead())){
                    cycle.setVisibleHead(removedCycleEdge.getVisiblePrevious());
                    cycle.setVisibleTail(removedCycleEdge.getVisibleNext());
                }
                if(removedCycleEdge.equals(cycle.getVisibleTail())){
                    cycle.setVisibleHead(removedCycleEdge.getVisibleNext());
                    cycle.setVisibleTail(removedCycleEdge.getVisiblePrevious());
                }
            }
            addedCycleEdges.clear();
            removedCycleEdges.clear();
        }

        return cycle;
    }

    public Edge[] getAdjacentPlane(Edge edge){
        Edge[] plane = new Edge[3];
        plane[0] = edge;
        plane[1] = plane[0].getNextEdge(plane[0].getNodes()[0]);
        plane[2] = plane[0].getPreviousEdge(plane[0].getNodes()[1]);
        return plane;
    }


    public void initiateCycle(Cycle cycle, Edge start){
        CycleEdge[] cycleEdges = new CycleEdge[3];
        cycleEdges[0] = new CycleEdge(start);
        cycleEdges[1] = new CycleEdge(start.getNextEdge(start.getNodes()[1]));
        cycleEdges[2] = new CycleEdge(start.getPreviousEdge(start.getNodes()[0]));
        cycle.add(cycleEdges[0], cycleEdges[1], cycleEdges[2]);
        for(CycleEdge cycleEdge: cycleEdges){
            cycleEdge.setVisiblePrevious(cycleEdge.getRealPrevious());
            cycleEdge.setVisibleNext(cycleEdge.getRealNext());
        }
        cycle.setVisibleHead(cycleEdges[0]);
        cycle.setVisibleTail(cycleEdges[2]);
    }

    //Recursive algorithm, always check other node in plane. If this hasn't been added, redirect cycle, and check first cycleEdge again
    //CycleEdge indicates the start
    public void addEdges2(CycleEdge cycleEdge){
        Edge[] plane = new Edge[3];
        CycleEdge[] cycleEdges = new CycleEdge[3];
        //The neighbours are used for appending the new CycleEdges, the given cycleEdge will be removed if more nodes can be added.
        CycleEdge neighbour1 = cycleEdge.getRealNext();
        CycleEdge neighbour2 = cycleEdge.getRealPrevious();

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
            cycle.setHead(cycleEdges[1]);
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
        cycle.setHead(cycleEdges[0]);
        cycleEdges[0].addNextCycleEdge(cycleEdges[1]);
        cycleEdges[1].addNextCycleEdge(cycleEdges[2]);
        cycleEdges[2].addNextCycleEdge(cycleEdges[0]);
        size+=3;
        return cycleEdges;
    }


}
