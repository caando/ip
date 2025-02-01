package duke.task;

import duke.exception.TaskNotFoundException;

public interface TaskContainer {

    @FunctionalInterface
    public interface TaskConsumer {
        void accept(int index, Task task);
    }

    public void add(Task task);

    public void list(TaskConsumer consumer);

    public Task get(int index) throws TaskNotFoundException;

    public Task remove(int index) throws TaskNotFoundException;

    public int size();
}
