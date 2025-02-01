package duke.task;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import duke.Utils;
import duke.exception.InvalidStatusIconException;
import duke.exception.ParseTaskException;

public class Event extends Task {

    private final LocalDate from;
    private final LocalDate to;

    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public static Task fromPsvString(String input) throws ParseTaskException {
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

    @Override
    public String getTaskIcon() {
        return Task.Type.E.name();
    }

    @Override
    public String toPsvString() {
        return String.format("%s | %s | %s | %s | %s", getTaskIcon(), getStatusIcon(),
                this.description, Utils.dateToString(this.from), Utils.dateToString(this.to));
    }

    @Override
    public String toString() {
        return String.format("[%s][%s] %s (from: %s to: %s)", getTaskIcon(), getStatusIcon(),
                this.description, Utils.dateToString(this.from), Utils.dateToString(this.to));
    }
}


