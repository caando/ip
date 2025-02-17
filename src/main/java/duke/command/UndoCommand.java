package duke.command;

import duke.State;

/**
 * Represents a command to undo the last executed command in the Duke application.
 * <p>
 * If there is a previous state available, it reverts to that state;
 * otherwise, it informs the user that no undo is possible.
 */
public class UndoCommand implements Command {

    /**
     * Parses the input and returns a new instance of {@code UndoCommand}.
     * <p>
     * This method does not inspect the input since the undo operation
     * does not require any arguments.
     *
     * @param input The user input (ignored).
     * @return A new instance of {@code UndoCommand}.
     */
    public static Command parse(String input) {
        return new UndoCommand();
    }

    /**
     * Executes the undo command by reverting to the previous state if it exists.
     * <p>
     * Displays an appropriate message to the user based on the success or failure of the operation.
     *
     * @param state The current application state containing tasks, storage, and UI.
     * @return The previous application state if available; otherwise, the current state.
     */
    @Override
    public State execute(State state) {
        if (state.getPreviousState() == null) {
            state.getUi().showError("No previous command to undo.");
            return state;
        } else {
            assert state.getPreviousCommand() != null : "Previous command should not be null";
            state.getUi().showOutput(String.format("Rewinding time! Undoing previous command: [%s]",
                    state.getPreviousCommand()), "You're welcome!");
            return state.getPreviousState();
        }
    }
}
