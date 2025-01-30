package task;

public abstract class Task {

    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markAsDone() {
        isDone = true;
    }

    public void markAsNotDone() {
        isDone = false;
    }

    public String toPsvString() {
        return String.format("%s | %s | %s", getTaskIcon(), getStatusIcon(), this.description);
    }

    public abstract String getTaskIcon();

    @Override
    public abstract String toString();
}
