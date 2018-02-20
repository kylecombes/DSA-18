import java.util.HashMap;

public class LargestSubArray {

    // Time complexity: O(N)
    // Space complexity: O(N)
    static int[] largestSubarray(int[] nums) {
        // Keep track of the highest index with a given sum
        HashMap<Integer, Integer> indicesBySum = new HashMap<Integer, Integer>();

        // Store the largest index for each running sum
        int sum = 0;
        for (int i = 0; i < nums.length; ++i) {
            sum += nums[i] * 2 - 1; // Convert 0s to -1s
            indicesBySum.put(sum, i);
        }

        // Find the largest range for which the sum is 0
        sum = 0;
        int maxRangeSize = 0;
        int[] maxRange = {0, 0};
        for (int i = 0; i < nums.length; ++i) {
            if (nums.length - i <= maxRangeSize) {
                // The remaining section of the array is smaller than the largest range we've found so far,
                // so there's no way we're going to find a larger one.
                break;
            }

            // Find the furthest index that would sum to 0 starting here
            int furthestIndexSummingToZero = indicesBySum.get(sum);
            int range = furthestIndexSummingToZero - i;
            if (range > maxRangeSize) { // Found new largest range
                maxRangeSize = range;
                maxRange[0] = i;
                maxRange[1] = furthestIndexSummingToZero;
            }
            sum += nums[i] * 2 - 1; // Convert 0s to -1s
        }

        return maxRange;
    }
}
