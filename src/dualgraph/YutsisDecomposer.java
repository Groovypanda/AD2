package dualgraph;

import dualgraph.DualGraph;
import datastructures.PlaneNodeArray;
import dualgraph.Face;
import dualgraph.Pair;
import graph.Plane;
import dualgraph.PlaneNode;

/**
 * Object used for computing the yutsis decomposition of a 2-connected cubic graph in linear time with a greedy algorithm.
 * This algorithm uses ideas written in the paper "To be or not to be Yutsis: algorithms for the
   decision problem" written by D. Van Dyck, G. Brinkmann and V. Fack.
 */
public class YutsisDecomposer {
    protected DualGraph graph;
    protected PlaneNodeArray V; //Contains all nodes which aren't in M or L[]. Initially all nodes.
    protected PlaneNodeArray M; //Contains all nodes of the current tree in the graph.
    /**
     * There are 3 L arrays: L[0], L[1] and L[2]. When adding a node to M, all neighbours of this node which are in V are added to
     * the correct L array. The index in the array of L arrays equals the amount of neighbours in V.
     */
    protected PlaneNodeArray[] L;
    protected PlaneNodeArray nonVisitable; //Contains all nodes which have been removed from L as they'll never become an addable node.
    /**
     * Initializes a new YutsisDecomposer object which is used for decomposing the given 2-connected cubic graph in 2 trees.
     * @param graph
     */
    public YutsisDecomposer(DualGraph graph){
        this.graph = graph;
    }

    /**
     * Executes the actual greedy algorithm for finding 2 independent trees with half of the nodes each
     * of the dual graph of this decomposer.  The base of this algorithm is the algorithm described in the syllabus.
     * The algorithm is additionally inspired by the ideas of the paper "To be or not to be Yutsis: algorithms for the decision problem".
     * The algorithm works as following:
     *
     * Precondition:
     * ==============
     * - graph is 2-connected cubic graph.
     * - V contains all the nodes of this graph.
     *
     * Postcondition:
     * ==============
     * - This function returns a yutsis-decomposition if he was able to find one.
     *
     * Algorithm:
     * ==============
     * - v <- The first node in V
     * - Add v to M
     * - Update L arrays
     * - while any L array has nodes && M is not complete
     *      - v <- node with highest amount of unplaced neighbours and 1 neighbour in M
     *      - if v isn't a cutvertex
     *           - add v to M
     *           - update L
     *           - nodeFound <- true
     *      - else
     *           - switch v from its L array to V
     *
     *
     * @return The Yutsis-decomposition generated by the greedy algorithm
     */
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
        if(M.length() == n/2){ //Algorithm found a decomposition.
            return M;
        }
        else { //Algorithm didn't find a decomposition.
            return null;
        }
    }

    /**
     * Finds the node with just 1 neighbour in M and the highest amount of neighbours in V.
     * @return A node with one neighbour in M and optimal amount of neighbours in V.
     * Every node is moved at most three times, so the amortized time of this method is 0(n).
     */
    public PlaneNode getHighestLNode() {
        int i = 2;
        while (i >= 0) {
            if (!L[i].empty()) { //Start with array with highest amount of unvisited neighbours.
                PlaneNode maxNode = L[i].pull();
                if (i > 0) {
                    //Check amount of unplaced neighbours
                    int unplacedNeighbourAmount = maxNode.neighbourAmount(V);

                    //If unplacedneighbourAmount isn't correct, switch this node to the correct L array.
                    if (unplacedNeighbourAmount != i) {
                        L[unplacedNeighbourAmount].add(maxNode);
                    } else {
                        if (maxNode.neighbourAmount(M) == 1) {
                            return maxNode;
                        } else {
                            nonVisitable.add(maxNode);
                        }
                    }

                } else { //If i = 0, neighbourAmount doesn't have to be checked.
                    return maxNode;
                }
            } else {
                i--;
            }
        }
        return null;
    }

    /**
     * Updates the neighbours of the newly added node to M
     * @param node The newly added node to M
     */
    protected void update(PlaneNode node) {
        for(PlaneNode neighbour: node.getNeighbours()){
            int index = neighbour.neighbourAmount(V);
            if(!neighbour.isPresent(M) && !neighbour.isPresent(nonVisitable)){

                /**
                 * 3 possible cases:
                 * neighbour is in V: place neighbour in the correct L array.
                 * neighbour is in the wrong L array: place neighbour in the correct L array.
                 * neighbour was in the correct L array:
                 */
                neighbour.switchTo(L[index]);
            }
        }
    }


    /**
     * Initializes all of the PlaneNodeArrays this algorithm uses.
     */
    protected void initializeYutsisArrays() {
        V = new PlaneNodeArray("V", graph.getSize());
        M = new PlaneNodeArray("M", graph.getSize()/2);
        L = new PlaneNodeArray[3];
        nonVisitable = new PlaneNodeArray("Non-visitable", graph.getSize());
        for(int j=0; j<3; j++){
            L[j] = new PlaneNodeArray("L" + j,graph.getSize());
        }
        for(Plane plane: graph.getPlanes()){ //Linear time, and is called once.
            if(plane!=null){
                V.add(plane.getNode());
            }
        }
    }
}