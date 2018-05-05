import java.util.LinkedList;
import java.util.List;

public class TextJustification {

    private static double cost(String[] words, int lo, int hi, int m) {
        if (hi <= lo)
            throw new IllegalArgumentException("Hi must be higher than Lo");
        int length = hi-lo-1; // account for spaces;
        for (int i = lo; i < hi; i++) {
            length += words[i].length();
        }
        if (length > m)
            return Double.POSITIVE_INFINITY;
        return Math.pow(m-length, 3);
    }

    public static List<Integer> justifyText(String[] w, int m) {
        // Build DP array
        double[][] DP = new double[w.length][w.length];
        for (int i = 0; i < w.length; ++i) {
            for (int j = i; j < w.length; ++j) {
                DP[i][j] = cost(w, i, j+1, m);
            }
        }

        double[] costs = new double[w.length];
        int[] lineIndices = new int[w.length];

        for (int i = w.length - 1; i >= 0; --i) {
            if (DP[i][w.length-1] < Double.POSITIVE_INFINITY) {
                costs[i] = DP[i][w.length-1]; // Note the cost of having this word on this line
                lineIndices[i] = w.length; // Note which word is the first word on the line after word i
            } else { // Time to split
                double bestSplitCost = Double.POSITIVE_INFINITY;
                int bestSplitJ = w.length - 1;
                // Determine where the best position to split is
                for (int j = w.length-1; j > i; --j) {
                    // Sum up the best costs between i and j and the best cost beyond j
                    double costSum = DP[i][j-1] + costs[j];
                    if (costSum < bestSplitCost) {
                        bestSplitCost = costSum;
                        bestSplitJ = j;
                    }
                }
                costs[i] = bestSplitCost;
                lineIndices[i] = bestSplitJ;
            }
        }

        // Turn the array/map of {word i => index of first word on next line}
        // into a list of the indices of words that appear first on a line
        List<Integer> wordsStartingLines = new LinkedList<>();
        wordsStartingLines.add(0);
        int i = lineIndices[0];
        while (i < w.length) {
            wordsStartingLines.add(i);
            i = lineIndices[i];
        }
        return wordsStartingLines;
    }

}