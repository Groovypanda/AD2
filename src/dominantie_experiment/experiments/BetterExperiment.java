package dominantie_experiment.experiments;

import dominantie_experiment.BetterDominatingSetCalculator;
import graph.Graph;
import graph.Node;
import gretig.DominatingSetCalculator;

import java.util.ArrayList;
import java.util.List;

/**
 * This experiment checks the difference between using the coverage or the actualcoverage in the algorithm.
 */
public class BetterExperiment extends DominanceExperiment {

    public BetterExperiment(){
        super("Vergelijking lineair - niet lineair gretig algoritme", new String[]{"Niet-lineair", "Lineair"});
    }

    public List<List<Node>> run(Graph graph) {
        DominatingSetCalculator calculator = new DominatingSetCalculator(graph);
        BetterDominatingSetCalculator betterCalculator = new BetterDominatingSetCalculator(graph);
        List<List<Node>> dominantLists = new ArrayList<>();
        dominantLists.add(betterCalculator.getDominantList());

        dominantLists.add(calculator.getDominantList());
        return dominantLists;
    }
}
