package gretig.dominantie_experiment.experiments;

import gretig.dominantie_experiment.ExperimentDominatingSetCalculator;
import gretig.graph.Edge;
import gretig.graph.Graph;
import gretig.graph.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * An experiment concerning the sorting order used in the dominance algorithm.
 */
public class SortExperiment extends DominanceExperiment {

    public SortExperiment(){
        super("Ordening", new String[]{"Geen ordening", "Stijgende volgorde", "Dalende volgorde"});
    }

    @Override
    public List<List<Node>> run(Graph graph) {
        SortExperimentDominatingSetCalculator setCalculator = new SortExperimentDominatingSetCalculator(graph);
        List<List<Node>> dominantLists = new ArrayList<>();
        dominantLists.add(setCalculator.getDominantList(false)); //Geen ordening
        dominantLists.add(setCalculator.getDominantList(true)); //Stijgende volgorde
        dominantLists.add(setCalculator.getDominantList()); //Dalende volgorde
        return dominantLists;
    }

    private class SortExperimentDominatingSetCalculator extends ExperimentDominatingSetCalculator {

        public SortExperimentDominatingSetCalculator(Graph graph) {
            super(graph);
        }

        /**
         * An experimental version of the getDominantList method from DominatingSetCalculator.
         *
         * @param sorted If this is true, the array will be sorted with highest degree first, else the list won't be sorted.
         * @return A dominant list
         */
        public List<Node> getDominantList(boolean sorted) {
            if (sorted) {
                graph.sort(false); //O(n+k)
            }
            prepareDominantList(); //O(n)
            for (int i = optimization; i >= 0; i--) {
                buildDominantList(i); //O(n)
            }
            List<Node> list = new ArrayList<>(dominantList);
            reset(); //Return to initial conditions. In initialiseNode to use this calculator again for the same graph.
            return list;
        }

        /**
         * Experimental prepareDominantList method, which runs trough all nodes. So this doesn't affect the results of the ordering.
         */
        @Override
        protected void prepareDominantList() {
            /**
             * This method when using a sorted array (by degree) with nodes because the nodes with degree one will have the
             * highest index. Once we reach a node with degree > 1, the loop may end.
             */
            Node[] nodes = graph.getSortedNodes();
            int index = 0; //Starting at the lowest index. (This node has the lowest degree.)
            for (Node v : nodes) {
                if (v.getDegree() == 1) { //Loop trough nodes with degree 1.
                    Edge edge = v.getEdges().get(0); //Get the only edge of the node. (degree 1 => 1 edge)
                    Node w = edge.getNeighbour(v);
                    //If coverage==0, adding the node wouldn't be logic as it wouldn't increase the reach of the dominant set.
                    if (w.getCoverage() > 0) {
                        dominantList.add(w); //This node can safely be added to the dominant set.
                        totalCoverage += w.visit(); //Visits a node and if it could be visited increment the totalCoverage.
                        totalCoverage += w.visitNeighbours(); //Adds total coverage which can be added by visiting the neighbours.
                        w.clearCoverage(); //Clear the coverage as w is added and can't increase the totalCoverage anymore.
                    }
                    v.clearCoverage(); //v only had one neighbour, so its safe to clear the coverage of v aswell.
                }
            }
        }
    }
}
