package elements;

/**
 * Created by Jarre on 23/11/2016.
 */
public class Face {
    private PlaneEdgePair[] boundaries;

    public Face(PlaneEdgePair first, PlaneEdgePair second) {
        int i=0;
        boundaries = new PlaneEdgePair[4];
        boundaries[i++] = first;
        boundaries[i++] = second;
        //Decide other 2.
        for(PlaneEdge edge1: first.getPair()){
            for(PlaneEdge edge2: first.getPair()){
                PlaneNode common = edge1.getCommonNode(edge2);
                if(common!=null){
                    PlaneEdgePair pair = common.getPlaneEdgePair(edge1, edge2);
                    boundaries[i++] = pair;
                }
            }
        }
        for(PlaneEdgePair pair: boundaries){
            pair.setFace(this);
        }
    }

    public PlaneEdgePair getBoundary(PlaneEdgePair pair){
        if(boundaries[0].equals(pair)){
            return boundaries[1];
        }
        else if(boundaries[1].equals(pair)){
            return boundaries[0];
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public void markBoundaries(){
        for(PlaneEdgePair pair: boundaries){
            pair.mark();
        }
    }
}
