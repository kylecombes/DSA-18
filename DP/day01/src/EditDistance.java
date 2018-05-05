public class EditDistance {

    // Bottom-up
    public static int minEditDist(String a, String b) {
        if (a.length() == 0 || b.length() == 0)
            return Math.abs(a.length() - b.length());

        // Initialize the DP lookup table
        int[][] DP = new int[a.length()][b.length()];

        // Build up the first row
        DP[0][0] = a.charAt(0) == b.charAt(0) ? 0 : 1;
        // Keep track of if we've found a match for our first character
        boolean foundCharMatch = DP[0][0] == 0;
        for (short i = 1; i < a.length(); ++i) { // First column
            if (!foundCharMatch && a.charAt(i) == b.charAt(0)) {
                DP[i][0] = DP[i-1][0];
                foundCharMatch = true;
            } else {
                DP[i][0] = DP[i-1][0] + 1;
            }
        }
        foundCharMatch = DP[0][0] == 0;
        for (short i = 1; i < b.length(); ++i) { // First row
            if (!foundCharMatch && a.charAt(0) == b.charAt(i)) {
                DP[0][i] = DP[0][i-1];
                foundCharMatch = true;
            } else {
                DP[0][i] = DP[0][i-1] + 1;
            }
        }

        // Build the rest of the lookup table
        for (short i = 1; i < a.length(); ++i) {
            for (short j = 1; j < b.length(); ++j) {
                if (a.charAt(i) == b.charAt(j)) {
                    DP[i][j] = DP[i-1][j-1];
                } else if (DP[i-1][j] == DP[i][j-1]) {
                    DP[i][j] = DP[i-1][j-1] + 1;
                } else {
                    DP[i][j] = Math.min(DP[i-1][j], DP[i][j-1]) + 1;
                }
            }
        }

        return DP[a.length()-1][b.length()-1];
    }

}
