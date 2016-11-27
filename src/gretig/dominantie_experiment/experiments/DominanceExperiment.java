package gretig.dominantie_experiment.experiments;

import gretig.graph.Node;
import gretig.graph.Graph;

import java.util.List;



/**
 * A class which is expendable with experiments for the dominance algorithm.
 */
public abstract class DominanceExperiment {
    protected String title;

    protected String[] columnNames;

    public DominanceExperiment(String title, String[] columnNames){
        this.columnNames = columnNames;
        this.title = title;
    }

    public DominanceExperiment(String title){
        this.title = title;
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public String[] getColumnNames(){
        return columnNames;
    }

    public String getTitle() {
        return title;
    }

    public abstract List<List<Node>> run(Graph graph);


}
