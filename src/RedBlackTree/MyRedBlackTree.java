package RedBlackTree;

import Algorithm.BestFit;
import Tree.BinIdComparator;
import pojo.Bin;
import pojo.Parcel;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import ArrayList.MyLinkedList;

public class MyRedBlackTree<T extends Number & Comparable<T>, V extends Queue<Bin<T>>> extends AbstractBinaryTree<T, RedBlackNode<T, V>> {

    // Root initialized to nil.
    private RedBlackNode<T, V> nil;

    // To print out the bins where the bins' id is in ascending order
    private MyLinkedList<Bin<T>> allBins = new MyLinkedList<Bin<T>>();

    //Maximum Bin Capacity
    private T binCapacity;

    // Provide a unique ID for each created bin
    private Integer binCount = 0;


    /**
     * Constructor to initiate the tree
     *
     * @param binCapacity Maximum capacity that a bin can store
     */
    public MyRedBlackTree(T binCapacity) {
        this.nil = new RedBlackNode<T, V>();
        super.root = nil;
        root.left = nil;
        root.right = nil;
        root.parent = nil;
        this.binCapacity = binCapacity;
    }

    /**
     * Insert a parcel into a bin that has the smallest key while being able to fit it
     *
     * @param newParcel Parcel to be inserted
     */
    public void insertParcel(Parcel<T> newParcel) {
        try {

            //Get the New Parcel Weight
            T parcelWeight = newParcel.getWeight();

            //Check parcel weight exceed the maximum bin capacity
            if (parcelWeight.compareTo(binCapacity) > 0) {
                System.out.println("Parcel ID: " + newParcel.getParcelId() + " weight exceeded the Maximum Bin Capacity.");
                return;
            }

            //a pointer node to traverse the tree
            RedBlackNode<T, V> temp = root;

            //find best fit remaning capacity
            T ceillingNodeKey = BestFit.findBestFit(this, temp, parcelWeight);

            // Rest of the bins are full or the TreeMap is empty
            if (ceillingNodeKey.compareTo(parcelWeight) < 0) {
                System.out.println("The Tree is Empty or All of the bins is fulled. Creating a new Bins. ");
                createNewBin(newParcel);
                return;
            }

            // Retrieve the key of the bin to insert the parcel
            RedBlackNode<T, V> suitableBinNode = search(ceillingNodeKey);
            if (suitableBinNode.key.compareTo(parcelWeight) < 0) {
                System.out.println("The suitable Bin Node Less Than Parcel Weight. Invalid");
                return;
            }


            // The PQ in the suitableBinNode is empty
            // Create a new bin and insert the parcel
            if (suitableBinNode.value.isEmpty()) {
                System.out.println("No Bins Found with the Remaining Capacity. Removing the node and Creating a New Node");
                this.remove(suitableBinNode);
                createNewBin(newParcel);
                return;
            }
            // The PQ is not empty
            // Dequeue the first bin and insert the parcel into it
            Bin<T> binToInsert = suitableBinNode.value.poll();


            //Remove the Node from tree if zero bins in the node
            if (suitableBinNode.value.isEmpty()) {
                remove(suitableBinNode);
            }

            //add parcel into bin
            if (binToInsert != null && binToInsert.addParcel(newParcel)) {
                T newRemaining = binToInsert.getRemainingCapacity();

                if (newRemaining.doubleValue() >= 0) {

                    // Check for whether the remaining capacity exists in the tree
                    RedBlackNode<T, V> sameRemainingCapacity = search(binToInsert.getRemainingCapacity());

                    // The remaining capacity key does not exist
                    // Create a new node and insert
                    if (sameRemainingCapacity == null) {
                        Queue<Bin<T>> binsPQ = new PriorityQueue<>(new BinIdComparator<>());
                        binsPQ.add(binToInsert);
                        sameRemainingCapacity = new RedBlackNode<T, V>(binToInsert.getRemainingCapacity(), (V) binsPQ);
                        this.insert(sameRemainingCapacity);
                        System.out.println("No Remaining capacity matched the new Remaining capacity");
                        return;
                    } else {
                        // The remaining capacity key exist, add the current bin into it
                        sameRemainingCapacity.value.add(binToInsert);
                    }

                } else {
                    System.out.println("The Bin is Full ! The Parcel Have Added Successfully");
                }
            }
        } catch (Exception e) {
            System.out.println("Error while adding parcel. " + newParcel.toString());
        }
    }

