package experiment;

import experiment.experiments.BetterExperiment;
import experiment.experiments.Experiment;
import graph.Node;
import gretig.DominatingSetCalculator;

import java.util.List;

/**
 * Created by Jarre on 2/11/2016.
 */
public class ExperimentRunner {

    public static String getLatexTable(Experiment experiment){
        return new LatexGenerator().getLatexTable(experiment);
    }

    public static void printOutcome(DominatingSetCalculator calculator){
        List<Node> dominantList = calculator.getDominantList();
        int dominantlistSize = dominantList.size();
        int graphSize = calculator.getGraph().getNodes().length;
        double percentage = ((double)dominantlistSize/(double)graphSize)*100;
        if(!calculator.isDominant(dominantList)){
            System.out.println("Something went horribly wrong!");
        }
        for(Node node: dominantList){
            int x = node.getNumber();
            System.out.println(node);
            //System.out.println(String.format("\\node[main node, fill=green] (%d) at (%d,%d) {%d};", x, (x-1)%8, 8-(x-1)/8, x));
        }

        System.out.println(String.format("%d/%d - %.2f", dominantlistSize, graphSize, percentage));
    }

    public static void main(String[] args) {
        System.out.println(getLatexTable(new BetterExperiment()));
    }
}
