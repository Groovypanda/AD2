package input;

import graph.Graph;
import graph.Node;
import sun.misc.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by Jarre on 8/10/2016.
 */
public class BinaryInputReader {
    int numberlength = 0;

    public List<Graph> readByteArray() throws IOException {
        byte[] headerbytes = new byte[7];
        System.in.read(headerbytes, 0, 7);
        String header = "";
        for(int i=0; i<7; i++){
            header += (char) headerbytes[i];
        }
        //Header must be >>SEC<<
        if(!header.equals(">>SEC<<")){
            System.err.println("The header is incorrect!");
        }
        List<Graph> graphs = new ArrayList<>();
        while(System.in.available()>0){
            numberlength = System.in.read();
            int nodeAmount = readNumber();
            int edgeAmount = readNumber();
            if(nodeAmount<1 || edgeAmount < 0){
                System.err.println("There should atleast be one node.");
            }
            Graph graph = new Graph(nodeAmount, edgeAmount);
            Node[] nodes = new Node[nodeAmount];
            int nodeIndex = 0;
            nodes[0] = new Node(1);
            //total length of one graph is = numberlength*(nodeAmount+edgeAmount*2)
            for(int i=0; i < numberlength*(nodeAmount+edgeAmount*2); i+=numberlength){
                int number = readNumber();
                if (number == 0) {
                    graph.addNode(nodes[nodeIndex]);
                    nodeIndex++;
                    if(nodeIndex!=nodeAmount){
                        nodes[nodeIndex] = new Node(nodeIndex+1);
                    }
                } else {
                    graph.addVertex(nodes[nodeIndex], number);
                }
            }
            graphs.add(graph);
        }
        return graphs;
    }

    private int readNumber() throws IOException {
        byte[] numberbyte = new byte[numberlength];
        System.in.read(numberbyte);
        int exp = 1;
        int number = 0;
        for(int k=0; k<numberlength; k++){
            number += Byte.toUnsignedInt(numberbyte[k])*exp;
            exp*=256;
        }
        return number;
    }
}