import gretig.BinaryFileReader;
import gretig.Graph;

import java.util.List;

/**
 * Created by Jarre on 8/10/2016.
 */
public class Test {
    public static void main(String[] args) {
        BinaryFileReader bfr = new BinaryFileReader();
        List<Graph> graphs = bfr.readByteArray(bfr.readBinaryFile("alle_5.sec"));
        int i =0;
        for(Graph graph: graphs){
            System.out.println("Graph " + i++);
            graph.printNodes();
            System.out.println("---------");
        }
        /*

        Dominantie d = new Dominantie();
        for(Graph g: graphs){
            d.getDominantList(g);
        }
        */
    }
}
