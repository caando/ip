import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

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
import duke.parser.Parser;

public class ParserTest {

    @Test
    public void testParseTodoCommand() throws ParseCommandException {
        // Prepare the input string
        String input = "todo Buy groceries";

        // Act
        Command result = Parser.parseCommand(input);

        // Assert
        assertTrue(result instanceof AddTodoCommand);
    }

    @Test
    public void testParseDeadlineCommand() throws ParseCommandException {
        // Prepare the input string
        String input = "deadline Finish homework /by 2025-02-01";

        // Act
        Command result = Parser.parseCommand(input);

        // Assert
        assertTrue(result instanceof AddDeadlineCommand);
    }

    @Test
    public void testParseEventCommand() throws ParseCommandException {
        // Prepare the input string
        String input = "event Attend meeting /from 2025-02-01 /to 2025-02-02";

        // Act
        Command result = Parser.parseCommand(input);

        // Assert
        assertTrue(result instanceof AddEventCommand);
    }

    @Test
    public void testParseListCommand() throws ParseCommandException {
        // Prepare the input string
        String input = "list";

        // Act
        Command result = Parser.parseCommand(input);

        // Assert
        assertTrue(result instanceof ListCommand);
    }

    @Test
    public void testParseDeleteCommand() throws ParseCommandException {
        // Prepare the input string
        String input = "delete 1";

        // Act
        Command result = Parser.parseCommand(input);

        // Assert
        assertTrue(result instanceof DeleteCommand);
    }

    @Test
    public void testParseMarkCommand() throws ParseCommandException {
        // Prepare the input string
        String input = "mark 1";

        // Act
        Command result = Parser.parseCommand(input);

        // Assert
        assertTrue(result instanceof MarkCommand);
    }

    @Test
    public void testParseUnmarkCommand() throws ParseCommandException {
        // Prepare the input string
        String input = "unmark 1";

        // Act
        Command result = Parser.parseCommand(input);

        // Assert
        assertTrue(result instanceof UnmarkCommand);
    }

    @Test
    public void testParseByeCommand() throws ParseCommandException {
        // Prepare the input string
        String input = "bye";

        // Act
        Command result = Parser.parseCommand(input);

        // Assert
        assertTrue(result instanceof ByeCommand);
    }

    @Test
    public void testParseUnknownCommand() {
        // Prepare the input string with an unknown command
        String input = "unknowncommand something";

        // Act & Assert
        ParseCommandException exception = assertThrows(ParseCommandException.class, () -> {
            Parser.parseCommand(input);
        });
        assertEquals("Unknown command [unknowncommand]", exception.getMessage());
    }
}
