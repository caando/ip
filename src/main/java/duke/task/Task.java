package duke.task;

import duke.exception.InvalidStatusIconException;
import duke.exception.ParseTaskException;

public abstract class Task {

    public enum Type {
        D, // Deadline
        E, // Event
        T // Todo
    }

    public static final String DONE_ICON = "X";
    public static final String NOT_DONE_ICON = "_";

    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? DONE_ICON : NOT_DONE_ICON);
    }

    public void markAsDone() {
        isDone = true;
    }

    public void markAsNotDone() {
        isDone = false;
    }

    protected void markFromStatusIcon(String statusIcon) throws InvalidStatusIconException {
        switch (statusIcon) {
        case NOT_DONE_ICON -> markAsNotDone();
        case DONE_ICON -> markAsNotDone();
        default -> throw new InvalidStatusIconException(
                String.format("Invalid status icon [%s]", statusIcon), statusIcon);
        }
    }

    public abstract String toPsvString();

    public static Task fromPsvString(String input) throws ParseTaskException {
        String[] parts = input.split("\\|", 2);

        Task.Type taskType;
        try {
            taskType = Type.valueOf(parts[0].trim());
        } catch (IllegalArgumentException e) {
            throw new ParseTaskException(String.format(
                    "Unable to parse task type from [%s]", input));
        }

        return switch (taskType) {
        case D -> Deadline.fromPsvString(input);
        case E -> Event.fromPsvString(input);
        case T -> Todo.fromPsvString(input);
        };
    }

    public abstract String getTaskIcon();

    @Override
    public abstract String toString();
}
