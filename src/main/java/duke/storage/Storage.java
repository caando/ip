package duke.storage;

import duke.exception.ReadStorageException;
import duke.exception.WriteStorageException;
import duke.task.TaskList;
import duke.ui.Ui;

public interface Storage {

    public void save(TaskList taskList, Ui ui) throws WriteStorageException;

    public TaskList load(Ui ui) throws ReadStorageException;
}
