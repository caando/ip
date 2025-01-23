package command;

import duke.DukeException;
import task.Event;
import task.TaskList;

public class AddEventCommand implements Command {

    private final String taskDescription;
    private final String from;
    private final String to;

    public AddEventCommand(String taskDescription, String from, String to) {
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
        return new AddEventCommand(eventParts[0].trim(), toParts[0].trim(), toParts[1].trim());
    }

    @Override
    public void execute(TaskList taskList) {
        Event event = new Event(taskDescription, from, to);
        taskList.add(event);  // Add to the task list
        System.out.println("    ____________________________________________________________");
        System.out.println("     Got it. I've added this task:");
        System.out.println("       " + event);
        System.out.println("     Now you have " + taskList.size() + " tasks in the list.");
        System.out.println("    ____________________________________________________________");
    }
}
