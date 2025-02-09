package duke.command;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import duke.State;
import duke.exception.ParseCommandException;
import duke.storage.Storage;
import duke.task.Deadline;
import duke.task.TaskContainer;
import duke.ui.Ui;

public class AddDeadlineCommandTest {

    @Test
    public void testParse_validInput_createsAddDeadlineCommand() throws ParseCommandException {
        // Prepare the input string
        String input = "deadline Finish homework /by 2025-02-01";

        // Act
        AddDeadlineCommand command = (AddDeadlineCommand) AddDeadlineCommand.parse(input);

        // Assert
        Assertions.assertNotNull(command);
        Assertions.assertEquals("Finish homework", command.getTaskDescription());
        Assertions.assertEquals(LocalDate.of(2025, 2, 1), command.getDate());
    }

    @Test
    public void testParse_missingDescription_throwsParseCommandException() {
        // Prepare the input string
        String input = "deadline /by 2025-02-01";

        // Act
        ParseCommandException exception = Assertions.assertThrows(ParseCommandException.class, () -> {
            AddDeadlineCommand.parse(input);
        });

        // Assert
        Assertions.assertEquals(
                "Unable to parse [deadline /by 2025-02-01] to deadline command.", exception.getMessage());
    }

    @Test
    public void testParse_missingDate_throwsParseCommandException() {
        // Prepare the input string
        String input = "deadline Finish homework /by";

        // Act
        ParseCommandException exception = Assertions.assertThrows(ParseCommandException.class, () -> {
            AddDeadlineCommand.parse(input);
        });

        // Assert
        Assertions.assertEquals(
                "Unable to parse [deadline Finish homework /by] to deadline command.", exception.getMessage());
    }

    @Test
    public void testParse_invalidDate_throwsParseCommandException() {
        // Prepare the input string
        String input = "deadline Finish homework /by invalid-date";

        // Act
        ParseCommandException exception = Assertions.assertThrows(ParseCommandException.class, () -> {
            AddDeadlineCommand.parse(input);
        });

        // Assert
        Assertions.assertTrue(exception.getMessage().contains("Unable to parse"));
    }

    @Test
    public void testParse_invalidFormat_throwsParseCommandException() {
        // Prepare the input string
        String input = "finish homework /by 2025-02-01";

        // Act
        ParseCommandException exception = Assertions.assertThrows(ParseCommandException.class, () -> {
            AddDeadlineCommand.parse(input);
        });

        // Assert
        Assertions.assertTrue(exception.getMessage().contains("Unable to parse"));
    }

    @Test
    public void testExecute_addsTaskToContainerAndShowsOutput() throws ParseCommandException {
        // Prepare the input string and deadline command
        String input = "deadline Finish homework /by 2025-02-01";
        AddDeadlineCommand command = (AddDeadlineCommand) AddDeadlineCommand.parse(input);

        // Mocking the dependencies
        TaskContainer taskContainer = Mockito.mock(TaskContainer.class);
        Storage storage = Mockito.mock(Storage.class);
        Ui ui = Mockito.mock(Ui.class);
        State state = new State(taskContainer, storage, ui, null);
        Mockito.when(taskContainer.copy()).thenReturn(taskContainer);

        // Act
        command.execute(state);

        // Assert
        Mockito.verify(taskContainer).add(ArgumentMatchers.any(Deadline.class));
        Mockito.verify(ui).showOutput(ArgumentMatchers.eq("Got it. I've added this task:"),
                ArgumentMatchers.eq("[D][_] Finish homework (by: Feb 1 2025)"), ArgumentMatchers.anyString());
    }

    @Test
    public void testGetDate_returnsCorrectDate() throws ParseCommandException {
        // Prepare the input string
        String input = "deadline Finish homework /by 2025-02-01";

        // Act
        AddDeadlineCommand command = (AddDeadlineCommand) AddDeadlineCommand.parse(input);

        // Assert
        Assertions.assertEquals(LocalDate.of(2025, 2, 1), command.getDate());
    }

    @Test
    public void testGetTaskDescription_returnsCorrectDescription() throws ParseCommandException {
        // Prepare the input string
        String input = "deadline Finish homework /by 2025-02-01";

        // Act
        AddDeadlineCommand command = (AddDeadlineCommand) AddDeadlineCommand.parse(input);

        // Assert
        Assertions.assertEquals("Finish homework", command.getTaskDescription());
    }
}
