package gretig;

import graph.Edge;
import graph.Graph;
import graph.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jarre on 8/10/2016.
 */
public class Dominantie {
    //Algoritme uitvoeren op insertion sorted list instead of nodearray & remove := index = null.
    public static List<Node> getDominantList(Graph graph){
        graph.sort();
        List<Node> dominantList = new ArrayList<>();
        int coverage = 0;
        int size = graph.size();
        while(coverage < size){
            Node v = graph.pull();
            dominantList.add(v);
            if(!v.visited()){
                v.visit();
                coverage++;
            }
            for(Edge edge: v.getEdges()){
                Node w = edge.getNeighbour(v);
                if(!w.visited()){
                    w.visit();
                    coverage++;
                }
            }
        }
        return dominantList;
    }
}

