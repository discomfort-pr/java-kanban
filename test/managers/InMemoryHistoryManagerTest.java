package managers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Task;
import util.Managers;

class InMemoryHistoryManagerTest {
    private InMemoryHistoryManager historyManager;

    @BeforeEach
    public void createNewManager() {
        historyManager = (InMemoryHistoryManager) Managers.getDefaultHistory();
    }

    @Test
    public void shouldNotContainCopies() {
        Task t1 = new Task("", "");
        t1.setId(1);
        Task t2 = new Task("", "");
        t2.setId(2);
        Task t3 = new Task("", "");
        t3.setId(3);
        // айди приходится задавать вручную чтобы менеджер не считал таски одинаковыми

        historyManager.add(t1);
        historyManager.add(t2);
        historyManager.add(t3);
        historyManager.add(t1);
        historyManager.add(t2);
        historyManager.add(t1);
        historyManager.add(t3);
        historyManager.add(t3);

        Task[] history = historyManager.getHistory();
        assertArrayEquals(new Task[] {t2, t1, t3}, history);
    }

    @Test
    public void shouldUpdateHistoryWhenTaskRemoved() {
        Task t1 = new Task("", "");
        t1.setId(1);
        Task t2 = new Task("", "");
        t2.setId(2);
        Task t3 = new Task("", "");
        t3.setId(3);
        Task t4 = new Task("", "");
        t4.setId(4);
        Task t5 = new Task("", "");
        t5.setId(5);
        Task t6 = new Task("", "");
        t6.setId(6);

        historyManager.add(t1);
        historyManager.add(t2);
        historyManager.add(t3);
        historyManager.add(t4);
        historyManager.add(t5);
        historyManager.add(t6);

        historyManager.remove(1);
        historyManager.remove(3);
        historyManager.remove(4);

        Task[] history = historyManager.getHistory();
        assertArrayEquals(new Task[] {t2, t5, t6}, history);
    }
}