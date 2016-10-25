import nodearray.BinomialHeap;
import graph.Edge;
import graph.Graph;
import graph.Node;
import gretig.*;

import java.util.List;

/**
 * Created by Jarre on 8/10/2016.
 */
public class Test {
    public static void main(String[] args) {
        List<Graph> graphs = readGraphs("triang_alle_06.sec");
        for(Graph g: graphs){
            g.initialiseNodes();
            showGraph(g);
            System.out.println("===========");
            System.out.println("===========");
            System.out.println("Graph:");
            for(Edge n: g.getEdges()){
                System.out.println(n);
            }
            System.out.println("===========");
            System.out.println("===========");
            System.out.println("Dominant List:");
            List<Node> dominantList = Dominantie.getDominantList(g);
            for(Node n: dominantList){
                System.out.println(n);
            }
        }
        System.out.println("===========");
        System.out.println("===========");
        System.out.println("===========");
        System.out.println("===========");
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
