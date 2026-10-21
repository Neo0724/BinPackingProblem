package Tree;

import java.util.Comparator;

import pojo.Bin;

public class BinIdComparator<T extends Number> implements Comparator<Bin<T>> {

    /**
     * Comparator Method to set bins in Priority Queue in Node in ascending Order.
     */
    @Override
    public int compare(Bin o1, Bin o2) {
        return Double.compare(o1.getBinId(), o2.getBinId());
    }
}
