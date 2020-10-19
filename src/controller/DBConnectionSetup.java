package controller;

import Utility.DbConnection;
import Utility.FileAccessControl;
import Utility.HardCodedValue;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class DBConnectionSetup {


    String DB_CONNECTION_IP = HardCodedValue.DB_CONNECTION_IP;
    String USERCATCHFILE    = HardCodedValue.USERCATCHFILE;
    Stage  stage            = null;
    @FXML
    private Text                   bugText;
    @FXML
    private JFXTextField           Ip_address;
    @FXML
    private JFXButton              testButton;
    @FXML
    private JFXButton              saveButton;
    @FXML
    private RequiredFieldValidator ipAddressValidator;
    @FXML
    private VBox                   depthVBox;
    @FXML
    private AnchorPane             headerPane;
    private boolean                inputValidated;

    @FXML
    private void initialize() {

        JFXDepthManager.setDepth(depthVBox, 10);
        JFXDepthManager.setDepth(headerPane, 10);


        Ip_address.getValidators().add(ipAddressValidator);
        saveButton.setDisable(true);

        Ip_address.setOnKeyPressed(k -> {
            if (k.getCode().equals(KeyCode.ENTER)) {
                try {

                    testConnection();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        testButton.setOnAction(event -> {
            try {

                testConnection();

            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        saveButton.setOnAction(event -> {
            try {

                saveConnection();

            } catch (Exception e) {
                e.printStackTrace();
            }

        });


    }

    private void saveConnection() {
        String DB_CONNECTION_IP = new HardCodedValue().DB_CONNECTION_IP;
        try {

            System.out.println("file path=" + DB_CONNECTION_IP);

            FileAccessControl fileAccessControl = new FileAccessControl();
            fileAccessControl.writetoFile(DB_CONNECTION_IP, Ip_address.getText());

            Scene scene = Ip_address.getScene();
            scene.setRoot(FXMLLoader.load(getClass().getResource("Login.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
            bugText.setText(e.getMessage());
        }


    }


    @FXML
    private void testConnection() throws IOException {


        try {
            DbConnection isConnected = new DbConnection(Ip_address);
            String result = isConnected.testConnection(Ip_address.getText());

            bugText.setText("Connection Status: " + result);
            if (result.equalsIgnoreCase("connected"))
                saveButton.setDisable(false);

        } catch (Exception e) {
            e.printStackTrace();
            bugText.setText("error in Database connection building " + e.getMessage());
        }


    }


}
