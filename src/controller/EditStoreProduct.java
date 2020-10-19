package controller;

import Utility.AlertClass;
import Utility.AutoCompleteTextField;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.DoubleValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.Main;
import model.Purchase;
import model.StoreProduct;
import org.hibernate.Session;

import java.io.IOException;


public class EditStoreProduct {


    ObservableList<Purchase> purchasedProducts = FXCollections.observableArrayList();
    AutoCompleteTextField    nameField;
    StoreProduct             storeProduct      = null;
    @FXML
    private AnchorPane             addProduct;
    @FXML
    private HBox                   nameHbox;
    @FXML
    private JFXTextField           model;
    @FXML
    private JFXTextField           brand;
    @FXML
    private JFXTextField           grade;
    @FXML
    private JFXTextField           type;
    @FXML
    private JFXTextField           size;
    @FXML
    private JFXTextField           unitSellingPrice;
    @FXML
    private JFXTextField           amount;
    @FXML
    private RequiredFieldValidator aountRequierValidator;
    @FXML
    private DoubleValidator        amountNumberValidator;
    @FXML
    private JFXButton              cancelButton;
    @FXML
    private JFXButton              resetInput;
    @FXML
    private JFXButton              saveAndStayButton;
    @FXML
    private JFXButton              saveButton;
    @FXML
    private Label                  notification;
    private JFXButton              addedList;

    @FXML
    private void initialize() {


        nameField = new AutoCompleteTextField();
        nameField.setPrefWidth(250);
        nameField.setMinHeight(30);
        nameField.fontProperty().set(Font.font("System Bold", 14));
        nameField.styleProperty().set("-fx-background-color: #D6EAF8;");
        nameField.promptTextProperty().set("Search product here");
        nameField.labelFloatProperty().set(true);
        nameField.paddingProperty().set(new Insets(0, 0, 0, 0));
        nameField.alignmentProperty().set(Pos.BOTTOM_LEFT);
        nameHbox.getChildren().add(1, nameField);


        amount.getValidators().add(amountNumberValidator);
        amount.getValidators().add(aountRequierValidator);
        //     expDateField.setValue(new LocalDate().);


        nameField.setEditable(false);
        model.setEditable(false);
        size.setEditable(false);
        brand.setEditable(false);
        type.setEditable(false);
        grade.setEditable(false);
        unitSellingPrice.setEditable(false);
        fieldPropertySetup();
    }

    private void fieldPropertySetup() {
        unitSellingPrice.setText("0");
        amount.setText("0");
        amount.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue.isEmpty())
                    return;
                double newval = Double.parseDouble(newValue);
                if (newValue.contains("d") || newValue.contains("f") ||
                        newValue.contains("D") || newValue.contains("F")) {
                    amount.setText(oldValue);
                } else amount.setText(newValue);
            } catch (NumberFormatException e) {
                amount.setText(oldValue);
            } finally {

            }
        });
    }


    @FXML
    void closeWindows(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void saveData(ActionEvent event) throws IOException {

        if (!amount.validate())
            return;


        double amountText = Double.parseDouble(amount.getText());
        storeProduct.setAmount(amountText);
        Session session = Main.getUpdateSession();
        session.saveOrUpdate(storeProduct);
        session.getTransaction().commit();
        session.close();
        notification.setText("Sucessfully Saved");
        Stage stage = (Stage) amount.getScene().getWindow();
        stage.close();
        new AlertClass().Alert("Info", "successfully Updated",
                Alert.AlertType.INFORMATION, "", amount.getScene().getWindow());

    }

    public void setId(StoreProduct storeProduct) {
        this.storeProduct = storeProduct;
    }

    public void setNameField(String nameField) {
        this.nameField.setText(nameField);
    }

    public void setModel(String model) {
        this.model.setText(model);
    }

    public void setBrand(String brand) {
        this.brand.setText(brand);
    }

    public void setGrade(String grade) {
        this.grade.setText(grade);
    }

    public void setType(String type) {
        this.type.setText(type);
    }

    public void setSize(String size) {
        this.size.setText(size);
    }

    public void setUnitSellingPrice(String unitSellingPrice) {
        this.unitSellingPrice.setText(unitSellingPrice);
    }

    public void setAmount(String amount) {
        this.amount.setText(amount);
    }
}