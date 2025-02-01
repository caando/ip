package duke.command;

import java.util.ArrayList;

import duke.storage.Storage;
import duke.task.TaskContainer;
import duke.ui.Ui;

public class ListCommand implements Command {

    public static Command parse(String input) {
        return new ListCommand();
    }

    @Override
    public void execute(TaskContainer tasks, Storage storage, Ui ui) {
        ArrayList<String> output = new ArrayList<>();
        tasks.list((index, task) -> {
            output.add(String.format("%d. %s", index + 1, task.toString()));
        });
        ui.showOutput(output);
    }
}
