import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The Duke class represents a java simple application that will progressively
 * gain more features
 * through the course of the ip project.
 */
public class Duke {

    private static final String OUTPUT_PADDING = "    ";
    private static final String LINE_SEPARATOR = "____________________________________________________________";

    private static void output(String output) {
        System.out.println(OUTPUT_PADDING + output);
    }

    private static void start() {
        output(LINE_SEPARATOR);
    }

    private static void greet() {
        output(" Hello! I'm Mr Meeseeks");
        output(" What can I do for you?");
        output(LINE_SEPARATOR);
    }

    private static void bye() {
        output(" Bye. Hope to see you again soon!");
        output(LINE_SEPARATOR);
    }
    /**
     * Adds a new task to the provided list of records and displays a confirmation message.
     * The message is printed in the format: "added: message", followed by a line separator.
     *
     * @param records A list of strings representing tasks. The new task (message) will be added to this list.
     * @param message The task to be added to the list of records.
     */
    private static void add(List<String> records, String message) {
        records.add(message);
        output(String.format(" added: %s", message));
        output(LINE_SEPARATOR);
    }

    /**
     * Displays a numbered list of tasks from the provided list of records.
     * The list is formatted with each task preceded by its corresponding number.
     *
     * @param records A list of strings representing tasks to be displayed.
     *                Each string in the list will be printed with a number in the format "1. task",
     *                "2. task", etc.
     */
    public static void list(List<String> records) {
        for (int i = 0; i < records.size(); i++) {
            output(String.format(" %d. %s", i + 1, records.get(i)));
        }
        output(LINE_SEPARATOR);
    }

    private static void receiveCommand(List<String> records) {
        String command;
        try (Scanner scanner = new Scanner(System.in)) {
            command = scanner.nextLine();
            output(LINE_SEPARATOR);
            executeCommand(records, command);
        }
    }

    private static void executeCommand(List<String> records, String command) {
        switch (command) {
        case "bye" -> {
        }
        case "list" -> {
            list(records);
            receiveCommand(records);
        }
        default -> {
            add(records, command);
            receiveCommand(records);
        }
        }
    }

    public static void main(String[] args) {
        start();
        greet();
        ArrayList<String> records = new ArrayList<>();
        receiveCommand(records);
        bye();
    }
}
