package duke.task;

import duke.exception.TaskNotFoundException;

/**
 * Represents a container for storing and managing tasks.
 * Provides methods to add, list, retrieve, remove, and get the size of tasks in the container.
 */
public interface TaskContainer {

    /**
     * A functional interface used for accepting a task and its index in the task list.
     */
    @FunctionalInterface
    public interface TaskConsumer {

        /**
         * Accepts a task and its index.
         * This method is used to process a task in the task list.
         *
         * @param index The index of the task in the task list.
         * @param task The task at the specified index.
         */
        void accept(int index, Task task);
    }

    /**
     * Adds a task to the container.
     *
     * @param task The task to be added.
     */
    public void add(Task task);

    /**
     * Lists all tasks in the container by passing each task to the specified consumer.
     *
     * @param consumer A {@link TaskConsumer} that processes each task along with its index.
     */
    public void list(TaskConsumer consumer);

    /**
     * Retrieves the task at the specified index in the container.
     *
     * @param index The index of the task to retrieve.
     * @return The task at the specified index.
     * @throws TaskNotFoundException If the index is out of range (invalid index).
     */
    public Task get(int index) throws TaskNotFoundException;

    /**
     * Removes the task at the specified index from the container.
     *
     * @param index The index of the task to remove.
     * @return The task that was removed.
     * @throws TaskNotFoundException If the index is out of range (invalid index).
     */
    public Task remove(int index) throws TaskNotFoundException;

    /**
     * Returns the number of tasks currently stored in the container.
     *
     * @return The number of tasks in the container.
     */
    public int size();

    /**
     * Returns a copy of the task container.
     *
     * @return A copy of the task container.
     */
    public TaskContainer copy();
}
