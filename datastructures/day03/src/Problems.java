import java.util.*;

public class Problems {

    public static class Node {
        int val;
        Node next;

        Node(int d) {
            this.val = d;
            next = null;
        }
    }

    // O(N) time and O(N) space
    public static List<Integer> removeKDigits(int[] A, int k) {
        // Convert it to a linked list
        List<Integer> l = new LinkedList<>();
        for (int a : A) {
            l.add(a);
        }
        // Keep track of the number of digits we've removed
        int count = 0;
        while (count < k) {
            for (int i = 0; i < l.size() && count < k; ) {
                if (l.get(i) > l.get(i + 1)) { // Left digit is greater than right
                    l.remove(i);
                    if (i > 0)
                        --i; // Check previous digit again
                    ++count;
                } else { // Shift right to look at the next digit
                    ++i;
                    if (i == l.size() - 1) { // Last element reached
                        l.remove(i);
                        ++count;
                    }
                }
            }
        }

        return l;
    }

    // O(N) time and O(1) space
    public static boolean isPalindrome(Node n) {
        if (n == null) return true;

        Node head1 = n; // Don't lose track of the head node
        // Count the number of nodes so we can find the middle
        int size = 0;
        while (n != null) {
            ++size;
            n = n.next;
        }
        // Grab the middle node
        Node middle = head1;
        boolean evenSize = size % 2 == 0;
        int upperBound = (evenSize) ? size/2 - 1 : size/2;
        for (int i = 0; i < upperBound; ++i) {
            middle = middle.next;
        }
        if (evenSize) { // If an even list, need to break all connections between two halves of list
            Node temp = middle.next;
            middle.next = null;
            middle = temp;
        } // Odd count: both lists will point to the middle node (which will in turn point to nothing)
        // Reverse the list after the middle node
        Node left = middle;
        Node center = middle.next;
        left.next = null;
        Node right;
        while (center != null) {
            right = center.next;
            center.next = left;
            left = center;
            center = right;
        }
        Node head2 = left;

        // Go through each list and compare the node values at each position
        while (head1 != null) {
            if (head1.val != head2.val)
                return false; // Different values, therefore not a palindrome
            head1 = head1.next;
            head2 = head2.next;
        }
        return true;
    }

    // O(N) time and O(?) space
    private static int index; // A little weird. Would be better to make everything non-static
    public static String infixToPostfix(String s) {
        index = 0;
        return parseExpression(s);
    }

    private static String parseExpression(String s) {
        index += 2; // Skip paren
        StringBuilder sb = new StringBuilder();

        // Get the first operand
        String firstOperand = parseOperand(s);
        index += 1; // Skip the next space

        // Get the operator
        char operator = s.charAt(index);
        index += 2; // Skip over the operator and space

        // Get the second operand
        String secondOperand = parseOperand(s);

        index += 2; // Skip past the space and paren

        // Build and return the result
        return sb.append(firstOperand).append(' ').append(secondOperand).append(' ').append(operator).toString();
    }

    private static String parseOperand(String s) {
        return (s.charAt(index) == '(') ? parseExpression(s) : parseNum(s);
    }

    private static String parseNum(String s) {
        StringBuilder sb = new StringBuilder();
        for (; s.charAt(index) != ' '; ++index)
            sb.append(s.charAt(index));

        return sb.toString();
    }

}
