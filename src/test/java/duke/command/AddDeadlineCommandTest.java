import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import duke.command.AddDeadlineCommand;
import duke.exception.ParseCommandException;
import duke.storage.Storage;
import duke.task.Deadline;
import duke.task.TaskContainer;
import duke.ui.Ui;

public class AddDeadlineCommandTest {

    // Test valid command parsing
    @Test
    public void testParse_validInput_createsAddDeadlineCommand() throws ParseCommandException {
        // Given a valid input string
        String input = "deadline Finish homework /by 2025-02-01";

        // When parsing the command
        AddDeadlineCommand command = (AddDeadlineCommand) AddDeadlineCommand.parse(input);

        // Then it should correctly parse the description and date
        assertNotNull(command);
        assertEquals("Finish homework", command.getTaskDescription());
        assertEquals(LocalDate.of(2025, 2, 1), command.getDate());
    }

    // Test invalid command: missing description
    @Test
    public void testParse_missingDescription_throwsParseCommandException() {
        // Given an input string with no description
        String input = "deadline /by 2025-02-01";

        // When parsing the command, it should throw a ParseCommandException
        ParseCommandException exception = assertThrows(ParseCommandException.class, () -> {
            AddDeadlineCommand.parse(input);
        });

        // Then the exception message should indicate the missing description
        assertEquals("Deadline command requires a description.", exception.getMessage());
    }

    // Test invalid command: missing date
    @Test
    public void testParse_missingDate_throwsParseCommandException() {
        // Given an input string with no date
        String input = "deadline Finish homework /by";

        // When parsing the command, it should throw a ParseCommandException
        ParseCommandException exception = assertThrows(ParseCommandException.class, () -> {
            AddDeadlineCommand.parse(input);
        });

        // Then the exception message should indicate the missing date
        assertEquals("Deadline command requires [/by] argument.", exception.getMessage());
    }

    // Test invalid command: invalid date format
    @Test
    public void testParse_invalidDate_throwsParseCommandException() {
        // Given an input string with an invalid date format
        String input = "deadline Finish homework /by invalid-date";

        // When parsing the command, it should throw a ParseCommandException
        ParseCommandException exception = assertThrows(ParseCommandException.class, () -> {
            AddDeadlineCommand.parse(input);
        });

        // Then the exception message should indicate invalid date format
        assertTrue(exception.getMessage().contains("Unable to parse"));
    }

    // Test invalid command format
    @Test
    public void testParse_invalidFormat_throwsParseCommandException() {
        // Given an input string that doesn't follow the expected format
        String input = "finish homework /by 2025-02-01";

        // When parsing the command, it should throw a ParseCommandException
        ParseCommandException exception = assertThrows(ParseCommandException.class, () -> {
            AddDeadlineCommand.parse(input);
        });

        // Then the exception message should indicate an invalid command format
        assertTrue(exception.getMessage().contains("Unable to parse"));
    }

    // Test execute method (mocking the dependencies)
    @Test
    public void testExecute_addsTaskToContainerAndShowsOutput() throws ParseCommandException {
        // Given a valid AddDeadlineCommand
        String input = "deadline Finish homework /by 2025-02-01";
        AddDeadlineCommand command = (AddDeadlineCommand) AddDeadlineCommand.parse(input);

        // Mocking the dependencies
        TaskContainer taskContainer = mock(TaskContainer.class);
        Storage storage = mock(Storage.class);
        Ui ui = mock(Ui.class);

        // When executing the command
        command.execute(taskContainer, storage, ui);

        // Then the task should be added to the container, and UI should show output
        verify(taskContainer).add(any(Deadline.class));
        verify(ui).showOutput(eq("Got it. I've added this task:"),
                eq("Deadline: Finish homework (by: 2025-02-01)"),
                anyString()); // verify task count message
    }

    // Test getDate method
    @Test
    public void testGetDate_returnsCorrectDate() throws ParseCommandException {
        // Given a valid input string
        String input = "deadline Finish homework /by 2025-02-01";

        // When parsing the command
        AddDeadlineCommand command = (AddDeadlineCommand) AddDeadlineCommand.parse(input);

        // Then the getDate method should return the correct date
        assertEquals(LocalDate.of(2025, 2, 1), command.getDate());
    }

    // Test getTaskDescription method
    @Test
    public void testGetTaskDescription_returnsCorrectDescription() throws ParseCommandException {
        // Given a valid input string
        String input = "deadline Finish homework /by 2025-02-01";

        // When parsing the command
        AddDeadlineCommand command = (AddDeadlineCommand) AddDeadlineCommand.parse(input);

        // Then the getTaskDescription method should return the correct description
        assertEquals("Finish homework", command.getTaskDescription());
    }
}
