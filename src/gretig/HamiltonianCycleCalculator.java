package gretig;

import datastructures.DualGraph;
import datastructures.Graph;
import datastructures.YutsisArray;
import elements.*;

/**
 * Created by Jarre on 18/11/2016.
 */
public class HamiltonianCycleCalculator {
    int nodeAmount;
    private DualGraph dualGraph;
    public YutsisArray[] possibleCycleTrees;

    public HamiltonianCycleCalculator(Graph graph) {
        nodeAmount = graph.getSize();
        this.dualGraph = new DualGraph(graph);
        //As the dualgraph contains 2n-4 nodes (the amount of planes). We can try to make a yutsis decomposition.
        possibleCycleTrees = new YutsisArray[2];
        boolean found = findYutsisDecomposition();
        if(!found){
            System.out.println("Geen cykel gevonden");
        }
        else{
            CycleEdge head = calculateCycle(YutsisArray.M);
            if(head==null){
                System.out.println("D");
                head = calculateCycle(YutsisArray.D);
            }
            CycleEdge current = head;
            while(current!=null){
                System.out.println(current);
                current = current.getPrevious();
            }
        }
    }

    private CycleEdge calculateCycle(YutsisArray array) {
        CycleEdge head = null;
        CycleEdge tail = null;
        Plane next = null;
        int i=0;
        int length = 0;
        Plane current = array.get(i++).getPlane();
        while(head==null){ //O(n) iterations
            for(Plane neighbour: current.getAdjacentPlanes()){ //3 iterations
                if(!neighbour.getNode().isPresent(array)){
                    Edge common = current.getCommonEdge(neighbour);
                    CycleEdge cycleEdge = new CycleEdge(common);
                    if(head==null){
                        head=cycleEdge;
                        length++;
                    }
                    else if(head.getEdge().getCommonNode(common)!=null){
                        tail = cycleEdge;
                        length++;
                    }
                }
            }
            current = array.get(i++).getPlane();
        }
        head.setPreviousEdge(tail);
        tail.setNextEdge(head);
        while(current!=null){
            current.visit();
            for(Plane neighbour: current.getAdjacentPlanes()){ //3 iterations
                if(!neighbour.getNode().isPresent(array)){
                    Edge common = current.getCommonEdge(neighbour);
                    if(common != null && !common.isVisited()){ //Not already a cycleEdge
                        CycleEdge cycleEdge = new CycleEdge(common);
                        if(cycleEdge.getEdge().getCommonNode(head.getEdge())!=null){ //Edges are adjacent.
                            CycleEdge newHead = new CycleEdge(common);
                            head.addNextCycleEdge(newHead);
                            length++;
                            head = newHead;
                        }
                        else { //Tail and common have to be adjacent.
                            CycleEdge newTail = new CycleEdge(common);
                            newTail.addNextCycleEdge(tail);
                            length++;
                            tail = newTail;
                        }
                    }
                }
                else {
                    if(!neighbour.isVisited()){
                        next = neighbour;
                    }
                }
            }
            current = next;
            next = null;
        }
        if(length == nodeAmount){
            return head;
        }
        else {
            return null;
        }
    }


    //Gebaseerd op het algoritme uit de syllabus.
    //Returns if a yutsisdecomposition is found.
    //If a yutsisdecomp is found, YutsisArray.D & YutsisArray.M will contain the 2 nodeplane sets.
    public boolean findYutsisDecomposition(){
        initializeYutsisArrays();
        YutsisArray V = YutsisArray.V;
        YutsisArray M = YutsisArray.M;
        YutsisArray L = YutsisArray.L;
        //Note: Always remove a node first, before adding it to another array!
        int n = V.length();
        M.initialize(n/2); //M can't contain more than n/2 elements.
        L.initialize(n);
        PlaneNode node = V.get(0);
        V.remove(node);
        M.add(node);
        update(node);
        while(M.length() < n/2 && L.length() >0){
            //In the original find yutsis algorithm, the max node of L is taken. This has a linear time complexity.
            //We can only use constant methods inside this while loop. So we look for the maximum neighbour of the last added
            //element to M. As there are only 3 neighbours, this method is constant.
            PlaneNode max = node.getMaxNeighbour(V); //Returns null if the node has 0 neighbours in V.
            if(max == null){
                max = L.get(0); //If doesn't have any addable neighbours, a random one is chosen from the L set. (The first one)
            }
            YutsisArray.values()[max.getArrayNumber()].remove(max); //Remove max from his current array.
            M.add(max);
            update(max);
        }

        boolean hasYutsisDecomposition = M.length() == n/2;
        if(hasYutsisDecomposition) {
            YutsisArray D = YutsisArray.D;
            D.initialize(n/2);
            //Search elements which are not in M.
            for (int i = 0; i < V.length(); i++) {
                D.add(V.get(i));
            }
            for (int i = 0; i < L.length(); i++) {
                D.add(L.get(i));
            }
            hasYutsisDecomposition = hasYutsisDecomposition && D.length() == n / 2; //If both sets contain n nodes, there is a yutsis decomposition.
            return hasYutsisDecomposition;
        }
        return false;
    }

    /**
     * Updates the L set.
     * @param node The element which was last added to M.
     * @return The new size of L.
     */
    public void update(PlaneNode node){
        YutsisArray L = YutsisArray.L;
        YutsisArray V = YutsisArray.V;
        YutsisArray M = YutsisArray.M;
        for(Plane neighbour: node.getPlane().getAdjacentPlanes()){ //3 iterations
            PlaneNode neighbourNode = neighbour.getNode();
            if(!neighbourNode.isPresent(M)){
                int neighbours = neighbourNode.neighbourAmount(M);
                if(!neighbourNode.isPresent(L) && !neighbourNode.isPresent(M)){
                    if(neighbours < 2){
                        V.remove(neighbourNode);
                        L.add(neighbourNode);
                    }
                }
                else if(neighbours >=2){
                    L.remove(neighbourNode);
                    V.add(neighbourNode);
                }
            }
        }
    }

    private void initializeYutsisArrays() {
        YutsisArray array = YutsisArray.V;
        array.initialize(dualGraph.getSize());
        for(Plane plane: dualGraph.getPlanes()){ //Linear time, and is called once.
            if(plane==null){
                System.out.println(plane);
            }
            else {
                array.add(plane.getNode());
            }
        }
    }
}
