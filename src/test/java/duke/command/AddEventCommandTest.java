package duke.command;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import duke.State;
import duke.exception.ParseCommandException;
import duke.storage.Storage;
import duke.task.Event;
import duke.task.TaskContainer;
import duke.ui.Ui;

/**
 * Unit tests for AddEventCommand.
 */
public class AddEventCommandTest {

    @Test
    public void testParse_validInput_createsAddEventCommand() throws ParseCommandException {
        // Prepare the input string
        String input = "event Conference /from 2025-02-01 /to 2025-02-03";

        // Act
        AddEventCommand command = (AddEventCommand) AddEventCommand.parse(input);

        // Assert
        Assertions.assertNotNull(command);
        Assertions.assertEquals("Conference", command.getTaskDescription());
        Assertions.assertEquals(LocalDate.of(2025, 2, 1), command.getFrom());
        Assertions.assertEquals(LocalDate.of(2025, 2, 3), command.getTo());
    }

    @Test
    public void testParse_missingDescription_throwsParseCommandException() {
        // Prepare the input string
        String input = "event /from 2025-02-01 /to 2025-02-03";

        // Act
        ParseCommandException exception = Assertions.assertThrows(ParseCommandException.class, () -> {
            AddEventCommand.parse(input);
        });

        // Assert
        Assertions.assertEquals("Unable to parse [event /from 2025-02-01 /to 2025-02-03] to event command.",
                exception.getMessage());
    }

    @Test
    public void testParse_missingFromDate_throwsParseCommandException() {
        // Prepare the input string
        String input = "event Meeting /from /to 2025-02-03";

        // Act
        ParseCommandException exception = Assertions.assertThrows(ParseCommandException.class, () -> {
            AddEventCommand.parse(input);
        });

        // Assert
        Assertions.assertEquals("Unable to parse [event Meeting /from /to 2025-02-03] to event command.",
                exception.getMessage());
    }

    @Test
    public void testParse_missingToDate_throwsParseCommandException() {
        // Prepare the input string
        String input = "event Workshop /from 2025-02-01 /to";

        // Act
        ParseCommandException exception = Assertions.assertThrows(ParseCommandException.class, () -> {
            AddEventCommand.parse(input);
        });

        // Assert
        Assertions.assertEquals("Unable to parse [event Workshop /from 2025-02-01 /to] to event command.",
                exception.getMessage());
    }

    @Test
    public void testParse_invalidDateFormat_throwsParseCommandException() {
        // Prepare the input string
        String input = "event Party /from invalid-date /to 2025-02-03";

        // Act
        ParseCommandException exception = Assertions.assertThrows(ParseCommandException.class, () -> {
            AddEventCommand.parse(input);
        });

        // Assert
        Assertions.assertTrue(exception.getMessage().contains("Unable to parse"));
    }

    @Test
    public void testExecute_addsTaskToContainerAndShowsOutput() throws ParseCommandException {
        // Prepare the input string and command
        String input = "event Conference /from 2025-02-01 /to 2025-02-03";
        AddEventCommand command = (AddEventCommand) AddEventCommand.parse(input);

        // Mocking dependencies
        TaskContainer taskContainer = Mockito.mock(TaskContainer.class);
        Storage storage = Mockito.mock(Storage.class);
        Ui ui = Mockito.mock(Ui.class);
        State state = new State(taskContainer, storage, ui, null);
        Mockito.when(taskContainer.copy()).thenReturn(taskContainer);

        // Act
        command.execute(state);

        // Assert
        Mockito.verify(taskContainer).add(ArgumentMatchers.any(Event.class));
        Mockito.verify(ui).showOutput(ArgumentMatchers.eq("Got it. I've added this task:"),
                ArgumentMatchers.eq("[E][_] Conference (from: Feb 1 2025 to: Feb 3 2025)"),
                ArgumentMatchers.anyString());
    }

    @Test
    public void testGetTaskDescription_returnsCorrectDescription() throws ParseCommandException {
        // Prepare the input string
        String input = "event Workshop /from 2025-02-01 /to 2025-02-03";

        // Act
        AddEventCommand command = (AddEventCommand) AddEventCommand.parse(input);

        // Assert
        Assertions.assertEquals("Workshop", command.getTaskDescription());
    }

    @Test
    public void testGetFromDate_returnsCorrectDate() throws ParseCommandException {
        // Prepare the input string
        String input = "event Party /from 2025-02-01 /to 2025-02-03";

        // Act
        AddEventCommand command = (AddEventCommand) AddEventCommand.parse(input);

        // Assert
        Assertions.assertEquals(LocalDate.of(2025, 2, 1), command.getFrom());
    }

    @Test
    public void testGetToDate_returnsCorrectDate() throws ParseCommandException {
        // Prepare the input string
        String input = "event Party /from 2025-02-01 /to 2025-02-03";

        // Act
        AddEventCommand command = (AddEventCommand) AddEventCommand.parse(input);

        // Assert
        Assertions.assertEquals(LocalDate.of(2025, 2, 3), command.getTo());
    }
}
