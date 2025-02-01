package duke.task;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import duke.Utils;
import duke.exception.InvalidStatusIconException;
import duke.exception.ParseTaskException;

/**
 * Represents a Deadline task in the task management system.
 * A Deadline task has a description and a specific deadline time.
 * The task can be parsed from a string representation in PSV (Pipe-Separated Values) format
 * and can also be converted to such a format.
 */
public class Deadline extends Task {

    private final LocalDate time;

    /**
     * Constructs a new Deadline task with the specified description and deadline time.
     *
     * @param description The description of the deadline task.
     * @param by The deadline time for the task.
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        this.time = by;
    }

    /**
     * Creates a Deadline task from a PSV string.
     * The PSV string is expected to have the format: "TaskType | StatusIcon | Description | Time".
     *
     * @param input The PSV string representing the deadline task.
     * @return A Deadline task created from the provided PSV string.
     * @throws ParseTaskException If the input string is not in the expected format or contains invalid data.
     */
    public static Task fromPsvString(String input) throws ParseTaskException {
        String[] parts = input.split("\\|", 4);

        if (parts.length != 4) {
            throw new ParseTaskException(String.format(
                    "Deadline PSV string [%s] have invalid number of columns", input));
        }

        String statusIconString = parts[1].trim();
        String description = parts[2].trim();
        String timeString = parts[3].trim();

        LocalDate time;

        try {
            time = Utils.parseDate(timeString);
        } catch (DateTimeParseException e) {
            throw new ParseTaskException(String.format("Unable to parse deadline time [%s]", timeString));
        }

        Deadline deadline = new Deadline(description, time);

        try {
            deadline.markFromStatusIcon(statusIconString);
        } catch (InvalidStatusIconException e) {
            throw new ParseTaskException(String.format(
                    "Invalid status icon [%s] for deadline task", statusIconString));
        }

        return deadline;
    }

    /**
     * Returns the icon representing the task type.
     * For Deadline tasks, the icon is always "D".
     *
     * @return The task type icon as a string.
     */
    @Override
    public String getTaskIcon() {
        return Task.Type.D.name();
    }

    /**
     * Converts this Deadline task to a PSV string.
     * The format of the PSV string is: "TaskType | StatusIcon | Description | Time".
     *
     * @return A PSV string representing this Deadline task.
     */
    @Override
    public String toPsvString() {
        return String.format("%s | %s | %s | %s", getTaskIcon(), getStatusIcon(), this.description,
        Utils.dateToString(this.time));
    }

    /**
     * Returns a string representation of the Deadline task.
     * The string representation includes the task type icon, the status icon, the description,
     * and the deadline time.
     *
     * @return A string representation of the Deadline task.
     */
    @Override
    public String toString() {
        return String.format("[%s][%s] %s (by: %s)", getTaskIcon(), getStatusIcon(), this.description,
                Utils.dateToString(this.time));
    }
}
