package ui;

import exception.SamSquareException;
import parser.Parser;
import storage.Storage;
import tasks.Task;
import tasks.TaskList;

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
        TaskList tasks = new TaskList(Storage.load());

        while (true) {
            try {
                Parser parser = new Parser(ui.readCommand());
                switch (parser.getCommand()) {
                    case "bye" -> {
                        ui.showGoodbye();
                        return;
                    }
                    case "list" -> ui.showTasks(tasks.getTasks());
                    case "mark" -> {
                        Task task = tasks.mark(parser.parseTaskNumber());
                        ui.showTaskStatus(task);
                        Storage.save(tasks.getTasks());
                    }
                    case "unmark" -> {
                        Task task = tasks.unmark(parser.parseTaskNumber());
                        ui.showTaskStatus(task);
                        Storage.save(tasks.getTasks());
                    }
                    case "delete" -> {
                        Task task = tasks.delete(parser.parseTaskNumber());
                        ui.showDeletedTask(task, tasks.size());
                        Storage.save(tasks.getTasks());
                    }
                    case "todo", "deadline", "event" -> {
                        Task task = parser.parseTask();
                        tasks.add(task);
                        ui.showAddedTask(task, tasks.size());
                        Storage.save(tasks.getTasks());
                    }
                    default -> throw new IllegalStateException("Parser returned an unsupported command.");
                }
            } catch (SamSquareException e) {
                ui.showError(e.getMessage());
            }
        }
    }
}
