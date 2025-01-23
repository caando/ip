package command;

import duke.DukeException;
import task.TaskList;

public class InvalidCommand implements Command {

    private final DukeException exception;

    public InvalidCommand(DukeException exception) {
        this.exception = exception;
    }

    @Override
    public void execute(TaskList taskList) {
        System.out.println(exception.getMessage());
        System.out.println("    ____________________________________________________________");
    }
}