package duke.command;

import duke.exception.DukeException;
import duke.task.Task;
import duke.task.TaskList;

public class DeleteCommand implements Command {

    private int index;

    // Constructor to initialize index
    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList taskList) {
        // Validate index
        if (index < 0 || index >= taskList.size()) {
            DukeException error = new DukeException(
                    "     OOPS!!! Invalid task number. Please provide a valid task index.");
            System.out.println(error.getMessage());
            System.out.println("    ____________________________________________________________");
            return;
        }

        // Get the task to be removed
        Task task = taskList.get(index);
        taskList.remove(index); // Remove the task from the list

        // Output the result
        System.out.println("     Noted. I've removed this task:");
        System.out.println("       " + task);
        System.out.println("     Now you have " + taskList.size() + " tasks in the list.");
        System.out.println("    ____________________________________________________________");
    }

    // Static parse method to create DeleteCommand from input
    public static Command parse(String[] parts) {
        if (parts.length < 2) {
            return new InvalidCommand(new DukeException(
                    "     OOPS!!! The 'delete' command requires a task number."));
        }

        try {
            int index = Integer.parseInt(parts[1]) - 1; // Convert to 0-based index
            return new DeleteCommand(index);
        } catch (NumberFormatException e) {
            return new InvalidCommand(new DukeException(
                    "     OOPS!!! Invalid task number. Please provide a valid task index."));
        }
    }
}
