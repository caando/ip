package duke.command;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import duke.exception.DukeException;
import duke.task.Event;
import duke.task.TaskList;

public class AddEventCommand implements Command {

    private final String taskDescription;
    private final LocalDate from;
    private final LocalDate to;

    public AddEventCommand(String taskDescription, LocalDate from, LocalDate to) {
        this.taskDescription = taskDescription;
        this.from = from;
        this.to = to;
    }

    public static Command parse(String[] parts) {
        String[] eventParts = parts[1].split("/from", 2);
        if (eventParts.length < 2) {
            return new InvalidCommand(new DukeException("     OOPS!!! The duration of the event is invalid."));
        }
        String[] toParts = eventParts[1].split("/to", 2);
        if (toParts.length < 2) {
            return new InvalidCommand(new DukeException("     OOPS!!! The duration of the event is invalid."));
        }
        try {
            LocalDate from = LocalDate.parse(toParts[0].trim());
            LocalDate to = LocalDate.parse(toParts[1].trim());
            return new AddEventCommand(eventParts[0].trim(), from, to);
        } catch (DateTimeParseException e) {
            return new InvalidCommand(new DukeException(String.format(
                    "     OOPS!!! Unable to parse [%s] or [%s] to date", toParts[0] , toParts[1])));
        }
    }

    @Override
    public void execute(TaskList taskList) {
        Event event = new Event(taskDescription, from, to);
        taskList.add(event); // Add to the task list
        System.out.println("     Got it. I've added this task:");
        System.out.println("       " + event);
        System.out.println("     Now you have " + taskList.size() + " tasks in the list.");
        System.out.println("    ____________________________________________________________");
    }
}
