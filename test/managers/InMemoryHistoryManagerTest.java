package managers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Managers;

class InMemoryHistoryManagerTest {
    private InMemoryHistoryManager historyManager;

    @BeforeEach
    public void createNewManager() {
        historyManager = (InMemoryHistoryManager) Managers.getDefaultHistory();
    }

    @Test
    public void shouldNotContainCopies() {
        historyManager.add(1);
        historyManager.add(2);
        historyManager.add(3);
        historyManager.add(1);
        historyManager.add(2);
        historyManager.add(1);
        historyManager.add(3);
        historyManager.add(3);

        Integer[] history = historyManager.getHistory().toArray(new Integer[0]);
        assertArrayEquals(new Integer[] {2, 1, 3}, history);
    }

    @Test
    public void shouldUpdateHistoryWhenTaskRemoved() {
        historyManager.add(1);
        historyManager.add(2);
        historyManager.add(3);
        historyManager.add(4);
        historyManager.add(5);
        historyManager.add(6);

        historyManager.remove(1);
        historyManager.remove(3);
        historyManager.remove(4);

        Integer[] history = historyManager.getHistory().toArray(new Integer[0]);
        assertArrayEquals(new Integer[] {2, 5, 6}, history);
    }
}