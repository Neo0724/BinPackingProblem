package RedBlackTree;

public abstract class TreeNode <T extends Number & Comparable<T>, N extends TreeNode<T,N>> {
    /**
     * Key of Each Node
     */
    public T key;
    public N left;
    public N right;

    /**
     * Default Constructor to initialize a node
     */
    public TreeNode(T key) {
        this.key =  key;
        left = null;
        right = null;
    }

}
