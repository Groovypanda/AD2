package elements;

/**
 * Alle operaties in een plane hebben constante tijd aangezien elke methode hoogstens 3 constante iteraties uitvoert.
 */
public class Plane {
    private PlaneNode node;
    private Plane[] neighbours;
    private int neighbourIndex = 0;
    protected static int PLANESIZE = 3;
    private Edge[] edges;
    private boolean visited;


    public Plane(Edge[] edges) {
        this.edges = edges;
        for(Edge edge: edges){
            edge.addPlane(this);
        }
        neighbours = new Plane[PLANESIZE];
        //Bidirectional association with plane. We need to be able to extract the PlaneNode from a plane, and the
        //Plane from a PlaneNode because the Plane is made in the DualGraph but doesn't need the functionality of a
        //PlaneNode. However once the yutsis decomposition begins, we need to be able to extract all the information
        //from the planeNode, while being able to use the functionality of the Plane object.
        this.node = new PlaneNode(this);
        visited = false;
    }

    public String toString(){
        Node[] nodes = new Node[PLANESIZE];
        int nodeAmount = 0;
        for(Edge edge: edges){
            if(nodeAmount<3){
                for(Node node1: edge.getNodes()){
                    boolean add = true;
                    for(Node node2: nodes){
                        if(node1.equals(node2)){
                            add = false;
                        }
                    }
                    if(add){
                        nodes[nodeAmount++] = node1;
                    }
                }
            }
        }
        String output = "";
        for(int i=0; i<nodes.length-1; i++){
            output += nodes[i].getNumber() + " ";
        }
        output += nodes[nodes.length-1].getNumber();
        return output;
    }

    public boolean equals(Object o){ //Constant time. This method will iterate 3 times.
        if(!(o instanceof  Plane)){
            return false;
        }
        Plane other = (Plane) o;
        for(int i=0; i<PLANESIZE; i++){
            Edge edge1 = edges[i];
            Edge edge2 = other.edges[i];
            if(!edge1.equals(edge2)){
                return false;
            }
        }
        return true;
    }

    public Edge[] getEdges() {
        return edges;
    }

    public boolean equalEdges(Edge[] edges) { //Constant time, checks 9 things.
        for(Edge edge1: edges){ //3 iterations
            boolean hasEdge = false;
            for(Edge edge2: getEdges()){ //3 iterations
                if(edge1.equals(edge2)){
                    hasEdge = true;
                }
            }
            if(!hasEdge){
                return false;
            }
        }
        return true;
    }

    public void addAdjacentPlane(Plane plane){
        if(neighbourIndex < PLANESIZE){
            for(int i=0; i<neighbourIndex; i++){
                if(neighbours[i].equals(plane)){
                    return; //Don't add the plane if it's already in the adjacent planes.
                }
            }
            neighbours[neighbourIndex++] = plane;
        }
    }

    public Plane[] getAdjacentPlanes(){
        return neighbours;
    }

    public PlaneNode getNode(){
        return node;
    }

    public Edge getCommonEdge(Plane adjacentPlane){
        for(Edge edge1: edges){
            for(Edge edge2: adjacentPlane.edges){
                if(edge1.equals(edge2)){
                    return edge2;
                }
            }
        }
        return null;
    }

    public boolean isVisited() {
        return visited;
    }

    public void visit() {
        visited = true;
    }
}
