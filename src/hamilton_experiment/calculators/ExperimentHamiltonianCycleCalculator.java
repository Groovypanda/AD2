package hamilton_experiment.calculators;

import cycle.CycleNode;
import datastructures.PlaneNodeArray;
import dualgraph.DualGraph;
import dualgraph.YutsisDecomposer;
import graph.Graph;
import gretig.HamiltonianCycleCalculator;
import hamilton_experiment.decomposers.StatisticsYutsisDecomposer;

/**
 * An experimental hamiltonian cycle calculator.
 */
public class ExperimentHamiltonianCycleCalculator extends HamiltonianCycleCalculator{

    public ExperimentHamiltonianCycleCalculator(Graph graph) {
        super(graph);
    }

    public ExperimentHamiltonianCycleCalculator(int size){
        super(size);
    }

    /**
     * Calculate the cycle with the given decomposer.
     * @param decomposer
     * @return A hamiltonian cycle if one was found, null if not.
     */
    public CycleNode[] calculateCycle(YutsisDecomposer decomposer) {
        return calculateCycle(decomposer.findYutsisDecomposition(0));
    }

    /**
     * Calculate the cycle in multiple trials
     * @param trials The amount of trials
     * @return A hamiltonian cycle if one was found, null if not.
     */
    public CycleNode[] calculateCycle(int trials) {

        PlaneNodeArray tree = null;
        boolean founddecomp = false;
        for(int i=0; i<trials & !founddecomp; i++){
            DualGraph dualGraph = new DualGraph(new Graph(graph));
            YutsisDecomposer decomposer = new YutsisDecomposer(dualGraph);
            tree = decomposer.findYutsisDecomposition(i); //Take with the given index first in the algorithm.
            if(tree != null){
                founddecomp = true;
            }
        }
        return calculateCycle(tree);
    }

    /**
     * @return An array with 2 elements: the first element is the size of the tree,
     *         the second element is the maximum size of the tree. (|DG|/2, DG is the dual graph)
     */
    public int[] getDecompositionStatistics(){
        DualGraph dualGraph = new DualGraph(graph);
        YutsisDecomposer decomposer = new StatisticsYutsisDecomposer(dualGraph);
        PlaneNodeArray M = decomposer.findYutsisDecomposition(0);
        int[] result = new int[2];
        result[0] = M.length();
        result[1] = dualGraph.getSize()/2;
        return result;
    }
}