    /**
     * Create a new bin to insert a given parcel
     *
     * @param newParcel Parcel to be inserted
     */
    private void createNewBin(Parcel<T> newParcel) {
        Bin<T> bin = new Bin<>(++binCount, binCapacity);
        if (bin.addParcel(newParcel)) {
            Queue<Bin<T>> newQueue = new PriorityQueue<>(new BinIdComparator<>());
            newQueue.offer(bin);
            RedBlackNode<T, V> node = new RedBlackNode<T, V>(bin.getRemainingCapacity(), (V) newQueue);
            this.insert(node);
            setAllBins(bin);
            System.out.println("New Bin Added Successfully ! The Parcel Have been added Successfully");
        } else {
            System.out.println("New Bin created Failed ! ");
        }
    }

    /**
     * Getter and setter for the bins array
     */
    public MyLinkedList<Bin<T>> getAllBins() {
        return allBins;
    }

    private void setAllBins(Bin<T> bin) {
        this.allBins.add(bin);
    }

    private int getAllBinSize() {
        return allBins.size();
    }


    /**
     * @param x The node which the lefRotate is to be performed on.
     *          Performs a leftRotate around x.
     */
    private void leftRotate(RedBlackNode<T, V> x) {

        // Call leftRotateFixup() which updates the numLeft
        // and numRight values.
        leftRotateFixup(x);

        // Perform the left rotate as described in the algorithm
        // in the course text.
        RedBlackNode<T, V> y;
        y = x.right;
        x.right = y.left;

        // Check for existence of y.left and make pointer changes
        if (!isNil(y.left))
            y.left.parent = x;
        y.parent = x.parent;

        // x's parent is nul
        if (isNil(x.parent))
            root = y;

            // x is the left child of it's parent
        else if (x.parent.left == x)
            x.parent.left = y;

            // x is the right child of it's parent.
        else
            x.parent.right = y;

        // Finish of the leftRotate
        y.left = x;
        x.parent = y;
    }


    /**
     * @param x The node which the leftRotate is to be performed on.
     *          Updates the numLeft & numRight values affected by leftRotate.
     */
    private void leftRotateFixup(RedBlackNode<T, V> x) {

        // Case 1: Only x, x.right and x.right.right always are not nil.
        if (isNil(x.left) && isNil(x.right.left)) {
            x.numLeft = 0;
            x.numRight = 0;
            x.right.numLeft = 1;
        }

        // Case 2: x.right.left also exists in addition to Case 1
        else if (isNil(x.left) && !isNil(x.right.left)) {
            x.numLeft = 0;
            x.numRight = 1 + x.right.left.numLeft +
                    x.right.left.numRight;
            x.right.numLeft = 2 + x.right.left.numLeft +
                    x.right.left.numRight;
        }

        // Case 3: x.left also exists in addition to Case 1
        else if (!isNil(x.left) && isNil(x.right.left)) {
            x.numRight = 0;
            x.right.numLeft = 2 + x.left.numLeft + x.left.numRight;

        }
        // Case 4: x.left and x.right.left both exist in addtion to Case 1
        else {
            x.numRight = 1 + x.right.left.numLeft +
                    x.right.left.numRight;
            x.right.numLeft = 3 + x.left.numLeft + x.left.numRight +
                    x.right.left.numLeft + x.right.left.numRight;
        }

    }


