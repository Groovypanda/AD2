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
    //Algoritme uitvoeren op insertion sorted list instead of nodearray & remove := index = null.
    public static List<Node> getDominantList(Graph graph){
        //graph.sort();
        graph.addAll();
        //graph.sortBackwards();
        List<Node> dominantList = new ArrayList<>();
        int coverage = 0;

        for(Node v: graph.getNodes()){
            if(v.getCoverage()==1){
                Edge edge = v.getEdges().get(0);
                Node w = edge.getNeighbour(v);
                dominantList.add(w);
                if(!w.visited()){
                    coverage++;
                    w.visit();
                }
                for(Edge wEdge: w.getEdges()){
                    Node x = wEdge.getNeighbour(w);
                    x.decrementCoverage();
                    if(!x.visited()){
                        coverage++;
                        x.visit();
                    }
                }
                w.clearCoverage();
                v.clearCoverage();
            }
        }

        int size = graph.size();
        while(coverage < size){
            Node v = graph.pull();
            Node maxNode = v;
            int maxCoverage = v.getCoverage();
            
            //Loop trough neighbours to find max.
            for(Edge edge: v.getEdges()){
                Node w = edge.getNeighbour(v);
                if(w.getCoverage() > maxCoverage){
                    maxNode = w;
                    maxCoverage = w.getCoverage();
                }
            }
            if(maxCoverage>0){
                maxNode.clearCoverage();
                dominantList.add(maxNode);
                if(!maxNode.visited()){
                    maxNode.visit();
                    coverage++;
                }
                //Other coverage is just an estimation. Calculating the real coverage can't be linear.
                for(Edge edge: maxNode.getEdges()){
                    Node w = edge.getNeighbour(maxNode);
                    w.decrementCoverage();
                    if(!w.visited()){
                        w.visit();
                        coverage++;
                    }
                }
            }
        }
        return dominantList;
    }

    public static boolean isDominant(Graph g, List<Node> dominantList){
        Node[] graphNodes = g.getNodes();
        Set<Node> coverage = new HashSet<>();
        for(Node node: dominantList){
            coverage.add(node);
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
                List<Node> dominantList = getDominantList(graph);
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

