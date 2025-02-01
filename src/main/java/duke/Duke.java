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

/**
 * The main entry point for the Duke application.
 * <p>
 * This class initializes and runs the Duke task manager application. It sets up the storage, task list,
 * and user interface, then enters an interactive loop where user commands are parsed, executed, and handled.
 * The application continues running until the user inputs the "bye" command, at which point the program terminates.
 */
public class Duke {

    /**
     * The main method that runs the Duke application.
     * <p>
     * It sets up the necessary components such as the storage, task list, and user interface. It attempts
     * to load existing tasks from storage, and then enters a loop where user input is continually parsed and
     * processed. Commands are executed accordingly, and errors are displayed if they occur. The program terminates
     * when the user inputs the "bye" command.
     *
     * @param args Command-line arguments (not used).
     */
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
