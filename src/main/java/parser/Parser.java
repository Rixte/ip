package parser;

import commands.Command;
import commands.ExitCommand;
import commands.ListCommand;
import exception.SamSquareException;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.Todo;

/**
 * Interprets one console command without changing the task list or performing I/O.
 */
public class Parser {
    private final String fullCommand;
    private final String command;

    /**
     * Recognizes a command while preserving its original whitespace rules.
     *
     * @param fullCommand Complete line entered by the user.
     * @throws SamSquareException If the input is empty or the command is unknown.
     */
    public Parser(String fullCommand) throws SamSquareException {
        if (fullCommand.trim().isEmpty()) {
            throw new SamSquareException("Please don't leave your input empty D:");
        }

        this.fullCommand = fullCommand;
        int separatorIndex = fullCommand.indexOf(' ');
        command = separatorIndex == -1 ? fullCommand : fullCommand.substring(0, separatorIndex);

        // Only commands with arguments accept a space after the command name.
        boolean isKnownCommand = switch (command) {
            case "todo", "deadline", "event", "mark", "unmark", "delete" -> true;
            case "list", "bye" -> fullCommand.equals(command);
            default -> false;
        };
        if (!isKnownCommand) {
            throw new SamSquareException(
                    "Hold up... I don't recognise that command. "
                            + "Please use todo, deadline, event, mark, "
                            + "unmark, list or bye."
            );
        }
    }

    /**
     * Returns the recognized command name for dispatch by the application.
     *
     * @return A supported command name.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Creates an executable list or exit command.
     * Task-changing commands remain dispatched by the application during extraction.
     *
     * @return The executable command for list or bye.
     * @throws IllegalStateException If this command has not been extracted yet.
     */
    public Command parseCommand() {
        return switch (command) {
            case "list" -> new ListCommand();
            case "bye" -> new ExitCommand();
            default -> throw new IllegalStateException("This command has not been extracted yet.");
        };
    }

    /**
     * Creates a task from a todo, deadline, or event command.
     *
     * @return The task described by the command, ready to add to the list.
     * @throws SamSquareException If the task description or format is invalid.
     * @throws IllegalStateException If this command does not add a task.
     */
    public Task parseTask() throws SamSquareException {
        return switch (command) {
            case "todo" -> parseTodo();
            case "deadline" -> parseDeadline();
            case "event" -> parseEvent();
            default -> throw new IllegalStateException("This command does not add a task.");
        };
    }

    /**
     * Parses a mark, unmark, or delete argument as a one-based task number.
     * The task list's owner checks whether that number exists.
     *
     * @return The requested task number.
     * @throws SamSquareException If the number is missing or is not an integer.
     * @throws IllegalStateException If this command does not use a task number.
     */
    public int parseTaskNumber() throws SamSquareException {
        if (!command.equals("mark") && !command.equals("unmark") && !command.equals("delete")) {
            throw new IllegalStateException("This command does not use a task number.");
        }

        // Bare commands and commands followed by spaces have distinct existing messages.
        if (fullCommand.equals(command)) {
            throw new SamSquareException("Specify the task number to " + command + " please!");
        }

        String numberText = getArguments();
        if (numberText.isEmpty()) {
            String message = command.equals("delete")
                    ? "Specify which task you want to delete please! D:"
                    : "Please specify which task you want to " + command + ".";
            throw new SamSquareException(message);
        }

        try {
            return Integer.parseInt(numberText);
        } catch (NumberFormatException e) {
            String message = command.equals("delete")
                    ? "HEY!! The task number must be a valid number!"
                    : "The task number must be a valid number.";
            throw new SamSquareException(message);
        }
    }

    private String getArguments() {
        return fullCommand.substring(command.length()).trim();
    }

    /**
     * Validates the description before creating a ToDo.
     */
    private Task parseTodo() throws SamSquareException {
        String description = getArguments();

        if (description.isEmpty()) {
            throw new SamSquareException(
                    "HEY!! The description of a todo cannot be empty!"
            );
        }

        return new Todo(description);
    }

    /**
     * Validates the deadline format before creating a task with its due date.
     */
    private Task parseDeadline() throws SamSquareException {
        String content = getArguments();
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

        return new Deadline(description, by);
    }

    /**
     * Validates the event format before creating a task with its time range.
     */
    private Task parseEvent() throws SamSquareException {
        String content = getArguments();

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

        return new Event(description, from, to);
    }
}
