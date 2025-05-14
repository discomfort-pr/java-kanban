package tasks;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Integer> subtasks;

    public Epic(String name, String description) {
        super(name, description);
        subtasks = new ArrayList<>();
    }

    public Epic(String name, String description, List<Integer> subtasks) {
        super(name, description);
        this.subtasks = subtasks;
    }

    @Override
    public String toString() {
        return "tasks.Epic{" +
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
        subtasks.remove((Integer) subtaskId);
    }

    public List<Integer> getSubtasks() {
        return subtasks;
    }
}
