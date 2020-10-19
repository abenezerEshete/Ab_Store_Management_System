package controller;

import Utility.AlertClass;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Main;
import model.Broker;
import org.hibernate.Session;
import org.json.JSONObject;

public class AddBrokerController {


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
    private JFXTextField           phone;
    @FXML
    private NumberValidator        phoneNumebrValidator;
    @FXML
    private JFXTextField           address;
    @FXML
    private JFXTextField           tin;
    @FXML
    private NumberValidator        tinNumberValidator;
    @FXML
    private JFXButton              resetInput;
    @FXML
    private JFXButton              saveButton;

    @FXML
    private void initialize() {

        System.out.println("in Edit Profile controle");

        name.getValidators().add(nameRequiredValidator);
        phone.getValidators().add(phoneNumebrValidator);
        tin.getValidators().add(tinNumberValidator);
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
        address.setText("");
    }

    @FXML
    void saveData(ActionEvent event) {

        System.out.print("Save data");

        saveNotification.setText("");

        if (!name.validate() || !phone.validate() || !tin.validate())
            return;

        Broker broker = new Broker();
        broker.setName(name.getText());
        broker.setAddress(address.getText());
        broker.setPhone(phone.getText());
        broker.setTin(tin.getText());
        Session session = Main.getUpdateSession();
        session.save(broker);
        session.getTransaction().commit();
        session.close();

        new AlertClass().Alert("Info", "Successfully Saved",
                Alert.AlertType.INFORMATION, "", name.getScene().getWindow());

    }
}