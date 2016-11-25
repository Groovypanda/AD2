package test;

import graph.Graph;
import graph.Node;
import gretig.DominatingSetCalculator;
import input.BinaryFileReader;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * A JUnit test for all non-trivial DominatingSetCalculator methods.
 */

public class DominatingSetCalculatorTest {


    @Test
    public void testGetDominantListSmallGraphs(){
        BinaryFileReader reader = new BinaryFileReader();
        testGetDominantListGraphs(reader.getDirectoryGraphs("klein"));
    }

    @Test
    public void testGetDominantListBigGraphs(){
        BinaryFileReader reader = new BinaryFileReader();
        testGetDominantListGraphs(reader.getDirectoryGraphs("testset"));
    }

    public void testGetDominantListGraphs(List<Graph> graphs){
        for(Graph graph: graphs){
            DominatingSetCalculator calculator = new DominatingSetCalculator(graph);
            List<Node> dominantList = calculator.getDominantList();
            System.out.println(String.format("%d/%d (%.2f%%)", dominantList.size(), graph.getNodes().length, ((double)dominantList.size()/(double)graph.getNodes().length)*100));
            assertTrue(calculator.isDominant(dominantList));
        }
    }


}