    /**
     * @param y The node which the rightRotate is to be performed on.
     *          Updates the numLeft and numRight values affected by the Rotate.
     */
    private void rightRotate(RedBlackNode<T, V> y) {

        // Call rightRotateFixup to adjust numRight and numLeft values
        rightRotateFixup(y);

        // Perform the rotate as described in the course text.
        RedBlackNode<T, V> x = y.left;
        y.left = x.right;

        // Check for existence of x.right
        if (!isNil(x.right))
            x.right.parent = y;
        x.parent = y.parent;

        // y.parent is nil
        if (isNil(y.parent))
            root = x;

            // y is a right child of it's parent.
        else if (y.parent.right == y)
            y.parent.right = x;

            // y is a left child of it's parent.
        else
            y.parent.left = x;
        x.right = y;

        y.parent = x;

    }// end rightRotate(RedBlackNode y)


    // @param: y, the node around which the righRotate is to be performed.
    // Updates the numLeft and numRight values affected by the rotate
    private void rightRotateFixup(RedBlackNode<T, V> y) {

        // Case 1: Only y, y.left and y.left.left exists.
        if (isNil(y.right) && isNil(y.left.right)) {
            y.numRight = 0;
            y.numLeft = 0;
            y.left.numRight = 1;
        }

        // Case 2: y.left.right also exists in addition to Case 1
        else if (isNil(y.right) && !isNil(y.left.right)) {
            y.numRight = 0;
            y.numLeft = 1 + y.left.right.numRight +
                    y.left.right.numLeft;
            y.left.numRight = 2 + y.left.right.numRight +
                    y.left.right.numLeft;
        }

        // Case 3: y.right also exists in addition to Case 1
        else if (!isNil(y.right) && isNil(y.left.right)) {
            y.numLeft = 0;
            y.left.numRight = 2 + y.right.numRight + y.right.numLeft;

        }

        // Case 4: y.right & y.left.right exist in addition to Case 1
        else {
            y.numLeft = 1 + y.left.right.numRight +
                    y.left.right.numLeft;
            y.left.numRight = 3 + y.right.numRight +
                    y.right.numLeft +
                    y.left.right.numRight + y.left.right.numLeft;
        }

    }


    /**
     * @param z the node to be inserted into the Tree rooted at root
     *          Inserts z into the appropriate position in the RedBlackTree while
     *          updating numLeft and numRight values.
     */
    @Override
    public void insert(RedBlackNode<T, V> z) {

        // Create a reference to root & initialize a node to nil
        RedBlackNode<T, V> y = nil;
        RedBlackNode<T, V> x = root;

        // While we haven't reached a the end of the tree keep
        // tryint to figure out where z should go
        while (!isNil(x)) {
            y = x;

            // if z.key is < than the current key, go left
            if (z.key.compareTo(x.key) < 0) {

                // Update x.numLeft as z is < than x
                x.numLeft++;
                x = x.left;
            }

            // else z.key >= x.key so go right.
            else {

                // Update x.numGreater as z is => x
                x.numRight++;
                x = x.right;
            }
        }
        // y will hold z's parent
        z.parent = y;

        // Depending on the value of y.key, put z as the left or
        // right child of y
        if (isNil(y))
            root = z;
        else if (z.key.compareTo(y.key) < 0)
            y.left = z;
        else
            y.right = z;

        // Initialize z's children to nil and z's color to red
        z.left = nil;
        z.right = nil;
        z.color = RedBlackNode.RED;

        // Call insertFixup(z)
        insertFixup(z);

    }


