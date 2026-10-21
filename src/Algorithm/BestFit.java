package Algorithm;

import RedBlackTree.RedBlackNode;
import RedBlackTree.MyRedBlackTree;
import pojo.Bin;

import java.util.Queue;

public class BestFit {
    //Private Constructor for Utility Class
    private BestFit () {
    }

    /**
     * A reusable method for best-fit bin-packing
     *
     * @param tree          The Red-Black Tree to store Red Black Node
     * @param node          The Red-Black Node to store remaining capacity and bin
     * @param parcelWeight  The new parcel weight
     * @return              return remaining capacity
     */
    public static <T extends Number & Comparable<T>, V extends Queue<Bin<T>>> T findBestFit(MyRedBlackTree<T, V> tree, RedBlackNode<T, V> node, T parcelWeight) {
        //Check current node is nil
        if (tree.isNil(node)) {
            if (parcelWeight instanceof Integer) {
                return (T) (Number) Integer.valueOf(0);
            } else {
                return (T) (Number) Double.valueOf(0.0);
            }
        }

        // Current capacity is smaller than required capacity
        if(parcelWeight.compareTo(node.key) > 0) {
            return findBestFit(tree, node.right, parcelWeight);
        }

        T leftResult = findBestFit(tree, node.left, parcelWeight);
        if (leftResult.compareTo(node.key) <= 0) {
            return (T) node.key;
        } else {
            return (leftResult.compareTo((T) node.key) < 0) ? leftResult : (T) node.key;
        }
    }
}