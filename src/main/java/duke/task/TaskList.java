package duke.task;
import java.util.ArrayList;
import java.util.List;

import duke.exception.TaskNotFoundException;

public class TaskList implements TaskContainer {

    private final List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    @Override
    public void add(Task task) {
        tasks.add(task);
    }

    @Override
    public void list(TaskConsumer consumer) {
        for (int i = 0; i < tasks.size(); i++) {
            consumer.accept(i, tasks.get(i));
        }
    }

    @Override
    public Task get(int index) throws TaskNotFoundException {
        if (index < 0 || index >= tasks.size()) {
            throw new TaskNotFoundException(String.format(
                    "Index [%d] out of range [%d]", index, tasks.size()));
        }
        return tasks.get(index);
    }

    @Override
    public Task remove(int index) throws TaskNotFoundException {
        if (index < 0 || index >= tasks.size()) {
            throw new TaskNotFoundException(String.format(
                    "Index [%d] out of range [%d]", index, tasks.size()));
        }
        return tasks.remove(index);
    }

    @Override
    public int size() {
        return tasks.size();
    }

}
