package controller;

import Utility.AlertClass;
import com.jfoenix.controls.*;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Main;
import model.User;
import org.hibernate.Session;
import org.json.JSONObject;

public class AddUserController {


    JSONObject loginedUser = null;
    @FXML
    private AnchorPane             addProduct;
    @FXML
    private Label                  saveNotification;
    @FXML
    private JFXTextField           name;
    @FXML
    private RequiredFieldValidator nameRequiredValidator;
    @FXML
    private RequiredFieldValidator passwordValidator;
    @FXML
    private JFXTextField           username;
    @FXML
    private RequiredFieldValidator usernameValidator;
    @FXML
    private NumberValidator        tinNumberValidator;
    @FXML
    private JFXCheckBox            active;
    @FXML
    private JFXComboBox<String>    type;
    @FXML
    private JFXPasswordField       password;
    @FXML
    private JFXTextField           phone;
    @FXML
    private NumberValidator        phoneNumebrValidator;
    @FXML
    private JFXButton              cancel;
    @FXML
    private JFXButton              saveButton;

    @FXML
    private void initialize() {

        System.out.println("in Edit Profile controle");

        name.getValidators().add(nameRequiredValidator);
        phone.getValidators().add(phoneNumebrValidator);
        password.getValidators().add(tinNumberValidator);
        username.getValidators().add(usernameValidator);

        type.getItems().addAll("Sales", "Finance", "Administrator");
        type.getSelectionModel().select(0);
    }

    @FXML
    void cancelCloseWindow(ActionEvent event) {
        Stage stage = (Stage) name.getScene().getWindow();
        stage.close();
    }


    @FXML
    void deletInput(ActionEvent event) {
        System.out.println("delete Input");
        saveNotification.setText("");
        name.setText("");
        phone.setText("");
        username.setText("");
        password.setText("");
        active.setSelected(false);
        type.getSelectionModel().select(0);
    }

    @FXML
    void saveData(ActionEvent event) {

        System.out.print("Save data");

        saveNotification.setText("");

        if (!name.validate() || !phone.validate() || !username.validate())
            return;

        User user = new User();
        user.setName(name.getText());
        user.setUsername(username.getText());
        user.setPassword(password.getText());
        user.setPhone(phone.getText());
        user.setType(type.getSelectionModel().getSelectedItem());
        user.setActive(active.isSelected());
        Session session = Main.getUpdateSession();
        session.save(user);
        session.getTransaction().commit();
        session.close();
        new AlertClass().Alert("Info", "Successfully Saved",
                Alert.AlertType.INFORMATION, "", name.getScene().getWindow());

    }
}