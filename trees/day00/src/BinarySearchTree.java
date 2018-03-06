import java.util.LinkedList;
import java.util.List;

public class BinarySearchTree<T extends Comparable<T>> {
    TreeNode<T> root;
    private int size;

    public int size() {
        return size;
    }

    public boolean contains(T key) {
        return find(root, key) != null;
    }

    /**
     * Add a node to the BST. Internally calls insert to recursively find the new node's place
     */
    public boolean add(T key) {
        if (find(root, key) != null) return false;
        root = insert(root, key);
        size++;
        return true;
    }

    public void addAll(T[] keys) {
        for (T k : keys)
            add(k);
    }

    public List<T> inOrderTraversal() {
        List<T> res = new LinkedList<>();
        traverseNodeInOrder(root, res);
        return res;
    }

    // Runtime: O(N)
    private void traverseNodeInOrder(TreeNode<T> node, List<T> result) {
        if (node == null) // Reached bottom of tree
            return;

        // Traverse left branch
        traverseNodeInOrder(node.leftChild, result);
        // Add current node
        result.add(node.key);
        // Traverse right branch
        traverseNodeInOrder(node.rightChild, result);
    }

    /**
     * Deletes a node from the BST using the following logic:
     * 1. If the node has a left child, replace it with its predecessor
     * 2. Else if it has a right child, replace it with its successor
     * 3. If it has no children, simply its parent's pointer to it
     */
    public boolean delete(T key) {
        TreeNode<T> toDelete = find(root, key);
        if (toDelete == null) {
            System.out.println("Key does not exist");
            return false;
        }
        TreeNode<T> deleted = delete(toDelete);
        if (toDelete == root) {
            root = deleted;
        }
        size--;
        return true;
    }

    private TreeNode<T> delete(TreeNode<T> n) {
        // Recursive base case
        if (n == null) return null;

        TreeNode<T> replacement;

        if (n.isLeaf())
            // Case 1: no children
            replacement = null;
        else if (n.hasRightChild() != n.hasLeftChild())
            // Case 2: one child
            replacement = (n.hasRightChild()) ? n.rightChild : n.leftChild; // replacement is the non-null child
        else {
            // Case 3: two children
            replacement = findSuccessor(n);
        }

        // Put the replacement in its correct place, and set the parent.
        n.replaceWith(replacement);
        return replacement;
    }

    public T findPredecessor(T key) {
        TreeNode<T> n = find(root, key);
        if (n != null) {
            TreeNode<T> predecessor = findPredecessor(n);
            if (predecessor != null)
                return predecessor.key;
        }
        return null;
    }

    public T findSuccessor(T key) {
        TreeNode<T> n = find(root, key);
        if (n != null) {
            TreeNode<T> successor = findSuccessor(n);
            if (successor != null)
                return successor.key;
        }
        return null;
    }

    // Worst-case runtime: O(N)
    private TreeNode<T> findPredecessor(TreeNode<T> n) {
        if (n.leftChild != null) { // The next lesser element is down to the left
            n = n.leftChild;

            // Go down the tree to find the greatest node less than the original n
            while (n.rightChild != null)
                n = n.rightChild;
            return n;
        } else { // Keep going up the tree until we hit the root or find an element less than the original node
            T origKey = n.key;

            while (n != null) {
                // Check if this element is less than the original node key
                if (origKey.compareTo(n.key) >= 0) {
                    return n;
                } else { // Go up the tree
                    n = n.parent;
                }
            }
            // Ran out of tree to ascend
            return null;
        }
    }

    // Worst-case runtime: O(N)
    private TreeNode<T> findSuccessor(TreeNode<T> n) {
        if (n.rightChild != null) { // The next greater element is down to the right
            n = n.rightChild;

            // Go down the tree to find the least node greater than the original n
            while (n.leftChild != null)
                n = n.leftChild;
            return n;
        } else { // Keep going up the tree until we hit the root or find an element greater than the original node
            T origKey = n.key;

            while (n != null) {
                // Check if this element is greater than the original node key
                if (origKey.compareTo(n.key) <= 0) {
                    return n;
                } else { // Go up the tree
                    n = n.parent;
                }
            }
            // Ran out of tree to ascend
            return null;
        }
    }

    /**
     * Returns a node with the given key in the BST, or null if it doesn't exist.
     */
    private TreeNode<T> find(TreeNode<T> currentNode, T key) {
        if (currentNode == null)
            return null;
        int cmp = key.compareTo(currentNode.key);
        if (cmp < 0)
            return find(currentNode.leftChild, key);
        else if (cmp > 0)
            return find(currentNode.rightChild, key);
        return currentNode;
    }

    /**
     * Recursively insert a new node into the BST
     */
    private TreeNode<T> insert(TreeNode<T> node, T key) {
        if (node == null) return new TreeNode<>(key);

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.leftChild = insert(node.leftChild, key);
            node.leftChild.parent = node;
        } else {
            node.rightChild = insert(node.rightChild, key);
            node.rightChild.parent = node;
        }
        return node;
    }
}
