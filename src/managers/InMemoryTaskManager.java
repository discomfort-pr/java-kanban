package managers;

import tasks.*;
import util.Managers;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private final Map<Integer, Task> tasks;
    private final Map<Integer, Subtask> subtasks;
    private final Map<Integer, Epic> epics;
    private final HistoryManager historyManager;
    private int taskId = 0;

    public InMemoryTaskManager() {
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
        historyManager = Managers.getDefaultHistory();
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public Task getTask(int id) {
        if (tasks.get(id) != null) {
            historyManager.add(tasks.get(id));
        }
        return tasks.get(id);
    }

    @Override
    public Subtask getSubtask(int id) {
        if (subtasks.get(id) != null) {
            historyManager.add(subtasks.get(id));
        }
        return subtasks.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        if (epics.get(id) != null) {
            historyManager.add(epics.get(id));
        }
        return epics.get(id);
    }

    @Override
    public void addTask(Task newTask) {
        newTask.setId(getNextId());
        tasks.put(newTask.getId(), newTask);
    }

    @Override
    public void addSubtask(Subtask newSubtask) {
        Epic epic = epics.get(newSubtask.getEpicId());
        if (epic == null) {
            return;
        }

        newSubtask.setId(getNextId());
        subtasks.put(newSubtask.getId(), newSubtask);

        epic.addSubtask(newSubtask.getId());
        updateEpicStatus(epic.getId());
    }

    @Override
    public void addEpic(Epic newEpic) {
        newEpic.setId(getNextId());
        epics.put(newEpic.getId(), newEpic);
    }

    @Override
    public void updateTask(Task newTask) {
        if (tasks.containsKey(newTask.getId())) {
            tasks.put(newTask.getId(), newTask);
        }
    }

    @Override
    public void updateSubtask(Subtask newSubtask) {
        if (!subtasks.containsKey(newSubtask.getId())) {
            return;
        }
        Epic updatable = epics.get(newSubtask.getEpicId());

        subtasks.put(newSubtask.getId(), newSubtask);
        updateEpicStatus(updatable.getId());
    }

    @Override
    public void updateEpic(Epic newEpic) {
        if (epics.containsKey(newEpic.getId())) {
            epics.put(newEpic.getId(), newEpic);
        }
    }

    @Override
    public void removeTask(int id) {
        historyManager.remove(id);
        tasks.remove(id);
    }

    @Override
    public void removeSubtask(int id) {
        Subtask removable = subtasks.get(id);
        Epic updatable = epics.get(removable.getEpicId());

        historyManager.remove(id);
        subtasks.remove(id);

        updatable.removeSubtask(removable.getId());
        updateEpicStatus(updatable.getId());
    }

    @Override
    public void removeEpic(int id) {
        Epic removable = epics.get(id);
        for (int subtaskId : removable.getSubtasks()) {
            historyManager.remove(subtaskId);
            subtasks.remove(subtaskId);
        }

        historyManager.remove(id);
        epics.remove(id);
    }

    @Override
    public List<Integer> getEpicSubtasks(int epicId) {
        return epics.get(epicId).getSubtasks();
    }

    private void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);

        boolean allSubsNew = true;
        boolean allSubsDone = true;
        for (int id : epic.getSubtasks()) {
            TaskStatus status = subtasks.get(id).getStatus();
            if (status != TaskStatus.NEW) {
                allSubsNew = false;
            }
            if (status != TaskStatus.DONE) {
                allSubsDone = false;
            }
        }

        if (allSubsNew) {
            epic.setStatus(TaskStatus.NEW);
        } else if (allSubsDone) {
            epic.setStatus(TaskStatus.DONE);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private int getNextId() {
        return taskId++;
    }
}