import graph.Graph;
import graph.Node;
import gretig.*;

import java.util.List;

/**
 * Created by Jarre on 8/10/2016.
 */
public class Test {
    public static void main(String[] args) {
        List<Graph> graphs = readGraphs("alle_5.sec");
        int i=0;
        for(Graph g: graphs){
            i++;
            List<Node> dominantList = Dominantie.getDominantList(g);
            for(Node node: dominantList){
                System.out.println(node);
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
