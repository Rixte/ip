import java.util.Scanner;

public class SamSquare {
    public static void main(String[] args) {
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

        Scanner scanner = new Scanner(System.in);

        // String[] tasks = new String[100];
        // boolean[] taskDone = new boolean[100];
        Task[] tasks = new Task[100];
        int taskCount = 0;

        while (true) {
            String message = scanner.nextLine();

            if (message.equals("bye")) {
                System.out.println("Byebye hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;

            } else if (message.equals("list")) {
                System.out.println(" Here are the tasks in your list:");

                for (int i = 0; i < taskCount; i++) {
                    System.out.println(" " + (i + 1) + ".["
                            + tasks[i].getStatusIcon() + "] "
                            + tasks[i].getDescription());
                }

                System.out.println("____________________________________________________________");

            } else if (message.startsWith("mark ")) {
                int taskNumber = Integer.parseInt(message.substring(5));
                int taskIndex = taskNumber - 1;

                tasks[taskIndex].markAsDone();

                System.out.println(" Nice! I've marked this task as done:");
                System.out.println("   [X] " + tasks[taskIndex]);
                System.out.println("____________________________________________________________");

            } else if (message.startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(message.substring(7));
                int taskIndex = taskNumber - 1;

                tasks[taskIndex].markAsNotDone();

                System.out.println(" OK, I've marked this task as not done yet:");
                System.out.println("   [ ] " + tasks[taskIndex]);
                System.out.println("____________________________________________________________");

            } else {
                tasks[taskCount] = new Task(message);
                taskCount++;

                System.out.println(" added: " + message);
                System.out.println("____________________________________________________________");
            }
        }

        scanner.close();
    }
}
