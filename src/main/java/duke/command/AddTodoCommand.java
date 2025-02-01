package duke.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import duke.exception.ParseCommandException;
import duke.storage.Storage;
import duke.task.TaskContainer;
import duke.task.Todo;
import duke.ui.Ui;

public class AddTodoCommand implements Command {

    private final String taskDescription;

    public AddTodoCommand(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public static Command parse(String input) throws ParseCommandException {
        // Captures `todo XXX`
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
            throw new ParseCommandException(String.format("Unable to parse [%s] to todo command", input));
        }
    }

    @Override
    public void execute(TaskContainer taskList, Storage storage, Ui ui) {
        Todo todo = new Todo(taskDescription);
        taskList.add(todo);
        ui.showOutput("Got it. I've added this task:", todo.toString(),
                "Now you have " + taskList.size() + " tasks in the list.");
    }
}

