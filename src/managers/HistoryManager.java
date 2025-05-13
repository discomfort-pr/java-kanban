package managers;

import java.util.List;

public interface HistoryManager {
    void add(int taskId);
    void remove(int taskId);
    List<Integer> getHistory();
}
