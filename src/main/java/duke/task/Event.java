package duke.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
        String[] parts = input.split("\\|", 4);
        String statusIconString = parts[1].trim();
        String description = parts[2].trim();
        String fromTimeString = parts[3].trim();
        String toTimeString = parts[3].trim();

        LocalDate fromTime;
        try {
            fromTime = LocalDate.parse(fromTimeString);
        } catch (DateTimeParseException e) {
            throw new ParseTaskException(String.format(
                    "Unable to parse event time [%s]", fromTimeString));
        }

        LocalDate toTime;
        try {
            toTime = LocalDate.parse(fromTimeString);
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
                this.description, this.from.format(DateTimeFormatter.ofPattern("MMM d yyy")),
                        this.to.format(DateTimeFormatter.ofPattern("MMM d yyy")));
    }

    @Override
    public String toString() {
        return String.format("[%s][%s] %s (from: %s to: %s)", getTaskIcon(), getStatusIcon(), 
                this.description, this.from.format(DateTimeFormatter.ofPattern("MMM d yyy")),
                        this.to.format(DateTimeFormatter.ofPattern("MMM d yyy")));
    }
}


