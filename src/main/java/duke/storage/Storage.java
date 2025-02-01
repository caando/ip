package duke.storage;

import duke.exception.ReadStorageException;
import duke.exception.WriteStorageException;
import duke.task.TaskContainer;
import duke.ui.Ui;

public interface Storage {

    public void save(TaskContainer taskList, Ui ui) throws WriteStorageException;

    public void load(TaskContainer taskContainer, Ui ui) throws ReadStorageException;
}
