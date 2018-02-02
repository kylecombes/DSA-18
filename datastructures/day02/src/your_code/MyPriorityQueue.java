package your_code;

import java.util.LinkedList;

/**
 * An implementation of a priority Queue
 */
public class MyPriorityQueue {

    LinkedList<Integer> ll = new LinkedList<>();

    public void enqueue(int item) {
        int insertIndex = 0;
        while (insertIndex < ll.size() && ll.get(insertIndex) < item) {
            ++insertIndex;
        }
        ll.add(insertIndex, item);
    }

    /**
     * Return and remove the largest item on the queue.
     */
    public int dequeueMax() {
        return ll.removeLast();
    }

}