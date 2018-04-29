public class SplitCoins {

    public static int splitCoins(int[] coins) {
        int N = coins.length;

        // Calculate the sum of all elements
        int sum = 0;
        for (int coin : coins) sum += coin;

        boolean DP[][] = new boolean[N + 1][sum + 1];

        // Initialize first column as true, since a sum of 0 is possible for all
        for (int i = 0; i <= N; i++) DP[i][0] = true;

        // Fill in the DP
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= sum / 2 + 1; j++) {
                // If ith element is excluded
                DP[i][j] = DP[i - 1][j];

                // If ith element is included
                if (coins[i - 1] <= j) // Make sure this element is not bigger than the sum up till this point
                    DP[i][j] |= DP[i - 1][j - coins[i - 1]];
            }
        }

        // Initialize the difference of two sums
        int diff = Integer.MAX_VALUE;

        // Find the largest j where there is at least one 'true'
        for (int j = sum / 2; j >= 0; j--) {
            if (DP[N][j]) { // The last row will always be true if any of the other rows are true
                diff = sum - 2 * j;
                break;
            }
        }
        return diff;
    }

}
