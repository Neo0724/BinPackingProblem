package ArrayList;

public abstract class myAbstractList<E> implements MyList<E> {

    protected int size = 0; // The size of the list

    /**
     * Constructor to create a default list
     */
    protected myAbstractList() {
    }

    /**
     * Constructor to create a list from an array of objects
     *
     * @param objects The items to be created upon instance creation
     */
    protected myAbstractList(E[] objects) {
        for (int i = 0; i < objects.length; i++) {
            add(objects[i]);
        }
    }

    /**
     * Add a new element at the end of this list
     *
     * @param e Item to be added to the list
     */
    @Override
    public void add(E e) {
        add(size, e);
    }

    /**
     * @return Return true if this list contains no elements
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @return Return the number of elements in this list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Remove the first occurrence of the element e from this list. Shift any
     * subsequent elements to the left.
     *
     * @param e The item to be removed from the list
     * @return True if the item is removed, else false
     */
    @Override
    public boolean remove(E e) {
        if (indexOf(e) >= 0) {
            remove(indexOf(e));
            return true;
        } else {
            return false;
        }
    }

    /**
     * Adds the elements in otherList to this list
     *
     * @param otherList List of items to be added to the current list
     * @return True if current list has been modified else false
     */
    @Override
    public boolean addAll(MyList<E> otherList) {
        for (int i = 0; i < otherList.size(); i++) {
            add(otherList.get(i));
        }

        if (otherList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes all the elements in otherList from this list.
     *
     * @param otherList List of items to be removed from the current list
     * @return True if current list has been modified else false
     */
    @Override
    public boolean removeAll(MyList<E> otherList) {
        boolean changed = false;

        for (int i = 0; i < otherList.size(); i++) {
            if (remove(otherList.get(i))) {
                changed = true;
            }
        }
        return changed;
    }

    /**
     * Retains the elements in this list that are also in otherList.
     *
     * @param otherList List of items to be compared with the current list
     * @return True if current list has been modified else false
     */
    @Override
    public boolean retainAll(MyList<E> otherList) {
        boolean changed = false;
        for (int i = 0; i < this.size(); ) {
            if (!otherList.contains(this.get(i))) {
                this.remove(get(i));
                changed = true;
            } else {
                i++;
            }
        }
        return changed;
    }

}
