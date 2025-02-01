package duke.task;
import java.util.ArrayList;
import java.util.List;

public class TaskList implements TaskContainer{

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
    public Task get(int index) {
        return tasks.get(index);
    }

    @Override
    public void remove(int index) {
        tasks.remove(index);
    }

    @Override
    public int size() {
        return tasks.size();
    }

}
