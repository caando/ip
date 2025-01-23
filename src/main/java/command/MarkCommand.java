package command;

import task.Task;
import task.TaskList;

public class MarkCommand implements Command {

    private final int taskIndex;

    private MarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    public static Command parse(String[] parts) {
        try {
            String[] args = parts[1].split(" "); // Split the command argument
            int taskIndex = Integer.parseInt(args[0].trim());
            return new MarkCommand(taskIndex);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid task number. Please provide a valid task index.");
            return null; // Handle invalid input
        }
    }

    @Override
    public void execute(TaskList taskList) {
        if (taskIndex >= 1 && taskIndex <= taskList.size()) {
            Task task = taskList.get(taskIndex - 1);
            task.markAsDone();
            System.out.println("     Nice! I've marked this task as done:");
            System.out.println("       " + task);
            System.out.println("    ____________________________________________________________");
        } else {
            System.out.println("    Invalid task index.");
            System.out.println("    ____________________________________________________________");
        }
    }
}
