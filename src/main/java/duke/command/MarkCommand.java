package duke.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import duke.State;
import duke.exception.ParseCommandException;
import duke.exception.TaskNotFoundException;
import duke.exception.WriteStorageException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskContainer;
import duke.ui.Ui;

/**
 * Represents a command to mark a task as done.
 * <p>
 * The command takes the task index, retrieves the task from the container, and marks it as done.
 */
public class MarkCommand implements Command {

    // Captures `mark XXX` where XXX is a positive integer
    static final String COMMAND_REGEX = "mark\\s+(\\d+)";

    private final int taskIndex;

    /** The raw input string from the user. */
    private final String rawInput;

    /**
     * Constructs a {@code MarkCommand} with the specified task index.
     *
     * @param taskIndex the index of the task to be marked as done
     */
    private MarkCommand(int taskIndex, String rawInput) {
        this.taskIndex = taskIndex;
        this.rawInput = rawInput;
    }

    /**
     * Parses the user input to create a new {@code MarkCommand}.
     * <p>
     * The input should contain the `mark` keyword followed by a positive integer index,
     * representing the task to mark as done.
     *
     * @param input the user input string
     * @return a new instance of {@code MarkCommand} with the task index
     * @throws ParseCommandException if the input is invalid or the task index is not a positive integer
     */
    public static Command parse(String input) throws ParseCommandException {
        assert input != null : "input must not be null";

        Pattern pattern = Pattern.compile(COMMAND_REGEX);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            String indexString = matcher.group(1);
            try {
                int index = Integer.parseInt(indexString);
                if (index <= 0) {
                    throw new ParseCommandException(String.format(
                            "Invalid index [%d]. Task index should be a positive integer.", index));
                }
                return new MarkCommand(index, input);
            } catch (NumberFormatException e) {
                throw new ParseCommandException(String.format(
                        "Unable to parse [%s] as integer. Task index should be a positive integer.",
                        indexString));
            }
        } else {
            throw new ParseCommandException("Mark command requires an integer index.");
        }
    }

    /**
     * Executes the mark command.
     * <p>
     * Retrieves the task from the task container with the specified index and mark it as done.
     * The task is then saved, and appropriate messages are shown via the user interface.
     *
     * @param state The current application state containing tasks, storage, and UI.
     *
     * @return A new {@link State} object reflecting the updated task list
     *         and retaining the previous state information.
     */
    @Override
    public State execute(State state) {
        TaskContainer tasks = state.getTasks().copy();
        Storage storage = state.getStorage();
        Ui ui = state.getUi();

        assert tasks != null : "Tasks must not be null";
        assert ui != null : "Ui must not be null";

        try {
            Task task = tasks.get(taskIndex - 1);
            task.markAsDone();
            ui.showOutput("Nice! I've marked this task as done:", task.toString());
        } catch (TaskNotFoundException e) {
            ui.showError(e.getMessage());
        }

        try {
            storage.save(tasks, ui);
        } catch (WriteStorageException e) {
            ui.showError(e.getMessage());
        }

        return new State(tasks, storage, ui, state, this.rawInput);
    }
}
