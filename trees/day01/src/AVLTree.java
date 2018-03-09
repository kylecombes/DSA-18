public class AVLTree<T extends Comparable<T>> extends BinarySearchTree<T> {

    /**
     * Delete a key from the tree rooted at the given node.
     */
    @Override
    TreeNode<T> delete(TreeNode<T> n, T key) {
        n = super.delete(n, key);
        if (n == null)
            return null;
        recalculateHeight(n);
        return balance(n);
    }

    /**
     * Insert a key into the tree rooted at the given node.
     * Runtime: O(log N)
     */
    @Override
    TreeNode<T> insert(TreeNode<T> node, T key) {
        node = super.insert(node, key);
        if (node == null)
            return null;
        recalculateHeight(node);
        return balance(node);
    }

    private void recalculateHeight(TreeNode<T> n) {
        n.height = Math.max(height(n.leftChild), height(n.rightChild)) + 1;
    }

    /**
     * Delete the minimum descendant of the given node.
     */
    @Override
    TreeNode<T> deleteMin(TreeNode<T> n) {
        n = super.deleteMin(n);
        if (n != null) {
            n.height = 1 + Math.max(height(n.leftChild), height(n.rightChild));
            return balance(n);
        }
        return null;
    }

    // Return the height of the given node. Return -1 if null.
    private int height(TreeNode<T> n) {
        return (n != null) ? n.height : -1;
    }

    public int height() {
        return Math.max(height(root), 0);
    }

    /**
     * Restores the AVL tree property of the subtree. Return the head of the new subtree
     * Runtime: O(1) - max 2 rotations, no searching, insertions, deletions, etc
      */

    TreeNode<T> balance(TreeNode<T> n) {
        if (n == null)
            return null;

        int balFac = balanceFactor(n);

        if (balFac >= 2 && balanceFactor(n.rightChild) >= 0) { // Node is VRH and right child is not LH
            return rotateLeft(n);
        } else if (balFac <= -2 && balanceFactor(n.leftChild) <= 0) { // Node is VLH and left child is not RH
            return rotateRight(n);
        } else if (balFac >= 2 && balanceFactor(n.rightChild) < 0) { // Node is VRH and right child is LH
            n.rightChild = rotateRight(n.rightChild);
            return rotateLeft(n);
        } else if (balFac <= -2 && balanceFactor(n.leftChild) > 0) { // Node is VLH and left child is RH
            n.leftChild = rotateLeft(n.leftChild);
            return rotateRight(n);
        }
        return n;
    }

    /**
     * Returns the balance factor of the subtree. The balance factor is defined
     * as the difference in height of the left subtree and right subtree, in
     * this order. Therefore, a subtree with a balance factor of -1, 0 or 1 has
     * the AVL property since the heights of the two child subtrees differ by at
     * most one.
     * Runtime: O(1)
     */
    private int balanceFactor(TreeNode<T> n) {
        if (n == null)
            return 0;
        return height(n.rightChild) - height(n.leftChild);
    }

    /**
     * Perform a right rotation on node `n`. Return the head of the rotated tree.
     * Runtime: O(1)
     */
    private TreeNode<T> rotateRight(TreeNode<T> n) {
        TreeNode<T> m = n.leftChild;
        TreeNode<T> tempOrphan = m.rightChild;
        m.rightChild = n;
        n.leftChild = tempOrphan;
        recalculateHeight(n);
        recalculateHeight(m);
        return m;
    }

    /**
     * Perform a left rotation on node `n`. Return the head of the rotated tree.
     * Runtime: O(1)
     */
    private TreeNode<T> rotateLeft(TreeNode<T> n) {
        TreeNode<T> m = n.rightChild;
        TreeNode<T> tempOrphan = m.leftChild;
        m.leftChild = n;
        n.rightChild = tempOrphan;
        recalculateHeight(n);
        recalculateHeight(m);
        return m;
    }
}
