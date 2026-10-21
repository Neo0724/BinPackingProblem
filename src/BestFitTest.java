import ArrayList.CustomArrayList;
import RedBlackTree.MyRedBlackTree;
import pojo.Bin;
import pojo.Parcel;

import java.util.PriorityQueue;

public class BestFitTest<T extends Number & Comparable<T>, V extends PriorityQueue<Bin<T>>> {
    /**
     * Method to test the best fit bin packing
     *
     * @param maximumBinCapacity Maximum capacity that a bin can hold
     * @param parcelList         All the parcel to be inserted
     */
    public void BestFitPacking(T maximumBinCapacity, CustomArrayList<Parcel<T>> parcelList) {
        MyRedBlackTree<T, V> myRedBlackTree = new MyRedBlackTree<>(maximumBinCapacity);
        for (int i = 0; i < 100; i++) {
            myRedBlackTree.insertParcel(parcelList.get(i));
        }
        System.out.println(myRedBlackTree.toString());
    }
}
