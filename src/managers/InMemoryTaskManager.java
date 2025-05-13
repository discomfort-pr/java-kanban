package managers;

import tasks.*;
import util.Managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
            historyManager.add(id);
        }
        return tasks.get(id);
    }

    @Override
    public Subtask getSubtask(int id) {
        if (subtasks.get(id) != null) {
            historyManager.add(id);
        }
        return subtasks.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        if (epics.get(id) != null) {
            historyManager.add(id);
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
        Epic epic = getEpicSilent(newSubtask.getEpicId());
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
        Epic subtaskEpic = getEpicSilent(newSubtask.getEpicId());

        subtasks.put(newSubtask.getId(), newSubtask);
        updateEpicStatus(subtaskEpic.getId());
    }

    @Override
    public void updateEpic(Epic newEpic) {
        if (!epics.containsKey(newEpic.getId())) {
            return;
        }
        epics.put(newEpic.getId(), newEpic);
    }

    @Override
    public void removeTask(int id) {
        if (getHistory().contains(id)) {
            historyManager.remove(id);
        }
        tasks.remove(id);
    }

    @Override
    public void removeSubtask(int id) {
        Subtask removable = getSubtaskSilent(id);
        Epic updatable = getEpicSilent(removable.getEpicId());

        if (getHistory().contains(id)) {
            historyManager.remove(id);
        }
        subtasks.remove(id);

        updatable.removeSubtask(removable.getId());
        updateEpicStatus(updatable.getId());
    }

    @Override
    public void removeEpic(int id) {
        Epic removable = getEpicSilent(id);
        for (int subtaskId : removable.getSubtasks()) {
            if (getHistory().contains(subtaskId)) {
                historyManager.remove(subtaskId);
            }
            subtasks.remove(subtaskId);
        }

        if (getHistory().contains(id)) {
            historyManager.remove(id);
        }
        epics.remove(id);
    }

    @Override
    public List<Integer> getEpicSubtasks(int epicId) {
        return getEpicSilent(epicId).getSubtasks();
    }

    private void updateEpicStatus(int epicId) {
        Epic epic = getEpicSilent(epicId);

        boolean allSubsNew = true;
        boolean allSubsDone = true;
        for (int id : epic.getSubtasks()) {
            TaskStatus status = getSubtaskSilent(id).getStatus();
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

    public List<Integer> getHistory() {
        return historyManager.getHistory();
    }

    private int getNextId() {
        return taskId++;
    }

    private Subtask getSubtaskSilent(int id) {
        return subtasks.get(id);
    }

    private Epic getEpicSilent(int id) {
        return epics.get(id);
    }
}
