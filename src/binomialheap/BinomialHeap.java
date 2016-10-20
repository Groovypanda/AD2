
package binomialheap;


import gretig.Node;


//Algorithms based on http://www.cse.yorku.ca/~aaw/Sotirios/BinomialHeapAlgorithm.html

public class BinomialHeap {

    BinomialNode head;
    private int size;
    private int currentSize;

    public BinomialHeap(int size){
        currentSize = 0;
        this.size = size;
    }

    public BinomialHeap(){
        head = null;
    }

    public void insert(Node key){
        assert(currentSize < size);
        BinomialHeap newHeap = new BinomialHeap();
        newHeap.setHead(new BinomialNode(key));
        head = union(newHeap);
        currentSize++;
    }

    //Returns an approximation of a sorted list of the BinomialNode in linear time.
    public Node[] getNodes(){
        currentSize=0;
        Node[] nodes = new Node[size];
        addNode(nodes, head);
        return nodes;
    }

    public void addNode(Node[] nodes, BinomialNode node){
        while (node != null) {
            node.getKey().setIndex(currentSize);
            nodes[currentSize++] = node.getKey();
            if (node.getChild() != null) {
                addNode(nodes, node.getChild());
            }
            node = node.getSibling();
        }
    }

    public void print() {
        System.out.println("Binomial heap:");
        if (head != null) {
            head.print(0);
        }
    }

    private BinomialNode union(BinomialHeap other){
        BinomialNode newHead = merge(other);
        if(newHead==null){
            return newHead;
        }
        BinomialNode previousNode = null;
        BinomialNode currentNode = newHead;
        BinomialNode nextNode = currentNode.getSibling();
        while(nextNode!=null){
            if(currentNode.getDegree() != nextNode.getDegree() ||
                    (nextNode.getSibling() != null && nextNode.getSibling().getDegree() == currentNode.getDegree())){
                previousNode = currentNode;
                currentNode = nextNode;
            }
            else {
                //Currentnode.key <= nextNode.key
                if(currentNode.getKey().compareTo(nextNode.getKey()) < 0){
                    currentNode.setSibling(nextNode.getSibling());
                    //Link nextNode en currentNode
                    link(nextNode, currentNode);
                }
                else {
                    if(previousNode==null){
                        newHead = nextNode;
                    }
                    else {
                        previousNode.setSibling(nextNode);
                    }
                    link(currentNode, nextNode);
                    currentNode = nextNode;
                }
            }
            nextNode = currentNode.getSibling();
        }
        return newHead;
    }

    private void link(BinomialNode x, BinomialNode y){
        x.setParent(y);
        x.setSibling(y.getChild());
        y.setChild(x);
        y.setDegree(y.getDegree()+1);
    }

    private BinomialNode merge(BinomialHeap other){
        BinomialNode h;
        BinomialNode h1 = head;
        BinomialNode h2 = other.getHead();
        if(h1==null){
            return h2;
        }
        if(h2==null){
            return h1;
        }

        if(h1.getDegree() <= h2.getDegree()){
            h = h1;
            h1 = h1.getSibling();
        }
        else {
            h = h2;
            h2 = h2.getSibling();
        }

        BinomialNode tail = h;

        while(h1 != null && h2 != null){
            if(h1.getDegree() < h2.getDegree()){
                tail.setSibling(h1);
                h1 = h1.getSibling();
            }
            else {
                tail.setSibling(h2);
                h2 = h2.getSibling();
            }
            tail = tail.getSibling();
        }

        if(h1!=null){
            tail.setSibling(h1);
        }
        else {
            tail.setSibling(h2);
        }
        return h;
    }

    private BinomialNode getHead() {
        return head;
    }

    private void setHead(BinomialNode head) {
        this.head = head;
    }

    private class BinomialNode {

        private Node key;
        private BinomialNode sibling = null;
        private BinomialNode child = null;
        private BinomialNode parent = null;
        private int degree = 0;

        public BinomialNode(Node key){
            this.key = key;
        }

        public Node getKey() {
            return key;
        }

        public void setKey(Node key) {
            this.key = key;
        }

        public BinomialNode getSibling() {
            return sibling;
        }

        public void setSibling(BinomialNode sibling) {
            this.sibling = sibling;
        }

        public BinomialNode getChild() {
            return child;
        }

        public void setChild(BinomialNode child) {
            this.child = child;
        }

        public BinomialNode getParent() {
            return parent;
        }

        public void setParent(BinomialNode parent) {
            this.parent = parent;
        }

        public int getDegree() {
            return degree;
        }

        public void setDegree(int degree) {
            this.degree = degree;
        }

        public void print(int level) {
            BinomialNode curr = this;
            while (curr != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Level " + level + ": ");
                sb.append(curr.key.toString());
                System.out.println(sb.toString());
                if (curr.child != null) {
                    curr.child.print(level + 1);
                }
                curr = curr.sibling;
            }
        }
    }
}