    /**
     * @param z the node which was inserted and may have caused a violation
     *          of the RedBlackTree properties.
     *          Fixes up the violation of the RedBlackTree properties that may have
     *          been caused during insert(z)
     */
    private void insertFixup(RedBlackNode<T, V> z) {

        RedBlackNode<T, V> y = nil;
        // While there is a violation of the RedBlackTree properties..
        while (z.parent.color == RedBlackNode.RED) {

            // If z's parent is the left child of it's parent.
            if (z.parent == z.parent.parent.left) {

                // Initialize y to z 's cousin
                y = z.parent.parent.right;

                // Case 1: if y is red...recolor
                if (y.color == RedBlackNode.RED) {
                    z.parent.color = RedBlackNode.BLACK;
                    y.color = RedBlackNode.BLACK;
                    z.parent.parent.color = RedBlackNode.RED;
                    z = z.parent.parent;
                }
                // Case 2: if y is black & z is a right child
                else if (z == z.parent.right) {

                    // leftRotaet around z's parent
                    z = z.parent;
                    leftRotate(z);
                }

                // Case 3: else y is black & z is a left child
                else {
                    // recolor and rotate round z's grandpa
                    z.parent.color = RedBlackNode.BLACK;
                    z.parent.parent.color = RedBlackNode.RED;
                    rightRotate(z.parent.parent);
                }
            }

            // If z's parent is the right child of it's parent.
            else {

                // Initialize y to z's cousin
                y = z.parent.parent.left;

                // Case 1: if y is red...recolor
                if (y.color == RedBlackNode.RED) {
                    z.parent.color = RedBlackNode.BLACK;
                    y.color = RedBlackNode.BLACK;
                    z.parent.parent.color = RedBlackNode.RED;
                    z = z.parent.parent;
                }

                // Case 2: if y is black and z is a left child
                else if (z == z.parent.left) {
                    // rightRotate around z's parent
                    z = z.parent;
                    rightRotate(z);
                }
                // Case 3: if y  is black and z is a right child
                else {
                    // recolor and rotate around z's grandpa
                    z.parent.color = RedBlackNode.BLACK;
                    z.parent.parent.color = RedBlackNode.RED;
                    leftRotate(z.parent.parent);
                }
            }
        }
        // Color root black at all times
        root.color = RedBlackNode.BLACK;

    }

    /**
     * @param node A RedBlackNode
     * @return RedBlackNode with the smallest key
     */
    public RedBlackNode<T, V> treeMinimum(RedBlackNode<T, V> node) {

        // while there is a smaller key, keep going left
        while (!isNil(node.left))
            node = node.left;
        return node;
    }


    /**
     * @param x a RedBlackNode whose successor we must find
     * @return the node the with the next largest key
     * from x.key
     */
    public RedBlackNode<T, V> treeSuccessor(RedBlackNode<T, V> x) {

        // if x.left is not nil, call treeMinimum(x.right) and
        // return it's value
        if (!isNil(x.left))
            return treeMinimum(x.right);

        RedBlackNode<T, V> y = x.parent;

        // while x is it's parent's right child...
        while (!isNil(y) && x == y.right) {
            // Keep moving up in the tree
            x = y;
            y = y.parent;
        }
        // Return successor
        return y;
    }


    /**
     * Remove a given node from the tree
     *
     * @param v Node to be removed
     */
    @Override
    public void remove(RedBlackNode<T, V> v) {

        RedBlackNode<T, V> z = search(v.key);

        // Declare variables
        RedBlackNode<T, V> x = nil;
        RedBlackNode<T, V> y = nil;

        // if either one of z's children is nil, then we must remove z
        if (isNil(z.left) || isNil(z.right))
            y = z;

            // else we must remove the successor of z
        else y = treeSuccessor(z);

        // Let x be the left or right child of y (y can only have one child)
        if (!isNil(y.left))
            x = y.left;
        else
            x = y.right;

        // link x's parent to y's parent
        x.parent = y.parent;

        // If y's parent is nil, then x is the root
        if (isNil(y.parent))
            root = x;

            // else if y is a left child, set x to be y's left sibling
        else if (!isNil(y.parent.left) && y.parent.left == y)
            y.parent.left = x;

            // else if y is a right child, set x to be y's right sibling
        else if (!isNil(y.parent.right) && y.parent.right == y)
            y.parent.right = x;

        // if y != z, transfer y's satellite data into z.
        if (y != z) {
            z.key = y.key;
            z.value = y.value;
        }

        // Update the numLeft and numRight numbers which might need
        // updating due to the deletion of z.key.
        fixNodeData(x, y);

        // If y's color is black, it is a violation of the
        // RedBlackTree properties so call removeFixup()
        if (y.color == RedBlackNode.BLACK)
            removeFixup(x);
    }

