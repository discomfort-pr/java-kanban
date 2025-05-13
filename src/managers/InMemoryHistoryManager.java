package managers;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    class Node {
        private Node next;
        private Node prev;
        private final int taskId;

        public Node(Node next, Node prev, int taskId) {
            this.next = next;
            this.prev = prev;
            this.taskId = taskId;
        }
    }

    private final Map<Integer, Node> nodes;
    private Node head;
    private Node tail;

    public InMemoryHistoryManager() {
        nodes = new HashMap<>();
    }

    @Override
    public void add(int taskId) {
        if (nodes.containsKey(taskId)) {
            removeNode(nodes.get(taskId));
        }
        Node last = linkLast(taskId);
        nodes.put(taskId, last);
    }

    @Override
    public void remove(int taskId) {
        removeNode(nodes.get(taskId));
        nodes.remove(taskId);
    }

    @Override
    public List<Integer> getHistory() {
        List<Integer> history = new ArrayList<>();

        Node current = head;
        while (current != null) {
            history.add(current.taskId);
            current = current.next;
        }
        return history;
    }

    private Node linkLast(int taskId) {
        Node last;
        if (head == null) {
            last = new Node(null, null, taskId);
            head = tail = last;
        } else {
            last = new Node(null, tail, taskId);
            tail.next = last;
            tail = tail.next;
        }
        return last;
    }

    private void removeNode(Node removed) {
        if (removed == head) {
            if (head.next == null) {
                head = null;
            } else {
                head = head.next;
                head.prev = null;
            }
        } else if (removed == tail) {
            tail = tail.prev;
            tail.next = null;
        } else {
            removed.prev.next = removed.next;
            removed.next.prev = removed.prev;
        }
    }

    // как я понимаю метод getTasks делает то же, что и getHistory
}
