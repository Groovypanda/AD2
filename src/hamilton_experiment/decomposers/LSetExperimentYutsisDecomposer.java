package hamilton_experiment.decomposers;

import dualgraph.*;
import datastructures.PlaneNodeArray;
import graph.Plane;

/**
 * A Yutsis decomposer in which one set for L is used.
 */
public class LSetExperimentYutsisDecomposer extends YutsisDecomposer {
    protected PlaneNodeArray L;

    public LSetExperimentYutsisDecomposer(DualGraph graph){ super(graph); }

    public PlaneNode getHighestLNode() {
        while (!L.empty()) { //Start with array with highest amount of unvisited neighbours.
            PlaneNode maxNode = L.pull();
            if (maxNode.neighbourAmount(M) == 1){
                return maxNode;
            } else {
                nonVisitable.add(maxNode);
            }
        }
        return null;
    }

    protected void update(PlaneNode node) {
        for(PlaneNode neighbour: node.getNeighbours()){
            if(neighbour.isPresent(V)){
                neighbour.switchTo(L);
            }
        }
    }

    @Override
    protected void initializeYutsisArrays() {
        V = new PlaneNodeArray("V", graph.getSize());
        M = new PlaneNodeArray("M", graph.getSize()/2);
        L = new PlaneNodeArray("L", graph.getSize());
        nonVisitable = new PlaneNodeArray("Non-visitable", graph.getSize());
        for(Plane plane: graph.getPlanes()){ //Linear time, and is called once.
            if(plane!=null){
                V.add(plane.getNode());
            }
        }
    }
}
