package gretig;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Jarre on 8/10/2016.
 */
public class Hamilton {
    public List<Node> getDominantList(Graph g){
        List<Node> dominantList = new ArrayList<>();
        while(!g.empty()){
            Node v = g.pull();
            for(Edge e: v.getEdges()){
                Node w = e.getNeighbour(v);
                if(v.getEdgesAmount()<w.getEdgesAmount()){
                    v.setRemoved(true);
                    dominantList.add(w);
                }
            }
            if(!v.isRemoved()){
                dominantList.add(v);
            }
            graph.remove(v);
        }
    }
}
