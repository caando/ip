package duke.command;

import java.util.ArrayList;

import duke.storage.Storage;
import duke.task.TaskContainer;
import duke.ui.Ui;

/**
 * Represents a command to list all the tasks in the task container.
 * The command retrieves all tasks and formats them for display to the user.
 */
public class ListCommand implements Command {

    /**
     * Parses the user input to create a new {@code ListCommand}.
     * Since the list command does not require additional parameters, it directly returns a new {@code ListCommand}.
     *
     * @param input the user input string
     * @return a new instance of {@code ListCommand}
     */
    public static Command parse(String input) {
        return new ListCommand();
    }

    /**
     * Executes the list command by retrieving all tasks in the task container and displaying them to the user.
     * The tasks are formatted as a numbered list, and the output is shown via the user interface.
     *
     * @param tasks the task container containing all tasks
     * @param storage the storage handler (not used in this command)
     * @param ui the user interface to display the list of tasks
     */
    @Override
    public void execute(TaskContainer tasks, Storage storage, Ui ui) {
        assert tasks != null : "Tasks must not be null";
        assert ui != null : "Ui must not be null";

        ArrayList<String> output = new ArrayList<>();
        tasks.list((index, task) -> {
            output.add(String.format("%d. %s", index + 1, task.toString()));
        });
        ui.showOutput(output);
    }
}
