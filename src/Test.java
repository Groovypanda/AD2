import gretig.BinaryFileReader;
import gretig.Dominantie;
import gretig.Graph;

import java.util.List;

/**
 * Created by Jarre on 8/10/2016.
 */
public class Test {
    public static void main(String[] args) {
        BinaryFileReader bfr = new BinaryFileReader();
        List<Graph> graphs = bfr.readByteArray(bfr.readBinaryFile("graaf1.sec"));
        /*
        for(Graph graph: graphs){
            graph.printNodes();
            System.out.println("---------");
        }
        */
        Dominantie d = new Dominantie();
        for(Graph g: graphs){
            d.getDominantList(g);
        }
    }
}
