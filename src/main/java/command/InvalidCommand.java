package command;

import task.TaskList;

public class InvalidCommand implements Command {

    @Override
    public void execute(TaskList taskList) {
        System.out.println("    Invalid command. Please try again.");
        System.out.println("    ____________________________________________________________");
    }
}