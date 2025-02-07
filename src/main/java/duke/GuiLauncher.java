package duke;

import duke.ui.gui.Main;
import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues.
 */
public class GuiLauncher {

    /**
     * Launches the GUI version of Duke.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}

