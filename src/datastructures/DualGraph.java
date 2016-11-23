package datastructures;

import elements.*;

/**
 * The dual elements is a elements which is returned by drawing a node in every plane and connecting 2 of these nodes
 * if the corresponding planes have a common edge. (Syllabus Algoritmen en Datastructuren 2)
 * This is a 3-regular elements as every plane in a triangulation has 3 adjacent planes.
 */
public class DualGraph {
    private Plane[] planes; //The nodes of this graph
    int planesIndex = 0; //The current index of the planes array.
    private Graph graph;
    private PlaneEdge[] planeEdges;
    private PlaneEdgePair[] pairs;

    public DualGraph(Graph graph) {
        this.graph = graph;
        setupPlaneSet();
        setupGraphStructure();
    }

    private void setupGraphStructure(){
        int edgesLength = 0;
        int pairsLength = 0;
        int length = 2*graph.getNodes().length-4;
        pairs = new PlaneEdgePair[3*length];
        planeEdges = new PlaneEdge[(3*length)/2];
        for(Plane plane: planes){ //2n-4 iterations
            for(Plane other: plane.getAdjacentPlanes()){ //3 iterations
                if(!other.isVisited()){
                    PlaneEdge edge = new PlaneEdge(plane, other);
                    planeEdges[edgesLength++] = edge;
                }
                else { //We can make pairs with the visited plane. (A visited plane means its planeEdges exists already.
                    PlaneNode first = plane.getNode();
                    PlaneNode second = other.getNode();
                    PlaneEdge connection = first.getEdgeTo(second); //Edge connecting the 2 planeNodes.
                    for(PlaneEdge edge: other.getNode().getAdjacentEdges()){ //3 iterations
                        if(!edge.equals(connection)){
                            PlaneNode commonNode = connection.getCommonNode(edge);
                            if(!commonNode.containsPlaneEdgePair(connection, edge)){ //Check if PlaneEdgePair doesn't exist yet.
                                PlaneEdgePair pair = new PlaneEdgePair(commonNode, connection, edge);
                                pairs[pairsLength++]=pair;
                            }
                        }
                    }
                }
            }
            plane.visit();
        }

        //Once all the pairs are made, the faces can be made.
        for(PlaneEdgePair pair: pairs){
            if(pair.getFace()==null){ //Make the face.
                PlaneNode[] pairNodes = pair.getNodes();
                for(PlaneEdge edge1: pairNodes[0].getAdjacentEdges()){ //Find other pair connecting the 2 endnodes of the given pair.
                    for(PlaneEdge edge2: pairNodes[2].getAdjacentEdges()) {
                        PlaneNode common = edge1.getCommonNode(edge2);
                        if (common != null && !common.equals(pairNodes[1])){ //Check if the node actually is the center of the other pair.
                            PlaneEdgePair other = common.getPlaneEdgePair(edge1, edge2);
                            new Face(pair, other);
                        }
                    }
                }
            }

        }
    }

    private void setupPlaneSet() {
        planes = new Plane[getSize()]; //There are 2n-4 planes in a triangulation.
        int planesFinishedIndex = 0;
        for(Plane plane: getAdjacentPlanes(graph.getEdges()[0])){
            planes[planesIndex++] = plane;
        }
        while(planesFinishedIndex<planes.length){ //Initiate all planes, this also makes sure a plane contains all of its adjacent planes.
            addAdjacentPlanes(planes[planesFinishedIndex++]);
        }
    }

    private void addAdjacentPlanes(Plane plane){
        for(Edge edge: plane.getEdges()){ //This iteration will happen in total 3(2n-4) times. Once for each edge in each plane.
            if(!edge.isFull()){ //If the 2 adjacent planes to an edge are known yet. Add a plane to the edge.
                addAdjacentPlane(plane, edge);
            }
            else {
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

    private void addAdjacentPlane(Plane plane, Edge edge) {
        Edge[] edges = new Edge[3];
        edges[0] = edge;
        edges[1] = edge.getNextEdge(edge.getNodes()[0]);
        edges[2] = edge.getPreviousEdge(edge.getNodes()[1]);
        if(plane.equalEdges(edges)){
            //If current plane has the same edges, than the other side contains the right edges.
            edges[1] = edge.getNextEdge(edge.getNodes()[1]);
            edges[2] = edge.getPreviousEdge(edge.getNodes()[0]);
        }
        Plane adjacentPlane = new Plane(edges);
        plane.addAdjacentPlane(adjacentPlane);
        adjacentPlane.addAdjacentPlane(plane);
        planes[planesIndex++] = adjacentPlane;
    }

    private Plane[] getAdjacentPlanes(Edge edge){
        Edge[] edges1 = new Edge[3]; //Edges of first plane.
        Edge[] edges2 = new Edge[3]; //Edges of second plane.
        Plane[] planes = new Plane[2];
        edges1[0] = edge;
        edges1[1] = edge.getNextEdge(edge.getNodes()[0]);
        edges1[2] = edge.getPreviousEdge(edge.getNodes()[1]);
        edges2[0] = edge;
        edges2[1] = edge.getNextEdge(edge.getNodes()[1]);
        edges2[2] = edge.getPreviousEdge(edge.getNodes()[0]);
        planes[0] = new Plane(edges1);
        planes[1] = new Plane(edges2);
        planes[0].addAdjacentPlane(planes[1]);
        planes[1].addAdjacentPlane(planes[0]);
        return planes;
    }

    public Plane[] getPlanes(){
        return planes;
    }

    public int getSize() {
        return 2*graph.getSize()-4;
    }
}
