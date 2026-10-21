package RedBlackTree;

import pojo.Bin;

import java.util.Queue;

public class RedBlackNode<T extends Number & Comparable<T>, V extends Queue<Bin<T>>> extends TreeNode<T, RedBlackNode<T, V>> {
    /**
     * Possible color for this node
     */
    public static final int BLACK = 0;

    /**
     * Possible color for this node
     */
    public static final int RED = 1;

    /*
     * The current value that the node holds, different from key
     * */
    protected V value;

    /**
     * Parent of node
     */
    protected RedBlackNode<T, V> parent;

    /**
     * The number of elements to the left of each node
     */
    protected int numLeft = 0;

    /**
     * The number of elements to the right of each node
     */
    protected int numRight = 0;

    /**
     * The color of the node
     */
    protected int color;

    RedBlackNode() {
        super(null);
    }

    /**
     * Constructor to initialize the  key and value.
     */
    RedBlackNode(T key, V value) {
        super(key);
        this.value = value;
        color = BLACK;
        numLeft = 0;
        numRight = 0;
        parent = null;
    }
}
