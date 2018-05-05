import java.util.ArrayList;

public class Regex {

    public static boolean isMatch(String s, String p) {
        // Tokenize the regex
        ArrayList<Token> regexp = tokenize(p);
        
        // Create a DP matrix
        boolean[][] DP = new boolean[s.length()][regexp.size()];

        // Do the do
        for (short i = 0; i < s.length(); ++i) {
            for (short j = 0; j < regexp.size(); ++j) {
                char c = s.charAt(i);
                Token ret = regexp.get(j);
                if (ret.c == '.' || c == ret.c) { // . or c match
                    if (i == 0 && j == 0) { // If we're at the cell (0,0)
                        DP[i][i] = true;
                    } else if (j == 0 || i == 0) { // If we're at only one of the edges
                        DP[i][j] = false;
                    } else {
                        // Check if the previous cell on the diagonal was true
                        DP[i][j] = DP[i - 1][j - 1];
                    }
                }
                if (!DP[i][j] && (c == ret.c || ret.c == '.') && ret.number == 0) { // c* match
                    // Check above
                    DP[i][j] = i > 0 && DP[i-1][j];
                }
                if (!DP[i][j] && ret.number == 0) { // c* and no match or .*
                    // Check left
                    DP[i][j] = (i == 0 && j == 0) || (j > 0 && DP[i][j-1]);
                }
            }
        }

        // Return whether or not the bottom-right position in the DP matches
        return DP[s.length()-1][regexp.size()-1];
    }

    private static ArrayList<Token> tokenize(String s) {
        ArrayList<Token> res = new ArrayList<>();
        for (short i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (c == '*') {
                // Set `number` on last token to 0 (for 0 or more matches)
                res.get(res.size()-1).number = 0;
            } else {
                // Add a new token for this character
                res.add(new Token(c, 1));
            }
        }
        return res;
    }

    private static class Token {
        char c; // . for any character
        int number; // 0 for any number of occurrences (0+), 1 for 1 occurrence

        Token(char c, int number) {
            this.c = c;
            this.number = number;
        }
    }

}
