package duke.ui.gui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {

    /**
     * Label to display the dialog text.
     */
    @FXML
    private Label dialog;

    /**
     * ImageView to display the speaker's image.
     */
    @FXML
    private ImageView displayPicture;

    /**
     * Constructs a DialogBox with the specified text and image.
     *
     * @param text the text to display in the dialog box
     * @param img  the image to display in the dialog box
     */
    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and the text is on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Sets the color of the dialog box.
     *
     * @param color the color to set
     */
    private void setColor(String color) {
        String existingStyle = dialog.getStyle();
        dialog.setStyle(String.format("%s -fx-background-color: %s;", existingStyle, color));
    }

    /**
     * Creates a dialog box for user input.
     *
     * @param text the user's input text
     * @param img  the user's profile image
     * @return a DialogBox containing the user's dialog
     */
    public static DialogBox getUserDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.setColor("#D6F6D5");
        return db;
    }

    /**
     * Creates a dialog box for Duke's response, with the layout flipped.
     *
     * @param text Duke's response text
     * @param img  Duke's profile image
     * @return a flipped DialogBox containing Duke's dialog
     */
    public static DialogBox getDukeDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        db.setColor("#E8F0FE");
        return db;
    }

    /**
     * Create a dialog box for an error message, with the layout flipped.
     *
     * @param text The message to display
     * @param img  The image to display
     * @return a flipped DialogBox containing Error message
     */
    public static DialogBox getErrorDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        db.setColor("#FFEDED");
        return db;
    }
}

