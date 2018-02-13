
public class InsertionSort extends SortAlgorithm {
    /**
     * Use the insertion sort algorithm to sort the array
     *
     * Best-case runtime: O(N) - already sorted
     * Worst-case runtime: O(N^2) - array sorted in reverse order
     * Average-case runtime: O(N^2)
     *
     * Space-complexity: O(1) - in-place sorting
     */
    @Override
    public int[] sort(int[] array) {
        // For each 1:end element, while less than prev, swap with prev
        for (int i = 1; i < array.length; ++i) {
            int elem = array[i]; // Get the value we're moving
            int comparePos = i - 1; // Start at the next position
            while (comparePos >= 0 && array[comparePos] > elem) {
                // "Swap" the values so the bigger one is on the right
                array[comparePos+1] = array[comparePos];
                array[comparePos] = elem; // Only really necessary to do on the last run through here
                // Look at the previous item
                --comparePos;
            }
        }
        return array;
    }
}
