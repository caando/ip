package duke.storage;

import duke.exception.ReadStorageException;
import duke.exception.WriteStorageException;
import duke.task.TaskContainer;
import duke.ui.Ui;

/**
 * Represents the contract for any storage system used to persist task data.
 * This interface defines methods for saving and loading tasks from a storage medium.
 * Implementations of this interface are responsible for handling the persistence of tasks
 * in a way that can be retrieved and updated.
 */
public interface Storage {

    /**
     * Saves the current list of tasks to a storage medium.
     *
     * @param taskList the container of tasks to be saved
     * @param ui the user interface to display any errors encountered during the saving process
     * @throws WriteStorageException if there is an error during the write process to the storage medium
     */
    public void save(TaskContainer taskList, Ui ui) throws WriteStorageException;

    /**
     * Loads the list of tasks from a storage medium into the task container.
     *
     * @param taskContainer the container where tasks will be loaded
     * @param ui the user interface to display any errors encountered during the loading process
     * @throws ReadStorageException if there is an error during the reading process from the storage medium
     */
    public void load(TaskContainer taskContainer, Ui ui) throws ReadStorageException;
}
