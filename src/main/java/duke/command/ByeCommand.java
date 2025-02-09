package duke.command;

import duke.State;
import duke.ui.Ui;

/**
 * Represents a command to exit the application.
 * This command is triggered by the user input "bye".
 */
public class ByeCommand implements Command {

    /**
     * Parses the input string to create a {@code ByeCommand}.
     * The input is expected to be simply "bye".
     *
     * @param input the user input string
     * @return a new instance of {@code ByeCommand}
     */
    public static Command parse(String input) {
        assert input != null : "input must not be null";
        assert input.startsWith("bye") : "Input must start with 'bye'";

        return new ByeCommand();
    }

    /**
     * Executes the {@code ByeCommand} by closing the user interface.
     * This command does not modify the task list or storage.
     *
     * @param state The current application state containing tasks, storage, and UI.
     *
     * @return The same {@link State} object, as no modifications are made.
     */
    @Override
    public State execute(State state) {
        Ui ui = state.getUi();
        assert ui != null : "Ui must not be null";

        ui.close();
        return state;
    }
}
