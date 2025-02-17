package duke.command;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import duke.State;
import duke.exception.ParseCommandException;
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
        assert input != null : "input must not be null";
        assert input.startsWith("find") : "Input must start with 'find'";

        Pattern pattern = Pattern.compile(COMMAND_REGEX);
        Matcher matcher = pattern.matcher(input);

        if (!matcher.matches()) {
            throw new ParseCommandException(String.format("Unable to parse [%s] to find command.", input));
        }

        String keyword = matcher.group(1).trim();
        if (keyword.isEmpty()) {
            throw new ParseCommandException("Find command requires a keyword.");
        }
        return new FindCommand(keyword);
    }

    /**
     * Executes the find command, searching for tasks that contain the keyword in their descriptions.
     * <p>
     * This method iterates through all tasks in the provided {@code TaskContainer}, filters the tasks
     * that contain the keyword in their description, and displays them using the provided {@code Ui}.
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

        List<Task> filteredTasks = StreamSupport.stream(tasks.spliterator(), false).filter(
            task -> task.getDescription().contains(keyword)
        ).collect(Collectors.toList());

        ArrayList<String> output = new ArrayList<>();
        output.add(String.format("Here you go! Tasks matching \"%s\":", keyword));

        output.addAll(IntStream.range(0, filteredTasks.size())
                .mapToObj(i -> String.format("%d. %s", i + 1, filteredTasks.get(i).toString()))
                .collect(Collectors.toList()));
        output.add("Now go finish it!");

        ui.showOutput(output);

        return state;
    }
}
