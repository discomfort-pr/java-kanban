package managers;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final ArrayList<Integer> history;

    public InMemoryHistoryManager() {
        history = new ArrayList<>();
    }

    @Override
    public void add(int taskId) {
        if (history.size() == 10) {
            history.removeFirst();
        }
        history.add(taskId);
    }

    @Override
    public void removeLast() {
        history.removeLast();
    }

    @Override
    public List<Integer> getHistory() {
        return history;
    }
}
