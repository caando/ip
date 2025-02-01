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
 * Represents a command to mark a task as done.
 * The command takes the task index, retrieves the task from the container, and marks it as done.
 */
public class MarkCommand implements Command {

    private final int taskIndex;

    /**
     * Constructs a {@code MarkCommand} with the specified task index.
     *
     * @param taskIndex the index of the task to be marked as done
     */
    private MarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    /**
     * Parses the user input to create a new {@code MarkCommand}.
     * The input should contain the `mark` keyword followed by a positive integer index,
     * representing the task to mark as done.
     *
     * @param input the user input string
     * @return a new instance of {@code MarkCommand} with the task index
     * @throws ParseCommandException if the input is invalid or the task index is not a positive integer
     */
    public static Command parse(String input) throws ParseCommandException {
        // Captures `mark XXX` where XXX is a positive integer
        String regex = "mark\\s+(\\d+)";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            String indexString = matcher.group(1);
            try {
                int index = Integer.parseInt(indexString);
                if (index <= 0) {
                    throw new ParseCommandException(String.format(
                            "Invalid index [%d]. Task index should be a positive integer.", index));
                }
                return new MarkCommand(index);
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
     * Executes the mark command by retrieving the task from the task container
     * using the specified index and marking it as done.
     * The task is then saved, and appropriate messages are shown via the user interface.
     *
     * @param tasks the task container containing all tasks
     * @param storage the storage handler for saving tasks (not used in this command directly)
     * @param ui the user interface to display the success or error message
     */
    @Override
    public void execute(TaskContainer tasks, Storage storage, Ui ui) {
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
    }
}
