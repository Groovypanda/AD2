package elements;

/**
 * Created by Jarre on 20/11/2016.
 */
public class CycleNode {
    private int number;
    private CycleNode[] neighbours;

    public CycleNode(int number) {
        this.number = number;
        neighbours = new CycleNode[2];
    }

    public CycleNode[] getNeighbours() {
        return neighbours;
    }

    public void addNeighbour(CycleNode cycleNode){
        if(neighbours[0] == null){
            neighbours[0]=cycleNode;
        }
        else if(neighbours[1] == null && !neighbours[0].equals(cycleNode)){
            neighbours[1]=cycleNode;
        }
        else {
            if(!neighbours[0].equals(cycleNode) || !neighbours[1].equals(cycleNode)){
                System.out.println("Tried adding to many edges.");
            }
        }
        //Else neighbours is filled.
    }

    public CycleNode getNext(CycleNode previous){
        return !previous.equals(neighbours[0]) ? neighbours[0] : neighbours[1];
    }

    public boolean equals(Object other){
        return other instanceof CycleNode && ((CycleNode) other).number == number;
    }

    public String toString(){
        return Integer.toString(number);
    }
}
