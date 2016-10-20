import binomialheap.BinomialHeap;
import gretig.BinaryFileReader;
import gretig.Dominantie;
import gretig.Graph;
import gretig.Node;

import java.util.List;

/**
 * Created by Jarre on 8/10/2016.
 */
public class Test {
    public static void main(String[] args) {
        List<Graph> graphs = readGraphs("graaf1.sec");
        Graph g = graphs.get(0);
        g.initialiseNodes();
        showGraph(g);
        for(Node n: g.getNodes()){
            System.out.println(n);
        }
        System.out.println("----");
        System.out.println("Dominant List");
        List<Node> dominantList = Dominantie.getDominantList(g);
        System.out.println(dominantList);
    }

    public static List<Graph> readGraphs(String filename){
        BinaryFileReader bfr = new BinaryFileReader();
        return bfr.readByteArray(bfr.readBinaryFile(filename));

    }

    public static void showGraphs(List<Graph> graphs){
        int i =0;
        for(Graph graph: graphs){
            System.out.println("Graph " + i++);
            showGraph(graph);
            System.out.println("---------");
        }
    }

    public static void showGraph(Graph graph){
        //graph.printNodes();
        BinomialHeap heap = graph.getHeap();
        heap.print();
    }
}
