package duke.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a Command Line Interface (CLI) for the application.
 * This class interacts with the user through the terminal/command line.
 * It handles taking user input and displaying output or error messages.
 *
 * The CLI prompts the user, processes input, and displays task-related outputs
 * or errors. It also provides methods to display formatted messages and a
 * method to close the interface.
 */
public class Cli implements Ui {

    private final Scanner scanner;

    /**
     * Constructs a new instance of the CLI, initializes the scanner for input,
     * and displays a welcome message to the user.
     */
    public Cli() {
        scanner = new Scanner(System.in);
        showLineSeparator();
        showLine("Hello! I'm Mr Meeseeks");
        showLine("What can I do for you?");
        showLineSeparator();
    }

    /**
     * Displays a separator line for visual clarity in the terminal output.
     */
    private void showLineSeparator() {
        System.out.println("   _____________________________________________________________________________");
    }

    /**
     * Displays a single line of text with indentation for formatting.
     *
     * @param line The text to be displayed.
     */
    private void showLine(String line) {
        System.out.println(String.format("    %s", line));
    }

    /**
     * Reads a line of input from the user, trims any surrounding whitespace,
     * and returns the input string.
     *
     * @return The trimmed input string from the user.
     */
    @Override
    public String getInput() {
        String input = scanner.nextLine().trim();
        showLineSeparator();
        return input;
    }

    /**
     * Displays a list of strings as output in the terminal, each on a new line.
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
     * Each string is displayed on a new line with a separator line at the end.
     *
     * @param lines The strings to be displayed.
     */
    @Override
    public void showOutput(String... lines) {
        showOutput(new ArrayList<>(Arrays.asList(lines)));
    }

    /**
     * Displays a list of error messages, each prefixed with "OOPS!!!",
     * with a separator line at the end.
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
     * Displays one or more error messages, each prefixed with "OOPS!!!",
     * with a separator line at the end.
     *
     * @param lines The error messages to be displayed.
     */
    @Override
    public void showError(String... lines) {
        showError(new ArrayList<>(Arrays.asList(lines)));
    }

    /**
     * Displays a goodbye message and a separator line before closing the interface.
     */
    @Override
    public void close() {
        showLine("Bye. Hope to see you again soon!");
        showLineSeparator();
    }
}
