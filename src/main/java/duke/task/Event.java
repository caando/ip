package duke.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {

    private final LocalDate from;
    private final LocalDate to;

    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String getTaskIcon() {
        return "E";
    }

    @Override
    public String toPsvString() {
        return String.format("%s | %s | %s | %s | %s", getTaskIcon(), getStatusIcon(), this.description,
                this.from.format(DateTimeFormatter.ofPattern("MMM d yyy")),
                        this.to.format(DateTimeFormatter.ofPattern("MMM d yyy")));
    }

    @Override
    public String toString() {
        return String.format("[%s][%s] %s (from: %s to: %s)", getTaskIcon(), getStatusIcon(), this.description,
                this.from.format(DateTimeFormatter.ofPattern("MMM d yyy")),
                        this.to.format(DateTimeFormatter.ofPattern("MMM d yyy")));
    }
}


