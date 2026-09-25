package tasks;

import java.util.ArrayList;
import java.util.List;

import exception.SamSquareException;

/**
 * Owns the ordered task collection and validates task numbers before changing it.
 */
public class TaskList {
    private final List<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Creates a task list from loaded tasks without sharing the supplied collection.
     *
     * @param loadedTasks Tasks to retain in their saved order, including completion status.
     */
    public TaskList(List<Task> loadedTasks) {
        tasks = new ArrayList<>(loadedTasks);
    }

    /**
     * Appends a task to the end of the list.
     *
     * @param task Validated task to add.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Removes the task identified by its displayed number.
     *
     * @param taskNumber One-based task number.
     * @return The removed task for the deletion response.
     * @throws SamSquareException If the task number does not exist.
     */
    public Task delete(int taskNumber) throws SamSquareException {
        return tasks.remove(getTaskIndex(taskNumber));
    }

    /**
     * Marks a task as done after validating its displayed number.
     *
     * @param taskNumber One-based task number.
     * @return The updated task for the status response.
     * @throws SamSquareException If the task number does not exist.
     */
    public Task mark(int taskNumber) throws SamSquareException {
        Task task = tasks.get(getTaskIndex(taskNumber));
        task.markAsDone();
        return task;
    }

    /**
     * Marks a task as not done after validating its displayed number.
     *
     * @param taskNumber One-based task number.
     * @return The updated task for the status response.
     * @throws SamSquareException If the task number does not exist.
     */
    public Task unmark(int taskNumber) throws SamSquareException {
        Task task = tasks.get(getTaskIndex(taskNumber));
        task.markAsNotDone();
        return task;
    }

    /**
     * Returns the current number of tasks.
     *
     * @return Number of tasks in the list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns an unmodifiable copy of the collection for display and saving.
     * The task objects are shared; their completion status is not copied.
     *
     * @return Tasks in their current order, without exposing the backing collection.
     */
    public List<Task> getTasks() {
        return List.copyOf(tasks);
    }

    /**
     * Converts a valid displayed number to a zero-based index before any mutation.
     */
    private int getTaskIndex(int taskNumber) throws SamSquareException {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new SamSquareException("There is no task numbered " + taskNumber + ".");
        }
        return taskNumber - 1;
    }
}
