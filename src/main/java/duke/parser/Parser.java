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

public class Parser {

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
