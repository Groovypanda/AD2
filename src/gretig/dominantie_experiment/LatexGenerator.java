package gretig.dominantie_experiment;

import gretig.dominantie_experiment.experiments.DominanceExperiment;
import gretig.graph.Graph;
import gretig.graph.Node;
import gretig.input.BinaryFileReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/**
 * A class usable for generating output in latex format from the experiments
 */
public class LatexGenerator {
    /**
     * Runs an experiment and displays a resume of the statistics.
     * @param experiment The experiment which needs to be run.
     */
    public void printTotalStastics(DominanceExperiment experiment){
        BinaryFileReader reader = new BinaryFileReader();
        int[][] resume = new int[experiment.getColumnNames().length][2];
        try(Stream<Path> paths = Files.walk(Paths.get(System.getProperty("user.dir"), "data", "testset"))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) try {
                    Graph graph = reader.readByteArray(Files.readAllBytes(filePath)).get(0); //There's only 1 graph in the testset graphs.
                    List<List<Node>> dominantLists = experiment.run(graph);
                    int i = 0;
                    for(List<Node> dominantList: dominantLists){
                        resume[i][0] += dominantList.size();
                        resume[i][1] += graph.getNodes().length;
                        i++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        int i = 0;
        for(int[] statistic: resume){
            System.out.print(i + " & ");
            System.out.print(String.format("%.4f", ((double)statistic[0]/(double)statistic[1])*100)); //Prints the planeAmount of the dominant list in proportion with |V(G)|
            System.out.println("\\% \\\\ \\hline");
            i++;
        }
    }

    /*
     * Run an experiment and get the statistics as a LaTeXtable.
     * @param experiment The experiment which needs to be run.
     * @return A string contain a latex table with the output of the experiment.
     */
    public String getLatexTable(DominanceExperiment experiment){
        BinaryFileReader reader = new BinaryFileReader();
        final String[] latexString = {createLatexTable(experiment)};
        try(Stream<Path> paths = Files.walk(Paths.get(System.getProperty("user.dir"), "data", "testset"))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) try {
                    Graph graph = reader.readByteArray(Files.readAllBytes(filePath)).get(0); //There's only 1 graph in the testset graphs.
                    latexString[0] += buildLatexRow(filePath.getFileName().toString(), graph, experiment.run(graph));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        latexString[0] += finishedLatexTable();
        return latexString[0];
    }

    private String createLatexTable(DominanceExperiment experiment) {
        String tableString = "\\begin{table}[H]\n\\centering\n";
        tableString += "\\begin{tabular}{|l|";
        for(int i = 0; i<experiment.getColumnNames().length; i++){
            //tableString+= "c|c|";
            tableString+= "c|";
        }
        tableString+="}\n";
        tableString+="\\hline\n";
        //tableString+="& \\multicolumn{" + 2*experiment.getColumnNames().length + "}{c|}{" + experiment.getTitle() + "}" + getLineEnd();
        tableString+="& \\multicolumn{" + experiment.getColumnNames().length + "}{c|}{" + experiment.getTitle() + "}" + getLineEnd();
        tableString+="Graaf";
        for(String columnName: experiment.getColumnNames()){
            //tableString+= String.format(" & \\multicolumn{2}{c|}{%s}", columnName);
            tableString+= String.format(" & %s ", columnName);
        }
        tableString += getLineEnd();
        return tableString;
    }

    private String getLineEnd(){
        return "\t\t\\\\ \\hline\n";
    }

    /**
     * Adds the data from the map to the latexString.
     * @param filename The name of the file used for the current data.
     * @param dominantLists An List of dominant lists.
     */
    private String buildLatexRow(String filename, Graph graph, List<List<Node>> dominantLists){
        String row = filename;
        int min = dominantLists.get(0).size();
        int minIndex = 0;
        int i;
        for(i=1; i<dominantLists.size(); i++){
            List<Node> dominantList = dominantLists.get(i);
            if(dominantList.size()<min){
                minIndex = i;
                min = dominantList.size();
            }
        }
        i = 0;
        for(List<Node> dominantList: dominantLists){
            int dominantlistSize = dominantList.size();
            int graphSize = graph.getNodes().length;
            double percentage = ((double)dominantlistSize/(double)graphSize)*100;
            //row += String.format("& %d/%d", dominantlistSize, graphSize);
            row+= " & ";
            if(i == minIndex){
                //row += " \\cellcolor{LimeGreen} & \\cellcolor{LimeGreen} ";
                row+=" \\cellcolor{LimeGreen}";
            }
            else {
                //row += " & ";
            }
            row += String.format("%.2f", percentage);
            row += "\\%";
            i++;
        }
        row += getLineEnd();
        return row;
    }

    private String finishedLatexTable(){
        return "\\end{tabular}\n\\end{table}";
    }
}
