package duke.command;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import duke.exception.DukeException;
import duke.task.Deadline;
import duke.task.TaskList;

public class AddDeadlineCommand implements Command {

    private final String taskDescription;
    private final LocalDate by;

    public AddDeadlineCommand(String taskDescription, LocalDate by) {
        this.taskDescription = taskDescription;
        this.by = by;
    }

    public static Command parse(String[] parts) {
        if (parts.length < 2) {
            return new InvalidCommand(new DukeException("     OOPS!!! The given deadline is invalid."));
        }
        String[] deadlineParts = parts[1].split("/by", 2);
        if (deadlineParts.length < 2) {
            return new InvalidCommand(new DukeException("     OOPS!!! The given deadline is invalid."));
        }
        try {
            LocalDate by = LocalDate.parse(deadlineParts[1].trim());
            return new AddDeadlineCommand(deadlineParts[0].trim(), by);
        } catch (DateTimeParseException e) {
            return new InvalidCommand(new DukeException(String.format(
                    "     OOPS!!! Unable to parse [%s] to date", deadlineParts[1])));
        }
    }

    @Override
    public void execute(TaskList taskList) {
        Deadline deadline = new Deadline(taskDescription, by);
        taskList.add(deadline); // Add to the task list
        System.out.println("     Got it. I've added this task:");
        System.out.println("       " + deadline);
        System.out.println("     Now you have " + taskList.size() + " tasks in the list.");
        System.out.println("    ____________________________________________________________");
    }
}
