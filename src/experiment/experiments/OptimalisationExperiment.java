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
public class OptimalisationExperiment extends Experiment {
    private int optimalisationstart;
    private int getOptimalisationend;
    private int counter;

    public OptimalisationExperiment(int optimalisationstart, int optimalisationend, int counter){
        super("Minimum Bedekking");
        this.optimalisationstart = optimalisationstart;
        this.getOptimalisationend = optimalisationend;
        this.counter = counter;
        String[] columnNames = new String[(int) (Math.floor(optimalisationend-optimalisationstart)/counter)];
        for(int optimalisation=optimalisationstart, i=0; optimalisation<optimalisationend; optimalisation+=counter, i++){
            columnNames[i] = String.valueOf(optimalisation);
        }
        this.columnNames = columnNames;
    }

    @Override
    public List<List<Node>> run(Graph graph) {
        List<List<Node>> dominantLists = new ArrayList<>();
        for(int i=optimalisationstart; i<getOptimalisationend; i+=counter){
            ExperimentDominatingSetCalculator setCalculator = new ExperimentDominatingSetCalculator(graph, i);
            dominantLists.add(setCalculator.getDominantList());
        }
        return dominantLists;
    }

}
