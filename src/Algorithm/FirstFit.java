package Algorithm;

import ArrayList.MyArrayList;
import pojo.Bin;
import pojo.Parcel;

import java.util.Iterator;

public class FirstFit {

    //Private Constructor for Utility Class
    private FirstFit() {
    }

    /**
     * A reusable method for first-fit bin-packing
     *
     * @param parcel      The parcel to be inserted into the bin
     * @param myArrayList The array list that stores all the available bins
     * @return True if the there is a bin that can fit the parcel, else false
     */
    public static <E extends Bin<V>, V extends Number & Comparable<V>> boolean insertIntoBin(
            Parcel<V> parcel,
            MyArrayList<E, V> myArrayList) {

        // Iterate the bin list , find the bin that have remaining capacity more than the parcel weight
        // Return false if no any suitable and create a new bin
        for (Bin<V> bin : myArrayList) {
            if (bin.addParcel(parcel)) {
                return true;
            }
        }
        return false;
    }
}