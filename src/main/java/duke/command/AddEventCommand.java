package duke.command;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import duke.Utils;
import duke.exception.ParseCommandException;
import duke.storage.Storage;
import duke.task.Event;
import duke.task.TaskContainer;
import duke.ui.Ui;

public class AddEventCommand implements Command {

    private final String taskDescription;
    private final LocalDate from;
    private final LocalDate to;

    public AddEventCommand(String taskDescription, LocalDate from, LocalDate to) {
        this.taskDescription = taskDescription;
        this.from = from;
        this.to = to;
    }

    public static Command parse(String input) throws ParseCommandException {
        // Captures `event XXX \from YYY \to ZZZ`
        String regex = "event\\s+(.+)\\s+/from\\s+(.+)\\s+/to\\s+(.+)";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            String description = matcher.group(1).trim();
            String fromDateString = matcher.group(2).trim();
            String toDateString = matcher.group(3).trim();

            if (description.isEmpty()) {
                throw new ParseCommandException("Event command requires a description.");
            }

            if (fromDateString.isEmpty()) {
                throw new ParseCommandException("Event command requires [\from] argument.");
            }

            if (toDateString.isEmpty()) {
                throw new ParseCommandException("Event command requires [\to] argument.");
            }

            LocalDate fromDate;
            try {
                fromDate = Utils.parseDate(fromDateString);
            } catch (DateTimeParseException e) {
                throw new ParseCommandException(String.format("Unable to parse [%s] to date", fromDateString));
            }

            LocalDate toDate;
            try {
                toDate = Utils.parseDate(toDateString);
            } catch (DateTimeParseException e) {
                throw new ParseCommandException(String.format("Unable to parse [%s] to date", toDateString));
            }

            return new AddEventCommand(description, fromDate, toDate);
        } else {
            throw new ParseCommandException(String.format("Unable to parse [%s] to event command", input));

        }
    }

    @Override
    public void execute(TaskContainer taskList, Storage storage, Ui ui) {
        Event event = new Event(taskDescription, from, to);
        taskList.add(event);
        ui.showOutput("Got it. I've added this task:", event.toString(),
                "Now you have " + taskList.size() + " tasks in the list.");
    }
}
