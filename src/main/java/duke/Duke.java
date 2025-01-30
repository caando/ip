package duke;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import command.AddDeadlineCommand;
import command.AddEventCommand;
import command.AddTodoCommand;
import command.Command;
import command.CommandType;
import command.DeleteCommand;
import command.InvalidCommand;
import command.ListCommand;
import command.MarkCommand;
import command.UnmarkCommand;
import exception.DukeException;
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

    private static void processCommands(TaskList taskList, File saveFile) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String input = scanner.nextLine().trim();
                System.out.println("    " + LINE_SEPARATOR);

                if (input.equals("bye")) {
                    break;
                }

                Command command = parseCommand(input);
                command.execute(taskList);

                try {
                    if (!saveFile.exists()) {
                        saveFile.getParentFile().mkdirs();
                        saveFile.createNewFile();
                    }

                    BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));
                    taskList.writePsvString(writer);
                    writer.flush();
                } catch (IOException e) {
                    System.err.println("Error writing tasklist to PSV: " + e.getMessage());
                }
            }
        }
    }

    private static Command parseCommand(String input) {
        String[] parts = input.split(" ", 2);
        String command = parts[0].toUpperCase();

        CommandType commandType;

        try {
            commandType = CommandType.valueOf(command); // Use enum to match the command
        } catch (IllegalArgumentException e) {
            // If the command is invalid, set it to ADD as default
            commandType = CommandType.INVALID;
        }

        return switch (commandType) {
        case DEADLINE -> AddDeadlineCommand.parse(parts);
        case DELETE -> DeleteCommand.parse(parts);
        case EVENT -> AddEventCommand.parse(parts);
        case LIST -> ListCommand.parse(parts);
        case MARK -> MarkCommand.parse(parts);
        case TODO -> AddTodoCommand.parse(parts);
        case UNMARK -> UnmarkCommand.parse(parts);
        case INVALID -> new InvalidCommand(new DukeException("     OOPS!!! I'm sorry, but I don't know what that means :-("));
        };
    }

    public static void main(String[] args) {
        start();
        greet();
        TaskList taskList = new TaskList();
        File saveFile = new File("./data/duke.txt");
        processCommands(taskList, saveFile);
        bye();
    }
}
