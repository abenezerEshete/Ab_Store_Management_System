package controller;

import Utility.AlertClass;
import com.jfoenix.controls.*;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Main;
import model.User;
import org.hibernate.Session;
import org.json.JSONObject;

import java.util.Optional;

public class EditUserController {

     @FXML
    private AnchorPane addProduct;
    @FXML
    private Label saveNotification;
    @FXML
    private JFXTextField name;
    @FXML
    private RequiredFieldValidator nameRequiredValidator;
    @FXML
    private JFXTextField username;
    @FXML
    private RequiredFieldValidator usernameValidator;
    @FXML
    private NumberValidator tinNumberValidator;
    @FXML
    private JFXCheckBox active;
    @FXML
    private JFXTextField phone;
    @FXML
    private NumberValidator phoneNumebrValidator;
    @FXML
    private JFXComboBox<String> type;
    @FXML
    private CheckBox resetcheckBox;
    @FXML
    private JFXPasswordField password;
    @FXML
    private RequiredFieldValidator passwordValidator;
    @FXML
    private JFXButton resetInput;
    User user;
    String passwordtext;
    JSONObject loginedUser = null;

    public EditUserController() {
    }


    @FXML
    private void initialize() {

        System.out.println("in Edit Profile controle");

        name.getValidators().add(nameRequiredValidator);
        phone.getValidators().add(phoneNumebrValidator);
        password.getValidators().add(passwordValidator);
        username.getValidators().add(usernameValidator);

        type.getItems().addAll("Sales", "Finance", "Administrator");
        type.getSelectionModel().select(0);

        resetcheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {

            System.out.println("is changed===" + newValue);
            if (newValue == true) {
                password.setDisable(false);
                password.setEditable(true);
            } else
                password.setDisable(true);
        });
    }

    @FXML
    void deletInput(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Are You Sure?");
        alert.setHeaderText("Deleting Task");
        alert.setContentText("Are you ok with this?");
        alert.initOwner(name.getScene().getWindow());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Session session = Main.getUpdateSession();
            session.delete(user);
            session.getTransaction().commit();
            session.close();
            closeWindows(null);
            new AlertClass().Alert("Info", "Successfully Deleted",
                    Alert.AlertType.INFORMATION, "", name.getScene().getWindow());
        } else {
            // ... user chose CANCEL or closed the dialog
        }
        System.out.println("delete Input");
        saveNotification.setText("");
    }
    @FXML
    void closeWindows(ActionEvent event) {
        Stage stage = (Stage) name.getScene().getWindow();
        stage.close();
    }
    @FXML
    void saveData(ActionEvent event) {

        System.out.print("Save data");

        saveNotification.setText("");

        // validation
        if (!name.validate() || !phone.validate() || !username.validate())
            return;

        if (resetcheckBox.isSelected()) {
            if (!password.validate())
                return;
            passwordtext = password.getText();
        }

        user.setName(name.getText());
        user.setUsername(username.getText());
        user.setPassword(passwordtext);
        user.setPhone(phone.getText());
        user.setType(type.getSelectionModel().getSelectedItem());
        user.setActive(active.isSelected());
        Session session = Main.getUpdateSession();
        session.update(user);
        session.getTransaction().commit();
        session.close();

        Stage stage = (Stage) name.getScene().getWindow();
            stage.close();

            new AlertClass().Alert("Info", "Successfully Saved",
                    Alert.AlertType.INFORMATION, "", name.getScene().getWindow());

    }

    public void setUser(User user) {
        this.user = user;
        if (user != null) {
            this.name.setText(user.getName());
            this.username.setText(user.getUsername());
            this.active.setSelected(user.getActive());
            this.type.getSelectionModel().select(user.getType());
            this.passwordtext = user.getPassword();
            this.phone.setText(user.getPhone());
        }
    }
}