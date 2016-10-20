
package binomialheap;


import gretig.Node;

//Algorithms based on http://www.cse.yorku.ca/~aaw/Sotirios/BinomialHeapAlgorithm.html

public class BinomialHeap {

    BinomialNode head;

    public void insert(Node key){
        BinomialHeap newHeap = new BinomialHeap();
        newHeap.setHead(new BinomialNode(key));
        head = union(newHeap);
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
                if(currentNode.getKey().compareTo(nextNode.getKey()) >= 0){
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
        BinomialNode h1 = other.getHead();
        BinomialNode h2 = head;
        if(h1==null){
            return h2;
        }
        if(h2==null){
            return h1;
        }

        BinomialNode h;

        if(h1.getDegree() < h2.getDegree()){
            h = h1;
            h1 = h1.getSibling();
        }
        else {
            h = h2;
            h2 = h2.getSibling();
        }
        while(h1 != null && h2!=null){
            if(h1.getDegree() < h2.getDegree()){
                h.setSibling(h1);
                h1 = h1.getSibling();
            }
            else {
                h.setSibling(h2);
                h2 = h2.getSibling();
            }
            //By reference or by value????
            h= h.getSibling();
        }

        if(h1!=null){
            h.setSibling(h1);
        }
        else {
            h.setSibling(h2);
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
    }
}
