package gretig.dominantie_experiment;

import gretig.graph.Edge;
import gretig.graph.Graph;
import gretig.graph.Node;

/**
 * This is a DominatingSetCalculator used for making better minimal dominating set approximations.
 * It doesn't use a variable coverage anymore, but just calculates the actual coverage for every node in every step.
 * This calculator doesn't run in linear time anymore.
 */
public class BetterDominatingSetCalculator extends ExperimentDominatingSetCalculator {
    public BetterDominatingSetCalculator(Graph graph) {
        super(graph);
    }

    /**
     * Prepare the dominant list by removing nodes which are neighbours of nodes with degree 1.
     * This implementation doesn't affect the coverage anymore.
     */
    protected void prepareDominantList() {
        Node[] nodes = graph.getSortedNodes();
        int index = 0;
        Node v = nodes[index];
        while(v.getDegree()==1){
            Edge edge = v.getEdges().get(0);
            Node w = edge.getNeighbour(v);
            if (w.getActualCoverage()!=0) {
                dominantList.add(w);
                totalCoverage += w.visit();
                totalCoverage += w.visitNeighbours();
            }
            v = nodes[index++];
        }
    }


    protected void buildDominantList(int minimumNodeCoverage) {
        Node[] nodes = graph.getSortedNodes();
        for(int i=0; i<nodes.length && totalCoverage < nodes.length; i++){
            Node v = nodes[i];
            if (v.getActualCoverage() > 0) {
                Node maxNode = v;
                int maxCoverage = maxNode.getActualCoverage();
                for (Edge edge : v.getEdges()) {
                    Node w = edge.getNeighbour(v);
                    if (w.getActualCoverage() > maxCoverage) {
                        maxNode = w;
                        maxCoverage = w.getActualCoverage();
                    }
                }
                if (maxCoverage > minimumNodeCoverage) {
                    //Update the graph
                    totalCoverage += maxCoverage;
                    dominantList.add(maxNode);
                    maxNode.visit();
                    maxNode.visitNeighbours();

                }
            }
        }
    }
}
