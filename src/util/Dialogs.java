package util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Created by AYOUB on 4/3/2016.
 */
public class Dialogs {

    public static Optional<ButtonType> createConfirmationDialog(String title, String content, String header) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        setInformation(alert,header,title,content);
        return alert.showAndWait();
    }

    public static Optional<ButtonType> createErrorDialog(String title, String content, String header) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        setInformation(alert,header,title,content);
        return alert.showAndWait();
    }

    private static void setInformation(Alert alert,String header,String title,String content){
        alert.setTitle(title);
        alert.setContentText(content);
        alert.setHeaderText(header);
    }
}
