public class LongestIncreasingSubsequence {

    // Runtime: O(N^2)
    // Space: O(N)
    public static int LIS(int[] A) {
        if (A.length == 0)
            return 0;

        // Initialize an array indicating the length of the LIS for each element in the original array
        int[] lis = new int[A.length];
        for (short i = 0; i < lis.length; ++i)
            lis[i] = 1;

        // Compute the LIS for each element in the array
        for (short i = 1; i < A.length; ++i) {
            // For each preceding element
            for (short j = 0; j < i; ++j) {
                // Check if the element is less than element i and part of a longer subsequence than currently recorded
                if (A[i] > A[j] && lis[i] < lis[j] + 1)
                    lis[i] = lis[j] + 1;
            }
        }

        // Find the global LIS
        int maximum = 1;
        for (int elemLIS : lis) {
            if (maximum < elemLIS)
                maximum = elemLIS;
        }

        return maximum;
    }
}