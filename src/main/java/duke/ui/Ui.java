package duke.ui;

import java.util.List;

/**
 * Represents the user interface for interacting with the application.
 * This interface defines methods for receiving user input, displaying output,
 * showing error messages, and closing the interface. Implementing classes
 * will handle the actual interaction with the user (e.g., via the command line).
 */
public interface Ui {

    /**
     * Retrieves a line of input from the user.
     *
     * @return The input string provided by the user.
     */
    public String getInput();

    /**
     * Displays a list of strings as output to the user.
     *
     * @param lines The list of strings to be displayed.
     */
    public void showOutput(List<String> lines);

    /**
     * Displays one or more strings as output to the user.
     *
     * @param lines The strings to be displayed.
     */
    public void showOutput(String... lines);

    /**
     * Displays a list of error messages to the user, usually prefixed with "OOPS!!!".
     *
     * @param lines The list of error messages to be displayed.
     */
    public void showError(List<String> lines);

    /**
     * Displays one or more error messages to the user, usually prefixed with "OOPS!!!".
     *
     * @param lines The error messages to be displayed.
     */
    public void showError(String... lines);

    /**
     * Closes the user interface and displays a goodbye message.
     */
    public void close();
}
