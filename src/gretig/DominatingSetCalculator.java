package gretig;

import graph.Edge;
import graph.Graph;
import graph.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <h1>Calculate the dominating set of a elements.</h1>
 * This class consists one method to construct a dominating set of the given elements
 * and a method to check if a dominating set is correct.
 * A dominating set is a set such that all nodes of the elements are either in the set or
 * the neighbour of a node in this set. The dominating set will be computed in linear time
 * with a greedy algorithm. The set will be an approximation of the minimal dominating set.
 * @author Jarre Knockaert
 */
public class DominatingSetCalculator {
    //The elements of which we want to calculate a minimal dominating set.
    protected Graph graph;
    protected int optimization;
    //The amount of coverage the dominant list gives. If this equals the amount of nodes in the graphs,
    // it covers the whole elements, in other words the amount of unique nodes with their neighbours equals the planeAmount of all nodes.
    protected int totalCoverage;
    //I use a list as internal data structure because a set doesn't offer constant time for adding items to the set.
    //Arrays would have to much overhead (a lot of null gaps (~75%)). List is useful because add has a constant amortized time.
    protected List<Node> dominantList;


    /**
     * Constructor for a dominating set calculator. This object can be used
     * for computing a dominating set for the given elements with a certain optimization level.
     * @param graph This is the elements of which the dominating set should be computed.
     * @param optimization The optimization level, This should range from 0-6, optimization level 0 will offer the
     *                     worst results and optimization level 6 will offer the best results.
     */
    public DominatingSetCalculator(Graph graph, int optimization){
        this.graph = graph;
        this.optimization = optimization;
        totalCoverage = 0; //At the beginning there is no coverage.
        dominantList = new ArrayList<>(); //
    }

    /**
     * Constructor for a dominating set calculator. This object can be used
     * for computing a dominating set for the given elements with the best optimization level (6).
     * @param graph This is the elements of which the dominating set should be computed.
     */
    public DominatingSetCalculator(Graph graph){
        this(graph, 6);
        //6 is the optimal optimization level. Higher or lower optimization levels offer worse results.
    }

    /**
     * Computes an approximation of a minimal set in linear time.
     * @return The method returns a list of nodes which represent the approximation of the minimal set.
     */
    public List<Node> getDominantList(){
        /**
         * Sort the nodes by their amount of edges with counting sort. O(n+k).
         * The algorithm works better if the nodes are sorted by the edges.
         * True indicates the nodes with the lowest degree should have a lower index
         * than nodes with a lower degree.
         */
        graph.sort(true); //O(n+k)
        /**
         *  This is an optimization: it immediatly adds the neighbours of every node with degree 1.
         *  These neighbours have to be in the set as this is the only way to reach the nodes with degree 1.
         */
        prepareDominantList(); //O(n)
        /**
         *  If the optimization level is 6, the approximation of the minimal dominanting set will be the best possible
         *  (in comparison with other optimization levels).
         *  The method adds nodes to the set which have a degree higher than 'i'.
         *  By ensuring nodes with actual degree i are added before nodes with degree lower than i the dominant set
         *  will cover the whole elements faster with less nodes.
         */
        for(int i=optimization; i>=0; i--){
            buildDominantList(i); //O(n)
        }
        return dominantList;
    }

    /**
     * This method prepares the dominant list by adding all the necessary nodes.
     * Nodes are necessary in the dominant set if only they can reach a certain other node.
     * This is the case if a node has degree one, the other endpoint of the only edge of this node is necessary,
     * and thus has to be added to the dominant set. This is an optimization method and isn't necessary for the
     * algorithm to work, however for certain graphs (with a lot of nodes with degree 1). The algorithm will give
     * a much better result in this case.
     */
    protected void prepareDominantList() {
        /**
         * This method when using a sorted array (by degree) with nodes because the nodes with degree one will have the
         * highest index. Once we reach a node with degree > 1, the loop may end.
         */
        Node[] nodes = graph.getSortedNodes();
        int index = 0; //Starting at the lowest index. (This node has the lowest degree.)
        Node v = nodes[index];
        while(v.getDegree()==1){ //Loop trough nodes with degree 1.
            Edge edge = v.getEdges().get(0); //Get the only edge of the node. (degree 1 => 1 edge)
            Node w = edge.getNeighbour(v);
            //If coverage==0, adding the node wouldn't be logic as it wouldn't increase the reach of the dominant set.
            if (w.getCoverage() > 0) {
                dominantList.add(w); //This node can safely be added to the dominant set.
                totalCoverage += w.visit(); //Visits a node and if it could be visited increment the totalCoverage.
                totalCoverage += w.visitNeighbours(); //Adds total coverage which can be added by visiting the neighbours.
                w.clearCoverage(); //Clear the coverage as w is added and can't increase the totalCoverage anymore.
            }
            v.clearCoverage(); //v only had one neighbour, so its safe to clear the coverage of v aswell.
            v = nodes[index++];
        }
    }

