package managers;

import java.util.List;

public interface HistoryManager {
    void add(int taskId);

    void removeLast();

    List<Integer> getHistory();
}
