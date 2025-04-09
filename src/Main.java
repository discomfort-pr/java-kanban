public class Main {

    public static void main(String[] args) {
        TaskManager tm = new TaskManager();

        Task t1 = tm.createTask("task1", "");
        Task t2 = tm.createTask("task2", "velosiped");
        Task t3 = tm.createTask(t2, TaskStatus.IN_PROGRESS);

        tm.addTask(t1);
        tm.addTask(t2);
        System.out.println(tm.getTasks());

        tm.updateTask(t2.getId(), t3);
        System.out.println(tm.getTasks());

        tm.removeTask(t1.getId());
        System.out.println(tm.getTasks());


        Epic e1 = tm.createEpic("epic1", "");
        Subtask s1 = tm.createSubtask("subtask1", "", e1.getId());
        Subtask s2 = tm.createSubtask("subtask2", "", e1.getId());
        Subtask s3 = tm.createSubtask(s1, TaskStatus.DONE);

        tm.addEpic(e1);

        tm.addSubtask(s1);
        tm.addSubtask(s2);
        tm.updateSubtask(s1.getId(), s3);

        Epic e2 = tm.createEpic(e1);

        tm.updateEpic(e1.getId(), e2);
        System.out.println(tm.getEpics());

        tm.removeSubtask(s3.getId());

        Epic e3 = tm.createEpic(e2);

        tm.updateEpic(e2.getId(), e3);
        System.out.println(tm.getEpics());
        System.out.println(tm.getSubtasks());

        Subtask s4 = tm.createSubtask(s2, TaskStatus.DONE);
        tm.updateSubtask(s2.getId(), s4);

        Epic e4 = tm.createEpic(e3);

        tm.updateEpic(e3.getId(), e4);
        System.out.println(tm.getEpics());

        tm.removeEpic(e4.getId());
        tm.removeTask(t3.getId());

        System.out.println("#####################################");
        System.out.println(tm.getTasks());
        System.out.println(tm.getSubtasks());
        System.out.println(tm.getEpics());
    }
}
