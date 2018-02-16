public class HeapSort extends SortAlgorithm {
    int size;
    int[] heap;

    private int parent(int i) {
        return (i-1) / 2;
    }

    private int leftChild(int i) {
        return 2*i + 1;
    }

    private int rightChild(int i) {
        return 2 * (i + 1);
    }

    // Check children, and swap with larger child if necessary.
    // Corrects the position of element indexed i by sinking it.
    // Use either recursion or a loop to then sink the child
    public void sink(int i) {
        sink(i, size-1);
    }

    /**
     * Checks the children and swaps with the larger element if necessary. Continues until the element is no longer
     * larger than any of its children.
     * @param i the index to sink
     * @param max the furthest distance to look in the array (used to hide the end of the heap during sorting)
     */
    public void sink(int i, int max) {
        int lc = leftChild(i);
        int rc = rightChild(i);
        // While the element has at least one child and is less than at least one of its children
        while (lc <= max && (heap[i] < heap[lc] || (rc <= max &&heap[i] < heap[rc]))) {
            // Determine which child it's greater than
            if (rc > max || heap[lc] > heap[rc]) { // Right child doesn't exist or left is greater
                swap(heap, lc, i);
                i = lc;
            } else { // Right child is greater or both are equal
                if (rc <= max) swap(heap, rc, i);
                i = rc;
            }
            // Update the child indices
            lc = leftChild(i);
            rc = rightChild(i);
        }
    }

    // Given the array, build a heap by correcting every non-leaf's position, starting from the bottom, then
    // progressing upward
    public void heapify(int[] array) {
        this.heap = array;
        this.size = array.length;

        for (int i=this.size / 2 - 1; i>=0; i--) {
            sink(i);
        }
    }

    /**
     * Best-case runtime: O(N*log(N))
     * Worst-case runtime: O(N*log(N))
     * Average-case runtime: O(N*log(N))
     *
     * Space-complexity: O(1) additional
     */
    @Override
    public int[] sort(int[] array) {
        heapify(array);
        for (int end=size-1; end > 0;) {
            // Move the largest element to the end of the array
            swap(heap, 0, end);
            // Repair the heap
            sink(0, --end);
        }

        return heap;
    }
}
