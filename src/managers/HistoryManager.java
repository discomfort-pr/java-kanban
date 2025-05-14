package managers;

import tasks.Task;

public interface HistoryManager {
    void add(Task task);

    void remove(int taskId);

    Task[] getHistory();
}
