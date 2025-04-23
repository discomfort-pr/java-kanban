package managers;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;
import util.Managers;

class InMemoryTaskManagerTest {
    private static InMemoryTaskManager taskManager;

    @BeforeEach
    public void initializeManager() {
        taskManager = (InMemoryTaskManager) Managers.getDefault();
    }

    @Test
    public void shouldReturnFalseWhenTryingAddEpicAsSubtask() {
        Subtask subtask = new Subtask("name", "description", 0);
        assertFalse(taskManager.addSubtask(subtask));
    }

    @Test
    public void shouldAddAndFindDifferentTasks() {
        Task task = new Task("1", "");
        taskManager.addTask(task);

        Epic epic = new Epic("2", "");
        taskManager.addEpic(epic);

        Subtask subtask = new Subtask("3", "", epic.getId());
        assertTrue(taskManager.addSubtask(subtask));

        assertNotNull(taskManager.getTask(task.getId()));
        assertNotNull(taskManager.getSubtask(subtask.getId()));
        assertNotNull(taskManager.getEpic(epic.getId()));
    }

    @Test
    public void shouldDifferTasksWithManualAndAutoIdSetting() {
        Task task1 = new Task("1", "");
        Task task2 = new Task("2", "");

        task2.setId(0);

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        assertFalse(taskManager.getTask(task1.getId()).equals(taskManager.getTask(task2.getId())));
    }

    @Test
    public void shouldNotChangeTaskObjects() {
        String taskName = "name1";
        String taskDescription = "description";
        Task task = new Task(taskName, taskDescription);
        taskManager.addTask(task);

        // метод clone почему то нельзя вызвать
        assertEquals(taskManager.getTask(task.getId()).getName(), taskName);
        assertEquals(taskManager.getTask(task.getId()).getDescription(), taskDescription);
        assertEquals(taskManager.getTask(task.getId()).getStatus(), TaskStatus.NEW);
        // айди по идее нет смысла проверять
    }
}