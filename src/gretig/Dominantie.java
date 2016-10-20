package gretig;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by Jarre on 8/10/2016.
 */
public class Dominantie {
    public static List<Node> getDominantList(Graph graph){
        List<Node> dominantList = new ArrayList<>();
        while(!graph.empty()){
            Node v = graph.getNode(0);
            boolean removed = false;
            for(Edge e: v.getEdges()){
                Node w = e.getNeighbour(v);
                /*
                TE BEKIJKEN, OFWEL BOOG VERWIJDEREN
                OFWEL NIET: MAAR DAN DOEN WE DUBBELEN TOPPEN, GEEN LINEAIRE TIJD MEER?
                 */
                if(w!=null){
                    graph.remove(w);
                    if(v.getEdgesAmount()<w.getEdgesAmount()){
                        dominantList.add(w);
                    }
                }
            }
            if(!removed){
                dominantList.add(v);
            }
            graph.remove(v);
        }
        return dominantList;
    }
}
