package duke.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import duke.exception.ParseCommandException;
import duke.exception.TaskNotFoundException;
import duke.exception.WriteStorageException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskContainer;
import duke.ui.Ui;

/**
 * Represents a command to mark a task as not done.
 * The command takes the task index, retrieves the task from the container,
 * and marks it as not done.
 */
public class UnmarkCommand implements Command {

    // Captures `unmark XXX` where XXX is a positive integer
    static final String COMMAND_REGEX = "unmark\\s+(\\d+)";

    private final int taskIndex;

    /**
     * Constructs an {@code UnmarkCommand} with the specified task index.
     *
     * @param taskIndex the index of the task to be marked as not done
     */
    private UnmarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    /**
     * Parses the user input to create a new {@code UnmarkCommand}.
     * The input should contain the `unmark` keyword followed by a positive integer index,
     * representing the task to mark as not done.
     *
     * @param input the user input string
     * @return a new instance of {@code UnmarkCommand} with the task index
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
                return new UnmarkCommand(index);
            } catch (NumberFormatException e) {
                throw new ParseCommandException(String.format(
                        "Unable to parse [%s] as integer. Task index should be a positive integer.",
                        indexString));
            }
        } else {
            throw new ParseCommandException("Unmark command requires an integer index.");
        }
    }

    /**
     * Executes the unmark command by retrieving the task from the task container
     * using the specified index and marking it as not done.
     * The task is then saved, and appropriate messages are shown via the user interface.
     *
     * @param tasks the task container containing all tasks
     * @param storage the storage handler for saving tasks (not used in this command directly)
     * @param ui the user interface to display the success or error message
     */
    @Override
    public void execute(TaskContainer tasks, Storage storage, Ui ui) {
        assert tasks != null : "Tasks must not be null";
        assert storage != null : "Storage must not be null";
        assert ui != null : "Ui must not be null";

        try {
            Task task = tasks.get(taskIndex - 1);
            task.markAsNotDone();
            ui.showOutput("Nice! I've marked this task as not done yet:", task.toString());
        } catch (TaskNotFoundException e) {
            ui.showError(e.getMessage());
        }

        try {
            storage.save(tasks, ui);
        } catch (WriteStorageException e) {
            ui.showError(e.getMessage());
        }
    }
}
