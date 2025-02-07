package duke.ui.gui;

import java.io.IOException;

import duke.Duke;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The entry point for the GUI version of the Duke application.
 * <p>
 * This class initializes the primary JavaFX application, loads the FXML layout,
 * and sets up communication between the Duke backend and the GUI components.
 * </p>
 */
public class Main extends Application {

    /**
     * Starts the JavaFX application by setting up the main stage and scene,
     * loading the FXML layout, and injecting dependencies.
     *
     * @param stage the primary stage for the application
     */
    @Override
    public void start(Stage stage) {
        try {
            // Load FXML file for the GUI layout
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();

            // Create and set the scene on the primary stage
            Scene scene = new Scene(ap);
            stage.setScene(scene);

            // Set up the adapter for GUI communication and initialize the Duke instance
            GuiAdaptor guiAdaptor = new GuiAdaptor(fxmlLoader.<MainWindow>getController(), () -> stage.close());
            Duke duke = new Duke(guiAdaptor);

            // Inject input consumer to handle user interactions
            fxmlLoader.<MainWindow>getController().setInputConsumer((String userInput) -> duke.process(userInput));

            // Show the main GUI window
            stage.show();

        } catch (IOException e) {
            // Handle any I/O errors when loading the FXML
            e.printStackTrace();
        }
    }
}
