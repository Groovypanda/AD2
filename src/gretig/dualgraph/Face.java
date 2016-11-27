package gretig.dualgraph;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Face (Plane) in a 2-connected cubic graph.
 * Important: The difference with the class Plane is the following, the nodes of this
 * class are PlaneNodes, the nodes of the Plane class are Nodes.
 */

public class Face {
    //A list of pairs contained in this,
    private List<Pair> pairs;

    /**
     * Initializes an empty face.
     */
    public Face() {
        pairs = new ArrayList<>();
    }

    /**
     * Mark all of the pairs in this face.
     */
    public void markPairs(){
        for(Pair pair: pairs){
            pair.mark();
        }
    }

    /**
     * Adds a pair to this face.
     * @param pair The boundary
     */
    public void addPair(Pair pair){
        pairs.add(pair);
        pair.setFace(this);
    }
}
