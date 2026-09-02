import java.util.Scanner;

public class SamSquare {
    private static final int MAX_TASKS = 100;
    private static final String LINE_SEPARATOR =
            "____________________________________________________________";
    private static final String TODO_COMMAND = "todo ";
    private static final String DEADLINE_COMMAND = "deadline ";
    private static final String EVENT_COMMAND = "event ";
    private static final String MARK_COMMAND = "mark ";
    private static final String UNMARK_COMMAND = "unmark ";

    public static void main(String[] args) {
        showGreeting();
        runTaskManager();
    }

    private static void showGreeting() {
        String banner = "      __________\n"
                + "    /            \\\n"
                + "   /   •     •    \\\n"
                + "  |      ᴥ         |\n"
                + "   \\    _____     /\n"
                + "    \\____________/\n"
                + "      ||    ||\n"
                + "      ||____||";

        System.out.println(banner);
        System.out.println("Hello I am SamSquare.\n"
                + "What can I do for you?");
        System.out.println(LINE_SEPARATOR);
    }

    private static void runTaskManager() {
        Scanner scanner = new Scanner(System.in);

        // String[] tasks = new String[100];
        // boolean[] taskDone = new boolean[100];
        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;

        while (true) {
            String message = scanner.nextLine();

            if (message.equals("bye")) {
                printGoodbyeMessage();
                break;

            } else if (message.equals("list")) {
                listTasks(tasks, taskCount);

            } else if (message.startsWith(MARK_COMMAND)) {
                markTask(message, tasks);

            } else if (message.startsWith(UNMARK_COMMAND)) {
                unmarkTask(message, tasks);

            } else if (message.startsWith(TODO_COMMAND)) {
                taskCount = addTodo(message, tasks, taskCount);

            } else if (message.startsWith(DEADLINE_COMMAND)) {
                taskCount = addDeadline(message, tasks, taskCount);

            } else if (message.startsWith(EVENT_COMMAND)) {
                taskCount = addEvent(message, tasks, taskCount);

            } else {
                taskCount = addGenericTask(message, tasks, taskCount);
            }
        }

        scanner.close();
    }

    private static void listTasks(Task[] tasks, int taskCount) {
        System.out.println(" Here are the tasks in your list:");

        for (int i = 0; i < taskCount; i++) {
            System.out.println(" " + (i + 1) + ".["
                    + tasks[i].getTypeIcon() + "]["
                    + tasks[i].getStatusIcon() + "] "
                    + tasks[i].getFullDescription());
        }

        System.out.println(LINE_SEPARATOR);
    }

    private static void markTask(String message, Task[] tasks) {
        int taskNumber = Integer.parseInt(message.substring(MARK_COMMAND.length()));
        int taskIndex = taskNumber - 1;

        tasks[taskIndex].markAsDone();

        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   [" + tasks[taskIndex].getTypeIcon() + "][X] "
                + tasks[taskIndex].getFullDescription());
        System.out.println(LINE_SEPARATOR);
    }

    private static void unmarkTask(String message, Task[] tasks) {
        int taskNumber = Integer.parseInt(
                message.substring(UNMARK_COMMAND.length()));
        int taskIndex = taskNumber - 1;

        tasks[taskIndex].markAsNotDone();

        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   [" + tasks[taskIndex].getTypeIcon() + "][ ] "
                + tasks[taskIndex].getFullDescription());
        System.out.println(LINE_SEPARATOR);
    }

    private static int addTodo(String message, Task[] tasks, int taskCount) {
        String description = message.substring(TODO_COMMAND.length());

        tasks[taskCount] = new Todo(description);
        taskCount++;

        System.out.println(" Got it. I've added this task:");
        System.out.println("   [T][ ] " + description);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        System.out.println(LINE_SEPARATOR);

        return taskCount;
    }

    private static int addDeadline(String message, Task[] tasks, int taskCount) {
        String content = message.substring(DEADLINE_COMMAND.length());
        String[] parts = content.split(" /by ", 2);

        String description = parts[0];
        String by = parts[1];

        tasks[taskCount] = new Deadline(description, by);
        taskCount++;

        System.out.println(" Got it. I've added this task:");
        System.out.println("   [D][ ] " + description + " (by: " + by + ")");
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        System.out.println(LINE_SEPARATOR);

        return taskCount;
    }

    private static int addEvent(String message, Task[] tasks, int taskCount) {
        String content = message.substring(EVENT_COMMAND.length());

        String[] fromParts = content.split(" /from ", 2);
        String description = fromParts[0];

        String[] toParts = fromParts[1].split(" /to ", 2);
        String from = toParts[0];
        String to = toParts[1];

        tasks[taskCount] = new Event(description, from, to);
        taskCount++;

        System.out.println(" Got it. I've added this task:");
        System.out.println("   [E][ ] " + description
                + " (from: " + from + " to: " + to + ")");
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        System.out.println(LINE_SEPARATOR);

        return taskCount;
    }

    private static int addGenericTask(String message, Task[] tasks, int taskCount) {
        tasks[taskCount] = new Task(message);
        taskCount++;

        System.out.println(" added: " + message);
        System.out.println(LINE_SEPARATOR);

        return taskCount;
    }

    private static void printGoodbyeMessage() {
        System.out.println("Byebye hope to see you again soon!");
        System.out.println(LINE_SEPARATOR);
    }
}