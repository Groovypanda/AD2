package gretig.test;

import gretig.graph.Graph;
import gretig.cycle.CycleNode;
import gretig.calculators.HamiltonianCycleCalculator;
import gretig.input.BinaryFileReader;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 *  A JUnit test for all non-trivial DominatingSetCalculator methods.
 */
public class HamiltonianCycleCalculatorTest {
    @Test
    public void testGetDominantListSmallGraphs(){
        BinaryFileReader reader = new BinaryFileReader();
        testGetDominantListGraphs(reader.getDirectoryGraphs("triang_klein"));
    }

    @Test
    public void testGetDominantListBigGraphs(){
        BinaryFileReader reader = new BinaryFileReader();
        testGetDominantListGraphs(reader.getDirectoryGraphs("triang_groot"));
    }

    public void testGetDominantListGraphs(List<Graph> graphs){
        for(Graph graph: graphs){
            HamiltonianCycleCalculator calculator = new HamiltonianCycleCalculator(graph);
            CycleNode[] cycle = calculator.calculateCycle();
            if(cycle!=null){ //Cycle is null if no cycle was found.
                assertTrue(calculator.isCycle(cycle));
            }
        }
    }

}
