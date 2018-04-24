import java.util.HashMap;

public class DiceRollSum {

    private static HashMap<Integer, Integer> memo = new HashMap<>();

    // Runtime: O(N)
    // Space: O(N)
    public static int diceRollSum(int N) {
        if (N < 0)
            return 0;
        if (N < 2)
            return 1;

        if (memo.containsKey(N))
            return memo.get(N);

        int total = 0;
        for (short roll = 1; roll <= 6; ++roll) {
            total += diceRollSum(N-roll);
        }
        memo.put(N, total);
        return total;
    }

}
