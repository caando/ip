package task;

public class Deadline extends Task {

    private final String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String getTaskIcon() {
        return "D";
    }

    @Override
    public String toPsvString() {
        return String.format("%s | %s | %s | %s", getTaskIcon(), getStatusIcon(), this.description, this.by);
    }

    @Override
    public String toString() {
        return String.format("[%s][%s] %s (by: %s)", getTaskIcon(), getStatusIcon(), description, by);
    }
}

