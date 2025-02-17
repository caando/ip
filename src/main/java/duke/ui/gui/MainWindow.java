package duke.ui.gui;

import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


/**
 * Controller for the main GUI of the Duke application.
 * <p>
 * Manages the layout and interactions for the chat interface, including displaying
 * user and Duke messages in dialog boxes, handling user input, and setting up initial properties.
 * </p>
 */
public class MainWindow extends AnchorPane {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox dialogContainer;

    @FXML
    private TextField userInput;

    @FXML
    private Button sendButton;

    private Consumer<String> inputConsumer;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private final Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/Meeseeks.png"));

    /**
     * Initializes the MainWindow by binding the scroll pane's vertical scroll value
     * to the height of the dialog container, ensuring it scrolls to the latest message.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Displays the user's message as a dialog box in the GUI.
     *
     * @param output the message text to display
     */
    public void showUserMessage(String output) {
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(output, userImage)
        );
    }

    /**
     * Displays Duke's response as a dialog box in the GUI.
     *
     * @param output the message text to display
     */
    public void showDukeMessage(String output) {
        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog(output, dukeImage)
        );
    }

    /**
     * Sets the input consumer responsible for processing user input.
     *
     * @param inputConsumer a {@code Consumer<String>} to handle user input
     */
    public void setInputConsumer(Consumer<String> inputConsumer) {
        this.inputConsumer = inputConsumer;
    }
}
