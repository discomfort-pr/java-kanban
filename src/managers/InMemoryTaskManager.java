package managers;

import tasks.*;
import util.Managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Subtask> subtasks;
    private final HashMap<Integer, Epic> epics;
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
        return new ArrayList<Task>(tasks.values());
    }

    @Override
    public List<Subtask> getSubtasks() {
        return new ArrayList<Subtask>(subtasks.values());
    }

    @Override
    public List<Epic> getEpics() {
        return new ArrayList<Epic>(epics.values());
    }

    @Override
    public Task getTask(int id) {
        historyManager.add(id);
        return tasks.get(id);
    }

    @Override
    public Subtask getSubtask(int id) {
        historyManager.add(id);
        return subtasks.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        historyManager.add(id);
        return epics.get(id);
    }

    @Override
    public void addTask(Task newTask) {
        newTask.setId(getNextId());
        tasks.put(newTask.getId(), newTask);
    }

    @Override
    public boolean addSubtask(Subtask newSubtask) {
        Epic epic = getEpic(newSubtask.getEpicId());
        historyManager.removeLast();
        /* проверка на наличие в менеджере объекта типа Epic, чтобы избежать ситуации, когда
           у сабтаски в качестве эпика хранится не эпик, либо если самого эпика в менеджере нет
         */
        if (epic == null) {
            return false;
        }

        newSubtask.setId(getNextId());
        subtasks.put(newSubtask.getId(), newSubtask);

        epic.addSubtask(newSubtask.getId());
        updateEpicStatus(epic.getId());

        // возвращаемое значение добавлено для удобства тестирования
        return true;
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
        Epic subtaskEpic = getEpic(newSubtask.getEpicId());
        historyManager.removeLast();

        subtasks.put(newSubtask.getId(), newSubtask);
        updateEpicStatus(subtaskEpic.getId());
    }

    @Override
    public void updateEpic(Epic newEpic) {
        if (!epics.containsKey(newEpic.getId())) {
            return;
        }
        // поскольку список подзаданий в конструкторе tasks.Epic создается пустым, его приходится пополнять здесь
        Epic oldEpic = getEpic(newEpic.getId());
        historyManager.removeLast();

        for (int subtaskId : oldEpic.getSubtasks()) {
            newEpic.addSubtask(subtaskId);
        }
        epics.put(newEpic.getId(), newEpic);
    }

    @Override
    public void removeTask(int id) {
        tasks.remove(id);
    }

    @Override
    public void removeSubtask(int id) {
        Subtask removable = getSubtask(id);
        Epic updatable = getEpic(removable.getEpicId());

        subtasks.remove(id);

        updatable.removeSubtask(removable.getId());
        updateEpicStatus(updatable.getId());
    }

    @Override
    public void removeEpic(int id) {
        Epic removable = getEpic(id);
        for (int subtaskId : removable.getSubtasks()) {
            subtasks.remove(subtaskId);
        }

        epics.remove(id);
    }

    @Override
    public List<Integer> getEpicSubtasks(int epicId) {
        return getEpic(epicId).getSubtasks();
    }

    private void updateEpicStatus(int epicId) {
        Epic epic = getEpic(epicId);
        historyManager.removeLast();

        boolean allSubsNew = true;
        boolean allSubsDone = true;
        for (int id : epic.getSubtasks()) {
            TaskStatus status = getSubtask(id).getStatus();
            historyManager.removeLast();
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

    public int getNextId() {
        return taskId++;
    }

    public HistoryManager getHistoryManager() {
        return historyManager;
    }
}
