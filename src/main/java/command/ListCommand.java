package command;

import task.TaskList;

public class ListCommand implements Command {

    public static Command parse(String[] args) {
        return new ListCommand();
    }

    @Override
    public void execute(TaskList taskList) {
        taskList.list((index, task) -> {
            System.out.println("    " + index + ". " + task);
        });
        System.out.println("    ____________________________________________________________");
    }
}
