package duke.exception;

/**
 * Exception thrown when an invalid status icon is encountered.
 * <p>
 * This exception is thrown when a status icon provided in the context of a task is not recognized
 * or is invalid. It holds the invalid status icon as additional information that can be retrieved
 * for debugging or logging purposes.
 */
public class InvalidStatusIconException extends Exception {

    private final String statusIcon;

    /**
     * Constructs a new {@code InvalidStatusIconException} with the specified detail message and invalid status icon.
     *
     * @param message The detail message explaining the reason for the exception.
     * @param statusIcon The invalid status icon that caused the exception.
     */
    public InvalidStatusIconException(String message, String statusIcon) {
        super(message);
        this.statusIcon = statusIcon;
    }

    /**
     * Retrieves the invalid status icon that caused the exception.
     *
     * @return The invalid status icon.
     */
    public String getStatusIcon() {
        return statusIcon;
    }
}
