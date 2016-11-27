package hamilton_experiment.experiments;

import cycle.CycleNode;
import dualgraph.*;
import graph.Graph;
import gretig.HamiltonianCycleCalculator;
import hamilton_experiment.calculators.ExperimentHamiltonianCycleCalculator;
import hamilton_experiment.decomposers.LSetExperimentYutsisDecomposer;

/**
 * This object represents an experiment in which we check the effect of a new set: nonVisitable in the algorithm.
 */
public class LSetExperiment extends HamiltonExperiment {

    public LSetExperiment() {
        super(new String[]{"Beste top uit L", "Willekeurige top uit L"});
    }

    @Override
    public CycleNode[][] run(Graph graph) {
        CycleNode[][] nodes = new CycleNode[2][];
        int size = graph.getSize();
        YutsisDecomposer nonoptimal = new LSetExperimentYutsisDecomposer(new DualGraph(new Graph(graph)));
        ExperimentHamiltonianCycleCalculator nonoptimalcalc = new ExperimentHamiltonianCycleCalculator(size);
        HamiltonianCycleCalculator optimalcalc = new HamiltonianCycleCalculator(graph);
        nodes[0] = optimalcalc.calculateCycle();
        nodes[1] = nonoptimalcalc.calculateCycle(nonoptimal);
        return nodes;
    }
}
