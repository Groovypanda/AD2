package gretig;

import datastructures.DualGraph;
import datastructures.PlaneArray;
import elements.Face;
import elements.Pair;
import elements.Plane;
import elements.PlaneNode;

import java.util.List;

/**
 * Created by Jarre on 22/11/2016.
 */
public class YutsisDecomposer {
    private DualGraph graph;
    private PlaneArray V;
    private PlaneArray M;
    private PlaneArray[] L;
    private PlaneArray D;

    public YutsisDecomposer(DualGraph graph){
        this.graph = graph;
        L = new PlaneArray[3];
    }

    //Gebaseerd op het algoritme uit de syllabus.
    //Returns if a yutsisdecomposition is found.
    //If a yutsisdecomp is found, PlaneArray.D & PlaneArray.M will contain the 2 nodeplane sets.
    public PlaneArray[] findYutsisDecomposition(){
        Face lastcheckedFace = null;
        initializeYutsisArrays();
        //Note: Always remove a node first, before adding it to another array!
        int n = V.length();
        PlaneNode node = V.get(0);
        V.remove(node);
        M.add(node);
        for(Pair pair: node.getPairs()){
            if(!pair.isMarked()){
                pair.getFace().markBoundaries();
            }
        }
        update(node);
        boolean finished = false;
        //Unplaced neighbour: neighbours that are not yet contained in Li or the tree
        while(M.length() < n/2 && !finished){
            int i=2;
            node = null;
            while(node==null && i>=0){
                //The computation of the number of unplaced neighbours can obviously be done
                //in constant time and every vertex is tested and moved at most 3 times.
                if(L[i].length()>0){
                    PlaneNode maxNode = L[i].peek();
                    if(i>0){
                        //Check amount of unplaced neighbours
                        int unplacedNeighbourAmount = maxNode.neighbourAmount(V);
                        //If unplacedneighbourAmount isn't correct, switch this node to the correct L array.
                        if(unplacedNeighbourAmount!=i){
                            maxNode.switchTo(L[unplacedNeighbourAmount]);
                        }
                        else {
                            node = maxNode;
                        }
                    }
                    else { //If i = 0, neighbourAmount doesn't have to be checked.
                        node = maxNode;
                    }
                }
                else {
                    i--;
                }
                if(node!=null){
                    if(node.neighbourAmount(M)!=1 ){
                        node.switchTo(V);
                        node = null;
                    }
                    else {
                        List<Pair> pairs = node.getPairs();
                        boolean found = false;
                        for(int j=0; j<pairs.size() && !found; j++){
                            Pair pair = pairs.get(j);
                            PlaneNode endNode = pair.getEndNode(node);
                            if(!endNode.isPresent(M) && !pair.isMarked()){
                                found = true;
                                lastcheckedFace = pair.getFace();
                            }
                        }
                        if(!found){
                            node.switchTo(V);
                            node = null;
                        }
                    }
                }
            }
            if(node==null){
                finished = true;
            }
            else{
                node.switchTo(M);
                lastcheckedFace.markBoundaries();
                update(node);
                //node.markPairs();
            }

        }
        return buildDecomposition(n); //Makes 2 independent trees from the sets.
    }

    public PlaneArray[] buildDecomposition(int n){
        System.out.println(M.length() + "/" + n/2);
        boolean hasYutsisDecomposition = M.length() == n/2;
        if(hasYutsisDecomposition) {
            //Search elements which are not in M.
            for (int i = 0; i < V.length(); i++) {
                PlaneNode planeNode = V.get(i);
                if(planeNode!=null){
                    D.add(V.get(i));
                }
            }
            for(int j=0; j<3; j++){
                for (int i = 0; i < L[j].length(); i++) {
                    PlaneNode planeNode = L[j].get(i);
                    if(planeNode!=null){
                        D.add(L[j].get(i));
                    }
                }
            }

            hasYutsisDecomposition = hasYutsisDecomposition && D.length() == n / 2; //If both sets contain n nodes, there is a yutsis decomposition.
            if(!hasYutsisDecomposition){
                return null;
            }
            else {
                PlaneArray[] yutsisdecomposition = new PlaneArray[2];
                yutsisdecomposition[0] = M;
                yutsisdecomposition[1] = D;
                return yutsisdecomposition;
            }
        }
        return null;
    }

    private void update(PlaneNode node) {
        for(PlaneNode neighbour: node.getNeighbours()){
            if(neighbour.isPresent(V) && neighbour.neighbourAmount(M)==1){
                int index = neighbour.neighbourAmount(V);
                neighbour.switchTo(L[index]);
            }
        }
    }


    private void initializeYutsisArrays() {
        V = new PlaneArray(graph.getSize());
        M = new PlaneArray(graph.getSize()/2);
        for(int j=0; j<3; j++){
            L[j] = new PlaneArray(graph.getSize());
        }
        D = new PlaneArray(graph.getSize()/2);
        for(Plane plane: graph.getPlanes()){ //Linear time, and is called once.
            if(plane!=null){
                V.add(plane.getNode());
            }
        }
    }
}
