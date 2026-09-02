import java.util.Scanner;

public class SamSquare {
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
        System.out.println("____________________________________________________________");
    }

    private static void runTaskManager() {
        Scanner scanner = new Scanner(System.in);

        // String[] tasks = new String[100];
        // boolean[] taskDone = new boolean[100];
        Task[] tasks = new Task[100];
        int taskCount = 0;

        while (true) {
            String message = scanner.nextLine();

            if (message.equals("bye")) {
                printGoodbyeMessage();
                break;

            } else if (message.equals("list")) {
                listTasks(tasks, taskCount);

            } else if (message.startsWith("mark ")) {
                markTask(message, tasks);

            } else if (message.startsWith("unmark ")) {
                unmarkTask(message, tasks);

            } else if (message.startsWith("todo ")) {
                taskCount = addTodo(message, tasks, taskCount);

            } else if (message.startsWith("deadline ")) {
                taskCount = addDeadline(message, tasks, taskCount);

            } else if (message.startsWith("event ")) {
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

        System.out.println("____________________________________________________________");
    }

    private static void markTask(String message, Task[] tasks) {
        int taskNumber = Integer.parseInt(message.substring(5));
        int taskIndex = taskNumber - 1;

        tasks[taskIndex].markAsDone();

        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   [" + tasks[taskIndex].getTypeIcon() + "][X] "
                + tasks[taskIndex].getFullDescription());
        System.out.println("____________________________________________________________");
    }

    private static void unmarkTask(String message, Task[] tasks) {
        int taskNumber = Integer.parseInt(message.substring(7));
        int taskIndex = taskNumber - 1;

        tasks[taskIndex].markAsNotDone();

        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   [" + tasks[taskIndex].getTypeIcon() + "][ ] "
                + tasks[taskIndex].getFullDescription());
        System.out.println("____________________________________________________________");
    }

    private static int addTodo(String message, Task[] tasks, int taskCount) {
        String description = message.substring(5);

        tasks[taskCount] = new Todo(description);
        taskCount++;

        System.out.println(" Got it. I've added this task:");
        System.out.println("   [T][ ] " + description);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        System.out.println("____________________________________________________________");

        return taskCount;
    }

    private static int addDeadline(String message, Task[] tasks, int taskCount) {
        String content = message.substring(9);
        String[] parts = content.split(" /by ", 2);

        String description = parts[0];
        String by = parts[1];

        tasks[taskCount] = new Deadline(description, by);
        taskCount++;

        System.out.println(" Got it. I've added this task:");
        System.out.println("   [D][ ] " + description + " (by: " + by + ")");
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        System.out.println("____________________________________________________________");

        return taskCount;
    }

    private static int addEvent(String message, Task[] tasks, int taskCount) {
        String content = message.substring(6);

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
        System.out.println("____________________________________________________________");

        return taskCount;
    }

    private static int addGenericTask(String message, Task[] tasks, int taskCount) {
        tasks[taskCount] = new Task(message);
        taskCount++;

        System.out.println(" added: " + message);
        System.out.println("____________________________________________________________");

        return taskCount;
    }

    private static void printGoodbyeMessage() {
        System.out.println("Byebye hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }
}