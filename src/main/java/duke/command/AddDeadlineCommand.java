package duke.command;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import duke.Utils;
import duke.exception.ParseCommandException;
import duke.exception.WriteStorageException;
import duke.storage.Storage;
import duke.task.Deadline;
import duke.task.TaskContainer;
import duke.ui.Ui;

/**
 * Represents a command to add a deadline task.
 * This command is responsible for parsing the input,
 * creating a {@code Deadline} task, and adding it to the task container.
 */
public class AddDeadlineCommand implements Command {

    // Captures `deadline XXX /by YYY` where XXX and YYY are strings
    static final String COMMAND_REGEX = "deadline\\s+(.+)\\s+/by\\s+(.+)";

    private final String taskDescription;
    private final LocalDate date;

    /**
     * Creates an {@code AddDeadlineCommand} with the specified task description and date.
     *
     * @param taskDescription the description of the task
     * @param date the deadline date for the task
     */
    public AddDeadlineCommand(String taskDescription, LocalDate date) {
        assert taskDescription != null : "Task description must not be null";
        assert date != null : "Date must not be null";

        this.taskDescription = taskDescription;
        this.date = date;
    }

    /**
     * Parses the input string to create an {@code AddDeadlineCommand}.
     * The input must follow the format: {@code "deadline <description> /by <date>"}.
     *
     * @param input the user input string
     * @return the parsed {@code AddDeadlineCommand} instance
     * @throws ParseCommandException if the input is invalid or cannot be parsed
     */
    public static Command parse(String input) throws ParseCommandException {
        Pattern pattern = Pattern.compile(COMMAND_REGEX);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            String description = matcher.group(1).trim();
            String dateString = matcher.group(2).trim();

            if (description.isEmpty()) {
                throw new ParseCommandException("Deadline command requires a description.");
            }

            if (dateString.isEmpty()) {
                throw new ParseCommandException("Deadline command requires [/by] argument.");
            }

            try {
                LocalDate date = Utils.parseDate(dateString);
                return new AddDeadlineCommand(description, date);
            } catch (DateTimeParseException e) {
                throw new ParseCommandException(String.format(
                        "Unable to parse [%s] as date for deadline command.", dateString));
            }
        } else {
            throw new ParseCommandException(String.format("Unable to parse [%s] to deadline command.", input));
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
     * Returns the deadline date of the task.
     *
     * @return the deadline date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Executes the {@code AddDeadlineCommand} by creating a new {@code Deadline} task,
     * adding it to the task list, and displaying the result to the user.
     *
     * @param tasks the task container to which the task is added
     * @param storage the storage used for persisting tasks
     * @param ui the user interface for displaying outputs
     */
    @Override
    public void execute(TaskContainer tasks, Storage storage, Ui ui) {
        assert tasks != null : "Tasks must not be null";
        assert storage != null : "Storage must not be null";
        assert ui != null : "Ui must not be null";

        Deadline deadline = new Deadline(taskDescription, date);
        tasks.add(deadline);
        ui.showOutput("Got it. I've added this task:", deadline.toString(),
                "Now you have " + tasks.size() + " tasks in the list.");

        try {
            storage.save(tasks, ui);
        } catch (WriteStorageException e) {
            ui.showError(e.getMessage());
        }
    }
}
