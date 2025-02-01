package duke.command;

import duke.storage.Storage;
import duke.task.TaskContainer;
import duke.ui.Ui;

public interface Command {

    public enum Type {
        BYE,
        DEADLINE,
        DELETE,
        EVENT,
        LIST,
        MARK,
        TODO,
        UNMARK
    }

    public void execute(TaskContainer taskList, Storage storage, Ui ui);
}
