package gretig.dominantie_experiment.experiments;

import gretig.dominantie_experiment.ExperimentDominatingSetCalculator;
import gretig.graph.Graph;
import gretig.graph.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * An experiment concerning prepending the algorithm with adding the neighbours of 1 degree nodes.
 */
public class OneNodeExperiment extends DominanceExperiment {

    public OneNodeExperiment(){
        super("Toppen van graad 1", new String[]{"Zonder optimalisatie", "Met optimalisatie"});
    }

    @Override
    public List<List<Node>> run(Graph graph) {
        OneNodeExperimentDominatingSetCalculator setCalculator1 = new OneNodeExperimentDominatingSetCalculator(graph);
        ExperimentDominatingSetCalculator setCalculator2 = new ExperimentDominatingSetCalculator(graph);
        List<List<Node>> dominantLists = new ArrayList<>();
        dominantLists.add(setCalculator1.getDominantList()); //Geen optimalisatie
        dominantLists.add(setCalculator2.getDominantList()); //Wel optimalisatie
        return dominantLists;
    }

    private class OneNodeExperimentDominatingSetCalculator extends ExperimentDominatingSetCalculator {

        public OneNodeExperimentDominatingSetCalculator(Graph graph) {
            super(graph);
        }

        /**
         * An experimental version of the getDominantList method from DominatingSetCalculator.
         *
         * @return A dominant list
         */
        public List<Node> getDominantList() {
            graph.sort(true); //O(n+k)
            for (int i = optimization; i >= 0; i--) {
                buildDominantList(i); //O(n)
            }
            List<Node> list = new ArrayList<>(dominantList);
            reset(); //Return to initial conditions. In order to use this calculator again for the same graph.
            return list;
        }

        /**
         * Experimental prepareDominantList method, which runs trough all nodes. So this doesn't affect the results of the ordering.
         */
    }
}
