package ui;

import java.util.ArrayList;

import exception.SamSquareException;
import storage.Storage;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.Todo;

/**
 * Coordinates command handling, task changes, storage, and console interaction.
 */
public class SamSquare {
    private static final String TODO_COMMAND = "todo ";
    private static final String DEADLINE_COMMAND = "deadline ";
    private static final String EVENT_COMMAND = "event ";
    private static final String MARK_COMMAND = "mark ";
    private static final String UNMARK_COMMAND = "unmark ";
    private static final String DELETE_COMMAND = "delete ";

    private final Ui ui = new Ui();

    /**
     * Starts the console task manager.
     *
     * @param args Command-line arguments, which are not used.
     */
    public static void main(String[] args) {
        new SamSquare().run();
    }

    /**
     * Shows the greeting and processes commands until the user exits.
     */
    public void run() {
        try (ui) {
            ui.showGreeting();
            runTaskManager();
        }
    }

    /**
     * Loads saved tasks and dispatches commands, saving successful changes.
     */
    private void runTaskManager() {
        ArrayList<Task> tasks = new ArrayList<>();
        Storage.load(tasks);

        while (true) {
            String message = ui.readCommand();

            try {
                if (message.equals("bye")) {
                    ui.showGoodbye();
                    break;

                } else if (message.equals("list")) {
                    ui.showTasks(tasks);

                } else if (message.startsWith(MARK_COMMAND)) {
                    markTask(message, tasks);
                    Storage.save(tasks);

                } else if (message.equals("mark")) {
                    throw new SamSquareException(
                            "Specify the task number to mark please!"
                    );

                } else if (message.startsWith(UNMARK_COMMAND)) {
                    unmarkTask(message, tasks);
                    Storage.save(tasks);

                } else if (message.equals("unmark")) {
                    throw new SamSquareException(
                            "Specify the task number to unmark please!"
                    );

                } else if (message.startsWith(DELETE_COMMAND)) {
                    deleteTask(message, tasks);
                    Storage.save(tasks);

                } else if (message.equals("delete")) {
                    throw new SamSquareException(
                            "Specify the task number to delete please!"
                    );

                } else if (message.equals("todo")) {
                    addTodo("todo ", tasks);
                    Storage.save(tasks);

                } else if (message.startsWith(TODO_COMMAND)) {
                    addTodo(message, tasks);
                    Storage.save(tasks);

                } else if (message.equals("deadline")) {
                    addDeadline("deadline ", tasks);
                    Storage.save(tasks);

                } else if (message.startsWith(DEADLINE_COMMAND)) {
                    addDeadline(message, tasks);
                    Storage.save(tasks);

                } else if (message.equals("event")) {
                    addEvent("event ", tasks);
                    Storage.save(tasks);

                } else if (message.startsWith(EVENT_COMMAND)) {
                    addEvent(message, tasks);
                    Storage.save(tasks);

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
                ui.showError(e.getMessage());
            }
        }
    }

    /**
     * Validates the requested task number before marking the task as done.
     */
    private void markTask(String message, ArrayList<Task> tasks)
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

        ui.showTaskStatus(task);
    }

    /**
     * Validates the requested task number before marking the task as not done.
     */
    private void unmarkTask(String message, ArrayList<Task> tasks)
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

        ui.showTaskStatus(task);
    }

    /**
     * Validates the requested task number before removing the task.
     */
    private void deleteTask(String message, ArrayList<Task> tasks)
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

        ui.showDeletedTask(deletedTask, tasks.size());
    }

    /**
     * Rejects task numbers outside the current one-based list range.
     */
    private void checkTaskNumber(int taskNumber, int taskCount)
            throws SamSquareException {

        if (taskNumber < 1 || taskNumber > taskCount) {
            throw new SamSquareException(
                    "There is no task numbered " + taskNumber + "."
            );
        }
    }

    /**
     * Validates the description before adding a ToDo.
     */
    private void addTodo(String message, ArrayList<Task> tasks)
            throws SamSquareException {
        String description = message.substring(TODO_COMMAND.length()).trim();

        if (description.isEmpty()) {
            throw new SamSquareException(
                    "HEY!! The description of a todo cannot be empty!"
            );
        }

        Task task = new Todo(description);
        tasks.add(task);
        ui.showAddedTask(task, tasks.size());
    }

    /**
     * Validates the deadline format before adding a task with its due date.
     */
    private void addDeadline(String message, ArrayList<Task> tasks)
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

        Task task = new Deadline(description, by);
        tasks.add(task);
        ui.showAddedTask(task, tasks.size());
    }

    /**
     * Validates the event format before adding a task with its time range.
     */
    private void addEvent(String message, ArrayList<Task> tasks)
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

        Task task = new Event(description, from, to);
        tasks.add(task);
        ui.showAddedTask(task, tasks.size());
    }
}
