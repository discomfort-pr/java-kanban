import java.util.ArrayList;
public class Epic extends Task {
    private final ArrayList<Subtask> subtasks;

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

    public void addSubtask(Subtask subtask) {
        if (!subtasks.contains(subtask)) {
            subtasks.add(subtask);
        }
    }

    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }
}
