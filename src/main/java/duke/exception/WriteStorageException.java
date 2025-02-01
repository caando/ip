package duke.exception;

/**
 * Exception thrown when there is an error writing to storage.
 * <p>
 * This exception is thrown when the application is unable to write the task data to the storage
 * system, such as when saving to a file or database.
 */
public class WriteStorageException extends Exception {

    /**
     * Constructs a new {@code WriteStorageException} with the specified detail message.
     *
     * @param message The detail message explaining the reason for the exception.
     */
    public WriteStorageException(String message) {
        super(message);
    }
}
