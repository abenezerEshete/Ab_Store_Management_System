package Utility;

import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Window;

public class AlertClass {

    public void Alert(String title, String header, Alert.AlertType type, String content, Window window) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setAlertType(type);
        alert.setHeaderText(header);
        alert.setTitle(title);

        alert.initModality(Modality.WINDOW_MODAL);
        alert.setContentText(content);
        alert.initOwner(window);

        alert.show();
    }
}
