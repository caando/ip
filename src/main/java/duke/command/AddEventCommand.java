package duke.command;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import duke.State;
import duke.Utils;
import duke.exception.ParseCommandException;
import duke.exception.WriteStorageException;
import duke.storage.Storage;
import duke.task.Event;
import duke.task.TaskContainer;
import duke.ui.Ui;

/**
 * Represents a command to add an event task. This command parses user input,
 * creates an {@code Event} task, and adds it to the task container.
 */
public class AddEventCommand implements Command {

    // Captures `event XXX /from YYY /to ZZZ` where XXX, YYY and ZZZ are strings
    static final String COMMAND_REGEX = "event\\s+(.+)\\s+/from\\s+(.+)\\s+/to\\s+(.+)";

    private final String taskDescription;
    private final LocalDate from;
    private final LocalDate to;

    /** The raw input string from the user. */
    private final String rawInput;

    /**
     * Creates an {@code AddEventCommand} with the specified task description
     * and time period.
     *
     * @param taskDescription the description of the task
     * @param from the starting date of the event
     * @param to the ending date of the event
     * @param rawInput the raw input string from the user
     */
    public AddEventCommand(String taskDescription, LocalDate from, LocalDate to, String rawInput) {
        assert taskDescription != null : "Task description must not be null";
        assert from != null : "From date must not be null";
        assert to != null : "To date must not be null";

        this.taskDescription = taskDescription;
        this.from = from;
        this.to = to;
        this.rawInput = rawInput;
    }

    /**
     * Parses the input string to create an {@code AddEventCommand}.
     * <p>
     * The input must follow the format:
     * {@code "event <description> /from <start_date> /to <end_date>"}.
     *
     * @param input the user input string
     * @return the parsed {@code AddEventCommand} instance
     * @throws ParseCommandException if the input is invalid or cannot be parsed
     */
    public static Command parse(String input) throws ParseCommandException {
        assert input != null : "input must not be null";

        Pattern pattern = Pattern.compile(COMMAND_REGEX);
        Matcher matcher = pattern.matcher(input);

        if (!matcher.matches()) {
            throw new ParseCommandException(String.format("Unable to parse [%s] to event command.", input));
        }

        String description = matcher.group(1).trim();
        String fromDateString = matcher.group(2).trim();
        String toDateString = matcher.group(3).trim();

        if (description.isEmpty()) {
            throw new ParseCommandException("Event command requires a description.");
        }

        if (fromDateString.isEmpty()) {
            throw new ParseCommandException("Event command requires [/from] argument.");
        }

        if (toDateString.isEmpty()) {
            throw new ParseCommandException("Event command requires [/to] argument.");
        }

        LocalDate fromDate;
        try {
            fromDate = Utils.parseDate(fromDateString);
        } catch (DateTimeParseException e) {
            throw new ParseCommandException(String.format("Unable to parse [%s] to date.", fromDateString));
        }

        LocalDate toDate;
        try {
            toDate = Utils.parseDate(toDateString);
        } catch (DateTimeParseException e) {
            throw new ParseCommandException(String.format("Unable to parse [%s] to date.", toDateString));
        }

        return new AddEventCommand(description, fromDate, toDate, input);
    }

    /**
     * Returns the description of the task.
     *
     * @return the task description
     */
    public String getTaskDescription() {
        return taskDescription;
    }

    /**
     * Returns the starting date of the event.
     *
     * @return the starting date
     */
    public LocalDate getFrom() {
        return from;
    }

    /**
     * Returns the ending date of the event.
     *
     * @return the ending date
     */
    public LocalDate getTo() {
        return to;
    }

    /**
     * Executes the {@code AddEventCommand} by creating a new {@code Event}
     * task, adding it to the task list, and displaying the result to the user.
     *
     * @param state The current application state containing tasks, storage, and UI.
     *
     * @return A new {@link State} object reflecting the updated task list
     *         and retaining the previous state information.
     */
    @Override
    public State execute(State state) {
        TaskContainer tasks = state.getTasks().copy();
        Storage storage = state.getStorage();
        Ui ui = state.getUi();
        assert tasks != null : "Tasks must not be null";
        assert storage != null : "Storage must not be null";
        assert ui != null : "Ui must not be null";

        Event event = new Event(taskDescription, from, to);
        tasks.add(event);
        ui.showOutput("Oooooooh! I've added this event:", event.toString(),
                "Now you have " + tasks.size() + " tasks in the list! Don't forget to show up!");

        try {
            storage.save(tasks, ui);
        } catch (WriteStorageException e) {
            ui.showError(e.getMessage());
        }

        return new State(tasks, storage, ui, state, this.rawInput);
    }
}
