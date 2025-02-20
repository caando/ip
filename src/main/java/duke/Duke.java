package duke;

import duke.command.Command;
import duke.exception.ParseCommandException;
import duke.exception.ReadStorageException;
import duke.parser.Parser;
import duke.storage.FileStorage;
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

    private static final String STORAGE_PATH = "./data/duke.txt";

    private State state;

    /**
     * Constructs a new instance of Duke with the specified storage, task list, and user interface.
     *
     * @param ui The user interface component to interact with the user.
     */
    public Duke(Ui ui) {
        this.state = new State(new TaskList(), new FileStorage(STORAGE_PATH), ui, null, null);

        ui.start();
        try {
            this.state.getStorage().load(this.state.getTasks(), ui);
        } catch (ReadStorageException e) {
            ui.showOutput(String.format("No storage file found at %s, created new storage file.", STORAGE_PATH));
        }
    }

    /**
     * Processes the user input by parsing the command and executing it.
     * <p>
     * This method parses the user input into a command, then executes the command with the task list, storage,
     * and user interface components. If the command is a "bye" command, the user interface is closed.
     *
     * @param input The user input to process.
     */
    public void process(String input) {
        try {
            Command command = Parser.parseCommand(input);
            this.state = command.execute(this.state);
        } catch (ParseCommandException e) {
            this.state.getUi().showError(e.getMessage());
        }
    }

    /**
     * Runs the Duke application with a simple command line UI.
     * <p>
     * It sets up the necessary components such as the storage, task list, and user interface. It attempts
     * to load existing tasks from storage, and then enters a loop where user input is continually parsed and
     * processed. Commands are executed accordingly, and errors are displayed if they occur. The program terminates
     * when the user inputs the "bye" command.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Cli ui = new Cli(System.in, System.out);

        Duke duke = new Duke(ui);

        while (ui.isOpen()) {
            duke.process(ui.getInput());
        }
    }
}
