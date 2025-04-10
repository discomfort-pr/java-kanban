public class Main {

    public static void main(String[] args) {
        TaskManager tm = new TaskManager();

        Task t1 = new Task("task1", "this");
        Task t2 = new Task("task2", "");
        Task t1_u = new Task(t1, TaskStatus.IN_PROGRESS);

        tm.addTask(t1);
        tm.addTask(t2);

        System.out.println("1###");
        System.out.println(tm.getTasks());

        tm.updateTask(t1_u);

        System.out.println("2###");
        System.out.println(tm.getTasks());

        tm.updateTask(t1);

        System.out.println("3###");
        System.out.println(tm.getTasks());

        tm.removeTask(t1.getId());
        tm.removeTask(t2.getId());

        System.out.println("4###");
        System.out.println(tm.getTasks());


        Epic e1 = new Epic("epic1", "");
        Epic e2 = new Epic("epic2", "");

        tm.addEpic(e1);
        tm.addEpic(e2);

        Subtask s1 = new Subtask("subtask1", "some", e1.getId());
        Subtask s2 = new Subtask("subtask2", "", e1.getId());
        Subtask s3 = new Subtask("subtask3", "in epic2", e2.getId());

        tm.addSubtask(s1);
        tm.addSubtask(s2);
        tm.addSubtask(s3);

        System.out.println("5###");
        System.out.println(tm.getEpics());
        System.out.println(tm.getSubtasks());

        Subtask s3_u = new Subtask(s3, TaskStatus.DONE);

        tm.updateSubtask(s3_u);

        System.out.println("6###");
        System.out.println(tm.getEpic(e2.getId()));
        System.out.println(tm.getSubtasks());

        s3_u = new Subtask(s3, TaskStatus.IN_PROGRESS);

        tm.updateSubtask(s3_u);

        System.out.println("7###");
        System.out.println(tm.getEpic(e2.getId()));
        System.out.println(tm.getSubtask(s3_u.getId()));

        Subtask s2_u = new Subtask(s2, TaskStatus.DONE);

        tm.updateSubtask(s2_u);

        System.out.println("8###");
        System.out.println(tm.getEpic(e1.getId()));
        System.out.println(tm.getSubtasks());

        Subtask s1_u = new Subtask(s1, TaskStatus.DONE);

        tm.updateSubtask(s1_u);

        System.out.println("9###");
        System.out.println(tm.getEpic(e1.getId()));
        System.out.println(tm.getSubtasks());

        tm.removeEpic(e1.getId());

        System.out.println("10###");
        System.out.println(tm.getEpics());
        System.out.println(tm.getSubtasks());

        tm.removeSubtask(s3_u.getId());

        System.out.println("11###");
        System.out.println(tm.getEpics());
        System.out.println(tm.getSubtasks());
    }
}
