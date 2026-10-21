package ArrayList;

import java.util.Iterator;
import java.util.Set;

public class CustomArrayList<E> extends myAbstractList<E> {

    /**
     * Set Initial Capacity of the Array
     */
    private Integer INITIAL_CAPACITY = 16;

    /**
     * Initialize the Array List
     */
    private E[] data = (E[]) new Object[INITIAL_CAPACITY];

    /**
     * Default constructor to create an array based on the INITIAL_CAPACITY
     */
    public CustomArrayList() {
    }
    /**
     * Default constructor to create an array based on the size given
     *
     * @param defaultSize Array initial size
     */
    public CustomArrayList(Integer defaultSize) {
        this.INITIAL_CAPACITY = defaultSize;
    }

    /**
     * Create a list from an array of objects
     *
     * @param objects An array of items to be inserted upon instance creation
     */
    public CustomArrayList(E[] objects) {
        for (int i = 0; i < objects.length; i++) {
            add(objects[i]);
        }
    }

    /**
     * Add a new element at the specified index
     *
     * @param index The index position to be inserted
     * @param e     The item to be inserted
     */
    @Override
    public void add(int index, E e) {
        checkAddIndex(index);
        // The insert index exceeded the current array size
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
     * Create a new larger array, double the current size and adds 1
     */
    private void ensureCapacity() {
        if (size >= data.length) {
            E[] newData = (E[]) (new Object[size * 2 + 1]);
            System.arraycopy(data, 0, newData, 0, size);
            data = newData;
        }
    }


    /**
     * Clear the list
     */
    @Override
    public void clear() {
        data = (E[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Return true if this list contains the element
     *
     * @param e The item to check if the list contain it
     * @return True if it is in the list, else false
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
     * Return the element at the specified index
     *
     * @param index Index to insert at
     * @return The item that is at the index
     */
    @Override
    public E get(int index) {
        checkIndex(index);
        return data[index];
    }

    /*
     * Method to check if the given index is within list size
     * */
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    /*
     * Method to check if the given index is within list size
     * Allows index equals to size as it is same as appending at the end
     * */
    private void checkAddIndex(int index) {
        if (index < 0 || index > size) {
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
        StringBuilder result = new StringBuilder("[");

        for (int i = 0; i < size; i++) {
            result.append(data[i]);
            if (i < size - 1) {
                result.append(", ");
            }
        }

        return result.toString() + "]";
    }

    /**
     * Trims the capacity to current size
     */
    public void trimToSize() {
        if (size != data.length) {
            E[] newData = (E[]) (new Object[size]);
            System.arraycopy(data, 0, newData, 0, size);
            data = newData;
        } // If size == capacity, no need to trim
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
    private class ArrayListIterator implements java.util.Iterator<E> {

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
            CustomArrayList.this.remove(current);
        }
    }
}
