package duke.command;

import duke.storage.Storage;
import duke.task.TaskContainer;
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
        return new ByeCommand();
    }

    /**
     * Executes the {@code ByeCommand} by closing the user interface.
     * No changes are made to the task list or storage.
     *
     * @param tasks the task container (not used in this command)
     * @param storage the storage handler (not used in this command)
     * @param ui the user interface, which will be closed
     */
    @Override
    public void execute(TaskContainer tasks, Storage storage, Ui ui) {
        assert ui != null : "Ui must not be null";

        ui.close();
    }
}
