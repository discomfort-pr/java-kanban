import java.util.ArrayList;
public class Epic extends Task {
    private final ArrayList<Integer> subtasks;

    public Epic(String name, String description, int id) {
        super(name, description, id);
        subtasks = new ArrayList<>();
    }

    public Epic(Epic epic, TaskStatus status, int id) {
        super(epic, status, id);
        subtasks = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subtasks=" + subtasks +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }

    public void addSubtask(int subtaskId) {
        if (!subtasks.contains(subtaskId)) {
            subtasks.add(subtaskId);
        }
    }

    public void removeSubtask(int subtaskId) {
        subtasks.remove(subtaskId);
    }

    public ArrayList<Integer> getSubtasks() {
        return subtasks;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
