package RedBlackTree;

import pojo.Bin;

import java.util.Queue;

public interface BinaryTree<T extends Number & Comparable<T>, N extends TreeNode<T,N>> {
    /**
     * Method to search a node in the tree
     * @param key   a tree node's key
     * @return a tree node
     */
    N search(T key);

    /**
     * Method to insert a node in the tree
     * @param newNode a new Tree Node
     * @return void
     */
    void insert(N newNode);

    /**
     * Method to remove a node in the tree
     * @param node an exisiting Tree Node
     * @return void
     */
    void remove(N node);

    /**
     * Method to get all node Tree Node
     */
    int size();

    /**
     * Method to check tree contains node
     */
    boolean isEmpty();

    /**
     * Method to check tree null
     */
    boolean isNil(N node);

    /**
     * Method to print binary tree inorder
     */
    void inorderPrint();

    /**
     * Method to print binary tree preorder
     */
    void preorderPrint();

    /**
     * Method to print binary tree postorder
     */
    void postorderPrint();


    /**
     * Method to print binary tree levelorder
     */
    void levelOrderPrint();
}