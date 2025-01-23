package command;

import duke.DukeException;
import task.TaskList;
import task.Todo;

public class AddTodoCommand implements Command {

    private final String taskDescription;

    public AddTodoCommand(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public static Command parse(String[] parts) {
        if (parts.length < 2) {
            return new InvalidCommand(new DukeException("     OOPS!!! The description of a todo cannot be empty."));
        }
        return new AddTodoCommand(parts[1]);
    }

    @Override
    public void execute(TaskList taskList) {
        Todo todo = new Todo(taskDescription);
        taskList.add(todo);  // Add to the task list
        System.out.println("    ____________________________________________________________");
        System.out.println("     Got it. I've added this task:");
        System.out.println("       " + todo);
        System.out.println("     Now you have " + taskList.size() + " tasks in the list.");
        System.out.println("    ____________________________________________________________");
    }
}

