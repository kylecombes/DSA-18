public class MyArrayList {
    private Cow[] elems;
    private int size = 0;

    // TODO: Runtime: O(1)
    public MyArrayList() {
        elems = new Cow[10];
    }

    // TODO: Runtime: O(1)
    public MyArrayList(int capacity) {
        elems = new Cow[capacity];
    }

    // TODO: Runtime: O(1)*
    public void add(Cow c) {
        if (size == elems.length) { // Array capacity reached, need to grow
            Cow[] newArray = new Cow[2*elems.length];
            System.arraycopy(elems, 0, newArray, 0, elems.length);
            elems = newArray;
        }
        elems[size++] = c;
    }

    // TODO: Runtime: O(1)
    public int size() {
        return size;
    }

    // TODO: Runtime: O(1)
    public Cow get(int index) {
        return elems[index];
    }

    // TODO: Runtime: O(N)
    public Cow remove(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        // Save the removed cow
        Cow removed = elems[index];
        --size;
        if (size > 0 && size == elems.length / 4) { // Check if we need to decrease the size of the array
            Cow[] newArray = new Cow[elems.length / 2];
            // Copy the first section over (before the removd element)
            System.arraycopy(elems, 0, newArray, 0, index);
            System.arraycopy(elems, index+1, newArray, index, size - index);
            elems = newArray;
        } else { // We don't need to decrease the size of the array
            // Shift all the other cows over
            System.arraycopy(elems, index + 1, elems, index, size - index);
            // Decrement the size counter and clear the last cow
            elems[size] = null;
        }

        return removed;
    }

    // TODO: Runtime: O(N)
    public void add(int index, Cow c) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();
        if (size == elems.length) { // Need to expand the array
            Cow[] newArray = new Cow[elems.length*2];
            System.arraycopy(elems, 0, newArray, 0, index);
            newArray[index] = c;
            System.arraycopy(elems, index, newArray, index+1, size - index);
            elems = newArray;
        } else {
            System.arraycopy(elems, index, elems, index + 1, size - index);
            elems[index] = c;
        }
        ++size;
    }
}
