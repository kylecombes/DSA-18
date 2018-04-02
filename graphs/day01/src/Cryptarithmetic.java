import java.util.*;

public class Cryptarithmetic {

    // Do not modify this function (though feel free to use it)
    public static boolean validSolution(String S1, String S2, String S3, Map<Character, Integer> assignments) {
        return (stringToInt(S1, assignments) + stringToInt(S2, assignments) == stringToInt(S3, assignments))
                && assignments.get(S1.charAt(0)) != 0
                && assignments.get(S2.charAt(0)) != 0
                && assignments.get(S3.charAt(0)) != 0;
    }


    private static Iterable<Integer> randomOrder() {
        List<Integer> l = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collections.shuffle(l);
        return l;
    }

    private static int stringToInt(String s, Map<Character, Integer> assignments) {
        int i = 0;
        for (int j = 0; j < s.length(); j++) {
            i *= 10;
            i += assignments.getOrDefault(s.charAt(j), 0);
        }
        return i;
    }

    public static Map<Character, Integer> solvePuzzle(String S1, String S2, String S3) {
        Map<Character, Integer> assignments = new HashMap<>();
        solvePuzzle(S1, S2, S3, assignments, 0);
        return assignments;
    }

    private static Map<Character, Integer> solvePuzzle(String S1, String S2, String S3, Map<Character, Integer> map, int pos) {
        if (pos > S1.length() + S2.length() + S3.length()) {
            return validSolution(S1, S2, S3, map) ? map : null;
        }

        char c;
        int whichWord = pos % 3;
        int whichChar = pos / 3;


        switch (whichWord) {
            case 0: // Just finished selecting the sum element for a column, so check everything up through that column
                if (pos > 0) {
                    int diff = stringToInt(S3, map) - stringToInt(S2, map) - stringToInt(S1, map);
                    if (diff % Math.pow(10, whichChar) != 0) // Letters up until this point do not sum properly
                        return null;
                }
                c = whichChar < S1.length() ? S1.charAt(S1.length() - whichChar - 1) : 0;
                break;

            case 1:
                c = whichChar < S2.length() ? S2.charAt(S2.length() - whichChar - 1) : 0;
                break;

            default:
                c = whichChar < S3.length() ? S3.charAt(S3.length() - whichChar - 1) : 0;
        }

        if (c == 0) { // Empty space in word (shorter than others), so proceed
            Map<Character, Integer> res = solvePuzzle(S1, S2, S3, map, pos+1);
            if (res != null) // Valid mapping found
                return res;
        } else if (!map.containsKey(c)) { // Haven't seen this letter before
            for (int n : randomOrder()) { // Try each value for this letter
                map.put(c, n);
                Map<Character, Integer> res = solvePuzzle(S1, S2, S3, map, pos+1);
                if (res != null) // Valid mapping found
                    return res;
                map.remove(c);
            }
        } else { // Skip this letter and continue trying to solve
            Map<Character, Integer> res = solvePuzzle(S1, S2, S3, map, pos+1);
            if (res != null) // Valid mapping found
                return res;
        }
        return null;
    }

}
