package elements;

/**
 * Created by Jarre on 23/11/2016.
 */
public class PlaneEdge {
    private PlaneNode[] endpoints;
    private Edge edge;
    private Face[] adjacentFaces;

    public PlaneEdge(Plane plane1, Plane plane2) {
        adjacentFaces = new Face[2];
        endpoints = new PlaneNode[2];
        endpoints[0]=plane1.getNode();
        endpoints[1]=plane2.getNode();
        plane1.getNode().addEdge(this);
        plane2.getNode().addEdge(this);
        edge = plane1.getCommonEdge(plane2);
    }

    public PlaneNode[] getEndpoints(){
        return endpoints;
    }

    public boolean equals(Object other){
        return other instanceof PlaneEdge && ((PlaneEdge) other).edge.equals(edge);
    }

    public PlaneNode getCommonNode(PlaneEdge planeEdge) {
        for(PlaneNode node1: endpoints){
            for(PlaneNode node2: planeEdge.endpoints){
                if(node1.equals(node2)){
                    return node1;
                }
            }
        }
        return null;
    }

    public void addFace(Face face){
        if(adjacentFaces[0]==null){
            adjacentFaces[0]=face;
        }
        else if(adjacentFaces[1]==null && !adjacentFaces[0].equals(face)){
            adjacentFaces[1]=face;
        }
    }

    public Face[] getAdjacentFaces(){
        return adjacentFaces;
    }

    public String toString(){
        return "(" + endpoints[0].toString() + " , " + endpoints[1].toString() + ")";
    }
}
