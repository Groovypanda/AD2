package gretig.dominantie_experiment;

import gretig.dominantie_experiment.experiments.BetterExperiment;
import gretig.dominantie_experiment.experiments.DominanceExperiment;
import gretig.graph.Node;
import gretig.calculators.DominatingSetCalculator;

import java.util.List;

/**
 * A class usable to run different experiments.
 */
public class ExperimentRunner {

    public static String getLatexTable(DominanceExperiment experiment){
        return new LatexGenerator().getLatexTable(experiment);
    }

    public static void printOutcome(DominatingSetCalculator calculator){
        List<Node> dominantList = calculator.getDominantList();
        int dominantlistSize = dominantList.size();
        int graphSize = calculator.getGraph().getNodes().length;
        double percentage = ((double)dominantlistSize/(double)graphSize)*100;
        for(Node node: dominantList){
            System.out.println(node);
        }

        System.out.println(String.format("%d/%d - %.2f", dominantlistSize, graphSize, percentage));
    }

    public static void main(String[] args) {
        System.out.println(getLatexTable(new BetterExperiment()));
    }
}
