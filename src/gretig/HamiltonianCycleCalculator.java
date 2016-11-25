package gretig;

import datastructures.DualGraph;
import datastructures.Graph;
import datastructures.PlaneNodeArray;
import elements.*;

/**
 * Object for creating cycles in a planar triangulation. It creates a dual graph of the given graph
 * of which the yutsis decomposition is calculated afterwards. The edges of the cycle can be calculated by traversing
 * a tree of the yutsis decomposition and adding the edges to the cycle of which the 2 neighbours are in a different
 * plane of the yutsis decomposition.
 */
public class HamiltonianCycleCalculator {
    int nodeAmount;
    Graph graph;
    //public static int i=0;

    /**
     * Constructor for a hamiltonian cycle calculator.
     * @param graph
     */
    public HamiltonianCycleCalculator(Graph graph) {
        this.graph = graph;
        nodeAmount = graph.getSize();
    }

    public void printCycle(){
        DualGraph dualGraph = new DualGraph(graph);
        YutsisDecomposer decomposer = new YutsisDecomposer(dualGraph);
        PlaneNodeArray tree =  decomposer.findYutsisDecomposition(0);
        //As the dualgraph contains 2n-4 nodes (the amount of planes). We can try to make a yutsis decomposition.
        //The yutsis decomposer only returns one of the trees. All of the PlaneNodes which aren't in this tree, form
        //the other tree. The tree is null if no yutsis decomposition was found.
        if(tree==null){
            System.out.println("Geen cykel gevonden");
        }
        else{
            CycleNode[] cycle = createCycle(tree);
            System.out.println(calculateCycleString(cycle));
        }
        System.out.println();
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
     * Creates a cycle given a tree of the yutsis decomposition. This method assumes that all nodes which aren't
     * in this tree, are contained in the union of all of the other arrays.
     * @param tree One of the two yutsis decomposition trees.
     * @return An array of cycleNodes which represent a cycle in the graph.
     */
    private CycleNode[] createCycle(PlaneNodeArray tree) {
        CycleNode cycle[] = new CycleNode[nodeAmount];
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
                            cycleNodes[i] = new CycleNode(node.getNumber());
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
}
