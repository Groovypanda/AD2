package gretig;

import graph.Edge;
import graph.Graph;
import graph.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jarre on 8/10/2016.
 */
public class Dominantie {
    //Algoritme uitvoeren op insertion sorted list instead of nodearray & remove := index = null.
    public static List<Node> getDominantList(Graph graph){
        List<Node> dominantList1 = new ArrayList<>();
        while(!graph.empty()){
            Node v = graph.peek();
            boolean added = false;
            for(Integer edgeNumber: v.getEdgeNumbers()){
                Edge e = graph.getEdge(edgeNumber);
                if(e!=null){
                    Node neighbour = e.getNeighbour(v);


                    if(graph.getNode(neighbour.getIndex())!=null){
                        graph.removeEdge(edgeNumber);
                        graph.removeNode(neighbour);
                        if(v.getEdgesAmount()<neighbour.getEdgesAmount()){
                            dominantList1.add(neighbour);
                            added = true;
                        }
                    }
                }
            }
            if(!added){
                dominantList1.add(v);
            }
            graph.removeNode(v);
        }
        return dominantList1;
    }
}


//Counting sort en verwijderen door op null te zetten!

//Check if graph still exists in graph.
//Iterating to remove edges would be to expensive.

//EVENTUEEL OM PROBLEEM OP TE LOSSEN: ITEREREN OVER VERWIJDERDE EDGES (MAAR IS HET DAN NOG LINEAR???)
                    /*
                    Nog een soort van controle of er buren van een toegevoegde top al in de lijst zitten,
                    zoja....
                    Ofwel na het algoritme nog eens de lijst overlopen als volgt:
                    while(!minimaal){
                        verwijder buur
                    }
                     */

//Niet elke iteratie een top toevoegen, maar enkel als het voldoet aan een bepaalde quota?
//Itereren over bogen is ook lineair in aantal toppen, want begrensd met 3n-6
//BEGIN AAN THEORETISCHE VRAAG 2 MORGEN!
