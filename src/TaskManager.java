import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Subtask> subtasks;
    private final HashMap<Integer, Epic> epics;
    private int taskId = 0;

    public TaskManager() {
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
    }

    public List<Task> getTasks() {
        return (List<Task>) tasks.values();
    }

    public List<Subtask> getSubtasks() {
        return (List<Subtask>) subtasks.values();
    }

    public List<Epic> getEpics() {
        return (List<Epic>) epics.values();
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

    public void addTask(Task newTask, int newTaskId) {
        tasks.put(newTaskId, newTask);
    }

    public void addSubtask(Subtask newSubtask, int newSubtaskId) {
        Epic epic = getEpic(newSubtask.getEpicId());

        epic.addSubtask(newSubtaskId);
        updateEpicStatus(epic.getId());

        subtasks.put(newSubtaskId, newSubtask);
    }

    public void addEpic(Epic newEpic, int newEpicId) {
        epics.put(newEpicId, newEpic);
    }

    public void updateTask(Task newTask) {
        tasks.put(newTask.getId(), newTask);
    }

    public void updateSubtask(Subtask newSubtask) {
        Epic subtaskEpic = getEpic(newSubtask.getEpicId());

        subtaskEpic.removeSubtask(newSubtask.getId());
        subtaskEpic.addSubtask(newSubtask.getId());
        updateEpicStatus(subtaskEpic.getId());

        subtasks.put(newSubtask.getId(), newSubtask);
    }

    public void updateEpic(Epic newEpic) {
        // поскольку список подзаданий в конструкторе Epic создается пустым, его приходится пополнять здесь
        Epic oldEpic = getEpic(newEpic.getId());
        for (int subtaskId : oldEpic.getSubtasks()) {
            newEpic.addSubtask(subtaskId);
        }
        epics.put(newEpic.getId(), newEpic);
    }

    public void removeTask(int id) {
        tasks.remove(id);
    }

    public void removeSubtask(int id) {
        Subtask removable = getSubtask(id);
        Epic updatable = getEpic(removable.getEpicId());

        updatable.removeSubtask(removable.getId());
        updateEpicStatus(updatable.getId());

        subtasks.remove(id);
    }

    public void removeEpic(int id) {
        Epic removable = getEpic(id);
        for (int subtaskId : removable.getSubtasks()) {
            subtasks.remove(subtaskId);
        }

        epics.remove(id);
    }

    public ArrayList<Integer> getEpicSubtasks(Epic epic) {
        return epic.getSubtasks();
    }

    private void updateEpicStatus(int epicId) {
        Epic epic = getEpic(epicId);

        boolean allSubsNew = true, allSubsDone = true;
        for (int id : epic.getSubtasks()) {
            TaskStatus status = getSubtask(id).getStatus();
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
}
