import java.util.ArrayList;
import java.util.HashMap;

public class TripleSum {

    static int tripleSum(int arr[], int sum) {
        HashMap<Integer, ArrayList<int[]>> pairSums = new HashMap<>();
        // Group pairs according to their sum
        for (int i = 0; i < arr.length-1; ++i) { // Time complexity: O(N^2)
            for (int j = i+1; j < arr.length; ++j) {
                int pairSum = arr[i] + arr[j];
                // Check if we've seen this pair sum before
                if (!pairSums.containsKey(pairSum)) {
                    // We haven't, so initialize a new list for pairs with this sum
                    pairSums.put(pairSum, new ArrayList<int[]>());
                }
                // Add the pair to the corresponding sum group
                pairSums.get(pairSum).add(new int[] {i, j});
            }
        }

        int tripleCount = 0;
        // Check each element in the array for a pair that would sum to the desired sum
        for (int i = 0; i < arr.length; ++i) {
            int matchingSum = sum - arr[i];
            // Find any pairs that would match this element
            ArrayList<int[]> matchingPairs = pairSums.get(matchingSum);
            if (matchingPairs != null) {
                // Make sure each pair doesn't include the element itself
                for (int[] pair : matchingPairs) {
                    if (pair[0] != i && pair[1] != i) {
                        ++tripleCount;
                    }
                }
            }
        }

        // Divide by 3 to account for duplicates
        return tripleCount / 3;
    }
}
