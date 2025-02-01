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
 * Represents a command to delete a task from the task list.
 * The user must specify a valid positive integer index of the task to be deleted.
 */
public class DeleteCommand implements Command {

    /** The index of the task to be deleted (1-based). */
    private final int taskIndex;

    /**
     * Constructs a {@code DeleteCommand} with the specified task index.
     *
     * @param taskIndex the 1-based index of the task to delete
     */
    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    /**
     * Parses the user input to create a {@code DeleteCommand}.
     * The input should match the pattern `delete XXX`, where `XXX` is a positive integer.
     *
     * @param input the user input string
     * @return a new instance of {@code DeleteCommand}
     * @throws ParseCommandException if the input does not match the expected pattern or the index is invalid
     */
    public static Command parse(String input) throws ParseCommandException {
        String regex = "delete\\s+(\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            String indexString = matcher.group(1);
            try {
                int index = Integer.parseInt(indexString);
                if (index < 1) {
                    throw new ParseCommandException(String.format(
                            "Delete index [%d] should be a positive integer", index));
                }
                return new DeleteCommand(index);
            } catch (NumberFormatException e) {
                throw new ParseCommandException(String.format("Unable to parse [%s] as integer.", indexString));
            }
        } else {
            throw new ParseCommandException("Delete command requires an integer index.");
        }
    }

    /**
     * Executes the delete command by removing the task from the task list,
     * updating storage, and displaying the relevant output to the user.
     *
     * @param tasks the task container from which the task will be removed
     * @param storage the storage handler to persist the updated task list
     * @param ui the user interface to display output and error messages
     */
    @Override
    public void execute(TaskContainer tasks, Storage storage, Ui ui) {
        try {
            Task task = tasks.remove(taskIndex - 1);
            ui.showOutput("Noted. I've removed this task:", task.toString(),
                    "Now you have " + tasks.size() + " tasks in the list.");
            task.markAsNotDone();
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
