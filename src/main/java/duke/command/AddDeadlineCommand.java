package duke.command;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import duke.Utils;
import duke.exception.ParseCommandException;
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

    private final String taskDescription;
    private final LocalDate date;

    /**
     * Creates an {@code AddDeadlineCommand} with the specified task description and date.
     *
     * @param taskDescription the description of the task
     * @param date the deadline date for the task
     */
    public AddDeadlineCommand(String taskDescription, LocalDate date) {
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
        String regex = "deadline\\s+(.+)\\s+/by\\s+(.+)";
        Pattern pattern = Pattern.compile(regex);
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
     * @param taskList the task container to which the task is added
     * @param storage the storage used for persisting tasks
     * @param ui the user interface for displaying outputs
     */
    @Override
    public void execute(TaskContainer taskList, Storage storage, Ui ui) {
        Deadline deadline = new Deadline(taskDescription, date);
        taskList.add(deadline);
        ui.showOutput("Got it. I've added this task:", deadline.toString(),
                "Now you have " + taskList.size() + " tasks in the list.");
    }
}