    /**
     * Remove a given from the tree
     *
     * @param key Key to be removed
     */
    @Override
    public void remove(T key) {

        RedBlackNode<T, V> z = search(key);

        // Declare variables
        RedBlackNode<T, V> x = nil;
        RedBlackNode<T, V> y = nil;

        // if either one of z's children is nil, then we must remove z
        if (isNil(z.left) || isNil(z.right))
            y = z;

            // else we must remove the successor of z
        else y = treeSuccessor(z);

        // Let x be the left or right child of y (y can only have one child)
        if (!isNil(y.left))
            x = y.left;
        else
            x = y.right;

        // link x's parent to y's parent
        x.parent = y.parent;

        // If y's parent is nil, then x is the root
        if (isNil(y.parent))
            root = x;

            // else if y is a left child, set x to be y's left sibling
        else if (!isNil(y.parent.left) && y.parent.left == y)
            y.parent.left = x;

            // else if y is a right child, set x to be y's right sibling
        else if (!isNil(y.parent.right) && y.parent.right == y)
            y.parent.right = x;

        // if y != z, transfer y's satellite data into z.
        if (y != z) {
            z.key = y.key;
            z.value = y.value;
        }

        // Update the numLeft and numRight numbers which might need
        // updating due to the deletion of z.key.
        fixNodeData(x, y);

        // If y's color is black, it is a violation of the
        // RedBlackTree properties so call removeFixup()
        if (y.color == RedBlackNode.BLACK)
            removeFixup(x);
    }


    private void fixNodeData(RedBlackNode<T, V> x, RedBlackNode<T, V> y) {

        // Initialize two variables which will help us traverse the tree
        RedBlackNode<T, V> current = nil;
        RedBlackNode<T, V> track = nil;


        // if x is nil, then we will start updating at y.parent
        // Set track to y, y.parent's child
        if (isNil(x)) {
            current = y.parent;
            track = y;
        }

        // if x is not nil, then we start updating at x.parent
        // Set track to x, x.parent's child
        else {
            current = x.parent;
            track = x;
        }

        // while we haven't reached the root
        while (!isNil(current)) {
            // if the node we deleted has a different key than
            // the current node
            if (y.key != current.key) {

                // if the node we deleted is greater than
                // current.key then decrement current.numRight
                if (y.key.compareTo(current.key) > 0)
                    current.numRight--;

                // if the node we deleted is less than
                // current.key then decrement current.numLeft
                if (y.key.compareTo(current.key) < 0)
                    current.numLeft--;
            }

            // if the node we deleted has the same key as the
            // current node we are checking
            else {
                // the cases where the current node has any nil
                // children and update appropriately
                if (isNil(current.left))
                    current.numLeft--;
                else if (isNil(current.right))
                    current.numRight--;

                    // the cases where current has two children and
                    // we must determine whether track is it's left
                    // or right child and update appropriately
                else if (track == current.right)
                    current.numRight--;
                else if (track == current.left)
                    current.numLeft--;
            }

            // update track and current
            track = current;
            current = current.parent;

        }

    }


