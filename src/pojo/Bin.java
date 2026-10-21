package pojo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import ArrayList.CustomArrayList;
import ArrayList.MyLinkedList;

public class Bin<K extends Number> {

    // Bin Id
    private Integer binId;

    // Bin remaining capacity
    private K remainingCapacity;

    // List of parcel inside bin
    private MyLinkedList<Parcel<K>> parcelStore;

    /**
     * Constructor to create a bin
     *
     * @param binId    Unique identification of a bin
     * @param capacity The bin capacity
     **/
    public Bin(Integer binId, K capacity) {
        this.binId = binId;
        this.remainingCapacity = capacity;
        this.parcelStore = new MyLinkedList<Parcel<K>>();
    }

    /**
     * Add a given parcel to a bin
     *
     * @param parcel Parcel to be added
     * @return True if the parcel has been added to a bin else false
     */
    public boolean addParcel(Parcel<K> parcel) {
        //Check if parcel weight is null or negative
        if (parcel.getWeight() == null || parcel.getWeight().doubleValue() < 0) {
            return false;
        }

        // If remainingCapacity belongs to Integer, perform normal operation
        // Else, perform BigDecimal operation
        if (remainingCapacity instanceof Integer) {
            if (remainingCapacity.intValue() >= parcel.getWeight().intValue()) {
                int updatedCapacity = remainingCapacity.intValue() - parcel.getWeight().intValue();

                //update the new remaining capacity
                this.remainingCapacity = (K) (Integer) updatedCapacity;
                parcelStore.add(parcel);
                return true;
            }
        } else {
            if (remainingCapacity.doubleValue() >= parcel.getWeight().doubleValue()) {
                BigDecimal updatedCapacity = BigDecimal.valueOf(remainingCapacity.doubleValue())
                        .subtract(BigDecimal.valueOf(parcel.getWeight().doubleValue()));
                this.remainingCapacity = (K) (Double) updatedCapacity.doubleValue();
                parcelStore.add(parcel);
                return true;
            }
        }

        // Current bin does not fit the given parcel
        return false;
    }

    //Getter and Setter
    public Integer getBinId() {
        return binId;
    }

    public K getRemainingCapacity() {
        return remainingCapacity;
    }

    //Linked List to Store the Parcel that inserted to the bin
    public MyLinkedList<Parcel<K>> getParcels() {
        return parcelStore;
    }

    /**
     * Build a string that contains the bin information
     *
     * @return Formated string that contains the bin's information
     */
    @Override
    public String toString() {
        return "Bin{"
                + "binId=" + binId
                + ", remainingCapacity=" + remainingCapacity
                + ", parcels=" + parcelStore
                + '}';
    }
}
