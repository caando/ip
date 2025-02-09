package duke.command;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import duke.State;
import duke.exception.TaskNotFoundException;
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
     *
     * @return a new instance of {@code ListCommand}
     */
    public static Command parse(String input) {
        assert input != null : "input must not be null";
        assert input.startsWith("list") : "Input must start with 'list'";

        return new ListCommand();
    }

    /**
     * Executes the list command by retrieving all tasks in the task container and displaying them to the user.
     * The tasks are formatted as a numbered list, and the output is shown via the user interface.
     *
     * @param state The current application state containing tasks, storage, and UI.
     *
     * @return The same {@link State} object, as no modifications are made.
     */
    @Override
    public State execute(State state) {
        TaskContainer tasks = state.getTasks().copy();
        Ui ui = state.getUi();

        assert tasks != null : "Tasks must not be null";
        assert ui != null : "Ui must not be null";

        List<String> output = IntStream.range(0, tasks.size())
                .mapToObj(i -> {
                    try {
                        return String.format("%d. %s", i + 1, tasks.get(i).toString());
                    } catch (TaskNotFoundException e) {
                        return String.format("%d. [Error: %s]", i + 1, e.getMessage());
                    }
                }).collect(Collectors.toList());
        ui.showOutput(output);

        return state;
    }
}
