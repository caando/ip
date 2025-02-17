package duke.command;

import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import duke.State;
import duke.exception.ParseCommandException;
import duke.storage.Storage;
import duke.task.TaskContainer;
import duke.task.Todo;
import duke.ui.Ui;

public class AddTodoCommandTest {

    @Test
    public void testParse_validInput_createsAddTodoCommand() throws ParseCommandException {
        // Prepare the input string
        String input = "todo Buy groceries";

        // Act
        AddTodoCommand command = (AddTodoCommand) AddTodoCommand.parse(input);

        // Assert
        Assertions.assertNotNull(command);
        Assertions.assertEquals("Buy groceries", command.getTaskDescription());
    }

    @Test
    public void testParse_missingDescription_throwsParseCommandException() {
        // Prepare the input string
        String input = "todo ";

        // Act
        ParseCommandException exception = Assertions.assertThrows(ParseCommandException.class, () -> {
            AddTodoCommand.parse(input);
        });

        // Assert
        Assertions.assertEquals("Unable to parse [todo ] to todo command.", exception.getMessage());
    }

    @Test
    public void testParse_invalidFormat_throwsParseCommandException() {
        // Prepare the input string
        String input = "do homework";

        // Act
        ParseCommandException exception = Assertions.assertThrows(ParseCommandException.class, () -> {
            AddTodoCommand.parse(input);
        });

        // Assert
        Assertions.assertTrue(Objects.requireNonNull(exception.getMessage()).contains("Unable to parse"));
    }

    @Test
    public void testExecute_addsTaskToContainerAndShowsOutput() throws ParseCommandException {
        // Prepare the input string and command
        String input = "todo Read a book";
        AddTodoCommand command = (AddTodoCommand) AddTodoCommand.parse(input);

        // Mocking the dependencies
        TaskContainer taskContainer = Mockito.mock(TaskContainer.class);
        Storage storage = Mockito.mock(Storage.class);
        Ui ui = Mockito.mock(Ui.class);
        State state = new State(taskContainer, storage, ui, null, null);
        Mockito.when(taskContainer.copy()).thenReturn(taskContainer);

        // Act
        command.execute(state);

        // Assert
        Mockito.verify(taskContainer).add(Mockito.any(Todo.class));
        Mockito.verify(ui).showOutput(Mockito.eq("Ooooh! Look at that! I've added this task:"),
                Mockito.eq("[T][_] Read a book"),
                Mockito.eq("Now you have 0 tasks in the list! Let's get it done fast!"));
    }

    @Test
    public void testGetTaskDescription_returnsCorrectDescription() throws ParseCommandException {
        // Prepare the input string
        String input = "todo Complete project";

        // Act
        AddTodoCommand command = (AddTodoCommand) AddTodoCommand.parse(input);

        // Assert
        Assertions.assertEquals("Complete project", command.getTaskDescription());
    }
}
