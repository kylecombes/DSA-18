import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Problems {

    private static PriorityQueue<Integer> minPQ() {
        return new PriorityQueue<>(11);
    }

    private static PriorityQueue<Integer> maxPQ() {
        return new PriorityQueue<>(11, Collections.reverseOrder());
    }

    private static double getMedian(List<Integer> A) {
        double median = (double) A.get(A.size() / 2);
        if (A.size() % 2 == 0)
            median = (median + A.get(A.size() / 2 - 1)) / 2.0;
        return median;
    }

    // Runtime of this algorithm is O(N^2). Sad! We provide it here for testing purposes
    public static double[] runningMedianReallySlow(int[] A) {
        double[] out = new double[A.length];
        List<Integer> seen = new ArrayList<>();
        for (int i = 0; i < A.length; i++) {
            int j = 0;
            while (j < seen.size() && seen.get(j) < A[i])
                j++;
            seen.add(j, A[i]);
            out[i] = getMedian(seen);
        }
        return out;
    }


    /**
     *
     * @param inputStream an input stream of integers
     * @return the median of the stream, after each element has been added
     */
    public static double[] runningMedian(int[] inputStream) {
        double[] runningMedian = new double[inputStream.length];
        PriorityQueue<Integer> upperPQ = minPQ();
        PriorityQueue<Integer> lowerPQ = maxPQ();
        for (int i = 0; i < inputStream.length; ++i) {
            if (i % 2 == 0) { // Odd number insertion
                if (i > 1) { // Not the first element to add
                    // Order the current item and the tops of the min and max PQs
                    int[] sorted = orderNums(inputStream[i], lowerPQ.poll(), upperPQ.poll());
                    // Add the items back to the PQs
                    lowerPQ.offer(sorted[0]);
                    runningMedian[i] = sorted[1];
                    upperPQ.offer(sorted[2]);
                } else { // First item being added
                    runningMedian[i] = inputStream[i];
                }
            } else { // Even number insertion
                // Determine which item should be added to each heap
                if (inputStream[i] > runningMedian[i-1]) {
                    lowerPQ.offer((int)runningMedian[i-1]); // Retreived median should always be an integer
                    upperPQ.offer(inputStream[i]);
                } else {
                    lowerPQ.offer(inputStream[i]);
                    upperPQ.offer((int)runningMedian[i-1]); // Retreived median should always be an integer
                }
                runningMedian[i] = ((double)(lowerPQ.peek() + upperPQ.peek())) / 2.0;
            }
        }
        return runningMedian;
    }

    /**
     * Orders three ints
     */
    private static int[] orderNums(int a, int b, int c) {
        return new InsertionSort().sort(new int[]{a, b, c});
    }

}
