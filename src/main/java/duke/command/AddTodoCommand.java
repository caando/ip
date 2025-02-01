package duke.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import duke.exception.ParseCommandException;
import duke.storage.Storage;
import duke.task.TaskContainer;
import duke.task.Todo;
import duke.ui.Ui;

/**
 * Represents a command to add a todo task.
 * This command parses user input, creates a {@code Todo} task, and adds it to the task container.
 */
public class AddTodoCommand implements Command {

    private final String taskDescription;

    /**
     * Creates an {@code AddTodoCommand} with the specified task description.
     *
     * @param taskDescription the description of the task
     */
    public AddTodoCommand(String taskDescription) {
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
        String regex = "todo\\s+(.+)";
        Pattern pattern = Pattern.compile(regex);
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
     * @param taskList the task container to which the task is added
     * @param storage the storage used for persisting tasks
     * @param ui the user interface for displaying outputs
     */
    @Override
    public void execute(TaskContainer taskList, Storage storage, Ui ui) {
        Todo todo = new Todo(taskDescription);
        taskList.add(todo);
        ui.showOutput("Got it. I've added this task:", todo.toString(),
                "Now you have " + taskList.size() + " tasks in the list.");
    }
}
