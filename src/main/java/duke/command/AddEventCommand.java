package duke.command;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * Creates an {@code AddEventCommand} with the specified task description
     * and time period.
     *
     * @param taskDescription the description of the task
     * @param from the starting date of the event
     * @param to the ending date of the event
     */
    public AddEventCommand(String taskDescription, LocalDate from, LocalDate to) {
        this.taskDescription = taskDescription;
        this.from = from;
        this.to = to;
    }

    /**
     * Parses the input string to create an {@code AddEventCommand}. The input
     * must follow the format:
     * {@code "event <description> /from <start_date> /to <end_date>"}.
     *
     * @param input the user input string
     * @return the parsed {@code AddEventCommand} instance
     * @throws ParseCommandException if the input is invalid or cannot be parsed
     */
    public static Command parse(String input) throws ParseCommandException {
        Pattern pattern = Pattern.compile(COMMAND_REGEX);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
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

            return new AddEventCommand(description, fromDate, toDate);
        } else {
            throw new ParseCommandException(String.format("Unable to parse [%s] to event command.", input));
        }
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
     * @param tasks the task container to which the task is added
     * @param storage the storage used for persisting tasks
     * @param ui the user interface for displaying outputs
     */
    @Override
    public void execute(TaskContainer tasks, Storage storage, Ui ui) {
        Event event = new Event(taskDescription, from, to);
        tasks.add(event);
        ui.showOutput("Got it. I've added this task:", event.toString(),
                "Now you have " + tasks.size() + " tasks in the list.");

        try {
            storage.save(tasks, ui);
        } catch (WriteStorageException e) {
            ui.showError(e.getMessage());
        }
    }
}
