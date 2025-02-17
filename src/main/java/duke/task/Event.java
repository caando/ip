package duke.task;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import duke.Utils;
import duke.exception.InvalidStatusIconException;
import duke.exception.ParseTaskException;

/**
 * Represents an Event task in the task management system.
 * An Event task has a description, a start time, and an end time.
 * The task can be parsed from a string representation in PSV (Pipe-Separated Values) format
 * and can also be converted to such a format.
 */
public class Event extends Task {

    private final LocalDate from;
    private final LocalDate to;

    /**
     * Constructs a new Event task with the specified description, start time, and end time.
     *
     * @param description The description of the event.
     * @param from The start time of the event.
     * @param to The end time of the event.
     */
    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Creates an Event task from a PSV string.
     * <p>
     * The PSV string is expected to have the format: "TaskType | StatusIcon | Description | FromTime | ToTime".
     *
     * @param input The PSV string representing the event task.
     * @return An Event task created from the provided PSV string.
     * @throws ParseTaskException If the input string is not in the expected format or contains invalid data.
     */
    public static Task fromPsvString(String input) throws ParseTaskException {
        assert input != null : "input must not be null";
        assert input.startsWith(input) : "Event PSV string must start with 'E'";

        String[] parts = input.split("\\|", 5);

        if (parts.length != 5) {
            throw new ParseTaskException(String.format(
                    "Event PSV string [%s] have invalid number of columns", input));
        }

        String statusIconString = parts[1].trim();
        String description = parts[2].trim();
        String fromTimeString = parts[3].trim();
        String toTimeString = parts[4].trim();

        LocalDate fromTime;
        try {
            fromTime = Utils.parseDate(fromTimeString);
        } catch (DateTimeParseException e) {
            throw new ParseTaskException(String.format(
                    "Unable to parse event time [%s]", fromTimeString));
        }

        LocalDate toTime;
        try {
            toTime = Utils.parseDate(fromTimeString);
        } catch (DateTimeParseException e) {
            throw new ParseTaskException(String.format(
                    "Unable to parse event time [%s]", toTimeString));
        }

        Event event = new Event(description, fromTime, toTime);

        try {
            event.markFromStatusIcon(statusIconString);
        } catch (InvalidStatusIconException e) {
            throw new ParseTaskException(String.format(
                    "Invalid status icon [%s] for event task", statusIconString));
        }

        return event;
    }

    /**
     * Returns the icon representing the task type.
     * <p>
     * For Event tasks, the icon is always "E".
     *
     * @return The task type icon as a string.
     */
    @Override
    public String getTaskIcon() {
        return Task.Type.E.name();
    }

    /**
     * Converts this Event task to a PSV string.
     * <p>
     * The format of the PSV string is: "TaskType | StatusIcon | Description | FromTime | ToTime".
     *
     * @return A PSV string representing this Event task.
     */
    @Override
    public String toPsvString() {
        return String.format("%s | %s | %s | %s | %s", getTaskIcon(), getStatusIcon(),
                this.description, Utils.dateToString(this.from), Utils.dateToString(this.to));
    }

    /**
     * Returns a string representation of the Event task.
     * <p>
     * The string representation includes the task type icon, the status icon, the description,
     * the start time, and the end time of the event.
     *
     * @return A string representation of the Event task.
     */
    @Override
    public String toString() {
        return String.format("[%s][%s] %s (from: %s to: %s)", getTaskIcon(), getStatusIcon(),
                this.description, Utils.dateToString(this.from), Utils.dateToString(this.to));
    }
}
