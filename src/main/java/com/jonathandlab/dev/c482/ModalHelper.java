package com.jonathandlab.dev.c482;

import javafx.scene.control.Alert;

/**
 * @author Jonathan Dowdell
 */
public class ModalHelper {

    /**
     *
     * @param alertType Type of Alert
     * @param text Alert Header
     * @return Alert object for further use
     */
    public static Alert displayAlert(Alert.AlertType alertType, String text) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(text);
        alert.showAndWait();
        return alert;
    }

    /**
     *
     * @param alertType Type of Alert
     * @param title Alert Title
     * @param header Alert Header
     * @param content Alert Content
     * @return Alert object for further use
     */
    public static Alert displayAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert nullAlert = new Alert(alertType);
        nullAlert.setTitle(title);
        nullAlert.setHeaderText(header);
        nullAlert.setContentText(content);
        nullAlert.showAndWait();
        return nullAlert;
    }
}
