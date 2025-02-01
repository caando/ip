package duke.exception;

/**
 * Exception thrown when a task is not found.
 * <p>
 * This exception is thrown when an operation is attempted on a task that does not exist, such as
 * when trying to delete or access a task by an invalid index.
 */
public class TaskNotFoundException extends Exception {

    /**
     * Constructs a new {@code TaskNotFoundException} with the specified detail message.
     *
     * @param message The detail message explaining the reason for the exception.
     */
    public TaskNotFoundException(String message) {
        super(message);
    }
}
