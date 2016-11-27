package hamilton_experiment;

import graph.Graph;
import hamilton_experiment.calculators.ExperimentHamiltonianCycleCalculator;
import input.BinaryFileReader;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class used for making statistics about the performance of the algorithm.
 */
public class PerformanceAnalyzer {
    private final static String BIG_TRIANG_DIR = "triang_groot";
    private final static char COLUMN_DELIMETER = ';';

    public static void main(String[] args) {
        generateCSVFile(runAnalysis("triang_groot"), "analysis.csv");
    }

    /**
     * Runs an analysis on all the graphs in the given directory.
     * @param directory The name of the directory with the files for the experiment.
     * @return A map with key the filename and value the result of that file.
     */
    public static Map<String, int[]> runAnalysis(String directory){
        BinaryFileReader reader = new BinaryFileReader();
        Map<String, List<Graph>> graphsMap = reader.getDirectoryGraphsExtended(directory);
        Map<String, int[]> result = new TreeMap<>();
        for (Map.Entry<String, List<Graph>> entry : graphsMap.entrySet()) {
            String filename = entry.getKey();
            List<Graph> fileGraphs = entry.getValue();
            //There's only one graph in the testsets.
            Graph graph = fileGraphs.get(0);
            ExperimentHamiltonianCycleCalculator calc = new ExperimentHamiltonianCycleCalculator(graph);
            result.put(filename, calc.getDecompositionStatistics());
        }
        return result;
    }

    /**
     * Generates a CSV file with the analysis.
     * @param result The results
     * @param fileName The output file name.
     */
    public static void generateCSVFile(Map<String, int[]> result, String fileName){
        try {
            FileWriter writer = new FileWriter("src/verslag/experiments/" + fileName);
            writer.append("Grafenbestand" + COLUMN_DELIMETER + "grootte boom" + COLUMN_DELIMETER + "maximale grootte boom\n");
            for(Map.Entry<String, int[]> entry: result.entrySet()){
                String inputFileName = entry.getKey();
                int[] inputFileResult = entry.getValue();
                writer.append(inputFileName + COLUMN_DELIMETER + inputFileResult[0] + COLUMN_DELIMETER + inputFileResult[1] + '\n');
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
