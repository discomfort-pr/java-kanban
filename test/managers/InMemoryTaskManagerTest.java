package managers;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tasks.*;
import util.Managers;

class InMemoryTaskManagerTest {
    private static InMemoryTaskManager taskManager;

    @BeforeEach
    public void createNewManager() {
        taskManager = (InMemoryTaskManager) Managers.getDefault();
    }

    @Test
    public void shouldNotAddEpicAsSubtask() {
        Subtask subtask = new Subtask("name", "description", 0);
        taskManager.addSubtask(subtask);
        assertNull(taskManager.getSubtask(subtask.getId()));
    }

    @Test
    public void shouldAddAndFindDifferentTasks() {
        Task task = new Task("1", "");
        taskManager.addTask(task);

        Epic epic = new Epic("2", "");
        taskManager.addEpic(epic);

        Subtask subtask = new Subtask("3", "", epic.getId());
        taskManager.addSubtask(subtask);

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

        assertNotEquals(taskManager.getTask(task1.getId()), taskManager.getTask(task2.getId()));
    }

    @Test
    public void shouldNotChangeTaskObjects() {
        String taskName = "name1";
        String taskDescription = "description";
        Task task = new Task(taskName, taskDescription);
        taskManager.addTask(task);

        assertEquals(taskName, taskManager.getTask(task.getId()).getName());
        assertEquals(taskDescription, taskManager.getTask(task.getId()).getDescription());
        assertEquals(TaskStatus.NEW, taskManager.getTask(task.getId()).getStatus());
    }

    @Test
    public void shouldNotSaveRemovedSubtasksInEpic() {
        Epic e = new Epic("", "");
        taskManager.addEpic(e);
        Subtask s1 = new Subtask("", "", e.getId());
        Subtask s2 = new Subtask("", "", e.getId());
        Subtask s3 = new Subtask("", "", e.getId());

        taskManager.addSubtask(s1);
        taskManager.addSubtask(s2);
        taskManager.addSubtask(s3);

        taskManager.removeSubtask(s2.getId());

        Integer[] subs = taskManager.getEpic(e.getId()).getSubtasks().toArray(new Integer[0]);
        assertArrayEquals(new Integer[] {1, 3}, subs);
    }

    @Test
    public void shouldNotSaveSubtasksWhenEpicRemoved() {
        Epic e = new Epic("", "");
        taskManager.addEpic(e);
        Subtask s1 = new Subtask("", "", e.getId());
        Subtask s2 = new Subtask("", "", e.getId());
        Subtask s3 = new Subtask("", "", e.getId());

        taskManager.addSubtask(s1);
        taskManager.addSubtask(s2);
        taskManager.addSubtask(s3);

        taskManager.getSubtask(s1.getId());
        taskManager.getSubtask(s2.getId());
        taskManager.getSubtask(s3.getId());

        taskManager.removeEpic(e.getId());

        assertArrayEquals(new Subtask[0], taskManager.getSubtasks().toArray(new Subtask[0]));
        assertArrayEquals(new Task[0], taskManager.getHistory());
    }
}