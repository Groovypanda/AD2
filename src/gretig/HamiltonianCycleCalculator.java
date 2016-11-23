package gretig;

import datastructures.DualGraph;
import datastructures.Graph;
import datastructures.PlaneArray;
import elements.*;

/**
 * Created by Jarre on 18/11/2016.
 */
public class HamiltonianCycleCalculator {
    int nodeAmount;
    private int cycleSize;

    public HamiltonianCycleCalculator(Graph graph) {
        nodeAmount = graph.getSize();
        YutsisDecomposer decomposer = new YutsisDecomposer(new DualGraph(graph));
        //As the dualgraph contains 2n-4 nodes (the amount of planes). We can try to make a yutsis decomposition.
        PlaneArray[] decomposition =  decomposer.findYutsisDecomposition();
        if(decomposition == null){
            System.out.println("Geen cykel gevonden");
        }
        else{
            CycleNode[] nodes = calculateCycle(decomposition[0]);
            if(cycleSize!=nodeAmount){
                System.out.println("Error");
                nodes = calculateCycle(decomposition[1]);
                if(cycleSize==nodeAmount){
                    System.out.println("De eerste keer werkte het niet?");
                }
            }
            if(cycleSize==nodeAmount) {
                CycleNode first = nodes[0];
                CycleNode cur = first;
                CycleNode next = first.getNeighbours()[0];
                CycleNode previous;
                boolean finished = false;
                while (!finished) {
                    System.out.print(cur + " ");
                    previous = cur;
                    cur = next;
                    next = cur.getNext(previous);
                    if (cur.equals(first)) {
                        finished = true;
                    }
                }
                System.out.println();
            }
            else {
                System.out.println("2 keer niet gevonden :(");
            }
        }
    }

    private CycleNode[] calculateCycle(PlaneArray array) {
        cycleSize = 0;
        CycleNode cycle[] = new CycleNode[nodeAmount];
        for(PlaneNode planeNode: array.getNodes()){
            Plane plane = planeNode.getPlane();
            for(Plane neighbour: plane.getAdjacentPlanes()){ //3 iterations
                if(!neighbour.getNode().isPresent(array)){
                    Node[] nodes = plane.getCommonEdge(neighbour).getNodes();
                    int i=0;
                    CycleNode[] cycleNodes = new CycleNode[2];
                    for(Node node: nodes){ //Create new CycleNode if the node doesn't have a cyclenode yet.
                        int index = node.getNumber()-1;
                        if(cycle[index]==null){
                            cycleNodes[i] = new CycleNode(node.getNumber());
                            cycle[index] = cycleNodes[i++];
                            cycleSize++;
                        }
                        else{
                            cycleNodes[i++] = cycle[index];
                        }
                    }
                    cycleNodes[0].addNeighbour(cycleNodes[1]);
                    cycleNodes[1].addNeighbour(cycleNodes[0]);
                }
            }
        }
        return cycle;
    }



}
