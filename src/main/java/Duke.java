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

    private static void echo(String message) {
        output(" " + message);
        output(LINE_SEPARATOR);
    }

    private static void receiveCommand() {
        String command;
        try (Scanner scanner = new Scanner(System.in)) {
            command = scanner.nextLine();
            output(LINE_SEPARATOR);
            executeCommand(command);
        }
    }

    private static void executeCommand(String command) {
        switch (command) {
        case "bye" -> {
        }
        default -> {
            echo(command);
            receiveCommand();
        }
        }
    }

    public static void main(String[] args) {
        start();
        greet();
        receiveCommand();
        bye();
    }
}