    /**
     * @param x the child of the deleted node from remove(RedBlackNode v)
     *          Restores the Red Black properties that may have been violated during
     *          the removal of a node in remove(RedBlackNode v)
     */
    private void removeFixup(RedBlackNode<T, V> x) {

        RedBlackNode<T, V> w;

        // While we haven't fixed the tree completely...
        while (x != root && x.color == RedBlackNode.BLACK) {

            // if x is it's parent's left child
            if (x == x.parent.left) {

                // set w = x's sibling
                w = x.parent.right;

                // Case 1, w's color is red.
                if (w.color == RedBlackNode.RED) {
                    w.color = RedBlackNode.BLACK;
                    x.parent.color = RedBlackNode.RED;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }

                // Case 2, both of w's children are black
                if (w.left.color == RedBlackNode.BLACK &&
                        w.right.color == RedBlackNode.BLACK) {
                    w.color = RedBlackNode.RED;
                    x = x.parent;
                }
                // Case 3 / Case 4
                else {
                    // Case 3, w's right child is black
                    if (w.right.color == RedBlackNode.BLACK) {
                        w.left.color = RedBlackNode.BLACK;
                        w.color = RedBlackNode.RED;
                        rightRotate(w);
                        w = x.parent.right;
                    }
                    // Case 4, w = black, w.right = red
                    w.color = x.parent.color;
                    x.parent.color = RedBlackNode.BLACK;
                    w.right.color = RedBlackNode.BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            }
            // if x is it's parent's right child
            else {

                // set w to x's sibling
                w = x.parent.left;

                // Case 1, w's color is red
                if (w.color == RedBlackNode.RED) {
                    w.color = RedBlackNode.BLACK;
                    x.parent.color = RedBlackNode.RED;
                    rightRotate(x.parent);
                    w = x.parent.left;
                }

                // Case 2, both of w's children are black
                if (w.right.color == RedBlackNode.BLACK &&
                        w.left.color == RedBlackNode.BLACK) {
                    w.color = RedBlackNode.RED;
                    x = x.parent;
                }

                // Case 3 / Case 4
                else {
                    // Case 3, w's left child is black
                    if (w.left.color == RedBlackNode.BLACK) {
                        w.right.color = RedBlackNode.BLACK;
                        w.color = RedBlackNode.RED;
                        leftRotate(w);
                        w = x.parent.left;
                    }

                    // Case 4, w = black, and w.left = red
                    w.color = x.parent.color;
                    x.parent.color = RedBlackNode.BLACK;
                    w.left.color = RedBlackNode.BLACK;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }// end while

        // set x to black to ensure there is no violation of
        // RedBlack tree Properties
        x.color = RedBlackNode.BLACK;
    }


    /**
     * @param key the key whose node we want to search for
     * @return a node with the key, key, if not found, returns null
     * Searches for a node with key k and returns the first such node, if no such node is found returns null
     */
    @Override
    public RedBlackNode<T, V> search(T key) {

        // Initialize a pointer to the root to traverse the tree
        RedBlackNode<T, V> current = root;

        // While we haven't reached the end of the tree
        while (!isNil(current)) {

            // If we have found a node with a key equal to key
            if (current.key.equals(key))

                // return that node and exit search(int)
                return current;

                // go left or right based on value of current and key
            else if (current.key.compareTo(key) < 0)
                current = current.right;

                // go left or right based on value of current and key
            else
                current = current.left;
        }

        // we have not found a node whose key is "key"
        return null;


    }

    /**
     * @param key any Comparable object
     * @return the number of elements greater than key
     */
    public int numGreater(T key) {

        // Call findNumGreater(root, key) which will return the number
        // of nodes whose key is greater than key
        return findNumGreater(root, key);

    }


    /**
     * @param key Any Comparable object
     * @return The number of elements smaller than key
     */
    public int numSmaller(T key) {

        // Call findNumSmaller(root,key) which will return
        // the number of nodes whose key is greater than key
        return findNumSmaller(root, key);

    }


    /**
     * @param node The root of the tree, the key who we must compare other node key's to.
     * @param key  Key to compare to
     * @return The number of nodes greater than key.
     */
    public int findNumGreater(RedBlackNode<T, V> node, T key) {

        // Base Case: if node is nil, return 0
        if (isNil(node))
            return 0;
            // If key is less than node.key, all elements right of node are
            // greater than key, add this to our total and look to the left
        else if (key.compareTo(node.key) < 0)
            return 1 + node.numRight + findNumGreater(node.left, key);

            // If key is greater than node.key, then look to the right as
            // all elements to the left of node are smaller than key
        else
            return findNumGreater(node.right, key);

    }

    /**
     * Returns sorted list of keys greater than key.  Size of list
     * will not exceed maxReturned
     *
     * @param key         Key to search for
     * @param maxReturned Maximum number of results to return
     * @return List of keys greater than key.  List may not exceed maxReturned
     */
    public List<T> getGreaterThan(T key, Integer maxReturned) {
        List<T> list = new ArrayList<T>();
        getGreaterThan(root, key, list);
        return list.subList(0, Math.min(maxReturned, list.size()));
    }


    /**
     * Get all the key that is greater than the given node
     *
     * @param node Node to compare
     * @param key  Key to compare
     * @param list List to store all the greater node's key
     */
    private void getGreaterThan(RedBlackNode<T, V> node, T key,
                                List<T> list) {
        if (isNil(node)) {
            return;
        } else if (node.key.compareTo(key) > 0) {
            getGreaterThan(node.left, key, list);
            list.add(node.key);
            getGreaterThan(node.right, key, list);
        } else {
            getGreaterThan(node.right, key, list);
        }
    }

    /**
     * @param node The root of the tree, the key who we must compare other node key's to.
     * @return The number of nodes smaller than key.
     */
    public int findNumSmaller(RedBlackNode<T, V> node, T key) {

        // Base Case: if node is nil, return 0
        if (isNil(node)) return 0;

            // If key is less than node.key, look to the left as all
            // elements on the right of node are greater than key
        else if (key.compareTo(node.key) <= 0)
            return findNumSmaller(node.left, key);

            // If key is larger than node.key, all elements to the left of
            // node are smaller than key, add this to our total and look
            // to the right.
        else
            return 1 + node.numLeft + findNumSmaller(node.right, key);

    }


    /**
     * @param node The RedBlackNode we must check to see whether it's nil
     * @return True of node is nil and false otherwise
     */
    @Override
    public boolean isNil(RedBlackNode<T, V> node) {

        // return appropriate value
        return node == nil;

    }


    /**
     * @return Return the size of the tree
     */
    @Override
    public int size() {
        // Return the number of nodes to the root's left + the number of
        // nodes on the root's right + the root itself.
        return root.numLeft + root.numRight + 1;
    }

    /**
     * Print out all the bin sorted by its remaining capacity, natural order
     */
    @Override
    public void inorderPrint() {
        RedBlackNode<T, V> temp = root;
        inorderTraversal(temp);
        System.out.println(allBins.toString());
    }

    /**
     * Traversal method
     *
     * @param root Root of the tree
     */
    @Override
    protected void inorderTraversal(RedBlackNode<T, V> root) {
        if (isNil(root)) {
            return;
        }

        this.inorderTraversal(root.left);
        System.out.println(root.value.toString());
        this.inorderTraversal(root.right);
    }

    /*
     * Print out all the bin in ascending order
     * */
    public void printAllBins() {
        System.out.println(allBins.toString());
    }


    /**
     * Return the information of the tree map in string format
     *
     * @return String that contains the tree map information
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        T sum = null;

        System.out.println("Best Fit Algorithm");

        // Maximum Capacity of Bin
        result.append("\nMaximum bin capacity : ").append(binCapacity).append("\n");
        // Total bins created
        result.append("Total bins created: ").append(getAllBinSize()).append("\n");

        // All bins details
        result.append("All bins details:\n");

        //Calculate Sum of Remaining Capacity
        for (Bin<T> bin : getAllBins()) {
            if (bin.getRemainingCapacity() instanceof Integer) {
                if (sum == null) {
                    sum = (T) Integer.valueOf(0);
                }
                Integer currentBin = (Integer) sum;
                currentBin += (Integer) bin.getRemainingCapacity();
                sum = (T) currentBin;
            } else if (bin.getRemainingCapacity() instanceof Double) {
                if (sum == null) {
                    sum = (T) Double.valueOf(0.0);
                }
                Double currentBin = (Double) sum;
                currentBin += (Double) bin.getRemainingCapacity();
                sum = (T) currentBin;

            }
            result.append("Bin ID: ").append(bin.getBinId())
                    .append(", Remaining Capacity: ").append(bin.getRemainingCapacity())
                    .append(", Parcel: ");
            for (Parcel<T> parcel : bin.getParcels()) {
                result.append(" ").append(parcel.getParcelId()).append(" ( Weight : ").append(parcel.getWeight()).append(" )"); // Parcel ID representation

            }

            //newline for Output
            result.append("\n");
        }
        result.append("\nRemaining capacity: " + (T) sum);

        result.append("\n");
        return result.toString();
    }
}