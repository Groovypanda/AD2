package gretig;

import cycle.CycleNode;
import dualgraph.DualGraph;
import dualgraph.PlaneNode;
import dualgraph.YutsisDecomposer;
import graph.Edge;
import graph.Graph;
import datastructures.PlaneNodeArray;
import graph.Node;
import graph.Plane;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Object for creating cycles in a planar triangulation. It creates a dual graph of the given graph
 * of which the yutsis decomposition is calculated afterwards. The edges of the cycle can be calculated by traversing
 * a tree of the yutsis decomposition and adding the edges to the cycle of which the 2 neighbours are in a different
 * plane of the yutsis decomposition. The algorithm runs in linear time.
 */
public class HamiltonianCycleCalculator {
    int nodeAmount;
    Graph graph;
    public static int total = 0; //Keeps track of the total amount of attempts to generate a cycle in this class.
    public static int succesful = 0; //Keeps track of the amount of succesful cycles generated by this class.

    /**
     * Constructor for a hamiltonian cycle calculator.
     * @param graph
     */
    public HamiltonianCycleCalculator(Graph graph) {
        this.graph = graph;
        nodeAmount = graph.getSize();
    }

    /**
     * Prints the given cycle to stdout.
     * @param cycle
     */
    public void printCycle(CycleNode[] cycle){
        //As the dualgraph contains 2n-4 nodes (the amount of planes). We can try to make a yutsis decomposition.
        //The yutsis decomposer only returns one of the trees. All of the PlaneNodes which aren't in this tree, form
        //the other tree. The tree is null if no yutsis decomposition was found.
        if(cycle==null){
            System.out.println("Geen cykel gevonden");
        }
        else{
            succesful++;
            System.out.println(calculateCycleString(cycle));
        }
        total++;
    }

    /**
     * Calculates the string representation of an array of nodes in which every node is correctly initialized.
     * @param nodes The nodes of the cycle.
     * @return A representation of this cycle.
     */
    private String calculateCycleString(CycleNode[] nodes){
        String cycleOutput = "";
        CycleNode first = nodes[0];
        CycleNode cur = first;
        CycleNode next = first.getNeighbours()[0];
        CycleNode previous;
        boolean finished = false;
        while (!finished) { //Loop trough all of the nodes in the cycle.
            cycleOutput += cur + " ";
            previous = cur;
            cur = next;
            next = cur.getNext(previous);
            if (cur.equals(first)) {
                finished = true;
            }
        }
        return cycleOutput;
    }

    /**
     * Creates a cycle by:
     * Computing the dual graph of the given graph.
     * Computing the yutsis decomposition of this dual graph.
     * Computing the edges which are common edges of 2 planes which are in a different tree of the decomposition.
     * @return An array of cycleNodes which represent a cycle in the graph.
     *         The nodes are part of the edges mentioned above.
     *         Returns null if no yutsis decomposition could be found.
     */
    public CycleNode[] calculateCycle() {
        CycleNode cycle[] = new CycleNode[nodeAmount];
        DualGraph dualGraph = new DualGraph(graph);
        YutsisDecomposer decomposer = new YutsisDecomposer(dualGraph);
        PlaneNodeArray tree =  decomposer.findYutsisDecomposition(0);
        if(tree==null){
            return null;
        }
        for(PlaneNode planeNode: tree.getNodes()){ //Loop trough all planes of the tree.
            Plane plane = planeNode.getPlane();
            for(Plane neighbour: plane.getAdjacentPlanes()){
                //The adjacent node isn't in the given tree, which means it's in the other tree.
                //This indicates that the common edge of these planes is part of the cycle.
                if(!neighbour.getNode().isPresent(tree)){
                    Node[] nodes = plane.getCommonEdge(neighbour).getNodes();
                    int i=0;
                    CycleNode[] cycleNodes = new CycleNode[2];
                    for(Node node: nodes){ //Create new CycleNode if the node doesn't have a cyclenode yet.
                        int index = node.getNumber()-1;
                        if(cycle[index]==null){ //Check if cycle exists.
                            cycleNodes[i] = new CycleNode(node);
                            cycle[index] = cycleNodes[i++];
                        }
                        else{
                            cycleNodes[i++] = cycle[index];
                        }
                    }
                    cycleNodes[0].addNeighbour(cycleNodes[1]);
                    cycleNodes[1].addNeighbour(cycleNodes[0]);
                }
            }
        }
        return cycle;
    }

    /**
     * Tests whether the given cycle is a hamiltonian cycle in the given graph.
     * @param cycle
     * @return True if the cycle is a hamiltonian cycle
     */
    public boolean isCycle(CycleNode[] cycle) {
        int cycleSize = cycle.length;
        int graphSize = graph.getSize();
        if(cycleSize!=graphSize){
            return false;
        }
        Set<CycleNode> cycleNodes = new HashSet<>(Arrays.asList(cycle));
        if(cycleNodes.size()!=graphSize){
            return false;
        }

        for(CycleNode cycleNode: cycleNodes){
            //Check for both neighbours if those neighbours have this cycleNode as neighbour.
            for(CycleNode neighbour: cycleNode.getNeighbours()){
                //Check if both nodes are neighbour of eachother.
                boolean hasNeighbour = false;
                for(CycleNode neighbourOfNeighbour: neighbour.getNeighbours()){
                    if(neighbourOfNeighbour.equals(cycleNode)){
                        hasNeighbour = true;
                    }
                }
                if(!hasNeighbour){
                    return false;
                }
            }
            int edgesToNeighboursAmount = 0;
            //Check if a cyclenode doesn't has precisely 2 edges leading to other cyclenodes.
            Node thisNode = cycleNode.getNode();
            for(Edge edge: thisNode.getEdges()){
                Node otherNode = edge.getOtherNode(thisNode);
                //Check if this edge leads to a cycleNode.
                for(CycleNode neighbour: cycleNode.getNeighbours()){
                    if(neighbour.getNode().equals(otherNode)){
                        edgesToNeighboursAmount++;
                    }
                }
            }
            if(edgesToNeighboursAmount!=2){
                return false;
            }
        }
        return true;
    }
}
