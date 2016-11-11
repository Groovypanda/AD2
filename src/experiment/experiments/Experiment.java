package experiment.experiments;

import graph.Node;
import graph.Graph;

import java.util.List;



/**
 * Created by Jarre on 2/11/2016.
 */
public abstract class Experiment {
    protected String title;

    protected String[] columnNames;

    public Experiment(String title, String[] columnNames){
        this.columnNames = columnNames;
        this.title = title;
    }

    public Experiment(String title){
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
