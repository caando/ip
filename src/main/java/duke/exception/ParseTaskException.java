package duke.exception;

/**
 * Exception thrown when there is an error in parsing a task.
 * <p>
 * This exception is thrown when a task cannot be parsed properly, often due to invalid formatting
 * or data related to the task.
 */
public class ParseTaskException extends Exception {

    /**
     * Constructs a new {@code ParseTaskException} with the specified detail message.
     *
     * @param message The detail message explaining the reason for the exception.
     */
    public ParseTaskException(String message) {
        super(message);
    }
}
