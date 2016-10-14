package gretig;

import java.util.List;

/**
 * Created by Jarre on 8/10/2016.
 */
public class Dominantie {
    public List getDominantList(Graph g){
        Node[] nodes = g.getNodes();
        int nodeAmount = nodes.length;
        for(Node n: nodes){
            //SORT BY AMOUNT OF VERTICES plz
            System.out.println(n.getVertices().size());
        }
        return null;
    }
}
