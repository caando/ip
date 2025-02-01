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

public class MarkCommand implements Command {

    private final int taskIndex;

    private MarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    public static Command parse(String input) throws ParseCommandException {
        // Captures `mark XXX` where XXX is a positive integer
        String regex = "mark\\s+(\\d+)";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            String indexString = matcher.group(1);
            try {
                int index = Integer.parseInt(indexString);
                if (index <= 1) {
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
