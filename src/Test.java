import graph.Graph;
import graph.Node;
import gretig.*;
import input.BinaryFileReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jarre on 8/10/2016.
 */
public class Test {
    static int i = 0;
    public static void main(String[] args) {
        List<Graph> graphs = readGraphs("klein/alle_5.sec");
        for(Graph g: graphs){
            List<Node> dominantList = new ArrayList<>();
            i++;
            dominantList = Dominantie.getDominantList(g);
            for(Node node: dominantList){
                System.out.println(node);
            }
            System.out.println(dominantList.size());
            System.out.println(g.getNodes().length);
            try {
                assert (Dominantie.isDominant(g, dominantList));
            }
            catch  (AssertionError err){
                System.out.println(err);
            }
            System.out.println("===========");
        }

    }

    public static List<Graph> readGraphs(String filename){
        BinaryFileReader bfr = new BinaryFileReader();
        return bfr.readByteArray(bfr.readBinaryFile(filename));

    }

    public static void showGraph(Graph graph){
        for(Node n: graph.getNodes()){
            System.out.println(n);
        }
    }
}
