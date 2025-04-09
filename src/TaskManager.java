import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Subtask> subtasks;
    private final HashMap<Integer, Epic> epics;
    private static int taskId = 0;

    public TaskManager() {
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public Subtask getSubtask(int id) {
        return subtasks.get(id);
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void addSubtask(Subtask subtask) {
        Epic epic = getEpic(subtask.getEpicId());
        epic.addSubtask(subtask);

        subtasks.put(subtask.getId(), subtask);
    }

    public void addEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void updateTask(int oldTaskId, Task task) {
        tasks.remove(oldTaskId);
        tasks.put(task.getId(), task);
    }

    public void updateSubtask(int oldSubtaskId, Subtask subtask) {
        Subtask oldSubtask = getSubtask(oldSubtaskId);
        Epic subtaskEpic = getEpic(oldSubtask.getEpicId());

        subtaskEpic.removeSubtask(oldSubtask);
        subtaskEpic.addSubtask(subtask);

        subtasks.remove(oldSubtaskId);
        subtasks.put(subtask.getId(), subtask);
    }

    public void updateEpic(int oldEpicId, Epic epic) {
        // поскольку список подзаданий в конструкторе Epic создается пустым, его приходится пополнять здесь
        Epic oldEpic = getEpic(oldEpicId);
        for (Subtask subtask : oldEpic.getSubtasks()) {
            subtask.setEpicId(epic.getId());
            epic.addSubtask(subtask);
        }

        epics.remove(oldEpicId);
        epics.put(epic.getId(), epic);
    }

    public void removeTask(int id) {
        tasks.remove(id);
    }

    public void removeSubtask(int id) {
        Subtask removable = getSubtask(id);
        Epic updatable = getEpic(removable.getEpicId());
        updatable.removeSubtask(removable);

        subtasks.remove(id);
    }

    public void removeEpic(int id) {
        Epic removable = getEpic(id);
        for (Subtask subtask : removable.getSubtasks()) {
            subtasks.remove(subtask.getId());
        }

        epics.remove(id);
    }

    public ArrayList<Subtask> getEpicSubtasks(Epic epic) {
        return epic.getSubtasks();
    }

    public Task createTask(String name, String description) {
        return new Task(name, description, taskId++);
    }

    public Task createTask(Task task, TaskStatus status) {
        return new Task(task, status, taskId++);
    }

    public Subtask createSubtask(String name, String description, int epicId) {
        return new Subtask(name, description, taskId++, epicId);
    }

    public Subtask createSubtask(Subtask subtask, TaskStatus status) {
        return new Subtask(subtask, status, taskId++);
    }

    public Epic createEpic(String name, String description) {
        return new Epic(name, description, taskId++);
    }

    public Epic createEpic(Epic epic) {
        if (epic.getSubtasks().isEmpty()) {
            return new Epic(epic, TaskStatus.NEW, taskId++);
        }

        boolean areSubsNew, areSubsDone;
        areSubsNew = areSubsDone = true;
        for (Subtask subtask : getEpicSubtasks(epic)) {
            if (subtask.getStatus() != TaskStatus.NEW) {
                areSubsNew = false;
            } else if (subtask.getStatus() != TaskStatus.DONE) {
                areSubsDone = false;
            }
        }
        if (areSubsNew) {
            return new Epic(epic, TaskStatus.NEW, taskId++);
        } else if (areSubsDone) {
            return new Epic(epic, TaskStatus.DONE, taskId++);
        } else {
            return new Epic(epic, TaskStatus.IN_PROGRESS, taskId++);
        }
    }
}
