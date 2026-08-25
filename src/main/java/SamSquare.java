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

        while (true) {
            String message = scanner.nextLine();

            if (message.equals("bye")) {
                System.out.println("Byebye hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            }

            System.out.println(" " + message);
            System.out.println("____________________________________________________________");
        }

        scanner.close();
    }
}
