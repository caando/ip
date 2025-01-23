package command;

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
