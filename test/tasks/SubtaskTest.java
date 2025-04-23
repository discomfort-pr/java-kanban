package tasks;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

public class SubtaskTest {
    @Test
    public void shouldSubtasksBeEqualWhenSameId() {
        Subtask t1 = new Subtask("1", "", 5);
        t1.setId(1);
        t1.setStatus(TaskStatus.NEW);

        Subtask t2 = new Subtask("2", "", 4);
        t2.setId(1);
        t2.setStatus(TaskStatus.IN_PROGRESS);

        Subtask t3 = new Subtask("1", "", 5);
        t3.setId(2);
        t3.setStatus(TaskStatus.NEW);

        Subtask t4 = new Subtask(t2, TaskStatus.DONE);

        assertTrue(t1.equals(t2));
        assertTrue(t1.equals(t4));
        assertTrue(t2.equals(t4));

        assertFalse(t1.equals(t3));
        assertFalse(t2.equals(t3));
    }
}
