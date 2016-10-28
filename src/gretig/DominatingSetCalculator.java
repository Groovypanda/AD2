package gretig;


import graph.Edge;
import graph.Graph;
import graph.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Calculate the dominating set of a graph.</h1>
 * This class consists one method to construct a dominating set of the given graph.
 * A dominating set is a set such that all nodes of the graph are either in the set or
 * the neighbour of a node in this set. The dominating set will be computed in linear time
 * with a greedy algorithm. The set will be an approximation of the minimal dominating set.
 * @author Jarre Knockaert
 */
public class DominatingSetCalculator {
    //The graph of which we want to calculate a minimal dominating set.
    private Graph graph;
    private int optimization;
    //The amount of coverage the dominant list gives. If this equals the amount of nodes in the graphs,
    // it covers the whole graph, in other words the amount of unique nodes with their neighbours equals the size of all nodes.
    private int totalCoverage;
    //I use a list as internal data structure because a set doesn't offer constant time for adding items to the set.
    //Arrays would have to much overhead (a lot of null gaps (~75%)). List is useful because add has a constant amortized time.
    private List<Node> dominantList;


    /**
     * Constructor for a dominating set calculator. This object can be used
     * for computing a dominating set for the given graph with a certain optimization level.
     * @param graph This is the graph of which the dominating set should be computed.
     * @param optimization The optimization level, This should range from 0-6, optimization level 0 will offer the
     *                     worst results and optimization level 6 will offer the best results.
     *                     Higher optimization levels are allowed but won't offer better results!
     *                     The higher the optimization the slower the dominating set will compute, but the complexity remains linear!
     */
    public DominatingSetCalculator(Graph graph, int optimization){
        this.graph = graph;
        this.optimization = optimization;
        totalCoverage = 0; //At the beginning there is no coverage.
        dominantList = new ArrayList<>(); //
    }

    /**
     * Constructor for a dominating set calculator. This object can be used
     * for computing a dominating set for the given graph with the best optimization level (6).
     * @param graph This is the graph of which the dominating set should be computed.
     */
    public DominatingSetCalculator(Graph graph){
        //6 is the optimal optimization level. Higher or lower optimization levels offer worse results.
        this(graph, 6);
    }

    /**
     * Computes an approximation of a minimal set in linear time.
     * @return The method returns a list of nodes which represent the approximation of the minimal set.
     */
    public List<Node> getDominantList(){
        /**
         * Sort the nodes by their amount of edges with counting sort. O(n+k).
         * The algorithm works better if the nodes are sorted by the edges, as it will find the best nodes to add
         * to the dominant list first. True indicates the nodes with the highest degree should have a lower index
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
         */
        for(int i=optimization; i>=0; i--){
            buildDominantList(i); //O(n)
        }
        return dominantList;
    }

    private void prepareDominantList() {
        for(Node v: graph.getNodes()) {
            if (v.getEdges().size() == 1) { //Optimalisatie 1
                Edge edge = v.getEdges().get(0);
                Node w = edge.getNeighbour(v);
                if (w.getCoverage() > 0) {
                    dominantList.add(w);
                    if (!w.visited()) {
                        totalCoverage++;
                        w.visit();
                    }
                    for (Edge wEdge : w.getEdges()) {
                        Node x = wEdge.getNeighbour(w);
                        x.decrementCoverage();
                        if (!x.visited()) {
                            totalCoverage++;
                            x.visit();
                        }
                    }
                    w.clearCoverage();
                }
                v.clearCoverage();
            }
        }
    }

    private void buildDominantList(int minimumNodeCoverage) {
        Node[] nodes = graph.getSortedNodes();
        for(int i=0; i<nodes.length && totalCoverage < nodes.length; i++){
            Node v = nodes[i];
            Node maxNode = v;
            //Loop trough neighbours to find max.
            if (v.getCoverage() != 0) { //Optimalisatie 3
                int maxCoverage = v.getCoverage();
                for (Edge edge : v.getEdges()) {
                    Node w = edge.getNeighbour(v);
                    if (w.getCoverage() > maxCoverage) {
                        maxNode = w;
                        maxCoverage = w.getCoverage();
                    }
                }
                if (maxCoverage > minimumNodeCoverage) {
                    int maxNodeActualCoverage = 0;
                    if (!maxNode.visited()) {
                        maxNodeActualCoverage++;
                    }

                    //Other totalCoverage is just an estimation. Calculating the real totalCoverage can't be linear.
                    for (Edge edge : maxNode.getEdges()) {
                        Node w = edge.getNeighbour(maxNode);
                        if (!w.visited()) {
                            maxNodeActualCoverage++;
                        }
                    }
                    if (maxNodeActualCoverage > minimumNodeCoverage) {
                        //Optimalisatie 2, eventueel > getal meegeven als parameter en controleren voor verschillende parameters
                        //Je kan eventueel doen: als alle toppen overlopen zijn, het zachte algoritme gebruiken die met minder strenge voorwaarden
                        //toppen toevoegt?
                        maxNode.clearCoverage();
                        dominantList.add(maxNode);
                        if (!maxNode.visited()) {
                            maxNode.visit();
                        }
                        totalCoverage += maxNodeActualCoverage;
                        for (Edge edge : maxNode.getEdges()) {
                            Node w = edge.getNeighbour(maxNode);
                            w.decrementCoverage();
                            if (!w.visited()) {
                                w.visit();
                            }
                        }
                    }
                }
            }
        }
    }



}
