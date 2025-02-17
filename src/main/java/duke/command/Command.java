package duke.command;

import duke.State;

/**
 * Represents a command that can be executed by the Duke application.
 * <p>
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

        /** Command to list all tasks in the current task container. */
        LIST,

        /** Command to mark a task as completed. */
        MARK,

        /** Command to add a todo task. */
        TODO,

        /** Command to undo the previous mutable command. */
        UNDO,

        /** Command to unmark a completed task. */
        UNMARK
    }

    /**
     * Executes the command using the provided application state.
     * <p>
     * The execution may modify tasks, update storage, and display output to the user.
     *
     * @param state The current application state, containing tasks, storage, and UI.
     *
     * @return The updated {@code State} after executing the command.
     */
    public State execute(State state);
}
