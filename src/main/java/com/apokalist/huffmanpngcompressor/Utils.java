package com.apokalist.huffmanpngcompressor;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;


/**
 * Utility class providing helper methods for the application.
 * Contains methods for displaying styled alerts.
 */
public class Utils {
    private static final String FORM_BG = "#3f3f3c"; // Background color for alert dialogs
    private static final String TEXT_PRIMARY = "#a19e96"; // Text color for alert dialogs

    /**
     * Displays an error alert with a custom style
     *
     * @param title   The title of the alert dialog.
     * @param message The message to display in the alert dialog.
     */
    public static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle(String.format(
                "-fx-background-color: %s; -fx-text-fill: %s;",
                FORM_BG, TEXT_PRIMARY
        ));
        alert.showAndWait();
    }
}