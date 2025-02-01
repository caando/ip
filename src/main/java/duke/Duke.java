package duke;

import duke.command.ByeCommand;
import duke.command.Command;
import duke.exception.ParseCommandException;
import duke.exception.ReadStorageException;
import duke.parser.Parser;
import duke.storage.FileStorage;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Cli;
import duke.ui.Ui;

public class Duke {

    public static void main(String[] args) {
        Storage storage = new FileStorage("./data/duke.txt");
        TaskList tasks = new TaskList();
        Ui ui = new Cli();

        try {
            storage.load(tasks, ui);
        } catch (ReadStorageException e) {
            ui.showError(e.getMessage());
        }

        while (true) {
            String input = ui.getInput();
            try {
                Command command = Parser.parseCommand(input);
                command.execute(tasks, storage, ui);

                if (command instanceof ByeCommand) {
                    break;
                }
            } catch (ParseCommandException e) {
                ui.showError(e.getMessage());
            }

        }
    }
}
