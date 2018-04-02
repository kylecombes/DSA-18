import java.util.*;

public class CoinsOnAClock {

    public static List<char[]> coinsOnAClock(int pennies, int nickels, int dimes, int hoursInDay) {
        List<char[]> result = new ArrayList<>();
        // Turn the coin counts into a list of unused coins
        LinkedList<Character> unused = new LinkedList<>();
        for (int i = 0; i < pennies; ++i) unused.push('p');
        for (int i = 0; i < nickels; ++i) unused.push('n');
        for (int i = 0; i < dimes; ++i) unused.push('d');

        // Find all the possible arrangements
        char[] clock = new char[hoursInDay];
        HashSet<String> arrSet = new HashSet<>();
        findPossibleArrangements(clock, unused, 0, 0, result, arrSet);

        return result;
    }


    /**
     * Creates a deep copy of the input array and returns it
     */
    private static char[] copyOf(char[] A) {
        char[] B = new char[A.length];
        System.arraycopy(A, 0, B, 0, A.length);
        return B;
    }


    private static void findPossibleArrangements(char[] clock, List<Character> unused, int pos, int addedCount, List<char[]> arrangements, HashSet<String> arrSet) {
        if (addedCount == clock.length) { // Valid arrangement found
            String strVersion = new String(clock);
            if (!arrSet.contains(strVersion)) {
                arrangements.add(copyOf(clock));
                arrSet.add(strVersion);
            }
            return;
        }

        // Make sure this position hasn't been used already
        if (clock[pos] > 0) {
            return; // Invalid arrangement
        }

        // Try adding each unused coin to this position and continuing from there
        for (int unusedIdx = 0; unusedIdx < unused.size(); ++unusedIdx) {
            char c = unused.remove(unusedIdx);
            clock[pos] = c;
            int newPos = (pos + getCoinAdvanceDistance(c)) % clock.length;
            findPossibleArrangements(clock, unused, newPos, addedCount + 1, arrangements, arrSet);
            clock[pos] = 0;
            unused.add(unusedIdx, c);
        }

    }

    private static int getCoinAdvanceDistance(char c) {
        if (c == 'p')
            return 1;
        if (c == 'n')
            return 5;
        if (c == 'd')
            return 10;
        throw new IllegalArgumentException("Coin " + c + " not recognized.");
    }

}
