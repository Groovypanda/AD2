package gretig;

import graph.Edge;
import graph.Graph;
import graph.Node;

import input.BinaryInputReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Jarre on 8/10/2016.
 */
public class Dominantie {

    public static boolean isDominant(Graph g, List<Node> dominantList){
        Node[] graphNodes = g.getNodes();
        Set<Node> coverage = new HashSet<>();
        for(Node node: dominantList){
            coverage.add(node);
        }
        assert(coverage.size()==dominantList.size());
        if(coverage.size() != dominantList.size()){
            return false; //Assert there are no double nodes added.
        }
        for(Node node: dominantList){
            for(Edge edge: node.getEdges()){
                coverage.add(edge.getNeighbour(node));
            }
        }
        return graphNodes.length == coverage.size();
    }

    public static void main(String[] args) {
        BinaryInputReader reader = new BinaryInputReader();
        try {
            List<Graph> graphs = reader.readByteArray();
            for(Graph graph: graphs){
                DominatingSetCalculator setCalculator = new DominatingSetCalculator(graph);
                List<Node> dominantList = setCalculator.getDominantList();
                int nodesSize = graph.getNodes().length;
                int dominantlistSize = dominantList.size();
                System.out.print(dominantlistSize + "/" + nodesSize + " (" + ((double)dominantlistSize/(double)nodesSize)*100 + "%): ");
                /*
                for(Node node: dominantList){
                    System.out.print(node.getNumber());
                    System.out.print(" ");
                }
                */
                assert(Dominantie.isDominant(graph, dominantList));
                System.out.println();

            }
        } catch (IOException e) {
            System.err.println("Couldn't read the given input.");
        }
    }
}

