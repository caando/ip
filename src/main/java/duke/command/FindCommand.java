package duke.command;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import duke.exception.ParseCommandException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskContainer;
import duke.ui.Ui;

/**
 * Represents a command that finds tasks with a description matching a given keyword.
 * <p>
 * This command parses the user input, searches for the keyword in the descriptions of all tasks in
 * the {@code TaskContainer}, and displays the tasks that match the keyword.
 */
public class FindCommand implements Command {

    // Captures `find XXX` where XXX is a string
    static final String COMMAND_REGEX = "find\\s+(.+)";

    private final String keyword;

    /**
     * Constructs a {@code FindCommand} with the specified keyword.
     *
     * @param keyword The keyword to search for in task descriptions.
     */
    public FindCommand(String keyword) {
        assert keyword != null : "Keyword must not be null";

        this.keyword = keyword;
    }

    /**
     * Parses the input string to create a {@code FindCommand} instance.
     * <p>
     * This method extracts the keyword from the input string and creates a {@code FindCommand}
     * if the input is in the correct format. If the input does not match the expected format,
     * a {@code ParseCommandException} is thrown.
     *
     * @param input The input string to parse.
     * @return A new {@code FindCommand} instance with the parsed keyword.
     * @throws ParseCommandException If the input is invalid or cannot be parsed.
     */
    public static Command parse(String input) throws ParseCommandException {
        Pattern pattern = Pattern.compile(COMMAND_REGEX);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            String keyword = matcher.group(1).trim();
            if (keyword.isEmpty()) {
                throw new ParseCommandException("Find command requires a keyword.");
            }
            return new FindCommand(keyword);
        } else {
            throw new ParseCommandException(String.format("Unable to parse [%s] to find command.", input));
        }
    }

    /**
     * Executes the find command, searching for tasks that contain the keyword in their descriptions.
     * <p>
     * This method iterates through all tasks in the provided {@code TaskContainer}, filters the tasks
     * that contain the keyword in their description, and displays them using the provided {@code Ui}.
     *
     * @param tasks The {@code TaskContainer} containing all the tasks to search.
     * @param storage The {@code Storage} instance used to access the task data (not used in this method).
     * @param ui The {@code Ui} instance used to display the filtered tasks.
     */
    @Override
    public void execute(TaskContainer tasks, Storage storage, Ui ui) {
        assert tasks != null : "Tasks must not be null";
        assert ui != null : "Ui must not be null";

        ArrayList<Task> filteredTasks = new ArrayList<>();
        tasks.list((index, task) -> {
            if (task.getDescription().contains(keyword)) {
                filteredTasks.add(task);
            }
        });
        ArrayList<String> output = new ArrayList<>();
        output.add("Here are the matching tasks in your list:");
        for (int i = 0; i < filteredTasks.size(); i++) {
            Task task = filteredTasks.get(i);
            output.add(String.format("%d. %s", i + 1, task.toString()));
        }
        ui.showOutput(output);
    }
}
