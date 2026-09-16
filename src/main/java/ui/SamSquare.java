package ui;

import exception.SamSquareException;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.Todo;

import java.util.ArrayList;
import java.util.Scanner;

public class SamSquare {
    private static final String LINE_SEPARATOR =
            "____________________________________________________________";
    private static final String TODO_COMMAND = "todo ";
    private static final String DEADLINE_COMMAND = "deadline ";
    private static final String EVENT_COMMAND = "event ";
    private static final String MARK_COMMAND = "mark ";
    private static final String UNMARK_COMMAND = "unmark ";
    private static final String DELETE_COMMAND = "delete ";

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
        System.out.println("HELLO!! I am SamSquare :D\n"
                + "What can I do for you?");
        System.out.println(LINE_SEPARATOR);
    }

    private static void runTaskManager() {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Task> tasks = new ArrayList<>();

        while (true) {
            String message = scanner.nextLine();

            try {
                if (message.equals("bye")) {
                    printGoodbyeMessage();
                    break;

                } else if (message.equals("list")) {
                    listTasks(tasks);

                } else if (message.startsWith(MARK_COMMAND)) {
                    markTask(message, tasks);

                } else if (message.equals("mark")) {
                    throw new SamSquareException(
                            "Specify the task number to mark please!"
                    );

                } else if (message.startsWith(UNMARK_COMMAND)) {
                    unmarkTask(message, tasks);

                } else if (message.equals("unmark")) {
                    throw new SamSquareException(
                            "Specify the task number to unmark please!"
                    );

                } else if (message.startsWith(DELETE_COMMAND)) {
                    deleteTask(message, tasks);

                } else if (message.equals("delete")) {
                    throw new SamSquareException(
                            "Specify the task number to delete please!"
                    );

                } else if (message.equals("todo")) {
                    addTodo("todo ", tasks);

                } else if (message.startsWith(TODO_COMMAND)) {
                    addTodo(message, tasks);

                } else if (message.equals("deadline")) {
                    addDeadline("deadline ", tasks);

                } else if (message.startsWith(DEADLINE_COMMAND)) {
                    addDeadline(message, tasks);

                } else if (message.equals("event")) {
                    addEvent("event ", tasks);

                } else if (message.startsWith(EVENT_COMMAND)) {
                    addEvent(message, tasks);

                } else if (message.trim().isEmpty()) {
                    throw new SamSquareException(
                            "Please don't leave your input empty D:"
                    );

                } else {
                    throw new SamSquareException(
                            "Hold up... I don't recognise that command. "
                                    + "Please use todo, deadline, event, mark, "
                                    + "unmark, list or bye."
                    );
                }
            } catch (SamSquareException e) {
                System.out.println(" WAIT PAUSE!! " + e.getMessage());
                System.out.println(LINE_SEPARATOR);
            }
        }

        scanner.close();
    }

    private static void listTasks(ArrayList<Task> tasks) {
        System.out.println(" Here are the tasks in your list:");

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + ".["
                    + tasks.get(i).getTypeIcon() + "]["
                    + tasks.get(i).getStatusIcon() + "] "
                    + tasks.get(i).getFullDescription());
        }

        System.out.println(LINE_SEPARATOR);
    }

    private static void markTask(String message, ArrayList<Task> tasks)
            throws SamSquareException {
        String numberText = message.substring(MARK_COMMAND.length()).trim();

        if (numberText.isEmpty()) {
            throw new SamSquareException(
                    "Please specify which task you want to mark."
            );
        }

        int taskNumber;

        try {
            taskNumber = Integer.parseInt(numberText);
        } catch (NumberFormatException e) {
            throw new SamSquareException(
                    "The task number must be a valid number."
            );
        }

        checkTaskNumber(taskNumber, tasks.size());

        int taskIndex = taskNumber - 1;

        Task task = tasks.get(taskIndex);
        task.markAsDone();

        System.out.println(" WELL DONE!! I've marked this task as done:");
        System.out.println("   [" + task.getTypeIcon() + "][X] "
                + task.getFullDescription());
        System.out.println(LINE_SEPARATOR);
    }

    private static void unmarkTask(String message, ArrayList<Task> tasks)
            throws SamSquareException {
        String numberText =
                message.substring(UNMARK_COMMAND.length()).trim();

        if (numberText.isEmpty()) {
            throw new SamSquareException(
                    "Please specify which task you want to unmark."
            );
        }

        int taskNumber;

        try {
            taskNumber = Integer.parseInt(numberText);
        } catch (NumberFormatException e) {
            throw new SamSquareException(
                    "The task number must be a valid number."
            );
        }

        checkTaskNumber(taskNumber, tasks.size());

        int taskIndex = taskNumber - 1;

        Task task = tasks.get(taskIndex);
        task.markAsNotDone();

        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   [" + task.getTypeIcon() + "][ ] "
                + task.getFullDescription());
        System.out.println(LINE_SEPARATOR);
    }

    private static void deleteTask(String message, ArrayList<Task> tasks)
            throws SamSquareException {

        String numberText = message.substring(DELETE_COMMAND.length()).trim();

        if (numberText.isEmpty()) {
            throw new SamSquareException(
                    "Specify which task you want to delete please! D:"
            );
        }

        int taskNumber;

        try {
            taskNumber = Integer.parseInt(numberText);
        } catch (NumberFormatException e) {
            throw new SamSquareException(
                    "HEY!! The task number must be a valid number!"
            );
        }

        checkTaskNumber(taskNumber, tasks.size());

        int taskIndex = taskNumber - 1;

        Task deletedTask = tasks.remove(taskIndex);

        System.out.println(" Ahh noted! I've removed this task:");
        System.out.println("   [" + deletedTask.getTypeIcon() + "]["
                + deletedTask.getStatusIcon() + "] "
                + deletedTask.getFullDescription());
        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(LINE_SEPARATOR);
    }

    private static void checkTaskNumber(int taskNumber, int taskCount)
            throws SamSquareException {

        if (taskNumber < 1 || taskNumber > taskCount) {
            throw new SamSquareException(
                    "There is no task numbered " + taskNumber + "."
            );
        }
    }

    private static void addTodo(String message, ArrayList<Task> tasks)
            throws SamSquareException {
        String description = message.substring(TODO_COMMAND.length()).trim();

        if (description.isEmpty()) {
            throw new SamSquareException(
                    "HEY!! The description of a todo cannot be empty!"
            );
        }

        tasks.add(new Todo(description));

        System.out.println(" Got it!! I've added this task:");
        System.out.println("   [T][ ] " + description);
        System.out.println(" Now you have " + tasks.size() + " tasks in the list :)");
        System.out.println(LINE_SEPARATOR);
    }

    private static void addDeadline(String message, ArrayList<Task> tasks)
            throws SamSquareException {
        String content = message.substring(DEADLINE_COMMAND.length()).trim();
        String[] parts = content.split(" /by ", 2);

        if (parts.length < 2) {
            throw new SamSquareException(
                    "Hey... a deadline needs the format: deadline <task> /by <date>."
            );
        }

        String description = parts[0];
        String by = parts[1];


        if (description.isEmpty()) {
            throw new SamSquareException(
                    "HEY!! The description of a deadline cannot be empty!"
            );
        }

        if (by.isEmpty()) {
            throw new SamSquareException(
                    "Hey... deadline must have a due date!"
            );
        }

        tasks.add(new Deadline(description, by));

        System.out.println(" Got it! I've added this task:");
        System.out.println("   [D][ ] " + description + " (by: " + by + ")");
        System.out.println(" Now you have " + tasks.size() + " tasks in the list :)");
        System.out.println(LINE_SEPARATOR);
    }

    private static void addEvent(String message, ArrayList<Task> tasks)
            throws SamSquareException {
        String content = message.substring(EVENT_COMMAND.length()).trim();

        String[] fromParts = content.split(" /from ", 2);

        if (fromParts.length < 2) {
            throw new SamSquareException(
                    "An event needs the format: "
                            + "event <task> /from <time> /to <time>."
            );
        }

        String description = fromParts[0].trim();

        String[] toParts = fromParts[1].split(" /to ", 2);

        if (toParts.length < 2) {
            throw new SamSquareException(
                    "An event needs both a starting and ending time."
            );
        }

        String from = toParts[0].trim();
        String to = toParts[1].trim();

        if (description.isEmpty()) {
            throw new SamSquareException(
                    "HEY!! The description of an event cannot be empty!"
            );
        }

        if (from.isEmpty()) {
            throw new SamSquareException(
                    "Broski.. an event must have a starting time!"
            );
        }

        if (to.isEmpty()) {
            throw new SamSquareException(
                    "Hello? Of course the event must have an ending time..."
            );
        }

        tasks.add(new Event(description, from, to));

        System.out.println(" Got it. I've added this task:");
        System.out.println("   [E][ ] " + description
                + " (from: " + from + " to: " + to + ")");
        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(LINE_SEPARATOR);
    }

    private static void printGoodbyeMessage() {
        System.out.println("Byebye hope to see you again soon!");
        System.out.println(LINE_SEPARATOR);
    }
}
