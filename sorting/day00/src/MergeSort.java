import java.util.Arrays;

public class MergeSort extends SortAlgorithm {

    private static final int INSERTION_THRESHOLD = 10;

    /**
     * This is the recursive step in which you split the array up into
     * a left and a right portion, sort them, and then merge them together.
     * Use Insertion Sort if the length of the array is <= INSERTION_THRESHOLD
     *
     * Best-case runtime: O(N*log(N))
     * Worst-case runtime: O(N*log(N))
     * Average-case runtime: O(N*log(N))
     *
     * Space-complexity: O(N), or O(log(N)) if sorting linked list in-place
     */
    @Override
    public int[] sort(int[] array) {
        // Use insertion sort if array is <= to INSERTION_THRESHOLD
        if (array.length <= INSERTION_THRESHOLD) {
            return new InsertionSort().sort(array);
        }

        // Sort each half
        int halfway = array.length / 2;
        int[] leftHalf = sort(Arrays.copyOfRange(array, 0, halfway));
        int[] rightHalf = sort(Arrays.copyOfRange(array, halfway, array.length));
        // Combine the sorted halves
        return merge(leftHalf, rightHalf);
    }

    /**
     * Given two sorted arrays a and b, return a new sorted array containing
     * all elements in a and b. A test for this method is provided in `SortTest.java`
     */
    public int[] merge(int[] a, int[] b) {
        int[] res = new int[a.length + b.length];
        int aPos = 0;
        int bPos = 0;
        int rPos = 0;
        while (aPos < a.length || bPos < b.length) {
            if (aPos >= a.length) { // Reached end of a
                res[rPos++] = b[bPos++]; // Copy next b element to res and increment poses
            } else if (bPos >= b.length) { // Reached end of b
                res[rPos++] = a[aPos++]; // Copy next a element to res and increment poses
            } else if (b[bPos] < a[aPos]) {
                res[rPos++] = b[bPos++]; // Copy next b element to res and increment poses
            } else {
                res[rPos++] = a[aPos++]; // Copy next a element to res and increment poses
            }
        }
        return res;
    }

}
