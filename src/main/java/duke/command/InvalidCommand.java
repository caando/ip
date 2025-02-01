package duke.command;

import duke.exception.DukeException;
import duke.task.TaskList;

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
