package managers;

import tasks.Task;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node> nodes;
    private Node head;
    private Node tail;

    public InMemoryHistoryManager() {
        nodes = new HashMap<>();
    }

    @Override
    public void add(Task task) {
        int taskId = task.getId();

        if (nodes.containsKey(taskId)) {
            removeNode(nodes.get(taskId));
        }
        Node last = linkLast(task);
        nodes.put(task.getId(), last);
    }

    @Override
    public void remove(int taskId) {
        if (nodes.containsKey(taskId)) {
            removeNode(nodes.get(taskId));
            nodes.remove(taskId);
        }
    }

    @Override
    public List<Task> getHistory() {
        List<Task> history = new ArrayList<>();

        Node current = head;
        while (current != null) {
            history.add(current.task);
            current = current.next;
        }
        return history;
    }

    private Node linkLast(Task task) {
        Node last;
        if (head == null) {
            last = new Node(null, null, task);
            head = tail = last;
        } else {
            last = new Node(null, tail, task);
            tail.next = last;
            tail = tail.next;
        }
        return last;
    }

    private void removeNode(Node removed) {
        if (removed.equals(head)) {
            if (head.next == null) {
                head = null;
            } else {
                head = head.next;
                head.prev = null;
            }
        } else if (removed.equals(tail)) {
            tail = tail.prev;
            tail.next = null;
        } else {
            removed.prev.next = removed.next;
            removed.next.prev = removed.prev;
        }
    }

    private static class Node {
        private Node next;
        private Node prev;
        private final Task task;

        public Node(Node next, Node prev, Task task) {
            this.next = next;
            this.prev = prev;
            this.task = task;
        }
    }
}
