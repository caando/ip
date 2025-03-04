package duke.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import duke.State;
import duke.exception.ParseCommandException;
import duke.exception.WriteStorageException;
import duke.storage.Storage;
import duke.task.TaskContainer;
import duke.task.Todo;
import duke.ui.Ui;

/**
 * Represents a command to add a todo task.
 * <p>
 * This command parses user input, creates a {@code Todo} task, and adds it to the task container.
 */
public class AddTodoCommand implements Command {

    // Captures `todo XXX` where XXX is a string
    static final String COMMAND_REGEX = "todo\\s+(.+)";

    private final String taskDescription;

    /** The raw input string from the user. */
    private final String rawInput;

    /**
     * Creates an {@code AddTodoCommand} with the specified task description.
     *
     * @param taskDescription the description of the task
     * @param rawInput the raw input string from the user
     */
    public AddTodoCommand(String taskDescription, String rawInput) {
        assert taskDescription != null : "Task description must not be null";

        this.taskDescription = taskDescription;
        this.rawInput = rawInput;
    }

    /**
     * Parses the input string to create an {@code AddTodoCommand}.
     * <p>
     * The input must follow the format: {@code "todo <description>"}.
     *
     * @param input the user input string
     * @return the parsed {@code AddTodoCommand} instance
     * @throws ParseCommandException if the input is invalid or cannot be parsed
     */
    public static Command parse(String input) throws ParseCommandException {
        assert input != null : "input must not be null";

        Pattern pattern = Pattern.compile(COMMAND_REGEX);
        Matcher matcher = pattern.matcher(input);

        if (!matcher.matches()) {
            throw new ParseCommandException(String.format("Unable to parse [%s] to todo command.", input));
        }

        String description = matcher.group(1).trim();
        if (description.isEmpty()) {
            throw new ParseCommandException("Todo command requires a description.");
        }
        return new AddTodoCommand(description, input);
    }

    /**
     * Returns the description of the task.
     *
     * @return the task description
     */
    public String getTaskDescription() {
        return taskDescription;
    }

    /**
     * Executes the {@code AddTodoCommand}.
     * <p>
     * Creats a new {@code Todo} task, adds it to the task list, and displays the result to the user.
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
        assert storage != null : "Storage must not be null";
        assert ui != null : "Ui must not be null";

        Todo todo = new Todo(taskDescription);
        tasks.add(todo);
        ui.showOutput("Ooooh! Look at that! I've added this task:", todo.toString(),
                "Now you have " + tasks.size() + " tasks in the list! Let's get it done fast!");

        try {
            storage.save(tasks, ui);
        } catch (WriteStorageException e) {
            ui.showError(e.getMessage());
        }

        return new State(tasks, storage, ui, state, this.rawInput);
    }
}
