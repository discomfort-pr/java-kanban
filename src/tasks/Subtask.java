package tasks;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    public Subtask(Subtask subtask, TaskStatus status) {
        super(subtask, status);
        this.epicId = subtask.epicId;
    }

    @Override
    public String toString() {
        return "tasks.Subtask{" +
                "epicId=" + epicId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }

    public int getEpicId() {
        return epicId;
    }
}
