package test;

import graph.Graph;
import graph.Node;
import gretig.DominatingSetCalculator;
import input.BinaryFileReader;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

/**
 * A JUnit test for all non-trivial DominatingSetCalculator methods.
 */

public class TestDominatingSetCalculator {
    List<Graph> graphs = new ArrayList<>();

    @After
    public void tearDown(){
        graphs.clear();
    }

    @Test
    public void testGetDominantListSmallGraphs(){
        addDirectoryGraphs("klein");
        testGetDominantListGraphs();
    }

    @Test
    public void testGetDominantListBigGraphs(){
        addDirectoryGraphs("testset");
        testGetDominantListGraphs();
    }

    public void testGetDominantListGraphs(){
        for(Graph graph: graphs){
            DominatingSetCalculator calculator = new DominatingSetCalculator(graph);
            List<Node> dominantList = calculator.getDominantList();
            System.out.println(String.format("(%d/%d): %.1f%% coverage", dominantList.size(), graph.getNodes().length, ((double)dominantList.size()/(double)graph.getNodes().length)*100));
            assertTrue(calculator.isDominant(dominantList));
        }
    }

    /**
     * Adds all graphs from a .sec file to the graphs list.
     */
    public void addFileGraphs(Path path){
        BinaryFileReader reader = new BinaryFileReader();
        try {
            graphs.addAll(reader.readByteArray(Files.readAllBytes(path)));
        } catch (IOException e) {
            throw new AssertionError("The file couldn't be read.");
        }
    }

    /**
     * Adds all graphs from a directory with .sec files to the graph list.
     * @param directoryName The name of the directory
     */
    public void addDirectoryGraphs(String directoryName){
        try {
            try(Stream<Path> paths = Files.walk(Paths.get(System.getProperty("user.dir"), "data", directoryName))) {
                paths.forEach(filePath -> {
                    if (Files.isRegularFile(filePath)) {
                        addFileGraphs(filePath);
                    }
                });
            }
        } catch (IOException e) {
            throw new AssertionError("Couldn't find the given directory");
        }
    }
}
