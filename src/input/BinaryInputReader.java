package input;

import graph.Graph;
import graph.Node;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Creates graphs out of the given standard input</h1>
 * This class contains a method to read SIMPLE EDGE CODE from standard input and transform these bytes
 * into a list of graphs.
 * @author Jarre Knockaert
 */
public class BinaryInputReader {
    int numberlength = 0; //Specifies the amount of bytes each value contains.

    /**
     * Transforms an array of bytes into a list of graphs. The array of bytes should be formatted as SIMPLE EDGE CODE.
     * These bytes are read from standard input.
     * @return A list containing the graphs with its node and edges specified by the SIMPLE EDGE CODE
     * @throws IllegalArgumentException if the byte array doesn't start with >>SEC<<
     */
    public List<Graph> readByteArray() throws IOException {
        byte[] headerbytes = new byte[7]; //Create an array in which the read bytes of the header will be stored.
        System.in.read(headerbytes, 0, 7); //Read the header from the standard input.
        String header = "";
        for(int i=0; i<7; i++){
            header += (char) headerbytes[i];
        }
        //Header must equal >>SEC<<
        if(!header.equals(">>SEC<<")){
            throw new IllegalArgumentException("The header should equal: '>>SEC<<'.");
        }
        List<Graph> graphs = new ArrayList<>();
        while(System.in.available()>0){ //While the stdin contains more bytes
            numberlength = System.in.read(); //Read the byte length of one value from stdin.
            int nodeAmount = readNumber(); //Read amount of nodes from stdin
            int edgeAmount = readNumber(); //Read amount of edges from stdin
            //There should atleast be one node and a positive amount of edges.
            if(nodeAmount<1 || edgeAmount < 0){
                throw new IllegalArgumentException("A elements should contain more than 1 node and a positive number of edges");
            }
            Graph graph = new Graph(nodeAmount, edgeAmount); //Create the new graph.
            Node[] nodes = new Node[nodeAmount]; //Create an empty array for storing the read nodes.
            int nodeIndex = 0;
            nodes[0] = new Node(1);
            /**
             *  Read all edges from the graph, a new node is indicated by zero,
             *  one graph contains the following amount of bytes: numberlength*(nodeAmount+edgeAmount*2)
             */
            for(int i=0; i < numberlength*(nodeAmount+edgeAmount*2); i+=numberlength){
                int number = readNumber(); //Read the next value from stdin.
                if (number == 0) { //If the number is zero a new node can be made.
                    graph.addNode(nodes[nodeIndex]); //Add the previous node, which is now configured with its edges.
                    nodeIndex++;
                    if(nodeIndex!=nodeAmount){ //If it's the last node, it indicates the end of the graph.
                        nodes[nodeIndex] = new Node(nodeIndex+1);
                    }
                } else { //If the number isn't zero, the number is an edgeNumber.
                    graph.addEdge(nodes[nodeIndex], number); //Add the edge to the node.
                }
            }
            graphs.add(graph);
        }
        return graphs;
    }

    /**
     * Reads a certain amount of bytes from standard input, and returns the value of these bytes as an integer.
     * @return An integer containing the value of maximum 4 bytes.
     * Note: it would be possible to work with longs for example if the graphs become really big. But this function works
     * for all given testcases, so it's unnecessary.
     */
    private int readNumber() throws IOException {
        byte[] numberbyte = new byte[numberlength]; //Create an array in which the read bytes will be stored.
        System.in.read(numberbyte); //Read the bytes from standard input.
        int exp = 1;
        int number = 0;
        for(int k=0; k<numberlength; k++){ //Loop trough the numberlength bytes.
            number += Byte.toUnsignedInt(numberbyte[k])*exp; //Numbers shouldn't ever be negative
            exp*=256; //If a byte has been read, the exponent has to be increased by 2^8.
        }
        return number;
    }
}