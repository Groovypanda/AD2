package gretig.hamilton_experiment.experiments;

import gretig.cycle.CycleNode;

import gretig.graph.Graph;

/**
 * A class extendable with hamilton experiments.
 */
public abstract class HamiltonExperiment {
    protected String[] columnNames;

    public HamiltonExperiment(String[] columnNames){
        this.columnNames = columnNames;
    }

    public String[] getColumnNames(){
        return columnNames;
    }

    public abstract CycleNode[][] run(Graph graph);
}
