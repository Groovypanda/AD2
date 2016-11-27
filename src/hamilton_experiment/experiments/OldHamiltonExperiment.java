package hamilton_experiment.experiments;

import cycle.CycleNode;
import graph.Graph;
import gretig.HamiltonianCycleCalculator;
import hamilton_experiment.calculators.HamiltonianCycleCalculatorOld;

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
