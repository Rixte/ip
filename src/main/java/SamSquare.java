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

        String[] tasks = new String[100];
        int taskCount = 0;

        while (true) {
            String message = scanner.nextLine();

            if (message.equals("bye")) {
                System.out.println("Byebye hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            }

            if (message.equals("list")) {
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(" " + (i + 1) + ". " + tasks[i]);
                }
                System.out.println("____________________________________________________________");
            } else {
                tasks[taskCount] = message;
                taskCount++;

                System.out.println(" added: " + message);
                System.out.println("____________________________________________________________");
            }
        }

        scanner.close();
    }
}
