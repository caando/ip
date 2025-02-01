package duke.command;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import duke.Utils;
import duke.exception.ParseCommandException;
import duke.storage.Storage;
import duke.task.Deadline;
import duke.task.TaskContainer;
import duke.ui.Ui;

public class AddDeadlineCommand implements Command {

    private final String taskDescription;
    private final LocalDate date;

    public AddDeadlineCommand(String taskDescription, LocalDate date) {
        this.taskDescription = taskDescription;
        this.date = date;
    }

    public static Command parse(String input) throws ParseCommandException {
        // Captures `deadline XXX \by YYY`
        String regex = "deadline\\s+(.+)\\s+/by\\s+(.+)";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            String description = matcher.group(1).trim();
            String dateString = matcher.group(2).trim();

            if (description.isEmpty()) {
                throw new ParseCommandException("Deadline command requires a description.");
            }

            if (dateString.isEmpty()) {
                throw new ParseCommandException("Deadline command requires [\by] argument.");
            }

            try {
                LocalDate date = Utils.parseDate(dateString);
                return new AddDeadlineCommand(description, date);
            } catch (DateTimeParseException e) {
                throw new ParseCommandException(String.format(
                        "Unable to parse [%s] as date for deadline command.", dateString));
            }
        } else {
            throw new ParseCommandException(String.format("Unable to parse [%s] to deadline command", input));
        }
    }

    @Override
    public void execute(TaskContainer taskList, Storage storage, Ui ui) {
        Deadline deadline = new Deadline(taskDescription, date);
        taskList.add(deadline);
        ui.showOutput("Got it. I've added this task:", deadline.toString(),
                "Now you have " + taskList.size() + " tasks in the list.");
    }
}
