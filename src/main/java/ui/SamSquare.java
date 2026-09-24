package ui;

import java.util.ArrayList;

import exception.SamSquareException;
import parser.Parser;
import storage.Storage;
import tasks.Task;

/**
 * Coordinates command handling, task changes, storage, and console interaction.
 */
public class SamSquare {
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
            try {
                Parser parser = new Parser(ui.readCommand());
                switch (parser.getCommand()) {
                    case "bye" -> {
                        ui.showGoodbye();
                        return;
                    }
                    case "list" -> ui.showTasks(tasks);
                    case "mark" -> {
                        Task task = tasks.get(getTaskIndex(parser, tasks.size()));
                        task.markAsDone();
                        ui.showTaskStatus(task);
                        Storage.save(tasks);
                    }
                    case "unmark" -> {
                        Task task = tasks.get(getTaskIndex(parser, tasks.size()));
                        task.markAsNotDone();
                        ui.showTaskStatus(task);
                        Storage.save(tasks);
                    }
                    case "delete" -> {
                        Task task = tasks.remove(getTaskIndex(parser, tasks.size()));
                        ui.showDeletedTask(task, tasks.size());
                        Storage.save(tasks);
                    }
                    case "todo", "deadline", "event" -> {
                        Task task = parser.parseTask();
                        tasks.add(task);
                        ui.showAddedTask(task, tasks.size());
                        Storage.save(tasks);
                    }
                    default -> throw new IllegalStateException("Parser returned an unsupported command.");
                }
            } catch (SamSquareException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    /**
     * Checks whether a parsed task number exists and converts it to a list index.
     *
     * @param parser Parser for a command that requires a task number.
     * @param taskCount Number of tasks currently in the list.
     * @return The corresponding zero-based index.
     * @throws SamSquareException If the number is invalid or outside the list.
     */
    private int getTaskIndex(Parser parser, int taskCount) throws SamSquareException {
        int taskNumber = parser.parseTaskNumber();
        if (taskNumber < 1 || taskNumber > taskCount) {
            throw new SamSquareException("There is no task numbered " + taskNumber + ".");
        }
        return taskNumber - 1;
    }
}
