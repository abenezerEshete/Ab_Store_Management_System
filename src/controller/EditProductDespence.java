package controller;

import Utility.AlertClass;
import Utility.AutoCompleteTextField;
import Utility.FileAccessControl;
import Utility.HardCodedValue;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.DoubleValidator;
import com.jfoenix.validation.RequiredFieldValidator;
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
import model.DespenceProduct;
import org.hibernate.Session;
import org.json.JSONObject;

import java.io.IOException;


public class EditProductDespence {

    AutoCompleteTextField nameField;
    DespenceProduct       despenceProduct = null;
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
        JSONObject loginedUser = new JSONObject(new FileAccessControl().readfromFileStringBuffer(new HardCodedValue().USERCATCHFILE).toString());
        System.out.println("type==" + loginedUser.optString("type"));
        if (!loginedUser.optString("type").isEmpty()) {

            if (loginedUser.optString("type").equalsIgnoreCase("Sales"))
                saveButton.setVisible(false);
        }
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
        despenceProduct.setAmount(amountText);
        Session session = Main.getUpdateSession();
        session.saveOrUpdate(despenceProduct);
        session.getTransaction().commit();
        session.close();
        notification.setText("Sucessfully Saved");
        Stage stage = (Stage) amount.getScene().getWindow();
        stage.close();
        new AlertClass().Alert("Info", "successfully Updated",
                Alert.AlertType.INFORMATION, "", amount.getScene().getWindow());



    }

    public void setId(DespenceProduct despenceProduct) {
        this.despenceProduct = despenceProduct;
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