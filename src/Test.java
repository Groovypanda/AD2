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
        List<Graph> graphs = readGraphs("klein/alle_8.sec");
        for(Graph g: graphs){
            i++;
            List<Node> dominantList = Dominantie.getDominantList(g);
            if(dominantList.size()>3){
                System.out.println(i);
            }
            //for(Node node: dominantList){
            //    System.out.println(node);
            //}
            //System.out.println(dominantList.size());
            //System.out.println(g.getNodes().length);
            //assert(Dominantie.isDominant(g, dominantList));
            //System.out.println("===========");
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
