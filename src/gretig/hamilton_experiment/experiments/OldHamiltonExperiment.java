package gretig.hamilton_experiment.experiments;

import gretig.cycle.CycleNode;
import gretig.graph.Graph;
import gretig.calculators.HamiltonianCycleCalculator;
import gretig.calculators.HamiltonianCycleCalculatorOld;

/**
 * This experiment compares the 2 algorithms
 */
public class OldHamiltonExperiment extends HamiltonExperiment {
    public OldHamiltonExperiment() {
        super(new String[]{"Nieuw algoritme", "Oud algoritme"});
    }

    @Override
    public CycleNode[][] run(Graph graph) {
        CycleNode[][] nodes = new CycleNode[2][];
        HamiltonianCycleCalculator newcalc = new HamiltonianCycleCalculator(graph);
        HamiltonianCycleCalculatorOld oldcalc = new HamiltonianCycleCalculatorOld(graph);
         nodes[0] = newcalc.calculateCycle();
         nodes[1] = oldcalc.calculateCycle().cycleToCycleNodes();
         return nodes;
    }
}
