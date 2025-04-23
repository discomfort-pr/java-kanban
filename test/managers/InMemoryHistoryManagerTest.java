package managers;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import util.Managers;

import java.util.ArrayList;

class InMemoryHistoryManagerTest {
    @Test
    public void shouldSaveOldTaskVersion() {
        InMemoryHistoryManager historyManager = (InMemoryHistoryManager) Managers.getDefaultHistory();
        historyManager.add(1);
        historyManager.add(2);
        historyManager.add(3);
        historyManager.add(4);
        historyManager.add(5);
        historyManager.add(4);

        ArrayList<Integer> history = new ArrayList<>(historyManager.getHistory());
        history.removeLast();
        assertTrue(history.contains(4));
    }
}