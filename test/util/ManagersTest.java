package util;

import static org.junit.jupiter.api.Assertions.*;

import managers.HistoryManager;
import managers.InMemoryTaskManager;
import org.junit.jupiter.api.Test;

class ManagersTest {
    @Test
    public void shouldReturnNotNullObjects() {
        InMemoryTaskManager taskManager = (InMemoryTaskManager) Managers.getDefault();
        HistoryManager historyManager = Managers.getDefaultHistory();

        assertNotNull(taskManager);
        assertNotNull(taskManager.getTasks());
        assertNotNull(taskManager.getSubtasks());
        assertNotNull(taskManager.getHistory());

        assertNotNull(historyManager);
    }
}