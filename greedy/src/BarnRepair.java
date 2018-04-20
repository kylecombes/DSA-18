import java.util.Arrays;
import java.util.PriorityQueue;

public class BarnRepair {
    public static int solve(int M, int[] occupied) {
        if (occupied.length == 0) {
            return 0;
        }

        // Make sure the array is sorted
        Arrays.sort(occupied);

        // Counter for the number of stalls with doors
        int blockedStalls = occupied.length;
        // Counter for the number of contiguous sets of unblocked stalls
        PriorityQueue<Integer> gapLengths = new PriorityQueue<>();

        // Identify the lengths of all the gaps
        int prevOccupiedStall = occupied[0];
        for (int i = 1; i < occupied.length; ++i) {
            if (occupied[i] - prevOccupiedStall > 1) { // Found a gap
                gapLengths.add(occupied[i] - prevOccupiedStall - 1);
            }
            prevOccupiedStall = occupied[i];
        }

        // Fill in the gaps to get down to our maximum board count
        // (There will always be one more board than the number of gaps
        // because our input, occupied, ignores gaps on the outside of
        // the filled stalls.)
        while (gapLengths.size() + 1 > M) {
            blockedStalls += gapLengths.poll();
        }

        return blockedStalls;
    }
}