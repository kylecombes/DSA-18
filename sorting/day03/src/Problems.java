import java.util.LinkedList;

public class Problems {

    static void sortNumsBetween100s(int[] A) {
        // Shift all numbers by 100 to get rid of the negatives temporarily
        for (int i = 0; i < A.length; ++i) {
            A[i] += 100;
        }
        // Sort the array using radix sort with a base of 2
        RadixSort.radixSort(A, 2);

        // Shift all numbers back to be in the range [-100, 100]
        for (int i = 0; i < A.length; ++i) {
            A[i] -= 100;
        }
    }

    /**
     * @param n the character number, 0 is the rightmost character
     * @return
     */
    private static int getNthCharacter(String s, int n) {
        return s.charAt(s.length() - 1 - n) - 'a';
    }


    /**
     * Use counting sort to sort the String array according to a character
     *
     * @param n The digit number (where 0 is the least significant digit)
     */
    static void countingSortByCharacter(String[] A, int n) {
        LinkedList<String>[] L = new LinkedList[26]; // 26 characters in the alphabet
        for (int i  = 0; i < 26; ++i) {
            L[i] = new LinkedList<String>();
        }
        // Group each string by their nth letter
        for (String string : A) {
            L[getNthCharacter(string, n)].push(string);
        }
        // Rebuild the array
        int aIdx = 0;
        for (LinkedList<String> stringList : L) {
            for (int i = stringList.size()-1; i >= 0; --i) {
                A[aIdx++] = stringList.get(i);
            }
        }
    }

    /**
     * @param stringLength The length of each of the strings in S
     */
    static void sortStrings(String[] S, int stringLength) {
        for (int i = 0; i < stringLength; ++i)
            countingSortByCharacter(S, i);
    }

    /**
     * @param A The array to count swaps in
     */

    public static int countSwaps(int[] A) {
        // TODO
        return 0;
    }

}
