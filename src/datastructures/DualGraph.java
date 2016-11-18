package datastructures;

import elements.Edge;
import elements.Plane;
import elements.Plane;

/**
 * The dual elements is a elements which is returned by drawing a node in every plane and connecting 2 of these nodes
 * if the corresponding planes have a common edge. (Syllabus Algoritmen en Datastructuren 2)
 * This is a 3-regular elements as every plane in a triangulation has 3 adjacent planes.
 */
public class DualGraph {

    private Plane[] planes; //The nodes of this elements.
    private int planesSize = 0;
    private Graph graph;

    public DualGraph(Graph graph) {
        this.graph = graph;
        setupPlaneSet();
    }

    private void setupPlaneSet() {
        planes = new Plane[getSize()]; //There are 2n-4 planes in a triangulation.
        addFirstAdjacentPlanes(graph.getEdges()[0]);
    }

    private void addFirstAdjacentPlanes(Edge edge) {
        Plane[] planes = getAdjacentPlanes(edge);
        for(Plane plane: planes){
            this.planes[planesSize++] = plane;
            addAdjacentPlanes(plane, edge);

        }
    }

    private void addAdjacentPlanes(Plane plane, Edge edge){
        for(Edge edge1: plane.getEdges()){ //This iteration will happen in total 3(2n-4) times. Once for each edge in each plane.
            if(!edge1.equals(edge)){
                if(!edge1.isFull()){
                    addAdjacentPlane(plane, edge1);
                }
                else {
                    Plane[] adjacentPlanes = edge.getAdjacentPlanes();
                    Plane other = adjacentPlanes[0];
                    if(other.equals(plane)){
                        other = adjacentPlanes[1];
                    }
                    if(other!=null){
                        plane.addAdjacentPlane(other);
                        other.addAdjacentPlane(plane);
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
        planes[planesSize++] = adjacentPlane;
        addAdjacentPlanes(adjacentPlane, edges[1]);
        addAdjacentPlanes(adjacentPlane, edges[2]);
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
