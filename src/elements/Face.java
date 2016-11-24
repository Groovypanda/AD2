package elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jarre on 23/11/2016.
 */
public class Face {
    private List<Pair> boundaries;

    public Face() {
        boundaries = new ArrayList<>();
    }

    public void markBoundaries(){
        for(Pair pair: boundaries){
            pair.mark();
        }
    }

    public void addBoundary(Pair pair){
        boundaries.add(pair);
        pair.setFace(this);
    }

    public void print(){
        for(Pair pair: boundaries){
            System.out.println(pair);
        }
        System.out.println("===============");
    }
}
