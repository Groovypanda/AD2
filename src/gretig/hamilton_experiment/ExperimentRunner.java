package gretig.hamilton_experiment;

import gretig.cycle.CycleNode;
import gretig.graph.Graph;
import gretig.hamilton_experiment.experiments.HamiltonExperiment;
import gretig.hamilton_experiment.experiments.LSetExperiment;
import gretig.input.BinaryFileReader;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * A class used for running hamilton experiments.
 */
public class ExperimentRunner {
    private final static char COLUMN_DELIMETER = ';';
    private final static String SMALL_TRIANG_DIR = "triang_klein";

    public static void main(String[] args) {
        String fileName = "LSetExperiment.csv";
        HamiltonExperiment experiment = new LSetExperiment();
        Map<String, List<CycleNode[][]>> result = runExperiment(SMALL_TRIANG_DIR, experiment);
        generateCSVFile(result, fileName, experiment.getColumnNames());
    }

    /**
     * Runs an experiment on all the graphs in the given directory.
     * @param experiment The experiment to run
     * @param directory The name of the directory with the files for the experiment.
     * @return A map with key the filename and value the result of that file.
     */
    public static Map<String, List<CycleNode[][]>> runExperiment(String directory, HamiltonExperiment experiment){
        BinaryFileReader reader = new BinaryFileReader();
        Map<String, List<Graph>> graphsMap = reader.getDirectoryGraphsExtended(directory);
        Map<String, List<CycleNode[][]>> result = new TreeMap<>();
        for (Map.Entry<String, List<Graph>> entry : graphsMap.entrySet()) {
            String filename = entry.getKey();
            List<Graph> fileGraphs = entry.getValue();
            List<CycleNode[][]> nodesList = new ArrayList<>();
            for(Graph graph: fileGraphs){
                nodesList.add(experiment.run(graph));
            }
            result.put(filename, nodesList);
        }
        return result;
    }

    /**
     * Generates a CSV file with the results of the experiment.
     * @param result The results
     * @param fileName The output file name.
     */
    public static void generateCSVFile(Map<String, List<CycleNode[][]>> result, String fileName, String[] columnTitles){
        try {
            FileWriter writer = new FileWriter("src/verslag/experiments/" + fileName);
            writer.append("Grafenbestand" + COLUMN_DELIMETER + generateCSVRow(columnTitles));
            for(Map.Entry<String, List<CycleNode[][]>> entry: result.entrySet()){
                String inputFileName = entry.getKey();
                List<CycleNode[][]> inputFileResult = entry.getValue();
                int[] total = new int[columnTitles.length];
                int[] succesful = new int[columnTitles.length];
                for(CycleNode[][] cycleNodesArray: inputFileResult){
                    int i = 0;
                    for(CycleNode[] cycleNodes: cycleNodesArray){
                        total[i]++;
                        if(cycleNodes!=null){
                            succesful[i]++;
                        }
                        i++;
                    }
                }
                String[] results = new String[total.length+1];
                results[0] = inputFileName.replace("_", "\\_");
                for(int j=0; j<total.length; j++){
                    results[j+1] = String.format("%.1f", ((double) succesful[j]/total[j])*100);
                }
                writer.append(generateCSVRow(results));
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String generateCSVRow(String[] row){
        String rowString = "";
        for(int i=0; i<row.length-1; i++){
            rowString += row[i] + ";";
        }
        rowString += row[row.length-1];
        return rowString + "\n";
    }
}
