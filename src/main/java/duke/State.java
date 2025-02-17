package duke;

import duke.storage.Storage;
import duke.task.TaskContainer;
import duke.ui.Ui;

/**
 * Represents the state of the application at a specific point in time.
 * The state includes the current task container, UI, storage,
 * and a reference to the previous state for state management operations.
 */
public class State {
    private final TaskContainer tasks;
    private final Storage storage;
    private final Ui ui;
    private final State previousState;
    private final String previousCommand;

    /**
     * Constructs a {@code State} with the specified tasks, UI, storage,
     * and reference to the previous state.
     *
     * @param tasks         The current task container holding user tasks.
     * @param storage       The storage system responsible for reading and writing tasks.
     * @param ui            The user interface component for displaying information.
     * @param previousState The previous state of the application, or {@code null} if none.
     * @param previousCommand The previous command that led to this state, or {@code null} if none.
     */
    public State(TaskContainer tasks, Storage storage, Ui ui, State previousState, String previousCommand) {
        assert tasks != null : "Task container should not be null";
        assert storage != null : "Storage should not be null";
        assert ui != null : "UI should not be null";

        this.tasks = tasks;
        this.ui = ui;
        this.storage = storage;
        this.previousState = previousState;
        this.previousCommand = previousCommand;
    }

    /**
     * Returns the current task container.
     *
     * @return The {@code TaskContainer} instance containing the tasks.
     */
    public TaskContainer getTasks() {
        return tasks;
    }

    /**
     * Returns the storage system component.
     *
     * @return The {@code Storage} instance used for task storage.
     */
    public Storage getStorage() {
        return storage;
    }

    /**
     * Returns the user interface component.
     *
     * @return The {@code Ui} instance for interacting with the user.
     */
    public Ui getUi() {
        return ui;
    }

    /**
     * Returns the previous state of the application.
     *
     * @return The previous {@code State}, or {@code null} if this is the initial state.
     */
    public State getPreviousState() {
        return previousState;
    }

    /**
     * Returns the previous command that led to this state.
     *
     * @return The previous command, or {@code null} if this is the initial state.
     */
    public String getPreviousCommand() {
        return previousCommand;
    }
}
