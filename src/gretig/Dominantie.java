package gretig;

import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by Jarre on 8/10/2016.
 */
public class Dominantie {
    public List getDominantList(Graph g){
        PriorityQueue<Node> nodes = g.getNodesQueue(); //0(n) voor het bouwen van een priority queue.
        while(!nodes.isEmpty()){
            Node max = nodes.poll();
            List<Edge> edges = max.getEdges();
        }
        return null;
    }
}
