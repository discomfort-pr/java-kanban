package managers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.List;

public interface TaskManager {
    List<Task> getTasks();

    List<Subtask> getSubtasks();

    List<Epic> getEpics();

    Task getTask(int id);

    Subtask getSubtask(int id);

    Epic getEpic(int id);

    void addTask(Task newTask);

    void addSubtask(Subtask newSubtask);

    void addEpic(Epic newEpic);

    void updateTask(Task newTask);

    void updateSubtask(Subtask newSubtask);

    void updateEpic(Epic newEpic);

    void removeTask(int id);

    void removeSubtask(int id);

    void removeEpic(int id);

    List<Integer> getEpicSubtasks(int epicId);

    List<Integer> getHistory();
}
