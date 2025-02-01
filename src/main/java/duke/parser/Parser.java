package duke.parser;

import duke.command.AddDeadlineCommand;
import duke.command.AddEventCommand;
import duke.command.AddTodoCommand;
import duke.command.ByeCommand;
import duke.command.Command;
import duke.command.DeleteCommand;
import duke.command.ListCommand;
import duke.command.MarkCommand;
import duke.command.UnmarkCommand;
import duke.exception.ParseCommandException;

/**
 * A utility class responsible for parsing user input into corresponding {@link Command} objects.
 * The input string is parsed to identify the type of command, which is then mapped to a specific
 * command implementation.
 */
public class Parser {

    /**
     * Parses the given input string into a corresponding {@link Command} object.
     * The method splits the input string to identify the command type, and based on that, delegates the parsing to
     * the appropriate command class. If the command is unknown or invalid, a {@link ParseCommandException} is thrown.
     *
     * @param input The input string from the user.
     * @return A {@link Command} object corresponding to the parsed command.
     * @throws ParseCommandException If the input does not match a known command type.
     */
    public static Command parseCommand(String input) throws ParseCommandException {
        String[] parts = input.split("\\s+");
        String command = parts[0].toUpperCase();

        Command.Type commandType;

        try {
            commandType = Command.Type.valueOf(command);
        } catch (IllegalArgumentException e) {
            throw new ParseCommandException(String.format("Unknown command [%s]", parts[0]));
        }

        return switch (commandType) {
        case DEADLINE -> AddDeadlineCommand.parse(input);
        case DELETE -> DeleteCommand.parse(input);
        case EVENT -> AddEventCommand.parse(input);
        case LIST -> ListCommand.parse(input);
        case MARK -> MarkCommand.parse(input);
        case TODO -> AddTodoCommand.parse(input);
        case UNMARK -> UnmarkCommand.parse(input);
        case BYE -> ByeCommand.parse(input);
        };
    }
}
