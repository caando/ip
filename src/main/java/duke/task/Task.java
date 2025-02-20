package duke.task;

import duke.exception.InvalidStatusIconException;
import duke.exception.ParseTaskException;

/**
 * Represents an abstract task in the task management system.
 * <p>
 * A task has a description, a completion status, and a task type (Deadline, Event, or Todo).
 * Subclasses of this class represent specific types of tasks such as Deadline, Event, and Todo.
 */
public abstract class Task {

    /**
     * Enum representing the possible types of tasks.
     * <p>
     * D = Deadline, E = Event, T = Todo.
     */
    public enum Type {
        /**  Deadline **/
        D,

        /** Event **/
        E,

        /** Todo **/
        T
    }

    /** Icon to represent task is done **/
    public static final String DONE_ICON = "X";

    /** Icon to represent task is not done **/
    public static final String NOT_DONE_ICON = "_";

    /** Description of the task **/
    protected String description;

    /** Completion status of the task **/
    protected boolean isDone; // Completion status of the task

    /**
     * Constructs a new Task with the given description.
     * <p>
     * By default, the task is marked as not done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Constructs a new task from the given task.
     *
     * @param task The task to be copied.
     */
    public Task(Task task) {
        this.description = task.description;
        this.isDone = task.isDone;
    }

    /**
     * Returns the status icon of the task.
     * <p>
     * The icon is "X" if the task is done, otherwise it is "_".
     *
     * @return The status icon of the task.
     */
    public String getStatusIcon() {
        return (isDone ? DONE_ICON : NOT_DONE_ICON);
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsNotDone() {
        isDone = false;
    }

    /**
     * Gets the description of the task.
     *
     * @return The description of the task.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the completion status of the task based on the given status icon.
     * <p>
     * Valid status icons are:
     * <ul>
     * <li>"_" for not done tasks</li>
     * <li>"X" for done tasks</li>
     * </ul>
     * If an invalid status icon is provided, an {@link InvalidStatusIconException} is thrown.
     *
     * @param statusIcon The status icon representing the task's completion status.
     * @throws InvalidStatusIconException If the status icon is invalid.
     */
    protected void markFromStatusIcon(String statusIcon) throws InvalidStatusIconException {
        switch (statusIcon) {
        case NOT_DONE_ICON -> markAsNotDone();
        case DONE_ICON -> markAsDone();
        default -> throw new InvalidStatusIconException(
                String.format("Invalid status icon [%s]", statusIcon), statusIcon);
        }
    }

    /**
     * Converts the task to a PSV (Pipe-Separated Values) string representation.
     * <p>
     * The exact format depends on the specific task subclass (e.g., Deadline, Event, Todo).
     *
     * @return A PSV string representing the task.
     */
    public abstract String toPsvString();

    /**
     * Creates a Task from a PSV string representation.
     * <p>
     * The input string should have the format: "TaskType | StatusIcon | Description".
     * The task type is inferred from the first part of the string.
     *
     * @param input The PSV string representation of the task.
     * @return A Task object created from the given PSV string.
     * @throws ParseTaskException If the PSV string is not in the expected format or cannot be parsed.
     */
    public static Task fromPsvString(String input) throws ParseTaskException {
        assert input != null : "input must not be null";

        String[] parts = input.split("\\|", 2);

        Task.Type taskType;
        try {
            taskType = Type.valueOf(parts[0].trim());
        } catch (IllegalArgumentException e) {
            throw new ParseTaskException(String.format(
                    "Unable to parse task type from [%s]", input));
        }

        return switch (taskType) {
        case D -> Deadline.fromPsvString(input);
        case E -> Event.fromPsvString(input);
        case T -> Todo.fromPsvString(input);
        };
    }

    /**
     * Returns the task icon representing the type of the task.
     * <p>
     * Each subclass (e.g., Deadline, Event, Todo) will provide its own implementation.
     *
     * @return The task type icon as a string.
     */
    public abstract String getTaskIcon();

    /**
     * Returns a string representation of the task.
     * <p>
     * Each subclass (e.g., Deadline, Event, Todo) will provide its own implementation of this method.
     *
     * @return A string representation of the task.
     */
    @Override
    public abstract String toString();

    /**
     * Creates a copy of the task.
     *
     * @return A new Task object with the same properties as this task.
     */
    public abstract Task copy();
}
