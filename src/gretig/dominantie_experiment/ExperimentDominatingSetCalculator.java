package gretig.dominantie_experiment;

import gretig.graph.Graph;
import gretig.graph.Node;
import gretig.calculators.DominatingSetCalculator;

import java.util.ArrayList;
import java.util.List;

/**
 * A dominatingSetCalculator usable for running the different experiments.
 */
public class ExperimentDominatingSetCalculator extends DominatingSetCalculator {
    public ExperimentDominatingSetCalculator(Graph graph, int optimization) {
        super(graph, optimization);
    }
    public ExperimentDominatingSetCalculator(Graph graph){ super(graph); }

    public List<Node> getDominantList(){
        List<Node> list = new ArrayList<>(super.getDominantList());
        reset();
        return list;
    }


    public void reset(){
        graph.reset();
        totalCoverage = 0;
        dominantList.clear();
    }

}
