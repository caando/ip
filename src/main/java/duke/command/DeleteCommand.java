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

public class DeleteCommand implements Command {

    private final int taskIndex;

    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    public static Command parse(String input) throws ParseCommandException {
        // Captures `delete XXX` where XXX is a positive integer
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
                throw new ParseCommandException(String.format("Unable to parse [%s] as integer.",
                        indexString));
            }
        } else {
            throw new ParseCommandException("Delete command requires an integer index.");
        }
    }

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
