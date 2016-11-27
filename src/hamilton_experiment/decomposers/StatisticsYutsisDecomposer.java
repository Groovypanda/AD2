package hamilton_experiment.decomposers;

import datastructures.PlaneNodeArray;
import dualgraph.*;

/**
 * This decomposer always returns a tree, even if it's not half of the size of the dual graph.
 */
public class StatisticsYutsisDecomposer extends YutsisDecomposer {

    public StatisticsYutsisDecomposer(DualGraph graph) {
        super(graph);
    }

    public PlaneNodeArray findYutsisDecomposition(int i){
        initializeYutsisArrays(); //Initialises the arrays used by this algorithm.
        int n = V.length(); //Amount of planeNodes
        PlaneNode node = V.get(i); //Get the node with index i
        node.switchTo(M); //add this node to M
        for(Pair pair: node.getPairs()){
            pair.getFace().markPairs(); //Mark the faces of the pairs of this node
        }
        update(node); //update L arrays
        boolean L_arrays_empty = false;
        while(M.length() < n/2 && !L_arrays_empty){
            node = getHighestLNode(); //Finds the highest node with 1 neighbour in M. This node is removed from its current array.
            if(node!=null){
                //Check if this node is a cutvertex. If it isn't, the face with 2 endpoints which aren't in M are returned.
                Face face = node.isCutVertex(M);
                if(face==null){ //Node is a cutvertex.
                    nonVisitable.add(node);
                }
                else { //Node isn't a cutvertex.
                    M.add(node); //Add the node to M
                    face.markPairs(); //Mark the face returned by isCutVertex
                    update(node); //update the neighbours.
                }
            }
            else { //If no node can be found the L array must be empty
                L_arrays_empty = true;
            }
        }
        return M;
    }
}
