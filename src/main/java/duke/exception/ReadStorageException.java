package duke.exception;

/**
 * Exception thrown when there is an error reading from storage.
 * <p>
 * This exception is thrown when the application is unable to read the task data from the storage
 * system, such as from a file or database.
 */
public class ReadStorageException extends Exception {

    /**
     * Constructs a new {@code ReadStorageException} with the specified detail message.
     *
     * @param message The detail message explaining the reason for the exception.
     */
    public ReadStorageException(String message) {
        super(message);
    }
}
