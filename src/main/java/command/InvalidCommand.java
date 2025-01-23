package command;

import task.TaskList;

public class InvalidCommand implements Command {

    private final String message;

    public InvalidCommand(String message) {
        this.message = message;
    }

    @Override
    public void execute(TaskList taskList) {
        System.out.println(message);
        System.out.println("    ____________________________________________________________");
    }
}