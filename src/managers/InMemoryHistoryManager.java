package managers;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Integer> history;

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
    public List<Integer> getHistory() {
        return history;
    }
}
