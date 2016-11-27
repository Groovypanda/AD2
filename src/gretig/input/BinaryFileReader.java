package gretig.input;

import gretig.graph.Graph;
import gretig.graph.Node;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;


/**
 * <h1>Creates graphs out of a given file containing SEC</h1>
 * This class is purely used for testing purposes. It doesn't read from standard input, buts reads from a specified
 * file. The BinaryFileReader can read a binary file and process its contents (simple edge code)
 * to create a list of graphs.
 * @author Jarre Knockaert
 */
public class BinaryFileReader {

    /**
     * This method should be used to read a binary file.
     * @param filename The name of the file to be read.
     * @return A byte array containing the contents of the given file. Returns null if the file couldn't be read.
     */
    public byte[] readBinaryFile(String filename) throws IOException {
        try {
            Path path = Paths.get(System.getProperty("user.dir"), "data", filename);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            System.err.println("Failed to read the given binary file: " + filename);
            throw e;
        }
    }

    /**
     * Transforms an array of bytes into a list of graphs. The array of bytes should be formatted as SIMPLE EDGE CODE.
     * @param bytes An array of bytes formatted as SIMPLE EDGE CODE
     * @return A list containing the graphs specified by the SIMPLE EDGE CODE
     * @throws IndexOutOfBoundsException if the byte array contains less than 7 bytes.
     * @throws IllegalArgumentException if the byte array doesn't start with >>SEC<<
     */
    public List<Graph> readByteArray(byte[] bytes) throws IllegalFormatException {
        //Check if format is correct.
        int i;
        String header = "";
        for(i=0; i<7; i++){
            header += (char) bytes[i];
        }
        if(!header.equals(">>SEC<<")){
            throw new IllegalArgumentException("The header should equal: '>>SEC<<'.");
        }
        List<Graph> graphs = new ArrayList<Graph>();
        //Loop trough bytes and and create graphs.
        while(i<bytes.length) {
            //Read a single graph.
            int numberLength = (int) bytes[i++];
            //Amount of nodes in the graph.
            int nodeAmount = readNumber(bytes, i, numberLength);
            //Amount of edges in the graph.
            int edgeAmount = readNumber(bytes, i + numberLength, numberLength);
            Graph graph = new Graph(nodeAmount, edgeAmount);
            int nodeIndex = 0;
            Node[] nodes = new Node[nodeAmount]; //An array for all read nodes.
            nodes[0] = new Node(1); //Start with one node.
            i += 2 * numberLength; //2*numberLength bytes have been read so far.
            //Loop trough all edges until the last node has been reached.
            //A new node is indicated by zero.
            for (; i < bytes.length && nodeIndex < nodeAmount; i += numberLength) {
                int number = readNumber(bytes, i, numberLength); //Read the next 'numberLength' bytes.
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
     * Reads a certain amount of bytes, and returns the value of these bytes as an integer.
     * @param bytes The byte array of which bytes will be read.
     * @param index The offset from where the bytes should be read.
     * @param length The amount of bytes to be read. This should be lower than 4 as an integer can't contain more bytes.
     * @return An integer containing the value of maximum 4 bytes.
     * Note: it would be possible to work with longs for example if the graphs become really big. But this function works
     * for all given testcases, so it's unnecessary.
     */
    private int readNumber(byte[] bytes, int index, int length){
        int exp = 1;
        int number = 0;
        for(int k=index; k<index+length; k++){ //Loop trough the length bytes starting at index.
            number += Byte.toUnsignedInt(bytes[k])*exp; //Numbers shouldn't ever be negative
            exp*=256; //If a byte has been read, the exponent has to be increased by 2^8.
        }
        return number;
    }

    /**
     * Adds all graphs from a .sec file to the graphs list.
     */
    public List<Graph> getFileGraphs(Path path){
        List<Graph> graphs = new ArrayList<>();
        try {
            graphs.addAll(readByteArray(Files.readAllBytes(path)));
        } catch (IOException e) {
            throw new AssertionError("The file couldn't be read.");
        }
        return graphs;
    }

    /**
     * Adds all graphs from a directory with .sec files to the graph list.
     * @param directoryName The name of the directory
     * @return A list with all the graphs
     */
    public List<Graph> getDirectoryGraphs(String directoryName){
        List<Graph> graphs = new ArrayList<>();
        try {
            try(Stream<Path> paths = Files.walk(Paths.get(System.getProperty("user.dir"), "data", directoryName))) {
                paths.forEach(filePath -> {
                    if (Files.isRegularFile(filePath)) {
                        graphs.addAll(getFileGraphs(filePath));
                    }
                });
            }
        } catch (IOException e) {
            throw new AssertionError("Couldn't find the given directory");
        }
        return graphs;
    }

    /**
     * Adds all the graphs from a directory with .sec files to the graph map.
     * The difference with the method getDirectoryGraphs is: the result here is saved in map in which each key represents
     * the name of the file.
     * @param directoryName The name of the directory
     * @return
     */
    public Map<String, List<Graph>> getDirectoryGraphsExtended(String directoryName){
        Map<String, List<Graph>> graphs = new HashMap<>();
        try {
            try(Stream<Path> paths = Files.walk(Paths.get(System.getProperty("user.dir"), "data", directoryName))) {
                paths.forEach(filePath -> {
                    if (Files.isRegularFile(filePath)) {
                        graphs.put(filePath.getFileName().toString(), getFileGraphs(filePath));
                    }
                });
            }
        } catch (IOException e) {
            throw new AssertionError("Couldn't find the given directory");
        }
        return graphs;
    }
}