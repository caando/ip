package command;

import task.TaskList;

public interface Command {
    void execute(TaskList taskList);
}
