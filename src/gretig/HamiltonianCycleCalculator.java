package gretig;

import graph.*;

/**
 * Created by Jarre on 11/11/2016.
 */
public class HamiltonianCycleCalculator {

    private Cycle cycle;
    private Graph graph;
    private int size = 0;

    public HamiltonianCycleCalculator(Graph graph) {
        this.graph = graph;
    }

    public Cycle getCycle(){
        Edge[] edges = graph.getEdges();
        cycle = new Cycle();

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
        addEdges(cycleEdges[0]);
        addEdges(cycleEdges[1]);
        addEdges(cycleEdges[2]);
        return cycle;
    }

    //Recursive algorithm, always check other node in plane. If this hasn't been added, redirect cycle, and check first cycleEdge again
    //CycleEdge indicates the start
    public void addEdges(CycleEdge cycleEdge){
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
        if(!plane[1].isVisited() && !plane[2].isVisited() && size < graph.getNodes().length){
            cycleEdges[0] = cycleEdge;
            cycleEdges[1] = new CycleEdge(plane[1]);
            cycleEdges[2] = new CycleEdge(plane[2]);
            neighbour2.addNextCycleEdge(cycleEdges[1]);
            cycleEdges[1].addNextCycleEdge(cycleEdges[2]);
            cycleEdges[2].addNextCycleEdge(neighbour1);
            cycle.setFirst(cycleEdges[1]);
            size++;
            addEdges(cycleEdges[1]);
            addEdges(cycleEdges[2]);
        }
    }
}