    /**
     * This method implements the actual greedy linear algorithm for building an approximation of the optimal minimal
     * dominant set. The basic algorithm is the following, (a more formal algorithm is written in the report):
     *
     * Preconditions:
     * ==============
     * - all nodes are initialized with their degree as coverage.
     * - all nodes are unvisited.
     *
     * Postconditions:
     * ==============
     * - dominating set contains an approximation of a minimal dominating set.
     *
     * Algorithm:
     * ==============
     * coverage = 0
     * while coverage < amount of nodes:
     *    loop trough neighbours and v to find node w with highest coverage
     *    add maximum to the dominant set
     *    for every neighbour of maximum:
     *      if neighbour is unvisited:
     *          visit neighbour
     *          coverage++
     *      decrement coverage of neighbour
     *
     * The optimalizations used are adressed in the report and in the notes of the code in the method.
     *
     *
     * @param minimumNodeCoverage if minimumNodeCoverage is 0 all nodes can be added, this will ensure the dominant list
     *                            is dominant after running the method with parameter 0. A higher minimumNodeCoverage
     *                            will only add nodes to the dominant set with 'minimumNodeCoverage' amount of edges.
     */
    protected void buildDominantList(int minimumNodeCoverage) {
        Node[] nodes = graph.getSortedNodes(); //The algorithm works best with a sorted array by degree (lowest degree first).
        /**
         * A loop over the nodes, this loop will end prematurely if the whole elements is covered, this is always the case
         * with minimumNodeCoverage 0.
         */
        for(int i=0; i<nodes.length && totalCoverage < nodes.length; i++){
            Node v = nodes[i];
            /**
             * The following statement is an optimization.
             * If the coverage of a node is 0, it's better to just move on to the next node.
             * Important: the coverage property of a node is an upper bound for the actual coverage!
             */
            if (v.getCoverage() > 0) {
                /**
                 * Local search for the node with the highest coverage.
                 * In a greedy algorithm this is the best node to add to the dominant set.
                 */
                Node maxNode = v;
                int maxCoverage = maxNode.getCoverage();
                for (Edge edge : v.getEdges()) {
                    Node w = edge.getNeighbour(v);
                    if (w.getCoverage() > maxCoverage) {
                        maxNode = w;
                        maxCoverage = w.getCoverage();
                    }
                }
                /**
                 *  If the maxCoverage (upper bound for the real added coverage of the node) won't add more than
                 *  the minimumNodeCoverage, the node won't be added.
                 */

                if (maxCoverage > minimumNodeCoverage) {
                    /**
                     *  The maxCoverage is an upperbound for the actual added coverage of a node.
                     *  Calculating the real totalCoverage for every node wouldn't be linear, thus we need an
                     *  approximation. Once we have found a node which looks optimal, we check its actual coverage.
                     */

                    int maxNodeActualCoverage = 0;
                    if (!maxNode.visited()) { //If a node isn't visited yet, it can only contribute to the coverage.
                        maxNodeActualCoverage++;
                    }

                    for (Edge edge : maxNode.getEdges()) {
                        Node w = edge.getNeighbour(maxNode);
                        if (!w.visited()) {
                            maxNodeActualCoverage++;
                        }
                    }

                    //Again if the actual coverage is lower than the minimumNodeCoverage, the node shouldn't be added.
                    if (maxNodeActualCoverage > minimumNodeCoverage) {
                        //Update the elements
                        totalCoverage += maxNodeActualCoverage; //the actual amount of coverage can be added to the total.
                        maxNode.clearCoverage(); //added node should have zero coverage.
                        dominantList.add(maxNode); //add the node the dominant set.
                        maxNode.visit(); //visit the node so that it can't contribute anymore to the coverage.
                        maxNode.visitNeighbours(); //visit it's neighbours so they can't contribute anymore to the coverage.
                    }
                }
            }
        }
    }

    /**
     * The method checks if the given list is a dominant set in the current elements and contains no doubles.
     * In other words, it checks if the algorithm works properly.
     * This method isn't linear and should only be used for testing purposes, not for the final algorithm!
     * @param dominantList The list which has the be checked.
     * @return Returns true if the list is dominant and contains no doubles.
     */
    public boolean isDominant(List<Node> dominantList){
        Node[] graphNodes = graph.getNodes();
        Set<Node> coverage = new HashSet<>();
        coverage.addAll(dominantList); //Adding all elements to set to remove doubles.
        if(coverage.size() != dominantList.size()){  //If the planeAmount isn't equal the list contains doubles.
            return false;
        }
        for(Node node: dominantList){
            for(Edge edge: node.getEdges()){ //adds all neighbours of the dominant list to the coverage set.
                coverage.add(edge.getNeighbour(node));
            }
        }
        //if coverage contains the same amount of nodes as the elements, the dominantlist is dominant.
        return graphNodes.length == coverage.size();
    }

    public Graph getGraph(){
        return graph;
    }

}
