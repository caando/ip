import java.util.Scanner;

import command.AddDeadlineCommand;
import command.AddEventCommand;
import command.AddTodoCommand;
import command.Command;
import command.InvalidCommand;
import command.ListCommand;
import command.MarkCommand;
import command.UnmarkCommand;
import command.DeleteCommand;
import duke.DukeException;
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
        case "list" -> ListCommand.parse(parts);
        case "mark" -> MarkCommand.parse(parts);
        case "unmark" -> UnmarkCommand.parse(parts);
        case "todo" -> AddTodoCommand.parse(parts);
        case "deadline" -> AddDeadlineCommand.parse(parts);
        case "event" -> AddEventCommand.parse(parts);
        case "delete" -> DeleteCommand.parse(parts);
        default -> new InvalidCommand(new DukeException("     OOPS!!! I'm sorry, but I don't know what that means :-("));
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
