package tasks;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

public class TaskTest {
    @Test
    public void shouldTasksBeEqualWhenSameId() {
        Task t1 = new Task("1", "");
        t1.setId(1);
        t1.setStatus(TaskStatus.NEW);

        Task t2 = new Task("2", "");
        t2.setId(1);
        t2.setStatus(TaskStatus.IN_PROGRESS);

        Task t3 = new Task("1", "");
        t3.setId(2);
        t3.setStatus(TaskStatus.NEW);

        Task t4 = new Task(t2, TaskStatus.DONE);

        assertTrue(t1.equals(t2));
        assertTrue(t1.equals(t4));
        assertTrue(t2.equals(t4));

        assertFalse(t1.equals(t3));
        assertFalse(t2.equals(t3));
    }
}
