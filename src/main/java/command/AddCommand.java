package command;

import task.Task;
import task.TaskList;

public class AddCommand implements Command {

    private final String taskDescription;

    private AddCommand(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public static AddCommand parse(String command) {
        return new AddCommand(command.trim());
    }

    @Override
    public void execute(TaskList taskList) {
        taskList.add(new Task(taskDescription));
        System.out.println("     added: " + taskDescription);
        System.out.println("    ____________________________________________________________");
    }
}
