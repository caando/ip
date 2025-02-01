package duke.command;

import duke.storage.Storage;
import duke.task.TaskContainer;
import duke.ui.Ui;

public class ByeCommand implements Command {

    public static Command parse(String input) {
        return new ByeCommand();
    }

    @Override
    public void execute(TaskContainer tasks, Storage storage, Ui ui) {
        ui.close();
    }
}
