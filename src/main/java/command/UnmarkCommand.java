package command;

import exception.DukeException;
import task.Task;
import task.TaskList;

public class UnmarkCommand implements Command {

    private final int taskIndex;

    private UnmarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    public static Command parse(String[] parts) {
        try {
            String[] args = parts[1].split(" "); // Split the command argument
            int taskIndex = Integer.parseInt(args[0].trim());
            return new UnmarkCommand(taskIndex);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return new InvalidCommand(new DukeException("     OOPS!!! Invalid task number. Please provide a valid task index."));
        }
    }

    @Override
    public void execute(TaskList taskList) {
        if (taskIndex >= 1 && taskIndex <= taskList.size()) {
            Task task = taskList.get(taskIndex - 1);
            task.markAsNotDone();
            System.out.println("     OK, I've marked this task as not done yet:");
            System.out.println("       " + task);
            System.out.println("    ____________________________________________________________");
        } else {
            DukeException error = new DukeException("     OOPS!!! Invalid task number. Please provide a valid task index.");
            System.out.println(error.getMessage());
            System.out.println("    ____________________________________________________________");
        }
    }
}
