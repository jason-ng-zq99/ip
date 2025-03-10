package duke.controllers;

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
import javafx.scene.text.Font;

/**
 * Encapsulates a DialogBox for Duke's GUI.
 * A custom control using FXML.
 * This control represents a dialog box consisting of an ImageView to represent the speaker's face and a label
 * containing text from the speaker.
 *
 * @author Jason Ng
 * @version Duke Level-10
 */
public class DialogBox extends HBox {
    /** The label for the dialog to come */
    @FXML
    private Label dialog;
    /** The ImageView for the picture to be displayed in the dialog */
    @FXML
    private ImageView displayPicture;
    /** The font to use for the DialogBox */
    private Font defaultFont = new Font("Arial", 15);

    /**
     * Constructor for a DialogBox.
     *
     * @param text The text to set for the dialog box.
     * @param img The image to set for the dialog box.
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
        dialog.setFont(defaultFont);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Receives the user input for the dialog box.
     * Processes input into a dialog box to be shown on the GUI.
     *
     * @param text The user input.
     * @param img The image of the user.
     * @return DialogBox that contains the user input and image.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Receives the system output for the dialog box.
     * Processes the output into a dialog box to be shown to the GUI.
     *
     * @param text The system output.
     * @param img The image of Duke.
     * @return DialogBox that contains the system output and image.
     */
    public static DialogBox getDukeDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        return db;
    }
}