package duke.command;

import duke.task.TaskList;

public interface Command {
    void execute(TaskList taskList);
}
