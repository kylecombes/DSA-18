public class CountingSort {

    /**
     * Use counting sort to sort non-negative integer array A.
     * Runtime: O(N+k)
     * Space: O(k)
     *
     * k: maximum element in array A
     */
    static void countingSort(int[] A) {
        if (A.length == 0) return;

        // Find the max element (probably a bit faster this way than converting to a Collection and using Collections.max())
        int k = A[0];
        for (int i = 1; i < A.length; ++i) {
            if (A[i] > k) k = A[i];
        }

        // Create an array of length k for keeping track of the count of each element
        int[] buckets = new int[k+1];

        // Count the occurrences of each element
        for (int num : A) {
            ++buckets[num];
        }

        // Build up the resulting (sorted) array
        int resIdx = 0;
        // For each number potentially in the array (i.e. a bucket)
        for (int bucketsIdx = 0; bucketsIdx <= k; ++bucketsIdx) {
            // For each occurrence of that number
            for (int occurrenceIdx = 0; occurrenceIdx < buckets[bucketsIdx]; ++occurrenceIdx) {
                // Save that number to the resulting array (A)
                A[resIdx++] = bucketsIdx;
            }
        }

    }

}
