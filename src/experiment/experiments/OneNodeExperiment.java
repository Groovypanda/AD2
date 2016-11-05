package experiment.experiments;

import experiment.ExperimentDominatingSetCalculator;
import experiment.experiments.Experiment;
import graph.Graph;
import graph.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jarre on 2/11/2016.
 */
public class OneNodeExperiment extends Experiment {

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
