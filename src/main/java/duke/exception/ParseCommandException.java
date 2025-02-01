package duke.exception;

/**
 * Exception thrown when there is an error in parsing a command.
 * <p>
 * This exception is thrown when the input command cannot be parsed properly, typically due to an
 * invalid or unknown command format.
 */
public class ParseCommandException extends Exception {

    /**
     * Constructs a new {@code ParseCommandException} with the specified detail message.
     *
     * @param message The detail message explaining the reason for the exception.
     */
    public ParseCommandException(String message) {
        super(message);
    }
}
