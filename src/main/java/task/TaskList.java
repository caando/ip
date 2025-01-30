package task;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskList {

    private final List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public void list(TaskListConsumer consumer) {
        for (int i = 0; i < tasks.size(); i++) {
            consumer.accept(i + 1, tasks.get(i));
        }
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public void remove(int index) {
        tasks.remove(index);
    }

    public int size() {
        return tasks.size();
    }

    @FunctionalInterface
    public interface TaskListConsumer {
        void accept(int index, Task task);
    }

    public void writePsvString(java.io.Writer writer) {
        this.list((index, task) -> {
            try {
                writer.write(task.toPsvString());
                writer.write('\n');
            } catch (IOException e) {
                System.err.println("Error writing task to PSV: " + e.getMessage());
            }
        });
    }
}
