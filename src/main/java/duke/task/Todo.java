package duke.task;

import duke.exception.InvalidStatusIconException;
import duke.exception.ParseTaskException;

public class Todo extends Task {

    public Todo(String description) {
        super(description);
    }

    public static Task fromPsvString(String input) throws ParseTaskException {
        String[] parts = input.split("\\|", 4);
        String statusIconString = parts[1].trim();
        String description = parts[2].trim();

        Todo todo = new Todo(description);

        try {
            todo.markFromStatusIcon(statusIconString);
        } catch (InvalidStatusIconException e) {
            throw new ParseTaskException(String.format("Invalid status icon [%s] for todo task", statusIconString));
        }

        return todo;
    }

    @Override
    public String toPsvString() {
        return String.format("%s | %s | %s", getTaskIcon(), getStatusIcon(), this.description);
    }

    @Override
    public String getTaskIcon() {
        return Task.Type.T.name();
    }

    @Override
    public String toString() {
        return String.format("[%s][%s] %s", getTaskIcon(), getStatusIcon(), description);
    }
}

