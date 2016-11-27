package gretig.main;

import gretig.graph.Graph;
import gretig.calculators.HamiltonianCycleCalculator;
import gretig.input.BinaryFileReader;
import gretig.calculators.HamiltonianCycleCalculatorOld;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A class purely used for testing purposes.
 * This class changes all the time.
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
            System.out.print("New: ");
            HamiltonianCycleCalculator calculator = new HamiltonianCycleCalculator(g);
            calculator.printCycle(calculator.calculateCycle());
            System.out.print("Old: ");
            HamiltonianCycleCalculatorOld calculatorOld = new HamiltonianCycleCalculatorOld(g);
            calculatorOld.printCycle(calculatorOld.calculateCycle());
        }
        System.out.println("New: " + (double)HamiltonianCycleCalculator.succesful/HamiltonianCycleCalculator.total);
        System.out.println("Old: " + (double)HamiltonianCycleCalculatorOld.succesful/HamiltonianCycleCalculatorOld.total);
    }
}
