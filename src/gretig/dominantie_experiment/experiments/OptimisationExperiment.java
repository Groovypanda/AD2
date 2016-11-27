package gretig.dominantie_experiment.experiments;

import gretig.dominantie_experiment.ExperimentDominatingSetCalculator;
import gretig.graph.Graph;
import gretig.graph.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * An experiment concerning the optimisation level used in the algorithm.
 */
public class OptimisationExperiment extends DominanceExperiment {
    private int optimisationstart;
    private int getOptimalisationend;
    private int counter;

    public OptimisationExperiment(int optimisationstart, int optimisationend, int counter){
        super("Minimum Bedekking");
        this.optimisationstart = optimisationstart;
        this.getOptimalisationend = optimisationend;
        this.counter = counter;
        String[] columnNames = new String[(int) (Math.floor(optimisationend-optimisationstart)/counter)];
        for(int optimalisation=optimisationstart, i=0; optimalisation<optimisationend; optimalisation+=counter, i++){
            columnNames[i] = String.valueOf(optimalisation);
        }
        this.columnNames = columnNames;
    }

    @Override
    public List<List<Node>> run(Graph graph) {
        List<List<Node>> dominantLists = new ArrayList<>();
        for(int i = optimisationstart; i<getOptimalisationend; i+=counter){
            ExperimentDominatingSetCalculator setCalculator = new ExperimentDominatingSetCalculator(graph, i);
            dominantLists.add(setCalculator.getDominantList());
        }
        return dominantLists;
    }

}
