package duke.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import duke.State;
import duke.exception.ParseCommandException;
import duke.exception.TaskNotFoundException;
import duke.exception.WriteStorageException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskContainer;
import duke.ui.Ui;

class DeleteCommandTest {

    private TaskContainer taskContainer;
    private Storage storage;
    private Ui ui;
    private State state;

    @BeforeEach
    void setUp() {
        taskContainer = Mockito.mock(TaskContainer.class);
        storage = Mockito.mock(Storage.class);
        ui = Mockito.mock(Ui.class);
        state = new State(taskContainer, storage, ui, null, null);
    }

    // Test parse() for valid input
    @Test
    void testParse_validInput_returnsDeleteCommand() throws ParseCommandException {
        String input = "delete 3";
        DeleteCommand command = (DeleteCommand) DeleteCommand.parse(input);

        Assertions.assertEquals(3, command.getTaskIndex());
    }

    // Test parse() for invalid input (non-integer)
    @Test
    void testParse_nonIntegerInput_throwsParseCommandException() {
        String input = "delete abc";
        ParseCommandException exception = Assertions.assertThrows(
                ParseCommandException.class, () -> DeleteCommand.parse(input));
        Assertions.assertEquals("Delete command requires an integer index.", exception.getMessage());
    }

    // Test parse() for missing index
    @Test
    void testParse_missingIndex_throwsParseCommandException() {
        String input = "delete";
        ParseCommandException exception = Assertions.assertThrows(
                ParseCommandException.class, () -> DeleteCommand.parse(input));
        Assertions.assertEquals("Delete command requires an integer index.", exception.getMessage());
    }

    // Test parse() for negative index
    @Test
    void testParse_negativeIndex_throwsParseCommandException() {
        String input = "delete -1";
        ParseCommandException exception = Assertions.assertThrows(
                ParseCommandException.class, () -> DeleteCommand.parse(input));
        Assertions.assertEquals("Delete command requires an integer index.", exception.getMessage());
    }

    // Test execute() for successful deletion
    @Test
    void testExecute_successfulDeletion_showsCorrectOutput() throws TaskNotFoundException, WriteStorageException {
        Task task = Mockito.mock(Task.class);
        Mockito.when(taskContainer.remove(2)).thenReturn(task);
        Mockito.when(task.toString()).thenReturn("Task 3");
        Mockito.when(taskContainer.size()).thenReturn(5).thenReturn(4);
        Mockito.when(taskContainer.copy()).thenReturn(taskContainer);

        DeleteCommand command = new DeleteCommand(3, "delete 3");
        command.execute(state);

        Mockito.verify(taskContainer).remove(2); // Ensure task was removed at correct index
        Mockito.verify(ui).showOutput(
                "POOF! I’ve removed this task:",
                "Task 3",
                "Now you have 4 tasks in the list! Less work for you!");
        Mockito.verify(storage).save(taskContainer, ui);
    }

    // Test execute() for task not found
    @Test
    void testExecute_taskNotFound_showsError() throws TaskNotFoundException, WriteStorageException {
        Mockito.when(taskContainer.remove(2)).thenThrow(new TaskNotFoundException("Task not found"));
        Mockito.when(taskContainer.copy()).thenReturn(taskContainer);

        DeleteCommand command = new DeleteCommand(3, "delete 3");
        command.execute(state);

        Mockito.verify(ui).showError("Task not found");
        Mockito.verify(storage).save(taskContainer, ui);
    }

    // Test execute() for WriteStorageException
    @Test
    void testExecute_writeStorageException_showsError() throws TaskNotFoundException, WriteStorageException {
        Task task = Mockito.mock(Task.class);
        Mockito.when(taskContainer.remove(2)).thenReturn(task);
        Mockito.when(taskContainer.size()).thenReturn(5).thenReturn(4);
        Mockito.doThrow(new WriteStorageException("Storage error")).when(storage).save(taskContainer, ui);
        Mockito.when(taskContainer.copy()).thenReturn(taskContainer);

        DeleteCommand command = new DeleteCommand(3, "delete 3");
        command.execute(state);

        Mockito.verify(ui).showError("Storage error");
    }
}
