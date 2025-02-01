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
import duke.task.TaskList;
import duke.ui.Ui;

public class FileStorage implements Storage {

    private final File file;
    
    public FileStorage(String filename) {
        file = new File(filename);
    }

    @Override
    public void save(TaskList taskList, Ui ui) throws WriteStorageException {
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            ArrayList<String> errors = new ArrayList<>();
            taskList.list((index, task) -> {
                try {
                    writer.write(task.toPsvString());
                    writer.write('\n');
                } catch (IOException e) {
                    errors.add(String.format("Error writing task to PSV [%s]", e.getMessage()));
                }
            });
            writer.flush();
            writer.close();
            ui.showError(errors);
        } catch (IOException e) {
            throw new WriteStorageException(String.format(
                    "Error writing tasklist to file [%s] " + e.getMessage()));
        }
    }

    @Override
    public TaskList load(Ui ui) throws ReadStorageException {
        TaskList taskList = new TaskList();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            ArrayList<String> errors = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                try {
                    Task task = Task.fromPsvString(line);
                    taskList.add(task);
                } catch (ParseTaskException e) {
                    errors.add(e.getMessage());
                }
            }
            ui.showError(errors);
        } catch (IOException e) {
            throw new ReadStorageException(String.format(
                    "Error reading tasklist from file [%s] " + e.getMessage()));
        }
        return taskList;
    }
}
