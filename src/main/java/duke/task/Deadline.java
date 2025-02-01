package duke.task;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import duke.Utils;
import duke.exception.InvalidStatusIconException;
import duke.exception.ParseTaskException;

public class Deadline extends Task {

    private final LocalDate time;

    public Deadline(String description, LocalDate by) {
        super(description);
        this.time = by;
    }

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

    @Override
    public String getTaskIcon() {
        return Task.Type.D.name();
    }

    @Override
    public String toPsvString() {
        return String.format("%s | %s | %s | %s", getTaskIcon(), getStatusIcon(), this.description,
        Utils.dateToString(this.time));
    }

    @Override
    public String toString() {
        return String.format("[%s][%s] %s (by: %s)", getTaskIcon(), getStatusIcon(), this.description,
                Utils.dateToString(this.time));
    }
}

