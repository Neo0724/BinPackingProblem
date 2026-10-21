import ArrayList.CustomArrayList;
import ArrayList.MyArrayList;
import pojo.Bin;
import pojo.Parcel;

public class FirstFitTest<T extends Number & Comparable<T>, V extends Bin<T>> {
    /**
     * Method to test the first fit bin packing
     *
     * @param maximumBinCapacity Maximum capacity that a bin can hold
     * @param parcelList         All the parcel to be inserted
     */
    public void FirstFitPacking(T maximumBinCapacity, CustomArrayList<Parcel<T>> parcelList) {
        MyArrayList<Bin<T>, T> myArrayList = new MyArrayList<>(Bin.class, maximumBinCapacity);
        for (int i = 0; i < 100; i++) {
            myArrayList.insertParcel(parcelList.get(i));
        }

        System.out.println(myArrayList.toString());
    }
}
