package RedBlackTree;

import java.util.ArrayDeque;

import ArrayList.CustomArrayList;

import java.util.Objects;
import java.util.Queue;

public abstract class AbstractBinaryTree<T extends Number & Comparable<T>, N extends TreeNode<T, N>> implements BinaryTree<T, N> {

    //Pointer to traverse the tree
    protected N root;

    //Check the tree is nill
    protected N nil;


    /**
     * Abstract method to insert a node in the tree
     * @param newNode a new Tree Node
     * @return void
     */
    public void insert(N newNode) {
    }

    /**
     * Abstract method to remove by node in the tree
     * @param node an exisiting Tree Node
     * @return void
     */
    public void remove(N nodeToRemove) {
    }


    /**
     * Abstract method to remove by key in the tree
     * @param key an key Tree Node
     * @return void
     */
    public void remove(T key) {
    }

    /**
     * Method to search a node in the tree
     * @param key   a tree node's key
     * @param root  a pointer to traverse the tree
     * @return a tree node
     */
    protected N search(T key, N root) {
        if (isNil(root)) {
            return null;
        }

        if (root.key.equals(key)) {
            return root;
        }
        N leftResult = search(key, root.left);
        N rightResult = search(key, root.right);
        if (leftResult != null) {
            return leftResult;
        } else {
            return rightResult;
        }
    }

    /**
     * Method to search a node in the tree
     * @param key   a tree node's key
     * @return a tree node
     */
    public N search(T key) {
        N temp = root;
        return search(key, temp);
    }


    /**
     * Method to print binary tree inorder
     */
    public void inorderPrint() {
        N temp = root;
        inorderTraversal(temp);
    }


    /**
     * Helper method to print the key
     */
    protected void inorderTraversal(N root) {
        if (isNil(root)) {
            return;
        }

        this.inorderTraversal(root.left);
        System.out.println(root.key);
        this.inorderTraversal(root.right);
    }

    /**
     * Method to print binary tree preorder
     */
    public void preorderPrint() {
        N temp = root;
        preorderTraversal(temp);
    }

    /**
     * Helper method to print the key
     */
    protected void preorderTraversal(N root) {
        if (isNil(root)) {
            return;
        }

        System.out.println(root.key);
        this.inorderTraversal(root.left);
        this.inorderTraversal(root.right);
    }

    /**
     * Method to print binary tree postorder
     */
    public void postorderPrint() {
        N temp = root;
        postorderTraversal(temp);
    }

    /**
     * Helper method to print the key
     */
    protected void postorderTraversal(N root) {
        if (isNil(root)) {
            return;
        }

        this.inorderTraversal(root.left);
        this.inorderTraversal(root.right);
        System.out.println(root.key);
    }


    /**
     * Method to print binary tree levelorder
     */
    public void levelOrderPrint() {
        N temp = root;
        bft(temp);
    }

    /**
     * Helper method to print the key
     */
    protected void bft(N root) {
        if (isNil(root)) {
            System.out.println("The current binary tree is empty");
            return;
        }
        CustomArrayList<T> currentKeys = new CustomArrayList<>();
        Queue<N> queue = new ArrayDeque<>();
        queue.offer(root);
        int level = 1;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                N node = queue.poll();
                currentKeys.add(node.key);
                if (!isNil(node.left)) {
                    queue.offer(node.left);
                }

                if (!isNil(node.right)) {
                    queue.offer(node.right);
                }
            }

            System.out.println("Level: " + level + ", Nodes: " + currentKeys.toString());
            currentKeys.clear();
            level += 1;
        }
    }

    /**
     * Method to get all node Tree Node
     */
    public int size() {
        N temp = root;
        return size(temp);
    }

    /**
     * Method to check tree null
     * @param root  pointer to traverse the tree
     */
    protected int size(N root) {
        if (isNil(root)) {
            return 0;
        }

        return 1 + size(root.left) + size(root.right);
    }

    /**
     * Method to check tree null
     * @param node  node to check is nil
     */
    public boolean isNil(N node) {
        return node == nil;
    }

    /**
     * Method to check tree contains node
     */
    public boolean isEmpty() {
        return isNil(root);
    }
}