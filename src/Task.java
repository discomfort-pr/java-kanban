import java.util.Objects;

public class Task {
    protected final String name;
    protected final String description;
    protected final int id;
    protected final TaskStatus status;

    public Task(String name, String description, int id) {
        this.name = name;
        this.description = description;
        this.id = id;
        status = TaskStatus.NEW;
    }

    public Task(Task task, TaskStatus status, int id) {
        this.name = task.name;
        this.description = task.description;
        this.id = id;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }

    public int getId() {
        return id;
    }

    public TaskStatus getStatus() {
        return status;
    }
}
