package controller;

import Utility.AlertClass;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Main;
import model.Customer;
import org.hibernate.Session;

import java.util.Optional;

public class EditCustomerController {


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
    Customer customer;

    @FXML
    private void initialize() {

        System.out.println("in Edit Profile controle");

        name.getValidators().add(nameRequiredValidator);
        phone.getValidators().add(phoneNumebrValidator);
        tin.getValidators().add(tinNumberValidator);
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
            session.delete(customer);
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

        if (!name.validate() || !phone.validate() || !tin.validate())
            return;

        customer.setName(name.getText());
        customer.setAddress(address.getText());
        customer.setPhone(phone.getText());
        customer.setTin(tin.getText());
        Session session = Main.getUpdateSession();
        session.update(customer);
        session.getTransaction().commit();
        session.close();

        Stage stage = (Stage) name.getScene().getWindow();
        stage.close();

        new AlertClass().Alert("Info", "Successfully Updated",
                Alert.AlertType.INFORMATION, "", name.getScene().getWindow());

    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setPhone(String phone) {
        this.phone.setText(phone);
    }

    public void setAddress(String address) {
        this.address.setText(address);
    }

    public void setTin(String tin) {
        this.tin.setText(tin);
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}