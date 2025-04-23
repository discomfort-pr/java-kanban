package tasks;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

public class EpicTest {
    @Test
    public void shouldTasksBeEqualWhenSameId() {
        Epic t1 = new Epic("1", "");
        t1.setId(1);
        t1.setStatus(TaskStatus.NEW);

        Epic t2 = new Epic("2", "");
        t2.setId(1);
        t2.setStatus(TaskStatus.IN_PROGRESS);

        Epic t3 = new Epic("1", "");
        t3.setId(2);
        t3.setStatus(TaskStatus.NEW);

        assertTrue(t1.equals(t2));

        assertFalse(t1.equals(t3));
        assertFalse(t2.equals(t3));
    }
}

