import java.util.Scanner;

import command.AddCommand;
import command.AddDeadlineCommand;
import command.AddEventCommand;
import command.AddTodoCommand;
import command.Command;
import command.ListCommand;
import command.MarkCommand;
import command.UnmarkCommand;
import task.TaskList;

public class Duke {

    private static final String LINE_SEPARATOR = "____________________________________________________________";

    private static void start() {
        System.out.println("    " + LINE_SEPARATOR);
    }

    private static void greet() {
        System.out.println("    Hello! I'm Mr Meeseeks");
        System.out.println("    What can I do for you?");
        System.out.println("    " + LINE_SEPARATOR);
    }

    private static void bye() {
        System.out.println("    Bye. Hope to see you again soon!");
        System.out.println("    " + LINE_SEPARATOR);
    }

    private static void processCommands(TaskList taskList) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String input = scanner.nextLine().trim();
                System.out.println("    " + LINE_SEPARATOR);

                if (input.equals("bye")) {
                    break;
                }

                Command command = parseCommand(input);
                command.execute(taskList);
            }
        }
    }

    private static Command parseCommand(String input) {
        String[] parts = input.split(" ", 2);
        String command = parts[0].toLowerCase();

        return switch (command) {
        case "list" -> new ListCommand();
        case "mark" -> MarkCommand.parse(parts);
        case "unmark" -> UnmarkCommand.parse(parts);
        case "todo" -> new AddTodoCommand(parts[1]);
        case "deadline" -> {
            String[] deadlineParts = parts[1].split("/by", 2);
            yield new AddDeadlineCommand(deadlineParts[0].trim(), deadlineParts[1].trim());
        }
        case "event" -> {
            String[] eventParts = parts[1].split("/from", 2);
            String[] toParts = eventParts[1].split("/to", 2);
            yield new AddEventCommand(eventParts[0].trim(), toParts[0].trim(), toParts[1].trim());
        }
        default -> AddCommand.parse(input);
        };
    }

    public static void main(String[] args) {
        start();
        greet();
        TaskList taskList = new TaskList();
        processCommands(taskList);
        bye();
    }
}
