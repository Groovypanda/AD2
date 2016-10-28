import graph.Graph;
import graph.Node;
import gretig.*;
import input.BinaryFileReader;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jarre on 8/10/2016.
 */
public class Test {
    static int i = 0;
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        List<Graph> graphs = readGraphs("testset/graaf6.sec");
        PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
        for(Graph g: graphs){
            i++;
            DominatingSetCalculator setCalculator = new DominatingSetCalculator(g);
            List<Node> dominantList = setCalculator.getDominantList();
            for(Node node: dominantList){
                //System.out.println(node);

            }
            for(Node node: g.getNodes()){
                writer.print(node.getEdgesAmount());
                writer.print(' ');
            }
            System.out.println(dominantList.size());
            System.out.println(g.getNodes().length);
            try {
                assert(Dominantie.isDominant(g, dominantList));
            } catch (AssertionError e ){
                System.out.println("The dominating list isn't domating!!!.");
            }
            //System.out.println("===========");
        }
    writer.close();
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
