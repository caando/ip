package duke.command;

import duke.storage.Storage;
import duke.task.TaskContainer;
import duke.ui.Ui;

/**
 * Represents a command that can be executed by the Duke application.
 * Commands are responsible for manipulating tasks, interacting with storage, and producing user outputs.
 */
public interface Command {

    /**
     * Enum representing the various types of commands supported by the application.
     */
    public enum Type {
        /** Command to exit the application. */
        BYE,

        /** Command to add a deadline task. */
        DEADLINE,

        /** Command to delete a task. */
        DELETE,

        /** Command to add an event task. */
        EVENT,

        /** Command to find task description. */
        FIND,

        /** Command to list all tasks. */
        LIST,

        /** Command to mark a task as completed. */
        MARK,

        /** Command to add a todo task. */
        TODO,

        /** Command to unmark a completed task. */
        UNMARK
    }

    /**
     * Executes the command using the given task list, storage, and user interface.
     *
     * @param taskList the container holding the current tasks
     * @param storage the storage handler for reading and writing tasks
     * @param ui the user interface for displaying outputs to the user
     */
    public void execute(TaskContainer taskList, Storage storage, Ui ui);
}
