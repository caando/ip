package duke.task;

public interface TaskContainer {

    @FunctionalInterface
    public interface TaskConsumer {
        void accept(int index, Task task);
    }

    public void add(Task task);

    public void list(TaskConsumer consumer);

    public Task get(int index);

    public void remove(int index);

    public int size();
}
