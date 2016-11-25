package test;

import graph.Edge;
import graph.Graph;
import graph.Node;
import input.BinaryFileReader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit test for all methods in the elements class.
 * Note: In this test the SortedNodeArray, Edge and Node are tested because Graph uses all functionality these classes offer!
 */
public class GraphTest {
    List<Graph> graphs;

    @Before
    public void setUp(){
        BinaryFileReader reader = new BinaryFileReader();
        try {
            graphs = reader.readByteArray(reader.readBinaryFile("klein/alle_5.sec"));
        } catch (IOException e) {
            throw new AssertionError("The file couldn't be read.");
        }
    }

    @After
    public void tearDown(){
        graphs.clear();
    }

    /**
     * Testing method for the sorting the nodes of the elements by lowest degree first.
     */
    @Test
    public void testSortBackward(){
        testSort(true);
    }

    @Test
    public void testSortForward(){
        testSort(false);
    }

    @Test
    public void testAddNode() {
        int nodeAmount = 10;
        Graph g = new Graph(nodeAmount, 0);
        Node[] nodes = new Node[nodeAmount];
        for (int i = 1; i <= nodeAmount; i++) {
            nodes[i - 1] = new Node(i); //The number can be anything.
            g.addNode(nodes[i - 1]);
            assertTrue(getActualNodeArraySize(g.getNodes()) == i);
        }
        Node[] graphNodes = g.getNodes();
        for (int i = 0; i < nodeAmount; i++) {
            assertTrue(nodes[i].equals(graphNodes[i]));
        }
    }

    @Test
    public void testAddEdge(){
        int edgeAmount = 10;
        Graph g = new Graph(2, edgeAmount);
        Node v = new Node(1);
        Node w = new Node(2);
        for(int i=1; i<=edgeAmount; i++){
            g.addEdge(v, i);
            g.addEdge(w, i);
            assertTrue(v.getDegree()==i);
            assertTrue(w.getDegree()==i);
            assertTrue(getActualEdgeArraySize(g.getEdges())==i);
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddNodesWithExceedingNumber(){
        Graph g = new Graph(1, 0);
        g.addNode(new Node(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddNodeWithNumberZero(){
        Graph g = new Graph(1, 0);
        g.addNode(new Node(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNodesWithSameNumber(){
        Graph g = new Graph(2, 0);
        g.addNode(new Node(1));
        g.addNode(new Node(1));
        g.addNode(new Node(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddToManyNodesToGraph(){
        Graph g = new Graph(1, 0);
        g.addNode(new Node(1));
        g.addNode(new Node(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddToManyEdgesToGraph(){
        Graph g = new Graph(1, 2);
        Node[] nodes = {new Node(1), new Node(2)};
        g.addEdge(nodes[0], 1);
        g.addEdge(nodes[0], 2);
        g.addEdge(nodes[0], 3);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddToManyNodesToEdge(){
        Graph g = new Graph(3,3);
        Node v = new Node(1);
        Node w = new Node(2);
        Node u = new Node(3);
        g.addEdge(v, 1);
        g.addEdge(w, 1);
        g.addEdge(u, 1);
    }

    public void testSort(boolean backwards){
        for(Graph graph: graphs){
            assertNull(graph.getSortedNodes()); //The sorted node array should be null when sort hasn't been called upon.
            graph.sort(backwards); //The sorting will be performed by lowest degree first.
            assertNotNull(graph.getSortedNodes());
            int previousDegree;
            if(backwards){
                previousDegree = 1; //The minimum possible degree.
                for(Node node: graph.getSortedNodes()){ //Lowest degree first.
                    assertTrue(node.getDegree()>=previousDegree);
                    previousDegree = node.getDegree();
                }
            }
            else {
                previousDegree = graph.getNodes().length-1; //The maximum possible degree.
                for(Node node: graph.getSortedNodes()){ //Highest degree first.
                    assertTrue(node.getDegree()<=previousDegree);
                    previousDegree = node.getDegree();
                }
            }
        }
    }

    /**
     * @return Amount of non null nodes in given array.
     */
    public int getActualNodeArraySize(Node[] nodes){
        int i =0;
        for(Node node: nodes){
            if(node!=null){
                i++;
            }
        }
        return i;
    }

    /**
     * @return Amount of non null nodes in given array.
     */
    public int getActualEdgeArraySize(Edge[] edges){
        int i =0;
        for(Edge edge: edges){
            if(edge!=null){
                i++;
            }
        }
        return i;
    }



}
