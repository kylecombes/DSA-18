public class Problems {

    public static int leastSum(int[] A) {
        // Do counting sort
        sort(A, 10);
        // Initialize stuff
        int i, sum = 0;
        // See if we have an odd or even number of numbers
        if (A.length % 2 == 0) { // Nothing special if we have an even number of numbers
            i = 0;
        } else { // If we have an odd number of numbers, then pull out the first number as the highest digit
            i = 1;
            sum = A[0];
        }
        // Pair up and sum up - whee!
        for (; i < A.length - 1; i+=2) {
            sum = sum*10 + A[i] + A[i+1];
        }
        return sum;
    }

    /**
     * Sort using counting sort.
     * @param A the array to sort
     * @param m the max number in the array
     */
    private static void sort(int[] A, int m) {
        // Init array to keep track of number counts
        int[] temp = new int[m];
        // Count up occurrences of each number
        for (int n : A)
            temp[n] += 1;
        // Build up the sorted array
        int resIdx = 0;
        for (int i = 0; i < m; ++i)
            for (short j = 0; j < temp[i]; ++j)
                A[resIdx++] = i;
    }
}
