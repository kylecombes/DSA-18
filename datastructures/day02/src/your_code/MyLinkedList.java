package your_code;

public class MyLinkedList {

    private Node head;
    private Node tail;
    private int size;

    private class Node {
        Chicken val;
        Node prev;
        Node next;

        private Node(Chicken d, Node prev, Node next) {
            this.val = d;
            this.prev = prev;
            this.next = next;
        }

        private Node(Chicken d) {
            this.val = d;
            prev = null;
            next = null;
        }
    }

    public MyLinkedList() {
        // Everything's already defaulted to null or 0
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void add(Chicken c) {
        addLast(c);
    }

    public Chicken pop() {
        return removeLast();
    }

    public void addLast(Chicken c) {
        if (size == 0) { // First chicken added
            tail = head = new Node(c, null, null);
        } else { // Non-empty list
            tail = tail.next = new Node(c, tail, null);
        }
        ++size;
    }

    public void addFirst(Chicken c) {
        if (size == 0) {
            tail = head = new Node(c, null, null);
        } else {
            head = head.prev = new Node(c, null, head);
        }
        ++size;
    }

    public Chicken get(int index) {
        return getNode(index).val;
    }

    private Node getNode(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The specified index is not within the list.");
        }
        Node res = head;
        for (int i = 0; i < index; ++i) {
            res = res.next;
        }
        return res;
    }

    public Chicken remove(int index) {
        if (size == 0) {
            throw new IllegalStateException("List is empty");
        }
        Node res = getNode(index);
        // Link the preceding and following nodes to each other
        if (res.prev != null)
            res.prev.next = res.next;
        if (res.next != null)
            res.next.prev = res.prev;
        // Update the head and tail pointers if necessary
        if (res == head)
            head = head.next;
        if (res == tail)
            tail = tail.prev;
        --size;
        return res.val;
    }

    public Chicken removeFirst() {
        return remove(0);
    }

    public Chicken removeLast() {
        return remove(size-1);
    }
}