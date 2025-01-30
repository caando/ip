package task;

public class Event extends Task {

    private final String from;
    private final String to;

    public Event(String description, String from, String to) {
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
        return String.format("%s | %s | %s | %s | %s", getTaskIcon(), getStatusIcon(), this.description, this.from, this.to);
    }

    @Override
    public String toString() {
        return String.format("[%s][%s] %s (from: %s to: %s)", getTaskIcon(), getStatusIcon(), description, from, to);
    }
}


