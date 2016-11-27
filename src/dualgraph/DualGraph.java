package dualgraph;

import graph.Edge;
import graph.Graph;
import graph.Plane;

/**
 * The dual graph is created by drawing a node in every plane and connecting 2 of these nodes
 * if the corresponding planes have a common edge. The edges are the connections between 2 adjacent planes and the vertices
 * are the different planes. This is a 3-regular graph as every plane in a triangulation has 3 adjacent planes.
 * This graph is also known as a 2-connected cubic graph.
 * Note: I refer to the constant n in the comments. This equals the amount of nodes in this graph which equals the size of
 * 2*x-4, with x the amount of nodes in the inner graph.
 */
public class DualGraph {
    private Plane[] planes; //The nodes of this graph
    int planesIndex; //The current index of the planes array.
    private Graph graph; //The graph of which this dualGraph is made.
    private Pair[] pairs; //An array with pairs of edges with size 3*n.
    /**
     * This constructor creates a DualGraph out of a given planar triangulation.
     * @param graph The inner graph of the DualGraph which is a planar triangulation.
     */
    public DualGraph(Graph graph) {
        planesIndex = 0;
        this.graph = graph;
        setupPlaneSet(); //Initialise all of the nodes of this graph.
        setupGraphStructure(); //Initialise all of the pairs and faces of this graph.
    }

    /**
     * Initialises all of the pairs and faces in this graph.
     * This method works in linear time. It iterates over all of the planes, and has 2 inner loops which each have
     * 3 iterations.
     */
    private void setupGraphStructure(){
        int pairsLength = 0;
        pairs = new Pair[3*getSize()];  //There are 3*n pairs in a dual graph. (Every node is the center of 3 pairs.)
        for(Plane startPlane: planes){  //Iterate trough all of the planes.
            PlaneNode startNode = startPlane.getNode();
            for(PlaneNode middleNode: startNode.getNeighbours()){
                for(PlaneNode endNode: middleNode.getNeighbours()){ //Create all of the possible pair variations.
                    //Check if Pair exists already in the endNode.
                    if(!startNode.equals(endNode) && !middleNode.containsCenterPair(startNode, endNode)){
                        Pair pair = new Pair(startNode, middleNode, endNode);
                        pairs[pairsLength++] = pair;
                    }
                }
            }
        }

        /**
         *  Once all the pairs are made, the faces can be made.
         *  A face can be made by running trough a cycle with all of its nodes.
         *  This is done by always taking the next or previous neighbour. Whether a previous or next neighbours have
         *  to be taken, is given by the pair.
         */

        //Initialise some variables for the cycle.
        PlaneNode first;
        PlaneNode last;
        PlaneNode current;
        PlaneNode previous;
        PlaneNode next;
        for(Pair pair: pairs){
            if(pair.getFace()==null){ //If the pair already has a face, the face exists already.
                PlaneNode[] endNodes = pair.getEndNodes();
                last = endNodes[0];
                next = endNodes[1];
                current = pair.getCenter();
                first = current;
                Face face = new Face(); //Create a new face
                face.addPair(pair); //Add the first pair to the face.
                while(next!=last){ //Loop trough all of the nodes of this face.
                    previous = current;
                    current = next;
                    if(pair.getDirection()==-1){
                        next = current.getNextNode(previous);
                    }
                    else {
                        next = current.getPreviousNode(previous);
                    }
                    //Find the pair with the current node as center.
                    //A node is the center of 3 pairs, thus this call operates in constant time.
                    Pair currentPair = current.findCenterPair(previous, next);
                    //Add the new pair to the face.
                    face.addPair(currentPair);
                }
                //Add the last pair to the face.
                face.addPair(last.findCenterPair(first, current));
            }

        }
    }

    /**
     * Initialise all of the nodes of this graph. The nodes are planes of the given inner graph.
     */
    private void setupPlaneSet() {
        planes = new Plane[getSize()]; //There are 2n-4 planes in a triangulation.
        int planesFinishedIndex = 0;
        for(Plane plane: getAdjacentPlanes(graph.getEdges()[0])){
            planes[planesIndex++] = plane;
        }
        while(planesFinishedIndex<planes.length){ //Initiate all planes, this also makes sure a plane contains all of its adjacent planes.
            addAdjacentPlanes(planes[planesFinishedIndex++]);
        }
        for(Plane plane: planes){
            //Initialises the node of the given plane correctly.
            plane.initialiseNode();
        }

    }

