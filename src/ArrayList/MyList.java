package ArrayList;

public interface MyList<E> extends java.lang.Iterable<E> {

    /**
     * Add a new element at the end of this list
     *
     * @param e Element to be added
     */
    public void add(E e);

    /**
     * Add a new element at the specified index in this list
     *
     * @param index Index to be inserted
     * @param e     Element to be inserted
     */
    public void add(int index, E e);

    /**
     * Clear the list
     */
    public void clear();

    /**
     * @return True if this list contains the element
     */
    public boolean contains(E e);

    /**
     * Return the element from this list at the specified index
     *
     * @param index Index to retrieve the element
     * @return Element at the given index
     */
    public E get(int index);

    /**
     * Return the index of the first matching element in this list. Return -1 if
     * no match.
     *
     * @param e Element to be checked for its index
     * @return -1 if the element does not exist else the index of the given element
     */
    public int indexOf(E e);

    /**
     * @return True if this list contains no elements
     */
    public boolean isEmpty();

    /**
     * Return the index of the last matching element in this list Return -1 if
     * no match.
     *
     * @param e Element to be checked for its index
     * @return -1 if the element does not exist else the index of the element
     */
    public int lastIndexOf(E e);

    /**
     * Remove the first occurrence of the element o from this list.
     * Shift any subsequent elements to the left.
     *
     * @param e Element to be removed
     * @return True if the element is removed else false
     */
    public boolean remove(E e);

    /**
     * Remove the element at the specified position in this list.
     * Shift any subsequent elements to the left.
     *
     * @param index The index of the item to be removed
     * @return The item that is removed from the list
     */
    public E remove(int index);

    /**
     * Replace the item at the specified position in this list with the specified item
     *
     * @param e The item that is intended to replace the item at a given index
     * @return The item that has been replaced
     */
    public Object set(int index, E e);

    /**
     * @return Number of elements in this list
     */
    public int size();

    // as requested in the question

    /**
     * Adds the elements in otherList to this list
     *
     * @param otherList List of elements to be added to the current list
     * @return True if current list has been modified else false
     */
    public boolean addAll(MyList<E> otherList);

    /**
     * Removes all the elements in otherList from this list.
     *
     * @param otherList List of elements to be removed from the current list
     * @return True if current list has been modified else false
     */
    public boolean removeAll(MyList<E> otherList);

    /**
     * Retains the elements in this list that are also in otherList.
     *
     * @param otherList List of items to be compared with the current list
     * @return True if current list has been modified else false
     */
    public boolean retainAll(MyList<E> otherList);
}
