import graph.Graph;
import gretig.HamiltonianCycleCalculator;
import input.BinaryFileReader;
import gretig.HamiltonianCycleCalculatorOld;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A class purely used for testing purposes.
 * It basically contains a main function and a few help methods.
 * @author Jarre Knockaert
 */
public class Main {
    public static void main(String[] args) {
        BinaryFileReader reader = new BinaryFileReader();
        Path path;
        boolean testset = false;
        if(testset){
            path = Paths.get(System.getProperty("user.dir"), "data", "testset", "triang16.sec");
        }
        else {
            path = Paths.get(System.getProperty("user.dir"), "data", "klein", "triang_alle_12.sec");
        }
        for(Graph g: reader.getFileGraphs(path)){
            //System.out.println("================ GRAPH " + ++graphNumber + " ================");
            System.out.print("New: ");
            HamiltonianCycleCalculator calculator = new HamiltonianCycleCalculator(g);
            calculator.printCycle(calculator.calculateCycle());
            System.out.print("Old: ");
            HamiltonianCycleCalculatorOld calculatorOld = new HamiltonianCycleCalculatorOld(g);
            calculatorOld.printCycle(calculatorOld.calculateCycle());
        }
        System.out.println("New: " + (double)HamiltonianCycleCalculator.succesful/HamiltonianCycleCalculator.total);
        System.out.println("Old: " + (double)HamiltonianCycleCalculatorOld.succesful/HamiltonianCycleCalculatorOld.total);
        /*
        for(int i=1; i<=10; i++){
            List<Graph> graphs = readGraphs(String.format("testset/triang%d.sec", i));
            for(Graph g: graphs){
                System.out.println("================ GRAPH " + ++graphNumber + " ================");
                HamiltonianCycleCalculator calculator = new HamiltonianCycleCalculator(g);
            }
        }
        */
    }
}
