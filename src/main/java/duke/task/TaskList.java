package duke.task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import duke.exception.TaskNotFoundException;

/**
 * Represents a container for managing a list of tasks.
 * <p>
 * This class provides methods to add, list, retrieve, and remove tasks in the container.
 */
public class TaskList implements TaskContainer {

    private final List<Task> tasks; // List of tasks in the task list

    /**
     * Constructs an empty TaskList.
     * <p>
     * Initializes the internal list of tasks as an empty ArrayList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the task list.
     *
     * @param task The task to be added to the list.
     */
    @Override
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Lists all tasks in the task list, providing each task to the given consumer.
     *
     * @param consumer A {@link TaskConsumer} to accept each task and its index in the list.
     */
    @Override
    public void list(TaskConsumer consumer) {
        for (int i = 0; i < tasks.size(); i++) {
            consumer.accept(i, tasks.get(i));
        }
    }

    /**
     * Retrieves the task at the specified index in the task list.
     *
     * @param index The index of the task to be retrieved.
     * @return The task at the specified index.
     * @throws TaskNotFoundException If the index is out of range (i.e., invalid index).
     */
    @Override
    public Task get(int index) throws TaskNotFoundException {
        if (index < 0 || index >= tasks.size()) {
            throw new TaskNotFoundException(String.format(
                    "Index [%d] out of range [%d]", index, tasks.size()));
        }
        return tasks.get(index);
    }

    /**
     * Removes the task at the specified index from the task list.
     *
     * @param index The index of the task to be removed.
     * @return The task that was removed.
     * @throws TaskNotFoundException If the index is out of range (i.e., invalid index).
     */
    @Override
    public Task remove(int index) throws TaskNotFoundException {
        if (index < 0 || index >= tasks.size()) {
            throw new TaskNotFoundException(String.format(
                    "Index [%d] out of range [%d]", index, tasks.size()));
        }
        return tasks.remove(index);
    }

    /**
     * Returns the number of tasks in the task list.
     *
     * @return The number of tasks in the list.
     */
    @Override
    public int size() {
        return tasks.size();
    }

    /**
     * Creates a copy of the task list.
     *
     * @return A new TaskList containing the same tasks as this task list.
     */
    @Override
    public TaskContainer copy() {
        TaskList copy = new TaskList();
        for (Task task : tasks) {
            copy.add(task);
        }
        assert copy.size() == tasks.size();
        return copy;
    }

    /**
     * Returns an iterator over the tasks in this task list.
     * <p>
     * The iterator provides sequential access to the tasks, starting from the first task
     * to the last task in the list.
     *
     * @return An {@link Iterator} over the tasks in this task list.
     */
    @Override
    public Iterator<Task> iterator() {
        return tasks.iterator();
    }
}
