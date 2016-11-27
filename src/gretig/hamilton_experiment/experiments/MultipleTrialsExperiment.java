package gretig.hamilton_experiment.experiments;

import gretig.cycle.CycleNode;
import gretig.graph.Graph;
import gretig.hamilton_experiment.calculators.ExperimentHamiltonianCycleCalculator;

/**
 * In this experiment, we start in multiple planes for the yutsis-decomposition, until one is found or until the all of the trials
 * have been used.
 */
public class MultipleTrialsExperiment extends HamiltonExperiment {

    public MultipleTrialsExperiment() {
        super(new String[]{"1", "2","3","4","5"});
    }

    @Override
    public CycleNode[][] run(Graph graph) {
        CycleNode[][] nodes = new CycleNode[5][];
        ExperimentHamiltonianCycleCalculator multipletrialscalc = new ExperimentHamiltonianCycleCalculator(new Graph(graph));
        for(int i=1; i<=5; i++){
            nodes[i-1] = multipletrialscalc.calculateCycle(i);

        }
        return nodes;
    }

}
