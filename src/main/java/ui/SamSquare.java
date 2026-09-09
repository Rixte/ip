package ui;

import tasks.Task;
import tasks.Todo;
import tasks.Deadline;
import tasks.Event;
import exception.SamSquareException;

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
        System.out.println("HELLO!! I am SamSquare :D\n"
                + "What can I do for you?");
        System.out.println(LINE_SEPARATOR);
    }

    private static void runTaskManager() {
        Scanner scanner = new Scanner(System.in);

        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;

        while (true) {
            String message = scanner.nextLine();

            try {
                if (message.equals("bye")) {
                    printGoodbyeMessage();
                    break;

                } else if (message.equals("list")) {
                    listTasks(tasks, taskCount);

                } else if (message.startsWith(MARK_COMMAND)) {
                    markTask(message, tasks, taskCount);

                } else if (message.equals("mark")) {
                    throw new SamSquareException(
                            "Specify the task number to mark please!"
                    );

                } else if (message.startsWith(UNMARK_COMMAND)) {
                    unmarkTask(message, tasks, taskCount);

                } else if (message.equals("unmark")) {
                    throw new SamSquareException(
                            "Specify the task number to unmark please!"
                    );

                } else if (message.equals("todo")) {
                    taskCount = addTodo("todo ", tasks, taskCount);

                } else if (message.startsWith(TODO_COMMAND)) {
                    taskCount = addTodo(message, tasks, taskCount);

                } else if (message.equals("deadline")) {
                    taskCount = addDeadline("deadline ", tasks, taskCount);

                } else if (message.startsWith(DEADLINE_COMMAND)) {
                    taskCount = addDeadline(message, tasks, taskCount);

                } else if (message.equals("event")) {
                    taskCount = addEvent("event ", tasks, taskCount);

                } else if (message.startsWith(EVENT_COMMAND)) {
                    taskCount = addEvent(message, tasks, taskCount);

                } else if (message.trim().isEmpty()) {
                    throw new SamSquareException(
                            "Please don't leave your input empty D:"
                    );

                } else {
                    throw new SamSquareException(
                            "I don't recognise that command. "
                                    + "Please use todo, deadline, event, mark, "
                                    + "unmark, list or bye."
                    );
                }


//                } else {
//                    taskCount = addGenericTask(message, tasks, taskCount);
//                }
            } catch (SamSquareException e) {
                System.out.println(" WAIT PAUSE!! " + e.getMessage());
                System.out.println(LINE_SEPARATOR);
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

    private static void markTask(String message, Task[] tasks, int taskCount)
            throws SamSquareException {
        // int taskNumber = Integer.parseInt(message.substring(MARK_COMMAND.length()));

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

        checkTaskNumber(taskNumber, taskCount);

        int taskIndex = taskNumber - 1;

        tasks[taskIndex].markAsDone();

        System.out.println(" WELL DONE!! I've marked this task as done:");
        System.out.println("   [" + tasks[taskIndex].getTypeIcon() + "][X] "
                + tasks[taskIndex].getFullDescription());
        System.out.println(LINE_SEPARATOR);
    }

    private static void unmarkTask(String message, Task[] tasks, int taskCount)
            throws SamSquareException {
        // int taskNumber = Integer.parseInt(
        //        message.substring(UNMARK_COMMAND.length()));

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

        checkTaskNumber(taskNumber, taskCount);

        int taskIndex = taskNumber - 1;

        tasks[taskIndex].markAsNotDone();

        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   [" + tasks[taskIndex].getTypeIcon() + "][ ] "
                + tasks[taskIndex].getFullDescription());
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

    private static int addTodo(String message, Task[] tasks, int taskCount)
            throws SamSquareException {

        if (taskCount >= MAX_TASKS) {
            throw new SamSquareException(
                    "Hey... your task list is full. You cannot add more tasks D:"
            );
        }

        String description = message.substring(TODO_COMMAND.length()).trim();

        if (description.isEmpty()) {
            throw new SamSquareException(
                    "HEY!! The description of a todo cannot be empty!"
            );
        }

        tasks[taskCount] = new Todo(description);
        taskCount++;

        System.out.println(" Got it!! I've added this task:");
        System.out.println("   [T][ ] " + description);
        System.out.println(" Now you have " + taskCount + " tasks in the list :)");
        System.out.println(LINE_SEPARATOR);

        return taskCount;
    }

    private static int addDeadline(String message, Task[] tasks, int taskCount)
            throws SamSquareException {

        if (taskCount >= MAX_TASKS) {
            throw new SamSquareException(
                    "Hey... your task list is full. You cannot add more tasks D:"
            );
        }

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

        tasks[taskCount] = new Deadline(description, by);
        taskCount++;

        System.out.println(" Got it! I've added this task:");
        System.out.println("   [D][ ] " + description + " (by: " + by + ")");
        System.out.println(" Now you have " + taskCount + " tasks in the list :)");
        System.out.println(LINE_SEPARATOR);

        return taskCount;
    }

    private static int addEvent(String message, Task[] tasks, int taskCount)
            throws SamSquareException {

        if (taskCount >= MAX_TASKS) {
            throw new SamSquareException(
                    "Hey... your task list is full. You cannot add more tasks D:"
            );
        }

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

        tasks[taskCount] = new Event(description, from, to);
        taskCount++;

        System.out.println(" Got it. I've added this task:");
        System.out.println("   [E][ ] " + description
                + " (from: " + from + " to: " + to + ")");
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        System.out.println(LINE_SEPARATOR);

        return taskCount;
    }

//    private static int addGenericTask(String message, Task[] tasks, int taskCount) {
//        tasks[taskCount] = new Task(message);
//        taskCount++;
//
//        System.out.println(" added: " + message);
//        System.out.println(LINE_SEPARATOR);
//
//        return taskCount;
//    }

    private static void printGoodbyeMessage() {
        System.out.println("Byebye hope to see you again soon!");
        System.out.println(LINE_SEPARATOR);
    }
}