import java.util.*;

public class Problems {

    public static BinarySearchTree<Integer> minimalHeight(List<Integer> values) {
        // Sort the elements
        Collections.sort(values);

        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.root = buildSubtree(values, 0, values.size()-1);
        return bst;
    }

    private static TreeNode<Integer> buildSubtree(List<Integer> values, int lo, int hi) {
        if (lo == hi) // Reached leaf
            return new TreeNode<>(values.get(lo));
        else if (lo > hi)
            return null;

        int midIdx = (hi - lo) / 2 + lo;
        TreeNode<Integer> node = new TreeNode<>(values.get(midIdx));

        node.leftChild = buildSubtree(values, lo, midIdx-1);
        node.rightChild = buildSubtree(values, midIdx+1, hi);
        return node;
    }

    public static boolean isIsomorphic(TreeNode n1, TreeNode n2) {
        // TODO
        return false;
    }
}
