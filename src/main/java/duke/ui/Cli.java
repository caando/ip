package duke.ui;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a Command Line Interface (CLI) for the application.
 * <p>
 * This class interacts with the user through the terminal/command line.
 * It handles taking user input and displaying output or error messages.
 * <p>
 * The CLI prompts the user, processes input, and displays task-related outputs
 * or errors. It also provides methods to display formatted messages and a
 * method to close the interface.
 */
public class Cli implements Ui {

    private final Scanner scanner;
    private final PrintStream printStream;
    private boolean isOpen;


    /**
     * Constructs a new instance of the CLI.
     * <p>
     * Initializes the scanner for input, and displays a welcome message to the user.
     *
     * @param inputStream The input stream to read user input from.
     * @param printStream The print stream to display output to the user.
     */
    public Cli(InputStream inputStream, PrintStream printStream) {
        scanner = new Scanner(inputStream);
        this.printStream = printStream;
        isOpen = true;
    }

    /**
     * Displays a separator line for visual clarity in the terminal output.
     */
    private void showLineSeparator() {
        printStream.println("   _____________________________________________________________________________");
    }

    /**
     * Displays a single line of text with indentation for formatting.
     *
     * @param line The text to be displayed.
     */
    private void showLine(String line) {
        printStream.println(String.format("    %s", line));
    }

    /**
     * Reads a line of input from the user.
     * <p>
     * Trims any surrounding whitespace, and returns the input string.
     *
     * @return The trimmed input string from the user.
     */
    public String getInput() {
        String input = scanner.nextLine().trim();
        showLineSeparator();
        return input;
    }

    /**
     * Displays a list of strings as output in the terminal, each on a new line.
     * <p>
     * A separator line is added at the end for formatting.
     *
     * @param lines The list of lines to be displayed.
     */
    @Override
    public void showOutput(List<String> lines) {
        for (String line : lines) {
            showLine(line);
        }
        showLineSeparator();
    }

    /**
     * Displays one or more strings as output in the terminal.
     * <p>
     * Each string is displayed on a new line with a separator line at the end.
     *
     * @param lines The strings to be displayed.
     */
    @Override
    public void showOutput(String... lines) {
        showOutput(new ArrayList<>(Arrays.asList(lines)));
    }

    /**
     * Displays a list of error messages.
     * <p>
     * Messages are prefixed with "OOPS!!!", with a separator line at the end.
     *
     * @param lines The list of error messages to be displayed.
     */
    @Override
    public void showError(List<String> lines) {
        for (String line : lines) {
            showLine(String.format("OOPS!!! %s", line));
        }
        showLineSeparator();
    }

    /**
     * Displays one or more error messages.
     * <p>
     * Messages are prefixed with "OOPS!!!", with a separator line at the end.
     *
     * @param lines The error messages to be displayed.
     */
    @Override
    public void showError(String... lines) {
        showError(new ArrayList<>(Arrays.asList(lines)));
    }

    /**
     * Displays a greeting message to the user when the program starts.
     * <p>
     * The greeting provides a welcome message and asks the user for input.
     */
    @Override
    public void start() {
        showLineSeparator();
        showLine("Hello! I'm Mr Meeseeks");
        showLine("What can I do for you?");
        showLineSeparator();
    }

    /**
     * Displays a goodbye message and a separator line before closing the interface.
     */
    @Override
    public void close() {
        assert isOpen() : "Cli is not open";

        showLine("Bye. Hope to see you again soon!");
        showLineSeparator();
        isOpen = false;
        try {
            scanner.close();
        } catch (IllegalStateException e) {
            showError(String.format("Error closing scanner [%s], something is wrong.", e.getMessage()));
        }
    }

    /**
     * Checks if the CLI is open and ready to receive input.
     *
     * @return true if the CLI is open, false otherwise.
     */
    public boolean isOpen() {
        return isOpen;
    }
}
