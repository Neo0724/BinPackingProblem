package ArrayList;

import Algorithm.FirstFit;

import java.lang.reflect.Array;

import pojo.Bin;
import pojo.Parcel;

public class MyArrayList<E extends Bin<V>, V extends Number & Comparable<V>> extends myAbstractList<E> {

    //Bin Array
    private E[] data;

    //Bin Array Initial Length
    private int INITIAL_CAPACITY = 16;

    //Actual Bin count
    private int size = 0;

    // Maximum Capacity of Each Bin
    private V binCapacity;

    //Bin Id Count
    private Integer binCount = 0;


    /**
     * Constructor to initialize the array list to store bin
     * @param clazz Class type to initialize class type array
     * @param binCapacity   maximum capacity of each bin
     */
    public MyArrayList(Class<E> clazz, V binCapacity) {
        this.data = (E[]) Array.newInstance(clazz, INITIAL_CAPACITY); // Properly create generic array
        this.binCapacity = binCapacity;
    }

    /**
     * Insert the given parcel into a suitable bin
     *
     * @param parcel Parcel to be inserted into a bin
     */
    public void insertParcel(Parcel<V> parcel) {

        //Check the parcel exceed the maximum bin capacity
        if (parcel.getWeight().compareTo(binCapacity) > 0) {
            System.out.println("Parcel ID: " + parcel.getParcelId() + "  weight exceeded the Maximum Bin Capacity.");
        }

        try {
            //find first fit bin
            boolean isSuccess = FirstFit.insertIntoBin(parcel, this);

            // if fail finding first fit bin ,  create new bin
            if (!isSuccess) {
                createNewBin(parcel);
            }
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
        }
    }

    /**
     * Create a new bin and insert the given parcel into it
     *
     * @param parcel The parcel to be inserted upon creation of new bin
     */
    private void createNewBin(Parcel<V> parcel) {

        try {
            //Create a new instance of bin
            E newBin = (E) new Bin<>(++binCount, binCapacity);

            //add parcel into bin
            if (newBin.addParcel(parcel)) {

                //add bin into Bin Array
                this.add(newBin);
            } else {
                System.out.println(parcel.toString() + " exceeded the maximum bin Capactiy. Insert Failed. ");
            }
        } catch (Exception e) {
            System.out.println("Error ! " + e.getMessage());
        }
    }

    /**
     * Create a list from an array of objects
     *
     * @param objects Array of objects to be inserted upon instance creation
     */
    public MyArrayList(E[] objects) {
        for (int i = 0; i < objects.length; i++) {
            add(objects[i]);
        }
    }

    /**
     * Add a new element at the specified index
     *
     * @param index Index to insert the given item
     * @param e     Item to be inserted
     */
    @Override
    public void add(int index, E e) {
        ensureCapacity();

        // Move the elements to the right after the specified index
        for (int i = size - 1; i >= index; i--) {
            data[i + 1] = data[i];
        }

        // Insert new element to data[index]
        data[index] = e;

        // Increase size by 1
        size++;
    }

    /**
     * Add an item to the current list
     *
     * @param e Item to be inserted
     */
    @Override
    public void add(E e) {
        add(size, e);
    }

    /**
     * Check if the current list is empty
     *
     * @return True if current list is empty, else false
     */
    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    /**
     * @return The size of the current list
     */
    @Override
    public int size() {
        return super.size();
    }

    /**
     * Create a new larger array, double the current size + 1
     */
    private void ensureCapacity() {
        if (size >= data.length) {
            E[] newData = (E[]) Array.newInstance(data.getClass().getComponentType(), size * 2 + 1);
            System.arraycopy(data, 0, newData, 0, size);
            data = newData;
        }
    }

    /**
     * Clear the list
     */
    @Override
    public void clear() {
        data = (E[]) new Object[size];
        size = 0;
    }

    /**
     * Check if the given element is contained in the list
     *
     * @param e Element to be checked
     * @return True if the list contains the element
     */
    @Override
    public boolean contains(E e) {
        for (int i = 0; i < size; i++) {
            if (e.equals(data[i])) {
                return true;
            }
        }

        return false;
    }

