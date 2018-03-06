public class TreeNode<T extends Comparable<T>> {
    T key;
    TreeNode<T> parent, leftChild, rightChild;

    TreeNode(T key) {
        this.key = key;
        this.parent = null;
        this.leftChild = null;
        this.rightChild = null;
    }

    public boolean isLeftChild() {
        if (parent != null)
            return parent.hasLeftChild() && parent.leftChild == this;
        return false;
    }

    public boolean isRightChild() {
        if (parent != null)
            return parent.hasRightChild() && parent.rightChild == this;
        return false;
    }

    public boolean hasLeftChild() {
        return this.leftChild != null;
    }

    public boolean hasRightChild() {
        return this.rightChild != null;
    }

    public boolean isRoot() {
        return this.parent == null;
    }

    public boolean isLeaf() {
        return !this.hasLeftChild() && !this.hasRightChild();
    }

    public void replaceWith(TreeNode<T> n) {
        if (isLeftChild())
            parent.leftChild = n;

        if (isRightChild())
            parent.rightChild = n;

        if (n != null) {
            // Temporarily move children so that they will be properly moved into place in moveChildrenFrom()
            if (n.isLeftChild())
                n.parent.leftChild = n.leftChild;
            else if (n.isRightChild())
                n.parent.rightChild = n.rightChild;

            n.parent = parent;
            n.moveChildrenFrom(this);
        }
    }

    public void moveChildrenFrom(TreeNode<T> n) {
        leftChild = n.leftChild;
        rightChild = n.rightChild;
        if (leftChild != null) // Don't overwrite non-null node with null (shouldn't happen)
            leftChild.parent = this;
        if (rightChild != null)
            rightChild.parent = this;
    }

    public boolean equals(TreeNode other) {
        return other.key.equals(this.key);
    }

    @Override
    public String toString() {
        String parent = this.parent == null ? "" : ", parent=" + this.parent.key.toString();
        String left = this.leftChild == null ? "" : ", left=" + this.leftChild.key.toString();
        String right = this.rightChild == null ? "" : ", right=" + this.rightChild.key.toString();
        return "TreeNode<T>{key=" + key + parent + left + right + '}';
    }
}
