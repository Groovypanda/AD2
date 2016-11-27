package hamilton_experiment.experiments;

import cycle.CycleNode;

import java.util.List;

import graph.Graph;
import graph.Node;

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
