package duke.ui.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import duke.ui.Ui;

/**
 * An adapter class that connects the GUI interface to Duke's backend logic.
 * <p>
 * This class implements the {@code Ui} interface and serves as a bridge between the backend
 * and the GUI, allowing Duke to display output, handle errors, and control GUI state transitions.
 * </p>
 */
public class GuiAdaptor implements Ui {

    private final MainWindow mainWindow;
    private final Runnable closeGui;

    /**
     * Constructs a {@code GuiAdaptor} with the specified {@code MainWindow} and a close operation.
     *
     * @param mainWindow the main GUI window
     * @param closeGui   a {@code Runnable} that closes the GUI
     */
    public GuiAdaptor(MainWindow mainWindow, Runnable closeGui) {
        this.mainWindow = mainWindow;
        this.closeGui = closeGui;
    }

    /**
     * Displays output messages in the GUI dialog box.
     *
     * @param lines a list of lines to display
     */
    @Override
    public void showOutput(List<String> lines) {
        mainWindow.showDukeMessage(lines.stream().collect(
                StringBuilder::new, (sb, line) -> sb.append(line).append("\n"), StringBuilder::append).toString());
    }

    /**
     * Displays output messages in the GUI dialog box.
     *
     * @param lines an array of strings to display
     */
    @Override
    public void showOutput(String... lines) {
        showOutput(new ArrayList<>(Arrays.asList(lines)));
    }

    /**
     * Displays error messages in the GUI dialog box with a prefix message.
     *
     * @param lines a list of error lines to display
     */
    @Override
    public void showError(List<String> lines) {
        mainWindow.showErrorMessage("OOPS!!!\n" + lines.stream().collect(
                StringBuilder::new, (sb, line) -> sb.append(line).append("\n"), StringBuilder::append).toString());
    }

    /**
     * Displays error messages in the GUI dialog box with a prefix message.
     *
     * @param lines an array of error lines to display
     */
    @Override
    public void showError(String... lines) {
        showError(new ArrayList<>(Arrays.asList(lines)));
    }

    /**
     * Initializes the GUI with a welcome message.
     */
    @Override
    public void start() {
        showOutput("Hello! I'm Mr Meeseeks", "What can I do for you?");
    }

    /**
     * Displays a farewell message and closes the GUI.
     */
    @Override
    public void close() {
        showOutput("Bye. Hope to see you again soon!");
        this.closeGui.run();
    }
}
