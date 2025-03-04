package duke.task;

import duke.exception.InvalidStatusIconException;
import duke.exception.ParseTaskException;

/**
 * Represents a Todo task in the task management system.
 * <p>
 * A Todo task is a basic task that has a description and a completion status.
 * The task can be parsed from a string representation in PSV (Pipe-Separated Values) format
 * and can also be converted to such a format.
 */
public class Todo extends Task {

    /**
     * Constructs a new Todo task with the specified description.
     *
     * @param description The description of the task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Constructs a new Todo task from the given Todo task.
     *
     * @param todo The Todo task to be copied.
     */
    public Todo(Todo todo) {
        super(todo);
    }

    /**
     * Creates a Todo task from a PSV string.
     * <p>
     * The PSV string is expected to have the format: "TaskType | StatusIcon | Description".
     *
     * @param input The PSV string representing the task.
     * @return A Todo task created from the provided PSV string.
     * @throws ParseTaskException If the input string is not in the expected format or contains invalid data.
     */
    public static Task fromPsvString(String input) throws ParseTaskException {
        assert input != null : "input must not be null";
        assert input.startsWith(input) : "Todo PSV string must start with 'T'";

        String[] parts = input.split("\\|", 3);

        if (parts.length != 3) {
            throw new ParseTaskException(String.format(
                    "Todo PSV string [%s] have invalid number of columns", input));
        }

        String statusIconString = parts[1].trim();
        String description = parts[2].trim();

        Todo todo = new Todo(description);

        try {
            todo.markFromStatusIcon(statusIconString);
        } catch (InvalidStatusIconException e) {
            throw new ParseTaskException(String.format("Invalid status icon [%s] for todo task", statusIconString));
        }

        return todo;
    }

    /**
     * Converts this Todo task to a PSV string.
     * <p>
     * The format of the PSV string is: "TaskType | StatusIcon | Description".
     *
     * @return A PSV string representing this Todo task.
     */
    @Override
    public String toPsvString() {
        return String.format("%s | %s | %s", getTaskIcon(), getStatusIcon(), this.description);
    }

    /**
     * Returns the icon representing the task type.
     * <p>
     * For Todo tasks, the icon is always "T".
     *
     * @return The task type icon as a string.
     */
    @Override
    public String getTaskIcon() {
        return Task.Type.T.name();
    }

    /**
     * Returns a string representation of the Todo task.
     * <p>
     * The string representation includes the task type icon, the status icon, and the description.
     *
     * @return A string representation of the Todo task.
     */
    @Override
    public String toString() {
        return String.format("[%s][%s] %s", getTaskIcon(), getStatusIcon(), description);
    }

    /**
     * Returns a copy of this Todo task.
     *
     * @return A new Task object with the same properties as this task.
     */
    @Override
    public Todo copy() {
        return new Todo(this);
    }
}
