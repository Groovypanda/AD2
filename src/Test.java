import graph.Graph;
import graph.Node;
import gretig.*;
import input.BinaryFileReader;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Jarre on 8/10/2016.
 */
public class Test {
    static int i = 0;
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        List<Graph> graphs = readGraphs("testset/graaf1.sec");
        //PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
        for(Graph g: graphs){
            i++;
            DominatingSetCalculator setCalculator = new DominatingSetCalculator(g);
            List<Node> dominantList = setCalculator.getDominantList();
            /*
            for(Node node: dominantList){
                //System.out.println(node);
            }
            for(Node node: g.getNodes()){
                writer.print(node.getDegree());
                writer.print(' ');
            }
            */
            System.out.println(dominantList.size());
            System.out.println(g.getNodes().length);
            assert(setCalculator.isDominant(dominantList));
        }
        //writer.close();
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
