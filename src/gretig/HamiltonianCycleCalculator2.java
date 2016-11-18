package gretig;

import datastructures.Graph;
import elements.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jarre on 11/11/2016.
 */
public class HamiltonianCycleCalculator2 {
    private Graph graph;

    public HamiltonianCycleCalculator2(Graph graph) {
        this.graph = graph;
    }

    public Cycle getCycle(){
        Cycle cycle = new Cycle(graph.getNodes().length);
        initiateCycle(cycle, graph.getEdges()[3]);
        //cycle.printCycle(false);
        List<CycleEdge> addedCycleEdges = new ArrayList<>();
        List<CycleEdge> removedCycleEdges = new ArrayList<>();
        while(!cycle.isComplete()){
            //System.out.println("==== Iteration =====");
            while(cycle.hasVisibleNext()){
                CycleEdge visibleCurrent = cycle.visibleNext();
                //System.out.println(visibleCurrent);
                Edge[] plane = getAdjacentPlane(visibleCurrent.getEdge());
                boolean isAddable = !plane[1].getCommonNode(plane[2]).visited() && !plane[1].isVisited()
                        && !plane[2].isVisited() && cycle.getSize() < graph.getNodes().length;
                if(isAddable){
                    //Create the 2 new CycleEdges.
                    CycleEdge current1 = new CycleEdge(plane[1]);
                    CycleEdge current2 = new CycleEdge(plane[2]);
                    //Add them to the cycle.
                    addCycleEdges(cycle, visibleCurrent, current1, current2);
                    //Add them to the added list.
                    addedCycleEdges.add(current1);
                    addedCycleEdges.add(current2);
                }
                else {
                    //Delete visibleCurrent from visible CycleEdges.
                    removeVisibleCycleEdge(cycle, visibleCurrent);
                    removedCycleEdges.add(visibleCurrent);
                }
            }
            //If no edges can't be visited anymore, we have to stop.
            if(addedCycleEdges.size()==0){
                return cycle;
            }
            //Amount of iterations are less or equal to the amount of (inner) while loop iterations of the previous loop.
            for(CycleEdge addedCycleEdge: addedCycleEdges){
                addedCycleEdge.update();
            }
            cycle.update();
            addedCycleEdges.clear();
            removedCycleEdges.clear();
            //cycle.printCycle(false);
            //cycle.printVisibleCycle();
        }

        return cycle;
    }

    private void removeVisibleCycleEdge(Cycle cycle, CycleEdge toRemove) {
        toRemove.getTmpVisiblePrevious().setTmpVisibleNext(toRemove.getTmpVisibleNext());
        toRemove.getTmpVisibleNext().setTmpVisiblePrevious(toRemove.getTmpVisiblePrevious());
        checkCycleHeadAndTail(cycle, toRemove, toRemove.getRealPrevious());
        checkVisibleCycleHeadAndTail(cycle, toRemove, toRemove.getTmpVisiblePrevious());
    }

    private void addCycleEdges(Cycle cycle, CycleEdge toRemove, CycleEdge toAdd1, CycleEdge toAdd2) {
        //Add the cycleEdge to the cycle.
        cycle.add(toRemove.getRealPrevious(), toAdd1, toAdd2, toRemove.getRealNext());
        //Update the temporary visible values. These values will be used as visible cycleEdges after looping over the current
        //visible values.
        toAdd1.setTmpVisiblePrevious(toRemove.getTmpVisiblePrevious());
        toAdd1.setTmpVisibleNext(toAdd2);
        toAdd2.setTmpVisiblePrevious(toAdd1);
        toAdd2.setTmpVisibleNext(toRemove.getTmpVisibleNext());
        toRemove.getTmpVisiblePrevious().setTmpVisibleNext(toAdd1);
        toRemove.getTmpVisibleNext().setTmpVisiblePrevious(toAdd2);
        //Check if any head or tail isn't in the cycle anymore.
        checkCycleHeadAndTail(cycle, toRemove, toAdd1);
        checkVisibleCycleHeadAndTail(cycle, toRemove, toAdd1);
    }

    public Edge[] getAdjacentPlane(Edge edge){
        Edge[] plane = new Edge[3];
        plane[0] = edge;
        plane[1] = plane[0].getNextEdge(plane[0].getNodes()[0]);
        plane[2] = plane[0].getPreviousEdge(plane[0].getNodes()[1]);
        if(plane[1].isVisited() && plane[2].isVisited()){
            //cycleEdges[1] = new CycleEdge(start.getNextEdge(start.getNodes()[1]));
            //cycleEdges[2] = new CycleEdge(start.getPreviousEdge(start.getNodes()[0]));
            plane[1] = plane[0].getNextEdge(plane[0].getNodes()[1]);
            plane[2] = plane[0].getPreviousEdge(plane[0].getNodes()[0]);
        }
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
            cycleEdge.setTmpVisiblePrevious(cycleEdge.getRealPrevious());
            cycleEdge.setVisibleNext(cycleEdge.getRealNext());
            cycleEdge.setTmpVisibleNext(cycleEdge.getRealNext());
        }
        cycle.setHead(cycleEdges[0]);
        cycle.setTail(cycleEdges[2]);
        cycle.setVisibleHead(cycleEdges[0]);
        cycle.setVisibleTail(cycleEdges[2]);
    }

    public void checkCycleHeadAndTail(Cycle cycle, CycleEdge current, CycleEdge newHead){
        if(cycle.getRealHead().equals(current) || cycle.getTmpRealHead().equals(current) ||
                cycle.getRealTail().equals(current) || cycle.getTmpRealTail().equals(current)){
            cycle.setTmpRealHead(newHead);
            cycle.setTmpRealTail(newHead.getRealPrevious());
        }
    }

    public void checkVisibleCycleHeadAndTail(Cycle cycle, CycleEdge current, CycleEdge newHead){
        if(cycle.getVisibleHead().equals(current) || cycle.getTmpVisibleHead().equals(current) ||
                cycle.getVisibleTail().equals(current) || cycle.getTmpVisibleTail().equals(current)){
            cycle.setTmpVisibleHead(newHead);
            cycle.setTmpVisibleTail(newHead.getTmpVisiblePrevious());
        }
    }
}
