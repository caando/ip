package duke.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import duke.exception.ParseCommandException;
import duke.exception.WriteStorageException;
import duke.storage.Storage;
import duke.task.TaskContainer;
import duke.task.Todo;
import duke.ui.Ui;

/**
 * Represents a command to add a todo task.
 * This command parses user input, creates a {@code Todo} task, and adds it to the task container.
 */
public class AddTodoCommand implements Command {

    // Captures `todo XXX` where XXX is a string
    static final String COMMAND_REGEX = "todo\\s+(.+)";

    private final String taskDescription;

    /**
     * Creates an {@code AddTodoCommand} with the specified task description.
     *
     * @param taskDescription the description of the task
     */
    public AddTodoCommand(String taskDescription) {
        assert taskDescription != null : "Task description must not be null";

        this.taskDescription = taskDescription;
    }

    /**
     * Parses the input string to create an {@code AddTodoCommand}.
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

        if (matcher.matches()) {
            String description = matcher.group(1).trim();
            if (description.isEmpty()) {
                throw new ParseCommandException("Todo command requires a description.");
            }
            return new AddTodoCommand(description);
        } else {
            throw new ParseCommandException(String.format("Unable to parse [%s] to todo command.", input));
        }
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
     * Executes the {@code AddTodoCommand} by creating a new {@code Todo} task,
     * adding it to the task list, and displaying the result to the user.
     *
     * @param tasks the task container to which the task is added
     * @param storage the storage used for persisting tasks
     * @param ui the user interface for displaying outputs
     */
    @Override
    public void execute(TaskContainer tasks, Storage storage, Ui ui) {
        assert tasks != null : "Tasks must not be null";
        assert storage != null : "Storage must not be null";
        assert ui != null : "Ui must not be null";

        Todo todo = new Todo(taskDescription);
        tasks.add(todo);
        ui.showOutput("Got it. I've added this task:", todo.toString(),
                "Now you have " + tasks.size() + " tasks in the list.");

        try {
            storage.save(tasks, ui);
        } catch (WriteStorageException e) {
            ui.showError(e.getMessage());
        }
    }
}