    /**
     * Adds all of the adjacent planes of the given plane to the array of planes.
     * This also adds the adjacent planes to the neighbours of the given plane.
     * @param plane The plane of which the adjacent planes have to be added to the given plane and to this graph.
     */
    private void addAdjacentPlanes(Plane plane){
        for(Edge edge: plane.getEdges()){ //This iteration will happen in total 3(2n-4) times. Once for each edge in each plane.
            if(!edge.isFull()){ //If the 2 adjacent planes to an edge are known yet. Add a plane to the edge.
                addAdjacentPlane(plane, edge); //Create the plane adjacent to this plane with the given edge as common edge.
            }
            else { //The adjacent plane with this edge already exists, it has to be added as adjacent plane to this plane.
                Plane[] adjacentPlanes = edge.getAdjacentPlanes();
                boolean foundOther = false;
                for(int i=0; i<adjacentPlanes.length && !foundOther; i++) { //2 iterations
                    Plane adjacentPlane = adjacentPlanes[i];
                    if(adjacentPlane!=null && !adjacentPlane.equals(plane)) {
                        foundOther=true;
                        plane.addAdjacentPlane(adjacentPlane);
                        adjacentPlane.addAdjacentPlane(plane);
                    }
                }
            }

        }
    }

    /**
     * Creates a new plane which is adjacent to the given plane and has the given edge as common edge.
     * This also adds the new plane to the list of neighbours of the given plane and adds the given plane to the list of
     * neighbours of the new plane.
     * @param plane The plane which has the new plane as neighbour.
     * @param edge The common edge of the 2 planes.
     */
    private void addAdjacentPlane(Plane plane, Edge edge) {
        //Find the edges of the new plane.
        Edge[] edges = new Edge[3];
        edges[0] = edge;
        edges[1] = edge.getNextEdge(edge.getEndPoints()[0]);
        edges[2] = edge.getPreviousEdge(edge.getEndPoints()[1]);
        //If current plane has the same edges, than the other side of this edge contains the right edges.
        if(plane.equalEdges(edges)){
            edges[1] = edge.getNextEdge(edge.getEndPoints()[1]);
            edges[2] = edge.getPreviousEdge(edge.getEndPoints()[0]);
        }
        Plane adjacentPlane = new Plane(edges); //Create the plane
        plane.addAdjacentPlane(adjacentPlane); //Add the new plane as adjacent plane to the given plane.
        adjacentPlane.addAdjacentPlane(plane); //Add the given plane as adjacent plane to the new plane.
        planes[planesIndex++] = adjacentPlane; //Add the new plane to the list.
    }

    /**
     * Creates and returns the both adjacent planes to this edge.
     * @param edge The common edge of the new planes.
     * @return 2 new planes adjacent to this edge.
     */
    private Plane[] getAdjacentPlanes(Edge edge){
        Edge[] edges1 = new Edge[3]; //Edges of first plane.
        Edge[] edges2 = new Edge[3]; //Edges of second plane.
        Plane[] planes = new Plane[2];
        edges1[0] = edge;
        edges1[1] = edge.getNextEdge(edge.getEndPoints()[0]);
        edges1[2] = edge.getPreviousEdge(edge.getEndPoints()[1]);
        edges2[0] = edge;
        edges2[1] = edge.getNextEdge(edge.getEndPoints()[1]);
        edges2[2] = edge.getPreviousEdge(edge.getEndPoints()[0]);
        planes[0] = new Plane(edges1);
        planes[1] = new Plane(edges2);
        planes[0].addAdjacentPlane(planes[1]);
        planes[1].addAdjacentPlane(planes[0]);
        return planes;
    }

    /**
     * @return The nodes (Planes) of this graph.
     */
    public Plane[] getPlanes(){
        return planes;
    }

    /**
     * @return The amount of nodes (planes) of this graph.
     */
    public int getSize() {
        return 2*graph.getSize()-4;
    }

    /**
     * Resets the graph to its initial conditions.
     */
    public void reset(){
        for(Plane plane: planes){
            plane.getNode().reset();
        }
        for(Pair pair: pairs){
            pair.unmark();
        }
    }
}
