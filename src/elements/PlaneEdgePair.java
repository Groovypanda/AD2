package elements;

/**
 * Created by Jarre on 23/11/2016.
 */
public class PlaneEdgePair {
    private PlaneEdge[] pair;
    private Face face; //Remark 5
    private boolean marked;
    private PlaneNode[] nodes;

    public PlaneEdgePair(PlaneNode commonNode, PlaneEdge edge1, PlaneEdge edge2) {
        pair = new PlaneEdge[2];
        pair[0]=edge1;
        pair[1]=edge2;
        marked = false;
        nodes = new PlaneNode[3];
        //Center is always nodes[1];
        nodes[1] = commonNode;
        nodes[0] = nodes[1].getNeighbour(edge1);
        nodes[2] = nodes[1].getNeighbour(edge2);
        nodes[1].addPair(this);
    }


    public boolean isMarked() { return marked; }

    public void mark() { marked = true; }

    public PlaneEdge get(int i) {
        return pair[i];
    }

    public Face getFace() {
        return face;
    }

    public void setFace(Face face) {
        this.face = face;
        for(PlaneNode node: nodes){
            node.addFace(face);
        }
    }

    public PlaneEdge[] getPair() {
        return pair;
    }

    public PlaneNode getCenter() {
        return nodes[1];
    }

    public PlaneNode[] getNodes() {
        return nodes;
    }

    public String toString(){
        String output = "";
        for(int i=0; i<nodes.length-1; i++){
            output += "(" + nodes[i] + ") ";
        }
        output += "(" + nodes[nodes.length-1] + ")";
        return output;
    }

    public boolean equals(PlaneEdge edge1, PlaneEdge edge2) {
        return (pair[0].equals(edge1) && pair[1].equals(edge2)) || (pair[0].equals(edge2) && pair[1].equals(edge1));
    }
}
