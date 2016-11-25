package test;

import graph.Graph;
import cycle.CycleNode;
import gretig.HamiltonianCycleCalculator;
import input.BinaryFileReader;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Jarre on 25/11/2016.
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