    /**
     * Retrieve the item at a given index
     *
     * @param index Index of the item to retrieve
     * @return The element that is at the given index
     */
    @Override
    public E get(int index) {
        checkIndex(index);
        return data[index];
    }

    /**
     * Check if the given index is out of bound
     *
     * @param index The index to be checked
     */
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    /**
     * Return the index of the first matching element in this list
     *
     * @param e The item to retrieve its first index
     * @return -1 if the item is not in the list, else the item's index
     */
    @Override
    public int indexOf(E e) {
        for (int i = 0; i < size; i++) {
            if (e.equals(data[i])) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Return the index of the last matching element in this list
     *
     * @param e The item to retrieve its last index
     * @return -1 if the item is not in the list, else the item's last index
     */
    @Override
    public int lastIndexOf(E e) {
        for (int i = size - 1; i >= 0; i--) {
            if (e.equals(data[i])) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Remove the element at the specified position in this list.
     * Shift any subsequent elements to the left.
     *
     * @param index The index of the item to be removed
     * @return The item that is removed from the list
     */
    @Override
    public E remove(int index) {
        checkIndex(index);

        E e = data[index];

        // Shift data to the left
        for (int j = index; j < size - 1; j++) {
            data[j] = data[j + 1];
        }

        data[size - 1] = null; // This element is now null

        // Decrement size
        size--;

        return e;
    }

    /**
     * Replace the item at the specified position in this list with the specified item
     *
     * @param e The item that is intended to replace the item at a given index
     * @return The item that has been replaced
     */
    @Override
    public E set(int index, E e) {
        checkIndex(index);
        E old = data[index];
        data[index] = e;
        return old;
    }

    /**
     * Return a formatted string that contains all the item in the list
     *
     * @return Formatted string that contains all the item in the list
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        //Sum of Remaining Capacity
        V sum = null;
        
        System.out.println("First Fit Algorithm");
        // Maximum Capacity of Bin
        result.append("Maximum bin capacity: ").append(binCapacity).append("\n");

        // Total bins created
        result.append("Total bins created: ").append(size).append("\n");

        // All bins details
        result.append("All bins details:\n");
        for (int i = 0; i < size; i++) {
            if (data[i].getRemainingCapacity() instanceof Integer) {
                if (sum == null) {
                    sum = (V) Integer.valueOf(0);
                }
                Integer currentBin = (Integer) sum;
                currentBin += (Integer) data[i].getRemainingCapacity();
                sum = (V) currentBin;
            } else if (data[i].getRemainingCapacity() instanceof Double) {
                if (sum == null) {
                    sum = (V) Double.valueOf(0.0);
                }
                Double currentBin = (Double) sum;
                currentBin += (Double) data[i].getRemainingCapacity();
                sum = (V) currentBin;
            }
            E bin = data[i];
            result.append("Bin ID : ").append(bin.getBinId())
                    .append(", Remaining Capacity : ").append(bin.getRemainingCapacity())
                    .append(", Parcel : ");

            // Extract parcels
            for (Parcel<V> parcel : bin.getParcels()) {
                result.append(" ").append(parcel.getParcelId()).append(" ( Weight : ").append(parcel.getWeight()).append(" )");
            }


            //newline for Output
            result.append("\n");
        }
        result.append("\nRemaining capacity: " + (V) sum);

        result.append("\n");
        return result.toString();
    }

    /**
     * Override iterator() defined in Iterable
     *
     * @return A custom array list iterator
     */
    @Override
    public java.util.Iterator<E> iterator() {
        return new ArrayListIterator();
    }

    /*
     * An ArrayListIterator class
     * */
    private class ArrayListIterator
            implements java.util.Iterator<E> {

        // Store the current list index, initialized at 0, which is beginning of the list
        private int current = 0; // Current index

        /**
         * @return True there are remaining items in the list, else false
         */
        @Override
        public boolean hasNext() {
            return (current < size);
        }

        /**
         * @return The next item in the list
         */
        @Override
        public E next() {
            return data[current++];
        }

        /**
         * Remove the current items that is being pointed by the iterator
         */
        @Override
        public void remove() {
            MyArrayList.this.remove(current);
        }
    }


}
