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
        Node[] nodes = graph.getNodes();
        Edge[] edges = graph.getEdges();
        cycle = new Cycle();

        //Start algorithm
        Edge current = edges[1];
        CycleEdge firstCycleEdge = cycle.addEdge(current);
        Node[] edgeNodes = current.getNodes();
        Node startNode = edgeNodes[0];
        Node endNode = edgeNodes[1];

        Edge secondEdge = current.getNextEdge(endNode);

        CycleEdge secondCycleEdge = firstCycleEdge.addNextEdge(secondEdge);

        Edge thirdEdge = current.getPreviousEdge(startNode);

        CycleEdge thirdCycleEdge = firstCycleEdge.addPreviousEdge(thirdEdge);
        secondCycleEdge.connectNextEdge(thirdCycleEdge);

        size += 3;
        addEdges(firstCycleEdge, secondCycleEdge, thirdCycleEdge); //Add the edges between the first and the second cycle edge.
        addEdges(secondCycleEdge, thirdCycleEdge, firstCycleEdge);
        addEdges(thirdCycleEdge, firstCycleEdge, secondCycleEdge);
        return cycle;
    }

    //Recursive algorithm, always check other node in plane. If this hasn't been added, redirect cycle, and check first cycleEdge again
    //CycleEdge indicates the start
    public void addEdges(CycleEdge firstPlanarCycleEdge, CycleEdge lastCycleEdge1, CycleEdge lastCycleEdge2){
        //Every edge has 2 neighbouring planes, we are in one, we want to add the node of the other plane.
        Edge firstPlanarEdge = firstPlanarCycleEdge.getEdge();
        Node[] edgeNodes = firstPlanarEdge.getNodes();
        Node startNode = edgeNodes[0];
        Node endNode = edgeNodes[1];
        Edge secondPlanarEdge = firstPlanarEdge.getNextEdge(startNode);
        Edge thirdPlanarEdge = firstPlanarEdge.getPreviousEdge(endNode);
        //First -> thirdPlanarEdge is the other plane.
        if(!secondPlanarEdge.isVisited() && !thirdPlanarEdge.isVisited() && graph.getNodes().length > size){
            CycleEdge secondPlanarCycleEdge = lastCycleEdge2.addNextEdge(secondPlanarEdge);
            CycleEdge thirdPlanarCycleEdge = secondPlanarCycleEdge.addNextEdge(thirdPlanarEdge);
            lastCycleEdge1.connectPreviousEdge(thirdPlanarCycleEdge);
            cycle.setFirst(lastCycleEdge1);
            size++;
            addEdges(secondPlanarCycleEdge, thirdPlanarCycleEdge, lastCycleEdge2);
        }
    }
}
