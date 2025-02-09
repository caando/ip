package duke.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import duke.exception.ParseTaskException;
import duke.exception.ReadStorageException;
import duke.exception.WriteStorageException;
import duke.task.Task;
import duke.task.TaskContainer;
import duke.ui.Ui;

/**
 * A storage implementation that handles saving and loading tasks to/from a file.
 * The tasks are stored in a file in the PSV (Pipe-Separated Values) format.
 * This class is responsible for reading from and writing to the storage file.
 */
public class FileStorage implements Storage {

    private final File file;

    /**
     * Constructs a {@code FileStorage} object with the specified file name.
     * The file will be used to save and load the task list.
     *
     * @param filename the name of the file where tasks will be stored
     */
    public FileStorage(String filename) {
        file = new File(filename);
    }

    /**
     * Saves the tasks in the given task container to the storage file.
     * If the file does not exist, it will be created along with its parent directories.
     *
     * Each task is written to the file in PSV format (Pipe-Separated Values).
     * Any errors encountered while writing the tasks are collected and displayed to the user.
     *
     * @param tasks the task container containing the tasks to be saved
     * @param ui the user interface to display any error messages
     * @throws WriteStorageException if there is an error writing to the storage file
     */
    @Override
    public void save(TaskContainer tasks, Ui ui) throws WriteStorageException {
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            ArrayList<String> errors = new ArrayList<>();
            for (Task task : tasks) {
                try {
                    writer.write(task.toPsvString());
                    writer.write('\n');
                } catch (IOException e) {
                    errors.add(String.format("Error writing task to PSV [%s]", e.getMessage()));
                }
            }
            writer.flush();
            writer.close();
            if (!errors.isEmpty()) {
                ui.showError(errors);
            }
        } catch (IOException e) {
            throw new WriteStorageException(String.format(
                    "Error writing tasklist to file [%s] " + e.getMessage()));
        }
    }

    /**
     * Loads the tasks from the storage file into the given task container.
     * If any errors are encountered while parsing the tasks, they are displayed to the user.
     *
     * @param taskContainer the container where tasks will be added
     * @param ui the user interface to display any error messages
     * @throws ReadStorageException if there is an error reading from the storage file
     */
    @Override
    public void load(TaskContainer taskContainer, Ui ui) throws ReadStorageException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            ArrayList<String> errors = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                try {
                    Task task = Task.fromPsvString(line);
                    taskContainer.add(task);
                } catch (ParseTaskException e) {
                    errors.add(e.getMessage());
                }
            }
            if (!errors.isEmpty()) {
                ui.showError(errors);
            }
        } catch (IOException e) {
            throw new ReadStorageException(String.format(
                    "Error reading tasklist from file [%s] ", e.getMessage()));
        }
    }
}
